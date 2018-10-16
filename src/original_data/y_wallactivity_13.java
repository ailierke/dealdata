package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  商会上墙主题活动表
 * @author CC
 *
 */
public class y_wallactivity_13 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select  fid as FID , FActivityTheme as FTheme,FBusinessID as FCommerceID, CASE FActivityState WHEN 1 THEN 5 WHEN 2 THEN 5 WHEN 3 THEN 6 ELSE 5  END as  Fstate ,FDateTime as FCreateTime from test.Y_Business_Activity_Theme where FBusinessID in (select fid from cocmoredb.y_basic_socialgroups)");
		String FID =null;String FTheme =null;String FCommerceID =null;
        Integer Fstate =null;String FCreateTime =null;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			FID =rs.getString(1);
			FTheme=rs.getString(2);
			FCommerceID =rs.getString(3);
			Fstate = rs.getInt(4);
			FCreateTime = rs.getString(5);
			System.out.println("上墙活动Id："+FID+"主题："+FTheme);
			ps = conn.prepareStatement("insert into cocmoredb.y_wallactivity(FID,FTheme,FCommerceID,Fstate,FCreateTime) values(?,?,?,?,?)");
			ps.setString(1, FID);
			ps.setString(2, FTheme);
			ps.setString(3, FCommerceID);
			ps.setInt(4, Fstate);
			ps.setString(5, FCreateTime);
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
