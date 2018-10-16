package quartz;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
* Quartz API 关键的几个接口：
  Scheduler：跟任务调度相关的最主要的API接口。
  Job：你期望任务调度执行的组件定义（调度器执行的内容），都必须实现该接口。
  JobDetail：用来定义Job的实例。
  Trigger：定义一个指定的Job何时被执行的组件，也叫触发器。
  JobBuilder：用来定义或创建JobDetail的实例，JobDetail限定了只能是Job的实例。
  TriggerBuilder：用来定义或创建触发器的实例。
     调度器的生命周期，起始于SchedulerFactory的创建，终止于调用shutdown方法。当调度器接口实例创建完成后，
     就可以添加，删除和查询Jobs和Triggers对象，也可以执行其它的跟调度器相关的操作，比如中止触发器的触发。
     并且，调度器在调用start方法之前，不会触发任何一个触发器去执行作业任务，如第一课所示的例子。
  Quartz框架提供许多构造器类来定义一套领域特定语言，简称DSL，有时候也称为“流接口”。在上一课中我们看到的示例，现在重新展示一部分代码如下：
  
  
    import static org.quartz.JobBuilder.*;  
    import static org.quartz.SimpleScheduleBuilder.*;  
    import static org.quartz.CronScheduleBuilder.*;  
    import static org.quartz.CalendarIntervalScheduleBuilder.*;  
    import static org.quartz.TriggerBuilder.*;  
    import static org.quartz.DateBuilder.*;  
    
    功能各样的ScheduleBuilder类提供多种方法来定义不同类型的调度器。
  DateBuilder类包含了许多方法可以更简捷地构建Java.util.Date实例对象，尤其针对特定的时间点，
     比如下一个的整点时间，换言之现在是9:43:27，我需要得到10:00:00的时间对象。
 */
/*
 * 无状态的job	----Job
 * 
 * 有状态的Job----StatefulJob 对应以上两个注解
 * 
 * Job 和 StatefulJob 在框架中使用中存在两个关键差异。
 * 首先，JobDataMap 在每次执行之后重新持久化到 JobStore 中。
 * 这样就确保你对 Job 数据的改变直到下次执行仍然保持着。你可以在有状态 Job 
 * 中简单的通过 map 的 put() 方法来修改 JobDataMap.已存在的任何数据会被新的数据覆盖掉。
 * 你也能对无状态的 Job 这么做，但是因为对于无状态 Job 来说，JobDataMap 不会持久化，
 * 所以数据不会保存下来。对于 Trigger 和 JobExecutionContext 上的 JobDataMap 的数据修改也是没能保存下来的。 
 * 
 * 
 * 
 * JobDetail 被加到 Scheduler 中了，而不是 job。
 * Job 类是作为 JobDetail 的一部份，但是它直到 Scheduler 准备要执行它的时候才会被实例化的。
 * 
 * 
 * Job 的实例要到该执行它们的时候才会实例化出来。每次 Job 被执行，
 * 一个新的 Job 实例会被创建。其中暗含的意思就是你的 Job 不必担心线程安全性，
 * 因为同一时刻仅有一个线程去执行给定 Job 类的实例，甚至是并发执行同一 Job 也是如此。
 * 
 * 
 * 可以使用 JobExecutionContext 的一个新的更方便的方法获取到 Job 和 Trigger 级的并集的 map 中的值。这个方法就是 getMergedJobDataMap()
 * 
 * Quartz,每次执行job,job永远是全新的对象,但是，如果job实现org.quartz.StatefulJob接口,而不是job接口.
      此时JobDetail的JobDataMap将会共享一个对象。
      注意:
     当实现有状态接口,StatefulJob时,只有JobDetail的JobDataMap是共用的,其他的，比如,Job本身,Trigger等,仍然每次执行
     的时候是全新的对象。所以,只有JobDetail的JobDataMap是共用的，其他的trigger.getJobDataMap(),context.getMergedJobDataMap(),等这些JobDataMap，任然是全新的
     
     
     注意：
     在spring中，org.springframework.scheduling.quartz.SchedulerFactoryBean是对 Quartz
     的org.quartz.Scheduler的封装，通过上面的配置，在spring启动的时候，quartz就会跟随着启动，
     不需要再用 scheduler.start()来启动。在spring中，如果要取得scheduler，可通过上面的配置文件那样，
     将 SchedulerFactoryBean注入到schdeuler中。  
 */
