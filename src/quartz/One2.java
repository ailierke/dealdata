package quartz;

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

/*
 * Exception in thread "main" org.quartz.SchedulerException: Jobs added with no trigger must be durable.
 *   at org.quartz.core.QuartzScheduler.addJob(QuartzScheduler.java:916)
 *   at org.quartz.impl.StdScheduler.addJob(StdScheduler.java:269)
 *   at com.gary.operation.jobdemo.demo1.SimpleExample.run(SimpleExample.java:88)
 *   at com.gary.operation.jobdemo.demo1.SimpleExample.main(SimpleExample.java:119)
 * Exception 
 * Jobs added with no trigger must be durable.
 * 需要注意构建Job的时候必须设置.storeDurably() 在添加Job到调度引擎当中的时候会抛出异常。
 * 然后将Job与Trigger加入引擎即可
 * sched.addJob(job, true);
 * sched.scheduleJob(trigger);
 * sched.scheduleJob(trigger2);
 * ER关系
 * JOB(1)-----(n)trigger
 * 一个任务JOB可以添加多个Trigger 但是一个Trigger只能绑定一个JOB 这点需要注意
 * 
 * 解决方案 看下面注释
 */
public class One2 implements Job{
	public static void main(String[] args) throws SchedulerException, InterruptedException {
	    SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();  
	      Scheduler sched = schedFact.getScheduler();  
	     
	      
	      JobDetail job = JobBuilder.newJob(One2.class)  
	          .withIdentity("myJob", "group1")
	          .storeDurably() //持久到scheduler
	          .build();
	      Trigger trigger = TriggerBuilder.newTrigger()  
	          .withIdentity("myTrigger", "group1")  
	          .startNow() 
	          .withSchedule(SimpleScheduleBuilder.simpleSchedule() 
	              .withIntervalInSeconds(5)  
	              .repeatForever()) 
	          .forJob(job)//为trigger配置到指定job
	          .build();  
	      
	      // Tell quartz to schedule the job using our trigger  
//	      sched.scheduleJob(job, trigger);  
	      /*
	       * 使用直接对trigger进行开启job
	       */
	      sched.scheduleJob(trigger);  
	      Thread.sleep(5000);
	      //只有当sched开始之后，任务才会想继执行
	      sched.start();  
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("开始了。。。");
	}
}
