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
public class T_SERVICE_POS_BRAND {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		ResultSet  rs1 = statement.executeQuery("select PPBH,PPMC from T_DMS_WP_BRAND");
		Long  ppbh = null;
		String ppmc = null;
//		Long wdbh = 233L;//�����̱��  ���޸�
		Long wdbh = 241L;//�����̱��  ���޸�
		while(rs1.next()){
			ppbh = rs1.getLong(1);
			ppmc=rs1.getString(2);
			//�ʼ��������Ӵ����Ʒ���ų���
				if(ppbh!=null&&ppbh.intValue()!=1000001){
						statement1.executeUpdate("insert into T_SERVICE_POS_BRAND(WDBH,PPBH,PPMC,SFYX,CJRQ) values("+wdbh+","+ppbh+",'"+ppmc+"',1,now())");
						System.out.println("�����ţ�"+wdbh+"  Ʒ�ƣ�"+ppbh+" ����ɹ���");
				}
		}
		rs1.close();
		statement.close();
		statement1.close();
		conn.close();
}
}
