package deal_data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBUtil;
import util.EncryptionForTellPhone;

/**
 * member表加密电话号码，没有对图片进行处理
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
			System.out.println("主建："+fid+"电话号码："+tel);
			if(tel!=null&&tel.length()<20){//可能跑着跑着宕机，这个判断是否已经修改过，tel<20就算是没有修改的。tel加密修改后大于20
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
