## 第9周作业（必做）改造自定义 RPC 的程序，提交到 GitHub：
#### 尝试将服务端写死查找接口实现类变成泛型和反射；
```java
        // 作业1：改成泛型和反射
        try {
            Class<?> aClass = Class.forName(serviceClass); //"io.kimmking.rpcfx.demo.api.UserService"
            Object o = aClass.newInstance();
            Method method = resolveMethodFromClass(aClass, request.getMethod());
            Object result = method.invoke(o, request.getParams()); // dubbo, fastjson,
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
```
#### 尝试将客户端动态代理改成 AOP，添加异常处理；
```java
//TODO
```
#### 尝试使用 Netty+HTTP 作为 client 端传输方式。
###### 初始化netty client
```java
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
```
###### clent post调用
```java
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
```
###### clent 异步获得结果
```java
@Data
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private String respStr;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf bb = (ByteBuf)msg;
            byte[] respByte = new byte[bb.readableBytes()];
            bb.readBytes(respByte);
            setRespStr(new String(respByte));
            System.err.println("client--收到响应：" + respStr);
        } finally{
            // 必须释放msg数据
            ReferenceCountUtil.release(msg);
        }
    }
}
```