@PersistJobDataAfterExecution//：保存在JobDataMap传递的参数,当你要一个计数器的时候
@DisallowConcurrentExecution//:保证多个任务间不会同时执行.所以在多任务执行时最好加上 
public class Two implements Job{
	String jobSays = null;
	//使用set直接获取jobDetail中JobDataMap中的key的值，不需要以下方式来获取
	/*
	 * JobDataMap dataMap = context.getMergedJobDataMap();  
	 * float myFloatValue = dataMap.getFloat("myFloatValue"); 
	 */
	public void setJobSays(String jobSays) {
		this.jobSays = jobSays;
	}
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
			JobKey key = context.getJobDetail().getKey();  
		  
//	      JobDataMap dataMap = context.getJobDetail().getJobDataMap();  
	      JobDataMap dataMap = context.getMergedJobDataMap();  
	  
	      float myFloatValue = dataMap.getFloat("myFloatValue")+1;  
	      dataMap.put("myFloatValue", myFloatValue);
	      System.err.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue); 
	}
	
	public static void main(String[] args) throws SchedulerException {
		 SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();  
	      
	      Scheduler sched = schedFact.getScheduler(); 
	    //只有当sched开始之后，任务才会想继执行
	      sched.start();
	      
		  JobDetail job =JobBuilder.newJob(Two.class)  
			      .withIdentity("myJob", "group1") // name "myJob", group "group1"  
			      .usingJobData("jobSays", "Hello World!")  
			      .usingJobData("myFloatValue", 3.141f)
			      .build();
		  Trigger trigger = TriggerBuilder.newTrigger()
				  .startAt(new Date(System.currentTimeMillis()+10000))//开始时间 
		          .withIdentity("myTrigger", "group1")  
//		          .startNow()
		          .endAt(new Date(System.currentTimeMillis()+30000))//结束时间
		          .withSchedule(SimpleScheduleBuilder.simpleSchedule()
		              .withIntervalInSeconds(5)
		              .repeatForever()) 
		          .build();  
		      // Tell quartz to schedule the job using our trigger  
		      sched.scheduleJob(job, trigger);  
	}
}



/*
 * 框架分析

    接口
    类图

Quartz中的设计模式

    Builder模式
        所有关键组件都有Builder模式来构建  <Builder> 如:JobBuilder、TriggerBuilder
    Factory模式
        最终由Scheduler的来进行组合各种组件  <Factory> 如SchedulerFactory
    Quartz项目中大量使用组件模式，插件式设计，可插拔，耦合性低，易扩展，开发者可自行定义自己的Job、Trigger等组件
    链式写法,Quartz中大量使用链式写法，与jQuery的写法有几分相似，实现也比较简单，如：
         $(this).addClass("divCurrColor").next(".divContent").css("display","block");  
        newTrigger().withIdentity( "trigger3", "group1").startAt( startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10)).build();

框架核心分析

    SchedulerFactory    -- 调度程序工厂
        StdSchedulerFactory   -- Quartz默认的SchedulerFactory
        DirectSchedulerFactory  --   DirectSchedulerFactory是对SchedulerFactory的直接实现,通过它可以直接构建Scheduler、threadpool 等
            ThreadExecutor / DefaultThreadExecutor   -- 内部线程操作对象
    JobExecutionContext -- JOB上下文,保存着Trigger、 JobDeaitl 等信息,JOB的execute方法传递的参数就是对象的实例
        JobExecutionContextImpl
    Scheduler    -- 调度器
        StdScheduler    -- Quartz默认的Scheduler
        RemoteScheduler  -- 带有RMI功能的Scheduler
    JOB --任务对象
        JobDetail  -- 他是实现轮询的一个的回调类，可将参数封装成JobDataMap对象,Quartz将任务的作业状态保存在JobDetail中.
        JobDataMap --  JobDataMap用来报错由JobDetail传递过来的任务实例对象
    Trigger
        SimpleTrigger <普通的Trigger> --  SimpleScheduleBuilder
        CronTrigger  <带Cron Like 表达式的Trigger> -- CronScheduleBuilder
        CalendarIntervalTrigger <带日期触发的Trigger> -- CalendarIntervalScheduleBuilder
        DailyTimeIntervalTrigger <按天触发的Trigger> -- DailyTimeIntervalScheduleBuilder
    ThreadPool  --  为Quartz运行任务时提供了一些线程
        SimpleThreadPool  --一个Quartz默认实现的简单线程池，它足够健壮，能够应对大部分常用场景
    -----以上是Quartz涉及到的一些关键对
    */
