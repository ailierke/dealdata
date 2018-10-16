package TestMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMap {
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		TestMemory th = new TestMemory();
		th.start();
//		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
//		for(StackTraceElement s :stackTraceElements){
//			System.out.println(s.toString());
//		}
		String code ="sdfasdfasf3er1293udjasdfjjasdhfasdhflasdhgfasdfhasdlfhasdhfasdbdsbvsdajhfwehuifhdsjcfasdhahfjsdjdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddwjeqfjelakj jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjf;asdlllllllllllllllllllllllllllllllllllllllllllllllllllllllllaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbblk";
		HashMap<String,String> map = new HashMap<String,String>(20000);
		for(int i=0;i<20000;i++){
			map.put("70901708954654"+i, "e131312412341"+i);
		}
		th.stopThread(true);
	}
}

class TestMemory extends Thread {
    private boolean _run = true;
       public void stopThread(boolean run) {
           this._run = !run;
       }

   public void run() {
       while(_run){
           long l = Runtime.getRuntime().totalMemory()/1024/1024;
           long f = Runtime.getRuntime().freeMemory()/1024/1024;
           long rem = l - f;
           System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%OccupiedSpace++++++++++%%%%%%%%%%%%%:" + rem + "M"+";totalMemory:"+l+"M"+";freeMeroy:"+f+"M");
       }
        
   }
}

class Vo {
	private int id;
	private String code;
	private String seq;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSeq() {
		return seq;
	}
	public Vo(int id, String code, String seq) {
		super();
		this.id = id;
		this.code = code;
		this.seq = seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
}