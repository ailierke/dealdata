package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBUtil;
/**
 * <p>���⣺������˺��tdsֵ���µ��˱�</p>
 * <p>������</p>
 * <p>Copyright��Copyright(c) 2015 diwinet</p>
 * <p>���ڣ�2015��8��20��</p>
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
			System.out.println("���Ǳ��Ϊ��"+jlbh+"  tdsֵΪ��"+tds+"!���");
			if(tds!=null&&tds!=0){
				//��������ˮTDS200���µ� ��������Ϊ25  200-700����������Ϊ35 700���ϵ���45 
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
