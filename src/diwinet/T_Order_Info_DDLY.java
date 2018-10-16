package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBUtil;
/**
 * <p>标题：获取历史报送水量tds平均值到RT表
 * insert into T_WATER_REPORT_HISTORY_RT(SBTM,YSSL,avgtds) values('7090150878008879526',127,996) 
 * on duplicate key update YSSL=127,avgtds=(avgtds+996)/2
 * RT表每次在报送的时候这样修改平均tds值
 * 
 * </p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年8月20日</p>
 * @author	jiangxing
 */
public class T_Order_Info_DDLY {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		Statement statement2 = conn.createStatement();
		ResultSet  rs1 = statement.executeQuery("select DDID,SFAZ from T_ORDER_INFO");
		Integer ddid = null;
		Integer sfaz = null;
		while(rs1.next()){
				ddid = rs1.getInt(1);
				sfaz = rs1.getInt(2);
				//如果不上门安装
				if(sfaz!=null&&sfaz.intValue() ==0){
						statement2.executeUpdate("update  T_ORDER_INFO  set DDLX=0 where DDID ="+ddid);
						System.out.println("订单id为："+ddid+"  更改订单类型为：0--滤芯订单 修改成功！");
				}else if(sfaz!=null&&sfaz.intValue() ==1){
					//如果上门安装
					statement2.executeUpdate("update  T_ORDER_INFO  set DDLX=2 where DDID ="+ddid);
					System.out.println("订单id为："+ddid+"  更改订单类型为：2--即是滤芯订单又是服务订单 修改成功！");
				}
		}
		rs1.close();
	statement.close();
	statement1.close();
	statement2.close();
	conn.close();
}
}
