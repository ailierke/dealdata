package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.DBUtil;
/**
 * <p>标题：活动年卡活动冲多少年送多少天，改为，冲多少个月，送多少天</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2016 diwinet</p>
 * <p>日期：2016年10月19日</p>
 * @author	jiangxing
 */
public class T_WP_COMPANY_ACTIVITY_ITEMS {
	
//	update T_LEASE_PAY set GMSL = 6 where CZLX =2;
//
//	update T_LEASE_PAY set GMSL = 12 where CZLX =1;
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		Statement statement2 = conn.createStatement();
		//查出所有是年卡的
		ResultSet  rs1 = statement.executeQuery("select i.ZJID as zjid ,i.CZSL as czsl "
				+ " FROM T_WP_COMPANY_ACTIVITY_ITEMS i"
				+ " INNER JOIN T_WP_COMPANY_ACTIVITY a ON a.HDID = i.HDID WHERE a.HDLX = 1");
		Long zjid = null;
		Double czsl = null;
		while(rs1.next()){
			zjid = rs1.getLong(1);
			czsl = rs1.getDouble(2);
			statement2.executeUpdate("update  T_WP_COMPANY_ACTIVITY_ITEMS  set CZSL ="+12*czsl+" where ZJID = "+zjid);
			System.out.println("年卡：ZJID="+zjid);
		}
		rs1.close();
		statement.close();
		statement1.close();
		statement2.close();
		conn.close();
}
}
