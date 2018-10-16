package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  IM账号表
 * @author CC
 *
 */
public class y_basic_imaccount_5 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select id as FID, tel as fimtel,imusername as FIMkey,impassword as FIMPassword from t_member_info where tel is not NULL and imusername is not null  group by tel");
		String FID =null;String fimtel =null;String FIMkey =null;
        String FIMPassword =null;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			FID =rs.getString(1);
			fimtel=rs.getString(2);
			FIMkey =rs.getString(3);
			FIMPassword = rs.getString(4);
			System.out.println("IMid："+FID+"IMtel："+fimtel);
			ps = conn.prepareStatement("insert into cocmoredb.y_basic_imaccount(FID,fimtel,FIMkey,FIMPassword) values(?,?,?,?)");
			ps.setString(1, FID);
			ps.setString(2, fimtel);
			ps.setString(3, FIMkey);
			ps.setString(4, FIMPassword);
			ps.executeUpdate();
			ps.close();
			count++;
		}
		rs.close();
		statement.close();
		System.out.println("获取总条数："+count);
		conn.close();
	}
	public static void main(String[] args) throws SQLException {
		dealData();
	}
}
