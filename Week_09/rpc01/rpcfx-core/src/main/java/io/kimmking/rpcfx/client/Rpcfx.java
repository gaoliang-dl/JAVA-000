package io.kimmking.rpcfx.client;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class Rpcfx {

    private static Bootstrap bs;
    private static ClientHandler clientHandler;

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
        // init netty client
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        bs = new Bootstrap();
        clientHandler = new ClientHandler();

        bs.group(bossGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 处理来自服务端的响应信息
                        socketChannel.pipeline().addLast(clientHandler);
                    }
                });
    }

    public static <T> T create(final Class<T> serviceClass, final String url) {
        // 0. 替换动态代理 -> AOP
//        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url));
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(serviceClass);
        enhancer.setCallback(new AopMethodInterceptor(url));
        return (T) enhancer.create();
    }

    public static class AopMethodInterceptor implements MethodInterceptor {

        private final String url;

        public <T> AopMethodInterceptor(String url) {
            this.url = url;
        }

        private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.get("application/json; charset=utf-8"), JSON.toJSONString(req)))
                    .build();
            String respJson = client.newCall(request).execute().body().string();
            System.out.println("resp json: " + respJson);
            return JSON.parseObject(respJson, RpcfxResponse.class);
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(o.getClass().getName());
            request.setMethod(method.getName());
            request.setParams(objects);
            RpcfxResponse response = post(request, url);
            return JSON.parse(response.getResult().toString());
        }
    }

    public static class RpcfxInvocationHandler implements InvocationHandler {

        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

        private final Class<?> serviceClass;
        private final String url;

        public <T> RpcfxInvocationHandler(Class<T> serviceClass, String url) {
            this.serviceClass = serviceClass;
            this.url = url;
        }

        // 可以尝试，自己去写对象序列化，二进制还是文本的，，，rpcfx是xml自定义序列化、反序列化，json: code.google.com/p/rpcfx
        // int byte char float double long bool
        // [], data class

        @Override
        public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass.getName());
            request.setMethod(method.getName());
            request.setParams(params);

            RpcfxResponse response = post(request, url);

            // 这里判断response.status，处理异常
            // 考虑封装一个全局的RpcfxException

            return JSON.parse(response.getResult().toString());
        }

        private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
            String reqJson = JSON.toJSONString(req);
            System.out.println("req json: " + reqJson);

            // 2.尝试使用httpclient或者netty client
            // 客户端开启
            try {
                ChannelFuture cf = bs.connect("ip", 123).sync();
                cf.channel().writeAndFlush(Unpooled.copiedBuffer(reqJson.getBytes()));
                cf.channel().closeFuture().sync();
                String respJson = clientHandler.getRespStr();
                System.out.println("resp json: " + respJson);
                return JSON.parseObject(respJson, RpcfxResponse.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }

//            // 1.可以复用client
//            // 2.尝试使用httpclient或者netty client
//            OkHttpClient client = new OkHttpClient();
//            final Request request = new Request.Builder()
//                    .url(url)
//                    .post(RequestBody.create(JSONTYPE, reqJson))
//                    .build();
//            String respJson = client.newCall(request).execute().body().string();
//            System.out.println("resp json: " + respJson);
//            return JSON.parseObject(respJson, RpcfxResponse.class);
        }
    }
}
