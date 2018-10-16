package TestThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestServiceExecutorException {
	/**
	 * ExecutorService�ӿ��ر���Ҫ����Ϊ���ṩ�˹ر��̳߳صķ�������ȷ�������˲���ʹ�õ���Դ��
	 * exec.shutdown();ʹ�������ͣ�ļ��
	 * 
	 */
	  private static final Integer POOLNUMBER=2;//�̳߳����߳�ִ���ߵĸ���
	 private static final ExecutorService exec = Executors.newFixedThreadPool(POOLNUMBER);
	 
	 public static void main(String[] args) {
		for(int i=0;i<5;i++){
			Runnable task = new Runnable(){
				public void run() {
					
					for(int j=0;j<5;j++){
//						String[] a = new String[1];
//						System.out.println(a[1]);
						System.out.println(Thread.currentThread().getName()+"   "+j);
						try {
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				};
			};	
				exec.execute(task);
		}
		exec.shutdown();
		/**
		*����رա�������ʱ���ߵ�ǰ�߳��жϣ�������һ�����ȷ���֮�󣬶�������������ֱ�������������ִ�С�
    	*	������
        *	timeout - ��ȴ�ʱ��
        *	unit - timeout ������ʱ�䵥λ 
    	* �׳���InterruptedException - ����ȴ�ʱ�����ж�
		 */
		try {
			/**
			 * ��������ʹ����sleep�ͷ������ж�,���Ի�������ط��׳��쳣
			 */
			//�����̳߳صĳ�ʱʱ�䣬���ҵ����ʱ��δ������͹ر�
//		    if (!exec.awaitTermination(12, TimeUnit.SECONDS)) {
		    if (!exec.awaitTermination(20, TimeUnit.SECONDS)) {
		        // pool didn't terminate after the first try
		    	exec.shutdownNow();
		    }
		} catch (InterruptedException ex) {
			exec.shutdownNow();
		    Thread.currentThread().interrupt();
		}
	}
}
