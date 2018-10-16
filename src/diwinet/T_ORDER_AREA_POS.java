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
 * 
 * 
 * </p>
 * <p>描述：把以前老客户的订单没有分给网点的都分到虚拟网店233下面</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年8月20日</p>
 * @author	jiangxing
 */
public class T_ORDER_AREA_POS {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		ResultSet  rs1 = statement.executeQuery("select ddid from T_ORDER_INFO where ddid  not in (select ddid  from T_ORDER_AREA_POS where ddid is not null) and khjsjbh is not null");
		Long  ddid = null;
		Long wdbh = 233L;//服务商编号  需修改
//		Long wdbh = 241L;//服务商编号  需修改
		while(rs1.next()){
			ddid = rs1.getLong(1);
			//最开始给网点添加代理的品牌排除开
				if(ddid!=null&&ddid!=292){
						statement1.executeUpdate("insert into T_ORDER_AREA_POS(DDID,WDBH,SFYX,CSSJ) values("+ddid+","+wdbh+",0,'2015-06-08 14:07:00')");
						System.out.println("订单id："+ddid);
				}
		}
		rs1.close();
		statement.close();
		statement1.close();
		conn.close();
}
}
