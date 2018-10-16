package quartz;

public class Six {
	/*
	 * JobStore是负责跟踪调度器中所有的工作数据：作业任务、触发器、日历等。
	 * 为你的Quartz调度器实例选择一个适当的JobStore是非常重要的一步。幸运的是，
	 * 一旦你理解了这些JobStore之间的区别，选择它们是非常容易的事。
	 * 你可以在配置文件（或是类对象）中定义调度器使用哪种JobStore，
	 * 这个JobStore将会提供给SchedulerFactory，用来创建你的调度器实例。
	 * 不要在你的代码中直接使用JobStore实例，因为一些原因许多开发者尝试这样做。JobStore是给Quartz在幕后使用的。
	 * 你只需要通过配置信息告知Quartz该用哪个JobStore，然后在你的代码里只需要使用调度器接口即可。
	 * 
	 * 1、RAMJobStore
	 * RAMJobStore是最容易使用的JobStore，它也是最高效的（从CPU时间计算）。从RAMJobStore的名字可以明显地发现：它将所有数据存储在RAM中。
	 * 
	 * 
	 * 2、JDBCJobStore
	 * org.quartz.impl.jdbcjobstore.JobStoreTX
	 * 	如果你不需要把调度命令（例如添加和移除触发器）和其他事务捆绑在一起，那么你就让Quartz使用JobStoreTx作为JobStore管理事务
	 * org.quartz.impl.jdbcjobstore.JobStoreCMT
	 * 如果你需要Quartz关联其他事务（例如在J2EE应用服务器中），然后你应该使用JobStoreCMT——这种情况下Quartz会让应用服务容器管理事务。
	 */
}
