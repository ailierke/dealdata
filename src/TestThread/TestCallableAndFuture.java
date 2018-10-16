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
 * 携带结果的任务Callable返回 Future
 * @author CC
 *
 *
 *如果使用
 */
public class TestCallableAndFuture {
	  private static final Integer POOLNUMBER=1;//线程池中线程执行者的个数
	 private static final ExecutorService exec = Executors.newFixedThreadPool(POOLNUMBER);
	 
	 public static void main(String[] args) throws InterruptedException {
		for(int i=0;i<5;i++){
			/**
			 * 交给线程池并且  加上阻塞Futrue.get()方法的阻塞
			 */
			Callable<Boolean> callTask = new Callable<Boolean>(){
				public Boolean call() {
					/**
					 * 这里面面执行具体的业务逻辑
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
			 * 这下面如果使用future.get()会阻塞上面thread work的调用.如果完成在返回，如果状态已终止就会抛出异常
			 * 如果注释掉下面的话就不会阻塞
			 */
			try {
				boolean flag = true;
//				try {
//					flag = future.get(1,TimeUnit.SECONDS);//由于get会阻塞，所以可以设置阻塞时间。抛出TimeOutException
					flag = future.get();
//				} catch (TimeoutException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				System.out.println("阻塞线程返回的flag值"+" "+flag);
			} catch (InterruptedException e) {
				//捕获异常，设置线程为终端状态
				Thread.currentThread().interrupt();
				//然后取消任务
				future.cancel(true);
			} catch (ExecutionException e) {
				throw new RuntimeException(e.getCause());
			}
		}
		System.out.println("#####################");
		System.out.println(exec.awaitTermination(1, TimeUnit.MILLISECONDS)); //是否终止
			exec.shutdown();//平缓的关闭，不在接受新任务，并且等待已经提交的任务完成
			System.out.println(exec.isShutdown());//是否关闭
			System.out.println(exec.isTerminated());//是否终止
	}
}
