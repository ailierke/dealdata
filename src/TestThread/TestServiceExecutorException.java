package TestThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestServiceExecutorException {
	/**
	 * ExecutorService接口特别重要，因为它提供了关闭线程池的方法，并确保清理了不再使用的资源。
	 * exec.shutdown();使用这个不停的检测
	 * 
	 */
	  private static final Integer POOLNUMBER=2;//线程池中线程执行者的个数
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
		*请求关闭、发生超时或者当前线程中断，无论哪一个首先发生之后，都将导致阻塞，直到所有任务完成执行。
    	*	参数：
        *	timeout - 最长等待时间
        *	unit - timeout 参数的时间单位 
    	* 抛出：InterruptedException - 如果等待时发生中断
		 */
		try {
			/**
			 * 由于上面使用了sleep就发生了中断,所以会在这个地方抛出异常
			 */
			//设置线程池的超时时间，如果业务处理到时见未处理完就关闭
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
