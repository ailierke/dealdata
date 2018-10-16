package deal_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.DBUtil;

/**
 * 商会通知添加时间
 * @author CC
 *
 */
public class Y_basic_socialgroupsinformRecord_dateTime {
	public static void main(String[] args) throws SQLException {
		Connection conn = DBUtil.getConnection();
		Statement statement = conn.createStatement();
		PreparedStatement statement1 = conn.prepareStatement("update cocmoredb.y_basic_socialgroupsinformrecord set updatetime=? where fid =?");

		ResultSet  rs = statement.executeQuery("select fid,updatetime from cocmoredb.y_basic_socialgroupsinformrecord");
		String fid =null;
		Date updatetime = null;
		while(rs.next()){
			fid = rs.getString(1);
			updatetime = rs.getDate(2);
			System.out.println("主键："+fid);
			if(updatetime==null){
				String fomart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.sql.Date(new java.util.Date().getTime()));
				statement1.setString(1, fomart);
				statement1.setString(2, fid);
				statement1.executeUpdate();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		rs.close();
		statement1.close();
		statement.close();
		conn.close();
	}
}
