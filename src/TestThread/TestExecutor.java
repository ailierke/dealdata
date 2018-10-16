package TestThread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TestExecutor {
	/**
	 * 如果这有一个线程然后 	exec.execute(task);  将task放入队列后   这一个线程就执行对队列里面的任务进行获取执行
	 * 
	 * 1、线程workers  对队列里面的任务进行获取执行
	 * 2、和传统的为每一个任务开启一个线程不同
	 * 
	 * 线程池的线程不会无限期创建，并且线程可以重复利用，任务的主要抽象不是线程thread而是Executor(thread works)
	 * 总结一点：线程池(thread pool)和线程队列(work queue)是密切相关的
	 */
	  private static final Integer POOLNUMBER=1;//线程池中线程执行者的个数
	 private static final Executor exec = Executors.newFixedThreadPool(POOLNUMBER);
	 
	 public static void main(String[] args) {
		for(int i=0;i<5;i++){
			Runnable task = new Runnable(){
				public void run() {
					
					for(int j=0;j<5;j++){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName()+"   "+j);
					}
				};
			};	
				exec.execute(task);
			
		}
	}
}
