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
 * �����޸ĸ�λʱ��  sbbh���ڵĿͻ�
 * 
 * </p>
 * <p>������</p>
 * <p>Copyright��Copyright(c) 2015 diwinet</p>
 * <p>���ڣ�2015��8��20��</p>
 * @author	jiangxing
 */
public class T_WP_FILTERELEMENT_RESET {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		Statement statement2 = conn.createStatement();
		ResultSet  rs1 = statement.executeQuery("select rest.fwbh,rest.SBBH,rest.FWSJ,info.SBTM from T_WP_FILTERELEMENT_RESET rest LEFT JOIN T_SENSOR_INFO info ON info.SBBH = rest.SBBH");
		String sbtm = null;
		String fwsj = null;
		Integer fwsl = null;
		Long fwbh = null;
	
		ResultSet  rs  = null;
		while(rs1.next()){
				sbtm = rs1.getString(4);
				try{
					fwsj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs1.getTimestamp(3)) ;
				}catch(Exception e){
					continue;
				}
				fwbh = rs1.getLong(1);
				if(sbtm!=null&&fwsj!=null){
					rs = statement1.executeQuery("select YSSL from T_WATER_REPORT_HISTORY where BSSJ <= '"+fwsj+"' AND SBTM = '"+sbtm+"' order by BSSJ desc ");
					if(rs.next()){
						fwsl = rs.getInt(1);
						statement2.executeUpdate("update  T_WP_FILTERELEMENT_RESET  set FWSL ="+fwsl+" where FWBH = "+fwbh);
						System.out.println("��λ��ţ�"+fwbh+"  ��λˮ����"+fwsl+" ��ӳɹ���");
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
