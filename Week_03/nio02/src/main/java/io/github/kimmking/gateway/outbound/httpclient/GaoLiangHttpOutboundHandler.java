package io.github.kimmking.gateway.outbound.httpclient;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class GaoLiangHttpOutboundHandler {

    private CloseableHttpClient httpClient;
    private String outboundUrl;

    public GaoLiangHttpOutboundHandler(String outboundUrl) {
        this.outboundUrl = outboundUrl;
        // 创建 HttpClient 客户端
        httpClient = HttpClients.createDefault();
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        final String url = this.outboundUrl + fullRequest.uri();
        get(url, fullRequest, ctx);
    }

    public void get(String url, final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        System.out.println("url: "+url);
        System.out.println("outboundUrl: "+outboundUrl);
        // 创建 HttpGet 请求
        HttpGet httpGet = new HttpGet(url);
        // 设置长连接
        httpGet.setHeader("Connection", "keep-alive");
        // 设置代理（模拟浏览器版本）
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");

        CloseableHttpResponse httpResponse = null;
        try {
            // 请求并获得响应结果
            httpResponse = httpClient.execute(httpGet);
//            HttpEntity httpEntity = httpResponse.getEntity();
            // 输出请求结果
//            System.out.println(EntityUtils.toString(httpEntity));


            handleResponse(fullRequest, ctx, httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 无论如何必须关闭连接
        finally {
//            if (httpResponse != null) {
//                try {
//                    httpResponse.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

//            if (httpClient != null) {
//                try {
//                    httpClient.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final HttpResponse endpointResponse) {
        FullHttpResponse response = null;
        try {
            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
//            response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));
            response.headers().setInt("Content-Length", body.length);

        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }

    }
}
