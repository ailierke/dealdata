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
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年8月20日</p>
 * @author	jiangxing
 */
public class T_SERVICE_POS_BRAND {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		ResultSet  rs1 = statement.executeQuery("select PPBH,PPMC from T_DMS_WP_BRAND");
		Long  ppbh = null;
		String ppmc = null;
//		Long wdbh = 233L;//服务商编号  需修改
		Long wdbh = 241L;//服务商编号  需修改
		while(rs1.next()){
			ppbh = rs1.getLong(1);
			ppmc=rs1.getString(2);
			//最开始给网点添加代理的品牌排除开
				if(ppbh!=null&&ppbh.intValue()!=1000001){
						statement1.executeUpdate("insert into T_SERVICE_POS_BRAND(WDBH,PPBH,PPMC,SFYX,CJRQ) values("+wdbh+","+ppbh+",'"+ppmc+"',1,now())");
						System.out.println("网点编号："+wdbh+"  品牌："+ppbh+" 代理成功！");
				}
		}
		rs1.close();
		statement.close();
		statement1.close();
		conn.close();
}
}
