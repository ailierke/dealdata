package TestThread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TestExecutor {
	/**
	 * �������һ���߳�Ȼ�� 	exec.execute(task);  ��task������к�   ��һ���߳̾�ִ�жԶ��������������л�ȡִ��
	 * 
	 * 1���߳�workers  �Զ��������������л�ȡִ��
	 * 2���ʹ�ͳ��Ϊÿһ��������һ���̲߳�ͬ
	 * 
	 * �̳߳ص��̲߳��������ڴ����������߳̿����ظ����ã��������Ҫ�������߳�thread����Executor(thread works)
	 * �ܽ�һ�㣺�̳߳�(thread pool)���̶߳���(work queue)��������ص�
	 */
	  private static final Integer POOLNUMBER=1;//�̳߳����߳�ִ���ߵĸ���
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
