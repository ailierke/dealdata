package TestThread;

import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Scanner s = new Scanner(System.in);
				int i = 0;
				while(i<10){
					i++;
					String str = s.nextLine();
					System.out.println("输入的是："+str);
				}
			}
		});
		t.start();
	}
}
