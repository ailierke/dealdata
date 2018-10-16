package access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestAcess {
	public void ConnectAccessDataSource()throws Exception {  
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  
        /** 
         * 采用ODBC连接方式 如何建立ODBC连接？ 
         * 答：在windows下，【开始】->【控制面板】->【性能和维护】->【管理工具】->【数据源】，在数据源这里添加一个指向a1.mdb文件的数据源。 
         * 比如创建名字为dataS1 
         */  
        String dbur1 = "jdbc:odbc:dw-wms";// 此为ODBC连接方式  
        Connection conn = DriverManager.getConnection(dbur1, "dwater-wms", "123456");  
        Statement stmt = conn.createStatement();  
        ResultSet rs = stmt.executeQuery("select * from Table1");  
        while (rs.next()) {  
            System.out.println(rs.getString(1));  
        }  
        rs.close();  
        stmt.close();  
        conn.close();  
    }  
	
	public static void main(String[] args) throws Exception {
		DBConnection db = new DBConnection();
		db.ConnectAccessDataSource();
	}
}
