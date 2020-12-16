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
