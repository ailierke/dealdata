package deal_data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBUtil;
import util.EncryptionForTellPhone;

/**
 * y_basic_imaccount表加密电话号码
 * @author ailierke
 *
 */
public class Y_basic_imaccount {
	public static void main(String[] args) throws SQLException {
		Connection conn = DBUtil.getConnection();
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();

		ResultSet  rs = statement.executeQuery("select fid,fimtel from cocmoredb.y_basic_imaccount");
		String tel = null;
		String fid =null;
		while(rs.next()){
			tel = rs.getString(2);
			fid = rs.getString(1);
			System.out.println("主建："+fid+"电话号码："+tel);
			if(tel.length()<20){
				tel=EncryptionForTellPhone.encryptToABC(tel);
				statement1.executeUpdate("update cocmoredb.y_basic_imaccount set fimtel = '"+tel+"' where fid ='"+fid+"'");
			}
		}
		rs.close();
		statement.close();
		conn.close();
	}
}
