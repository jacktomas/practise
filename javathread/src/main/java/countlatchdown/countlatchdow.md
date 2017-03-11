##countdownlatch 文档介绍
 CountDownLatch类位于java.util.concurrent包下，利用它可以实现类似计数器的功能。比如有一个任务A，它要等待其他4个任务执行完毕之后才能执行，此时就可以利用CountDownLatch来实现这种功能了。

　CountDownLatch类只提供了一个构造器：
` public CountDownLatch(int count) {  };  //参数count为计数值 `
 
 然后下面这3个方法是CountDownLatch类中最重要的方法：
``` 
 public void await() throws InterruptedException { };   //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
 public boolean await(long timeout, TimeUnit unit) throws InterruptedException { };  //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
 public void countDown() { };  //将count值减1
```