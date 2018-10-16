package quartz;

import java.util.Date;

import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.DateBuilder;
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
import org.quartz.impl.calendar.HolidayCalendar;

/**
 * <p>标题：更多关于Triggers</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2017 diwinet</p>
 * <p>日期：2017年3月23日</p>
 * @author	jiangxing
 */
/*
 *   jobKey表示job实例的标识，触发器被触发时，该指定的job实例会执行。
 * 
 *   startTime属性表示触发器的时间表首次被触发的时间。它的值是定义由指定日历时间的Java.util.Date对象
 *   
 *    endTime属性指定触发器的不再被触发的时间
 *    Priority（优先级）
 *    有些时候，当你有许多触发器（或Quartz线程池中只有少数几个工作线程），Quartz可能没有足够的资源去触发所有的在同一时间段内排定好的触发器。
 *    既然这样，你可能期望控制哪个触发器能第一个获得Quartz空闲工作线程的调用。为了达到这个目的，你可以设定触发器的Priority属性。
 *    如果N个触发器在同一时间内被触发，但只有Z工作线程当前空闲可用，那么拥有最高优先级的Z触发器将会第一个被触发。如果你没有设置触发器的优先级，
 *    它将会使用默认的优先级，优先级值为5.任何Integer类型的值都可以作为优先级，正数负数都可以。
 * 
 * 提示：当一个触发器的作业任务发现设置了请求恢复参数，在恢复调度执行时的优先级和原来的一样。
 * 触发失败指令
 *   触发器另外一个重要的属性就是“misfireinstruction”。触发失败的情况是由于调度器被关闭导致存储的触发器错过了触发的时间，
 *   或是由于Quartz线程池内没有空闲的线程去执行作业任务。不同类型的触发器有不同的触发失败处理机制。默认情况下使用“智能策略”指令——基于触发器类型和配置的动态机制。
 *   当调度器启动时，它会查询所有存储的、触发失败的触发器，然后根据各自配置的触发失败指令更新触发器。当你开始在你的项目中使用Quartz时，
 *   你应该让自己熟悉在给定触发器类型上定义的触发失败指令和JavaDoc上的文档解释。
 *   更多关于触发失败指令的详细信息将会在教程里每个触发器类型课程中作详细介绍。
 * 
 */
public class Four implements Job{
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
	      /*
	       * 
	       * Calendars（日历）
	       * 当触发器在调度器中创建和存储时，Quartz日历对象（区别于java.util.Calendar对象）可以与触发器相关联。
	       * 日历对触发器调度定义不包含的时间段非常方便。例如，你可以创建一个触发器，定义在每个工作日上午9：30分触发作业任务，
	       * 另外添加一个日历表排除当中所有的假期。
	       * 可以查阅org.quartz.impl.calendar包目录下的几个Calendar实现类，估计有适合你需要的类。
	       */
	      Scheduler sched = schedFact.getScheduler(); 
	    //只有当sched开始之后，任务才会想继执行
	      sched.start();
	      
	      HolidayCalendar cal = new HolidayCalendar();  
	      cal.addExcludedDate(new Date());  
	      sched.addCalendar("excludDays", cal, false, false);//添加日历排除其中的时间
	      
		  JobDetail job =JobBuilder.newJob(Two.class)  
			      .withIdentity("myJob", "group1") // name "myJob", group "group1"  
			      .usingJobData("jobSays", "Hello World!")  
			      .usingJobData("myFloatValue", 3.141f)
			      .build();
		  Trigger trigger = TriggerBuilder.newTrigger()
				  .startAt(new Date(System.currentTimeMillis()+10000))//开始时间 
		          .withIdentity("myTrigger", "group1")
		          .startNow()
//		          .endAt(new Date(System.currentTimeMillis()+30000))//结束时间
		          .withSchedule(SimpleScheduleBuilder.simpleSchedule()
		              .withIntervalInSeconds(5)
		              .repeatForever())
//		          .modifiedByCalendar("excludDays")//添加排除日历类
		          .build();  
		      // Tell quartz to schedule the job using our trigger  
		      sched.scheduleJob(job, trigger);  
	}
}
