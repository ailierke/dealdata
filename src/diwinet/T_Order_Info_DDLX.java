package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBUtil;
/**
 * <p>描述：将原先的所有订单的类型改为水管家订单</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年8月20日</p>
 * @author	jiangxing
 */
public class T_Order_Info_DDLX {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		int count = statement.executeUpdate("update  T_ORDER_INFO  set DDLY=0");
	System.out.println("修改成功 ，修改总记录数条数："+count);
	statement.close();
	conn.close();
}
}
