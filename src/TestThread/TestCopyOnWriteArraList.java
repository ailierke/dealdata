package TestThread;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class TestCopyOnWriteArraList {
	 transient final ReentrantLock lock = new ReentrantLock();
	public static void main(String[] args) {
		final TestCopyOnWriteArraList test = new TestCopyOnWriteArraList();
		new Thread(){
			@SuppressWarnings("unchecked")
			public void run() {
				test.lock.lock();
				for(int i=0;i<100;i++){
					System.out.println(Thread.currentThread().getName()+"  "+i);
				}
				test.lock.unlock();
			};
		}.start();
		new Thread(){
			@SuppressWarnings("unchecked")
			public void run() {
				test.lock.lock();
				for(int j=0;j<100;j++){
					System.out.println(Thread.currentThread().getName()+"  "+j);
				}
				test.lock.unlock();
			};
		}.start();
	}
	}

