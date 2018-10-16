package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  商会上墙回复表
 * @author CC
 *
 */
public class y_wallreply_14 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select FID as FID ,FContent as FContent,FMemberID as FUserID, FActivityThemeID as FWallActivityID, FBusinessID as FCommerceID,FDateTime as FReplyTime , CASE FAuditState WHEN 1 THEN 15  WHEN 2 THEN 16 WHEN 3 THEN 17 ELSE NULL  END  as FState  from test.Y_Business_Activity_Onwall where FMemberID in (select fid from cocmoredb.y_basic_member) and FActivityThemeID in (select fid from cocmoredb.y_wallactivity) and FBusinessID in (select fid from cocmoredb.y_basic_socialgroups)");
		String FID =null;String FContent =null;String FUserID =null;
        String FWallActivityID =null;String FCommerceID =null;
        String FReplyTime =null;Integer FState =null;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			FID =rs.getString(1);
			FContent=rs.getString(2);
			FUserID =rs.getString(3);
			FWallActivityID = rs.getString(4);
			FCommerceID = rs.getString(5);
			FReplyTime = rs.getString(6);
			FState = rs.getInt(7);
			System.out.println("上墙回复Id："+FID+"用户id："+FUserID);
			ps = conn.prepareStatement("insert into cocmoredb.y_wallreply(FID,FContent,FUserID,FWallActivityID,FCommerceID,FReplyTime,FState) values(?,?,?,?,?,?,?)");
			ps.setString(1, FID);
			ps.setString(2, FContent);
			ps.setString(3, FUserID);
			ps.setString(4, FWallActivityID);
			ps.setString(5, FCommerceID);
			ps.setString(6, FReplyTime);
			ps.setInt(7, FState);
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
