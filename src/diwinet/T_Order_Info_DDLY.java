package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBUtil;
/**
 * <p>���⣺��ȡ��ʷ����ˮ��tdsƽ��ֵ��RT��
 * insert into T_WATER_REPORT_HISTORY_RT(SBTM,YSSL,avgtds) values('7090150878008879526',127,996) 
 * on duplicate key update YSSL=127,avgtds=(avgtds+996)/2
 * RT��ÿ���ڱ��͵�ʱ�������޸�ƽ��tdsֵ
 * 
 * </p>
 * <p>������</p>
 * <p>Copyright��Copyright(c) 2015 diwinet</p>
 * <p>���ڣ�2015��8��20��</p>
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
				//��������Ű�װ
				if(sfaz!=null&&sfaz.intValue() ==0){
						statement2.executeUpdate("update  T_ORDER_INFO  set DDLX=0 where DDID ="+ddid);
						System.out.println("����idΪ��"+ddid+"  ���Ķ�������Ϊ��0--��о���� �޸ĳɹ���");
				}else if(sfaz!=null&&sfaz.intValue() ==1){
					//������Ű�װ
					statement2.executeUpdate("update  T_ORDER_INFO  set DDLX=2 where DDID ="+ddid);
					System.out.println("����idΪ��"+ddid+"  ���Ķ�������Ϊ��2--������о�������Ƿ��񶩵� �޸ĳɹ���");
				}
		}
		rs1.close();
	statement.close();
	statement1.close();
	statement2.close();
	conn.close();
}
}
