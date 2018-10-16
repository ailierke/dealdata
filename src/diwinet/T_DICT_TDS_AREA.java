package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBUtil;
/**
 * <p>标题：区域过滤后的tds值更新到此表</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年8月20日</p>
 * @author	jiangxing
 */
public class T_DICT_TDS_AREA {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement2 = conn.createStatement();

		ResultSet  rs = statement.executeQuery("select JLBH as jlbh, TDS as tds from T_DICT_TDS_AREA");
		Integer tds=null;
		Integer jlbh=null;
		Integer cstds = null;
		while(rs.next()){
			jlbh =rs.getInt(1);
			tds=rs.getInt(2);
			System.out.println("净虑编号为："+jlbh+"  tds值为："+tds+"!入库");
			if(tds!=null&&tds!=0){
				//当地自来水TDS200以下的 上限设置为25  200-700的设置上限为35 700以上的是45 
				if(tds>700){
					cstds = 45;
				}else if(tds>=200){
					cstds = 35;
				}else{
					cstds = 25;
				}
			}else{
				cstds = 35;
			}
			statement2.executeUpdate("update  T_DICT_TDS_AREA  set CSTDS="+cstds+" where jlbh = "+jlbh);
	}
		statement2.close();
		rs.close();
	statement.close();
	conn.close();
}
//	public static void main(String[] args) {
//		Integer tds = 100;
//		Integer cstds = null;
//		if(tds>700){
//			cstds = 45;
//		}else if(tds>=200){
//			cstds = 35;
//		}else{
//			cstds = 25;
//		}
//		System.out.println(cstds);
//	}
}
