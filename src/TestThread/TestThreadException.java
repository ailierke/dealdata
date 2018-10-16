package TestThread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TestThreadException {
	/**
	 * 使用new Thread().start()的形式，在run方法中调用的时候出现异常后,线程会自动死掉回收
	 */
	 
	 public static void main(String[] args) {
		for(int i=0;i<5;i++){
//			Runnable task = new Runnable(){
//				public void run() {
//					
//					for(int j=0;j<5;j++){
//						System.out.println(Thread.currentThread().getName()+"   "+j);
//						String[] a = new String[1];
//						System.out.println(a[1]);
//					}
//				};
//			};
			new Thread(){
				public void run() {
					System.out.println(Thread.currentThread().getName()+"   ");
					String[] a = new String[1];
					System.out.println(a[1]);
				};
			}.start();
		}
	}
}
