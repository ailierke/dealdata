package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  商会通知表参与人员表
 * @author CC
 *
 */
public class y_basic_socialgroupsinformrecord_11 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select id as FID,activity_id as FInformID,user_id as FInformPeopleID,update_time as updatetime, CASE status WHEN 1 THEN '0' WHEN 2 THEN '1' WHEN 3 THEN '1' ELSE NULL  END as FYNParticipation from test.t_info_activity_user where user_id in (select fid from cocmoredb.y_basic_member) and activity_id in (select fid from cocmoredb.y_basic_socialgroupsinform) ");
		String FID =null;String FInformID =null;String FInformPeopleID =null;
        String updatetime =null;String FYNParticipation =null;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			FID =rs.getString(1);
			FInformID=rs.getString(2);
			FInformPeopleID =rs.getString(3);
			updatetime = rs.getString(4);
			FYNParticipation = rs.getString(5);
			System.out.println("通知Id："+FID+"通知用户Id："+FInformPeopleID);
			ps = conn.prepareStatement("insert into cocmoredb.y_basic_socialgroupsinformrecord(FID,FInformID,FInformPeopleID,updatetime,FYNParticipation) values(?,?,?,?,?)");
			ps.setString(1, FID);
			ps.setString(2, FInformID);
			ps.setString(3, FInformPeopleID);
			ps.setString(4, updatetime);
			ps.setString(5, FYNParticipation);
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
