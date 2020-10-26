# 使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例  
![image](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_02/gcLog.svg)  
  
# 使用压测工具（wrk或sb），演练gateway-server-0.0.1-SNAPSHOT.jar 示例  
![image](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_02/sb.svg)  

# [使用HttpClient访问http://localhost:8801](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_02/httpclient/src/main/java/gaol/practice/htttpclient/Main.java)  
```Java
    private static void get(String url) {
        // 创建 HttpClient 客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();

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
            HttpEntity httpEntity = httpResponse.getEntity();
            // 输出请求结果
            System.out.println(EntityUtils.toString(httpEntity));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 无论如何必须关闭连接
        finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
```
