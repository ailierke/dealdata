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
public class T_WATER_REPORT_HISTORY_RT_AVGTDS {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		Statement statement2 = conn.createStatement();
		ResultSet  rs1 = statement.executeQuery("select sbtm from T_WATER_REPORT_HISTORY_RT");
		String sbtm = null;
		Integer avgtds = 35;
		ResultSet  rs  = null;
		while(rs1.next()){
				sbtm = rs1.getString(1);
				if(sbtm!=null){
					rs = statement1.executeQuery("select tmp.sbtm,AVG(tmp.CSSZ) as avgtds from ( SELECT wrh.* FROM ( SELECT history.sbtm,history.cssz FROM T_WATER_REPORT_HISTORY history ) wrh JOIN T_SENSOR_INFO si ON wrh.SBTM = si.SBTM JOIN T_SENSOR_BINDING sb ON wrh.SBTM = sb.SBTM) tmp where tmp.sbtm='"+sbtm+"' group by tmp.sbtm");
					if(rs.next()){
						sbtm = rs.getString(1);
						avgtds = rs.getInt(2);
						statement2.executeUpdate("update  T_WATER_REPORT_HISTORY_RT  set AVGTDS="+avgtds+" where sbtm = '"+sbtm+"'");
						System.out.println("设备条码："+sbtm+"  tds平均值："+avgtds+" 添加成功！");
					}
				}
		}
		rs1.close();
		rs.close();
	statement.close();
	statement1.close();
	statement2.close();
	conn.close();
}
}
