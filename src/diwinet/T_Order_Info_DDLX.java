package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBUtil;
/**
 * <p>��������ԭ�ȵ����ж��������͸�Ϊˮ�ܼҶ���</p>
 * <p>Copyright��Copyright(c) 2015 diwinet</p>
 * <p>���ڣ�2015��8��20��</p>
 * @author	jiangxing
 */
public class T_Order_Info_DDLX {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		int count = statement.executeUpdate("update  T_ORDER_INFO  set DDLY=0");
	System.out.println("�޸ĳɹ� ���޸��ܼ�¼��������"+count);
	statement.close();
	conn.close();
}
}
