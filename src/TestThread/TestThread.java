package TestThread;

public class TestThread {
	private Object lock = new Object();
	private Object lock1 = new Object();

	public void start(){
		new Put().start();
		new Get().start();
	}
	public static void main(String[] args){
		TestThread testThread = new TestThread();
		testThread.start();
	}
	//Put线程创建货物，搬运到指定位置，并通知Get线程去取货
	class Put extends Thread {
		@Override
		public void run() {
			synchronized (lock1.getClass()){
				for (int i = 0; i < 20; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("put:" + i);
				}
			}
		}
	}
	//Get线程收到通知后，取货，再通知Put线程搬运新的货物
	class Get extends Thread {
		@Override
		public void run() {
			synchronized (lock.getClass()){
				for (int i = 0; i < 20; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("get:" +i);
				}
			}
		}
	}
}
