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
 * <p>����������ǰ�Ͽͻ��Ķ���û�зָ�����Ķ��ֵ���������233����</p>
 * <p>Copyright��Copyright(c) 2015 diwinet</p>
 * <p>���ڣ�2015��8��20��</p>
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
		Long wdbh = 233L;//�����̱��  ���޸�
//		Long wdbh = 241L;//�����̱��  ���޸�
		while(rs1.next()){
			ddid = rs1.getLong(1);
			//�ʼ��������Ӵ����Ʒ���ų���
				if(ddid!=null&&ddid!=292){
						statement1.executeUpdate("insert into T_ORDER_AREA_POS(DDID,WDBH,SFYX,CSSJ) values("+ddid+","+wdbh+",0,'2015-06-08 14:07:00')");
						System.out.println("����id��"+ddid);
				}
		}
		rs1.close();
		statement.close();
		statement1.close();
		conn.close();
}
}
