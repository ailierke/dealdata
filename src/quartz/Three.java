package quartz;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
/**
 * <p>标题：Job状态和并发机制测试</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2017 diwinet</p>
 * <p>日期：2017年3月23日</p>
 * @author	jiangxing
 */
public class Three implements Job{
/*
 * job的生命周期
 * 
 * Job(1)-----(n)JobDetail
 * 	对象---实例
 * 
 *  例如：你创建一个Job接口的实现类，类名为“SalesReportJob”，
 *  Job类可以预先传入一些假想的参数（通过JobDataMap）来指定销售报表中业务员的名字。
 *  接下来创建多个Job实例的定义（即JobDetails），
 *  如“SalesReportForJoe”和“SalesReportForMike”
 *  通过“Joe”和“Mike”指定到相应的JobDataMaps中作为参数输入到各自的Job对象中。
 */
	
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
		  for(int i=0;i<5;i++){
			  try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		      JobDataMap dataMap = context.getJobDetail().getJobDataMap();  
		      JobDataMap dataMap = context.getMergedJobDataMap();  
		  
		      float myFloatValue = dataMap.getFloat("myFloatValue")+1;  
		      dataMap.put("myFloatValue", myFloatValue);
		      System.err.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue); 
		  }
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
		  JobDetail job1 =JobBuilder.newJob(Two.class)  
			      .withIdentity("myJob12", "group1") // name "myJob", group "group1"  
			      .usingJobData("jobSays", "Hello World111111111!")  
			      .usingJobData("myFloatValue", 2.141f)  
			      .build();
		  
		  Trigger trigger = TriggerBuilder.newTrigger()  
		          .withIdentity("myTrigger", "group1")  
		          .startNow()  
		          .withSchedule(SimpleScheduleBuilder.simpleSchedule() 
		              .withIntervalInSeconds(5)  
		              .repeatForever())  
		          .build();  
		  Trigger trigger1 = TriggerBuilder.newTrigger()  
		          .withIdentity("myTrigger1", "group2")  
		          .startNow()  
		          .withSchedule(SimpleScheduleBuilder.simpleSchedule() 
		              .withIntervalInSeconds(5)  
		              .repeatForever())  
		          .build();  
		      // Tell quartz to schedule the job using our trigger  
		      sched.scheduleJob(job, trigger);  
		      sched.scheduleJob(job1, trigger1);  
	}
}

/*
 * Job "Instances"

        许多用户对Job实例对象确切的结构是什么疑惑了很长时间，我们将尝试在这为大家解答，并且在下一个板块讲述Job状态和并发机制。

        你可以创建一个单独的Job实现类，创建多个不同的JobDetails实例，将不同Job实例定义存储在调度器中，每个JobDetails实例都有各自的参数和JobDataMap，
        并且把这些JobDetails添加到调度器中。

        例如：你创建一个Job接口的实现类，类名为“SalesReportJob”，Job类可以预先传入一些假想的参数（通过JobDataMap）来指定销售报表中业务员的名字。
        接下来创建多个Job实例的定义（即JobDetails），如“SalesReportForJoe”和“SalesReportForMike”通过“Joe”和“Mike”指定到相应的JobDataMaps
        中作为参数输入到各自的Job对象中。

        当触发器被触发时，相关的JobDetail实例会被加载，通过在调度器中配置的JobFactory会将关联的Job类实例化，默认的JobFactory只是在Job类中调用newInstance方法，
        然后尝试调用匹配JobDataMap键值的setter方法。你可以开发自己的JobFactory实现类通过应用IOC或DI机制完成Job实例的创建和初始化。

    用Quartz框架的话来说，我们将每个存储的JobDetail称为Job定义或JobDetail实例，将每个执行的作业任务（Job）称为Job实例或Job定义实例。
    通常我们只用“job”单词来对应命名的Job定义或是JobDetail。当我们指Job接口的实现类时，一般使用“job class”术语。

 
Job状态和并发机制

        现在介绍一些关于Job状态值和并发的额外信息。有一对加在Job类上面的注解，可以影响Quartz框架的这些方面的行为。

         @DisallowConcurrentExecution注解添加到Job类中，会告知Quartz不要并发执行相同Job定义创建的多个实例对象。注意这里的措辞，
         要慎重地选择。引用上一章节的例子，如果SalesReportJob添加这个注解，在给定的时间段内只能执行一个SalesReportJobForJoe实例对象，
         但是可以并发执行一个SalesReportJobForMike实例。然而，在Quartz设计阶段决定在该类中携带注解，因为该注解会影响JobDetail类的编码。

         @PersistJobDataAfterExecution注解添加到Job类中，会告知Quartz成功执行完execute方法后（有异常抛出的情况除外）
         更新JobDetail的JobDataMap中存储的数据。例如同一个JobDetail下一次执行时将接收更新的值而不是初始值。跟@DisallowConcurrentExecution注解类似，
         @PersistJobDataAfterExecution注解适用于Job定义实例，而不是Job类实例。只是该注解是附着在Job类的成员变量中，因为它不会影响整个类的编码
         （例如statefulness只需要在execute方法代码内正确使用即可）。

        如果你使用@PersistJobDataAfterExecution注解，强烈建议你也应该考虑使用@DisallowConcurrentExecution注解，
        为了避免当两个相同JobDetail实例并发执行时可能由于最后存储状态数据不一致导致执行混乱。

 
Jobs的其它属性

        接下来浏览Job实例的其它属性，这些属性是通过JobDetail对象传递给Job实例的。

        Durability-如果一个Job是非持久化的，一旦没有任何活跃的触发器关联这个Job实例时，这个实例会自动地从调度器中移除。换句话说，
        非持久化的jobs的生命周期是以存在的触发器为界限的。

        RequestsRecovery-如果一个Job设置了请求恢复参数，并且在调度器强制关闭过程中恰好在执行（强制关闭的情况例如：运行的线程崩溃，或者服务器宕机），
        当调度器重启时，它会重新被执行。这种情况下，JobExecutionContext的isRecovery方法会返回true。

 
JobExecutioinException

    最后，我们需要告知你Job.execute方法的一些细节。允许你从execute方法抛出的唯一一种异常类型是JobExecutionException（
    运行时异常除外，可以正常抛出），由于这个限制，你应该在execute方法内的try-catch代码块中包装好要处理的异常。
    你也可以花些时间查阅一下JobExecutionException的文档，便于你在开发的Job类中需要捕获处理异常时，为调度器提供各种信息。
 */