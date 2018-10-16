package TestThread;
/**
 * ������TestThread1�γɶԱ�
 * @author CC
 *
 */
public class TestThreadLocal {
	private static ThreadLocal<TestObject> holder = new ThreadLocal<TestObject>();

	public static TestObject getTestObject(){
		if(holder.get()==null){
			TestObject object = new TestObject("����",0);
			holder.set(object);
		}
		return holder.get();
	}
	
	public static void setTestObject(TestObject object){
			holder.set(object);
	}
	public static void main(String[] args) {
		TestThreadLocal testThreadLocal = new TestThreadLocal();
		new MyThread(testThreadLocal).start();
		new MyThread3(testThreadLocal).start();
	}

}
/**
 * ��Ҫ�߳���
 * @author CC
 *
 */
class MyThread extends Thread{
	private TestThreadLocal testThreadLocal;

	public MyThread(TestThreadLocal testThreadLocal) {
		super();
		this.testThreadLocal = testThreadLocal;
	}

	public TestThreadLocal getTestThreadLocal() {
		return testThreadLocal;
	}

	public void setTestThreadLocal(TestThreadLocal testThreadLocal) {
		this.testThreadLocal = testThreadLocal;
	}

	@Override
	public void run() {
		TestObject object  = null;
		for(int i=0;i<10;i++){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			object = TestThreadLocal.getTestObject();
			System.out.println(Thread.currentThread().getName()+"   "+object.getAge());
			object.setAge(i);
			TestThreadLocal.setTestObject(object);
		}
		
	}
}

/**
 * ��Ҫ�߳���
 * @author CC
 *
 */
class MyThread3 extends Thread{
	private TestThreadLocal testThreadLocal;

	public MyThread3(TestThreadLocal testThreadLocal) {
		super();
		this.testThreadLocal = testThreadLocal;
	}

	public TestThreadLocal getTestThreadLocal() {
		return testThreadLocal;
	}

	public void setTestThreadLocal(TestThreadLocal testThreadLocal) {
		this.testThreadLocal = testThreadLocal;
	}

	@Override
	public void run() {
		TestObject object  = null;
		for(int i=0;i<10;i++){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			object = TestThreadLocal.getTestObject();//�ڵ�ǰ�߳��л�ȡobject���󸱱�
			System.out.println(Thread.currentThread().getName()+"   "+object.getAge());
			object.setAge(object.getAge()+5);
			/*
			 * ������仰���Բ��ã���Ϊobject�Զ���Ϊ��ǰ�߳̿ɼ���
			 */
//			TestThreadLocal.setTestObject(object);//��Ŀǰ��object�Żص���ǰ��ThreadLocal
		}
		
	}
}
/**
 * ��ThreadLocal���ṩ���󸱱�
 * @author CC
 *
 */
class TestObject {
	public TestObject(String name, int age) {
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
