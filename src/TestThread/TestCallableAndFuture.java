package TestThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
/**
 * Я�����������Callable���� Future
 * @author CC
 *
 *
 *���ʹ��
 */
public class TestCallableAndFuture {
	  private static final Integer POOLNUMBER=1;//�̳߳����߳�ִ���ߵĸ���
	 private static final ExecutorService exec = Executors.newFixedThreadPool(POOLNUMBER);
	 
	 public static void main(String[] args) throws InterruptedException {
		for(int i=0;i<5;i++){
			/**
			 * �����̳߳ز���  ��������Futrue.get()����������
			 */
			Callable<Boolean> callTask = new Callable<Boolean>(){
				public Boolean call() {
					/**
					 * ��������ִ�о����ҵ���߼�
					 */
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+"   "+"dosomething");
					return true;
				};
			};
			Future<Boolean> future = exec.submit(callTask);
			/**
			 * ���������ʹ��future.get()����������thread work�ĵ���.�������ڷ��أ����״̬����ֹ�ͻ��׳��쳣
			 * ���ע�͵�����Ļ��Ͳ�������
			 */
			try {
				boolean flag = true;
//				try {
//					flag = future.get(1,TimeUnit.SECONDS);//����get�����������Կ�����������ʱ�䡣�׳�TimeOutException
					flag = future.get();
//				} catch (TimeoutException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				System.out.println("�����̷߳��ص�flagֵ"+" "+flag);
			} catch (InterruptedException e) {
				//�����쳣�������߳�Ϊ�ն�״̬
				Thread.currentThread().interrupt();
				//Ȼ��ȡ������
				future.cancel(true);
			} catch (ExecutionException e) {
				throw new RuntimeException(e.getCause());
			}
		}
		System.out.println("#####################");
		System.out.println(exec.awaitTermination(1, TimeUnit.MILLISECONDS)); //�Ƿ���ֹ
			exec.shutdown();//ƽ���Ĺرգ����ڽ��������񣬲��ҵȴ��Ѿ��ύ���������
			System.out.println(exec.isShutdown());//�Ƿ�ر�
			System.out.println(exec.isTerminated());//�Ƿ���ֹ
	}
}
