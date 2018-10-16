package TestThread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TestExecutorException {
	/**
	 * �����ʵ����Կ���ʹ��Executor������������������һֱ�ȴ�.��Ȼ�߳����׳��쳣������µ��̲߳���,���Ǿ��ǲ������
	 * Excutorû�취������JVMֻ���������ػ��߳̽�������ܽ�����������Ƴ�ExecutorService �����ṩ�˶��̳߳ص���������
	 * TestServiceExecutorException.java���رյ�ʵ��
	 */
	  private static final Integer POOLNUMBER=1;//�̳߳����߳�ִ���ߵĸ���
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
