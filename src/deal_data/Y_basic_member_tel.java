package deal_data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBUtil;
import util.EncryptionForTellPhone;

/**
 * member����ܵ绰���룬û�ж�ͼƬ���д���
 * @author CC
 *
 */
public class Y_basic_member_tel {
	public static void main(String[] args) throws SQLException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		
		ResultSet  rs = statement.executeQuery("select fid,fmobilephone from cocmoredb.y_basic_member");
		String tel = null;
		String fid =null;
		while(rs.next()){
			 tel = rs.getString(2);
			 fid = rs.getString(1);
			System.out.println("������"+fid+"�绰���룺"+tel);
			if(tel!=null&&tel.length()<20){//������������崻�������ж��Ƿ��Ѿ��޸Ĺ���tel<20������û���޸ĵġ�tel�����޸ĺ����20
				tel=EncryptionForTellPhone.encryptToABC(tel);
				System.out.println(tel.length());
				statement1.executeUpdate("update cocmoredb.y_basic_member set FMobilePhone = '"+tel+"'  where fid ='"+fid+"'");
			}else{
				//do nothing
			}
		}
		rs.close();
		statement.close();
		conn.close();
	}
}
