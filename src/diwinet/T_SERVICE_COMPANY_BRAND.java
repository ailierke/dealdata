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
 * <p>������</p>
 * <p>Copyright��Copyright(c) 2015 diwinet</p>
 * <p>���ڣ�2015��8��20��</p>
 * @author	jiangxing
 */
public class T_SERVICE_COMPANY_BRAND {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		ResultSet  rs1 = statement.executeQuery("select PPBH,PPMC from T_DMS_WP_BRAND");
		Long  ppbh = null;
		String ppmc = null;
//		Long fwsbh = 524L;//�����̱��
		Long fwsbh = 122L;//�����̱��
		while(rs1.next()){
			ppbh = rs1.getLong(1);
			ppmc=rs1.getString(2);
				if(ppbh!=null){
						statement1.executeUpdate("insert into T_SERVICE_COMPANY_BRAND(FWSBH,PPBH,PPMC,DLLX,SFYX,CJSJ) values("+fwsbh+","+ppbh+",'"+ppmc+"',0,1,now())");
						System.out.println("�����̱�ţ�"+fwsbh+"  Ʒ�Ʊ�ţ�"+ppbh+" ����ɹ���");
				}
		}
		rs1.close();
		statement.close();
		statement1.close();
		conn.close();
}
}
