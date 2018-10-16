package deal_data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import util.DBUtil;
import util.IMUtils;

/**
 * ����IMӦ������û�����������ݵ�IM�˺ţ�������������(�������ݲ���Ҫ)
 * @author CC
 *
 */
public class CreateIMUser {
	public static void main(String[] args) throws SQLException {
		Connection conn = DBUtil.getConnection();
		Statement statement = conn.createStatement();

		ResultSet  rs = statement.executeQuery("select FIMkey,FIMPassword from y_basic_imaccount");
		String username = "";
		String password="";
		Map<String,String> userMap = new HashMap<String,String>();
		while(rs.next()){
			username = rs.getString(1);
			password = rs.getString(2);
			System.out.println("IM�û���"+username+"IM����"+password);
			if(username!=null){
				userMap.put("username", username);
				userMap.put("password",password);
				try {
					IMUtils.createUser(userMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		rs.close();
		statement.close();
		conn.close();
	}
}
