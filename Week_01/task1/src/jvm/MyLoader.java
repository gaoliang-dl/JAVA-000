package jvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyLoader extends ClassLoader {

    /**
     * 调用Hello.class的hello方法，输出”Hello, classLoader!“
     * @param args
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> hello = new MyLoader().findClass("Hello");
        Object obj = hello.newInstance();
        Method method = hello.getMethod("hello");
        method.invoke(obj);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            //读Hello.xlass文件到byte数组
            byte[] helloXlassBytes = getContent("Hello.xlass");
            /*
                将Hello.xlass解密为Hello.class
             */
            byte[] helloClassBytes = new byte[helloXlassBytes.length];
            for (int i = 0; i < helloXlassBytes.length; i++) {
                helloClassBytes[i] = (byte) (255 - helloXlassBytes[i]);
            }
            /*
                载入Hello.class
             */
            return defineClass(name, helloClassBytes, 0, helloClassBytes.length);
        } catch (IOException e) {
            e.printStackTrace();
            return super.findClass(name);
        }
    }

    /**
     * 读Hello.xlass文件到byte数组
     * @param filePath
     * @return
     * @throws IOException
     */
    public byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }

        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        fi.close();
        return buffer;
    }
}
