package TestThread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TestExecutorException {
	/**
	 * 有这个实验可以看出使用Executor不能正常结束，他会一直等待.虽然线程中抛出异常后会有新的线程补上,但是就是不会结束
	 * Excutor没办法结束，JVM只有在所有守护线程结束后才能结束，所以设计出ExecutorService 里面提供了对线程池的生命周期
	 * TestServiceExecutorException.java做关闭的实验
	 */
	  private static final Integer POOLNUMBER=1;//线程池中线程执行者的个数
	 private static final Executor exec = Executors.newFixedThreadPool(POOLNUMBER);
	 
	 public static void main(String[] args) {
		for(int i=0;i<5;i++){
			Runnable task = new Runnable(){
				public void run() {
					
					for(int j=0;j<5;j++){
//						String[] a = new String[1];
//						System.out.println(a[1]);
						System.out.println(Thread.currentThread().getName()+"   "+j);
					}
				};
			};	
				exec.execute(task);
		}
	}
}
