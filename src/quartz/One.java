package quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class One implements Job{
	/**
	 * 在你使用调度器之前，需要借助一些具体的例子去理解（谁愿意只是猜啊？
	 * 你可以使用SchedulerFactory类来达到程序调度的目的。
	 * 有一些Quartz框架的用户可能会将factory的实例存储在JNDI中，
	 * 其他人为了便于举例子就直接使用factory的实例（比如下文的代码片段）。
	 * 一旦调度器实例化后，它就能够启动，等待执行和关闭。需要注意的是一旦调度器调用了shutdown方法关闭后，
	 * 如果不重新实例化，它就不会启动了。触发器在调度器未启动时，或是中止状态时，都不会被触发。
     * 接下来浏览下面的代码片段，它实例化和启动了一个调度器，并且调度执行了一个Job对象。
     * 
     * 
     * jobKey   TrrigerKey作业任务和触发器的键（或标识符）的名字是由键名和组名共同组成的
	 * @throws SchedulerException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws SchedulerException, InterruptedException {
	    SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();  
	      Scheduler sched = schedFact.getScheduler();  
	     
	      
	      // define the job and tie it to our HelloJob class  
	      JobDetail job = JobBuilder.newJob(One.class)  
	          .withIdentity("myJob", "group1")
	          .build();
	      // Trigger the job to run now, and then every 40 seconds  
	      Trigger trigger = TriggerBuilder.newTrigger()  
	          .withIdentity("myTrigger", "group1")  
	          .startNow()  
	          .withSchedule(SimpleScheduleBuilder.simpleSchedule() 
	              .withIntervalInSeconds(5)  
	              .repeatForever()) 
	          .forJob(job)
	          .build();  
	      // Tell quartz to schedule the job using our trigger  
	      sched.scheduleJob(job, trigger);  
	       
	      Thread.sleep(5000);
	      //只有当sched开始之后，任务才会想继执行
	      sched.start();  
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("开始了。。。");
	}
}
