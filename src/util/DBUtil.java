package util;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBUtil {
	public static Connection getConnection(){
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://101.201.104.132:3306/dwater_all?useUnicode=true&characterEncoding=utf8";
		String user = "DW_2016"; 
		String password = "dwater-173";
		Connection conn = null;
		try { 
			Class.forName(driver);

			 conn = DriverManager.getConnection(url, user, password);

		} catch(Exception e) {
			System.out.println("Sorry,can`t find the Driver!"); 
			e.printStackTrace();
		}
		return conn;
	}
	public static void main(String[] args) {
			String headimage = "http://yunzo.oss.aliyuncs.com/sadasd/asdas.jpg";
			System.out.println(headimage.substring("http://yunzo.oss.aliyuncs.com/".length(), headimage.length()));
		}
}
