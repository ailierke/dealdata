package TestThread;

public class TestThreadLocal1 {
	private  TestObject1 object = null;

	
	public TestObject1 getObject() {
		if(object ==null){
			object = new TestObject1("对象", 0);
			setObject(object);
		}
		return object;
	}


	public void setObject(TestObject1 object) {
		this.object = object;
	}


	public static void main(String[] args) {
		TestThreadLocal1 testThreadLocal = new TestThreadLocal1();
		MyThread1 thread1  = new MyThread1(testThreadLocal);
		thread1.start();
		MyThread2 thread2  =  new MyThread2(testThreadLocal);
		thread2.start();
		}

}
/**
 * 主要线程类
 * @author CC
 *
 */
class MyThread1 extends Thread{
	private TestThreadLocal1 testThreadLocal;

	public MyThread1(TestThreadLocal1 testThreadLocal) {
		super();
		this.testThreadLocal = testThreadLocal;
	}

	public TestThreadLocal1 getTestThreadLocal() {
		return testThreadLocal;
	}

	public void setTestThreadLocal(TestThreadLocal1 testThreadLocal) {
		this.testThreadLocal = testThreadLocal;
	}

	@Override
	public void run() {
		TestObject1 object  = null;
		for(int i=0;i<5;i++){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			object = testThreadLocal.getObject();
			System.out.println(Thread.currentThread().getName()+"   "+object.getAge());
			object.setAge(i);
			testThreadLocal.setObject(object);
		}
		
	}
}

class MyThread2 extends Thread{
	private TestThreadLocal1 testThreadLocal;

	public MyThread2(TestThreadLocal1 testThreadLocal) {
		super();
		this.testThreadLocal = testThreadLocal;
	}

	public TestThreadLocal1 getTestThreadLocal() {
		return testThreadLocal;
	}

	public void setTestThreadLocal(TestThreadLocal1 testThreadLocal) {
		this.testThreadLocal = testThreadLocal;
	}

	@Override
	public void run() {
		TestObject1 object  = null;
		for(int i=0;i<5;i++){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			object = testThreadLocal.getObject();
			System.out.println(Thread.currentThread().getName()+"   "+object.getAge());
			object.setAge(object.getAge()+5);
			testThreadLocal.setObject(object);
		}
		
	}
}
/**
 * 由ThreadLocal类提供对象副本
 * @author CC
 *
 */
class TestObject1 {
	public TestObject1(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}


