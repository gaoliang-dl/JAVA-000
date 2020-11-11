# 把多线程和并发相关知识带你梳理一遍，画一个脑图，截图上传到github上
![image](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_04/多线程和并发.svg)  
# 思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程？
[通过JUnit写了8个用例实现了8中方式](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_04/class4/src/test/java/gaol/practice/class4/AllStyleTests.java)  
## 第1种 join
AllStyleTests.style_1_runnable_join()  
## 第2种 sleep
AllStyleTests.style_2_runnable_sleep()  
## 第3种 callable
AllStyleTests.style_3_callable()  
## 第4种 future
AllStyleTests.style_4_future()  
## 第5种 loop
AllStyleTests.style_5_loop()  
## 第6种 lock
AllStyleTests.style_6_lock()  
## 第7种 semaphore
AllStyleTests.style_7_semaphore()  
## 第8种 synchronized
AllStyleTests.style_8_synchronized()  
