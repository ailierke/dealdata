package TestThread;

public class TestVolatile {
	private  volatile boolean stopped = true;

	 public void Stop(){
	   stopped = true;
	 }
	 public void Stop1(){
		   stopped = true;
		 }
	 public void FindFiles(){
	   while (!stopped){
	     // searching files
	   }
	 }
	public static void main(String[] args) {
		
	}
}

class MyThread4 extends Thread {
	private TestVolatile testVolatile ;
	public MyThread4(TestVolatile testVolatile) {
			this.testVolatile = testVolatile;
	}
	@Override
	public void run() {
		testVolatile.Stop();
	}
}

