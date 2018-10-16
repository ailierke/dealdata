package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBUtil;
import util.StringUtil;
/**
 * <p>标题：同步地址表到订单信息</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2016 diwinet</p>
 * <p>日期：2016年5月26日</p>
 * @author	jiangxing
 */
public class T_Order_Info_Adress {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		Statement statement2 = conn.createStatement();
		ResultSet  rs1 = statement.executeQuery("select i.DDID,addr.shrm as lxrm ,addr.lxdh as lxdh from T_ORDER_INFO i left join T_ORDER_SERVICE_ADDR addr ON addr.DZBH = i.DZBH");
		Integer ddid = null;
		String  shrm = null;
		String  lxdh = null;
		while(rs1.next()){
				ddid = rs1.getInt(1);
				shrm = rs1.getString(2);
				lxdh = rs1.getString(3);
				//如果不上门安装
				if(StringUtil.isEmpty(shrm)){
					shrm = "";
				}
				if(StringUtil.isEmpty(lxdh)){
					lxdh = "";
				}
				statement2.executeUpdate("update  T_ORDER_INFO  set LXRM='"+shrm+"',LXDH='"+lxdh+"' where DDID ="+ddid);
				
				System.out.println("订单id为:"+ddid+",收货信息同步成功");
				
		}
		rs1.close();
	statement.close();
	statement1.close();
	statement2.close();
	conn.close();
}
}
