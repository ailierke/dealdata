package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  商会需求
 * @author CC
 *
 */
public class y_basic_socialgroupsdemand_16 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select id as FID,'1' as FNumber,1 as flevel, title as FHeadline,cont as FMessage,creator as FPublisherID,create_time as FStartTime,business_id as FSocialGroupsID ,5 as  FBillState,'b11f8b53-d7d3-41b5-a690-06fc4a0cd281' as ftradeid  from test.t_article  where art_type =2 and creator in (select fid from cocmoredb.y_basic_member) and business_id in (select fid from cocmoredb.y_basic_socialgroups)");
		String FID =null;String FNumber =null;Integer flevel =null;
        String FHeadline =null;
        String FMessage =null;String FPublisherID =null;
        String FStartTime =null;String FSocialGroupsID =null;
        Integer FBillState =null;String ftradeid =null;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			FID =rs.getString(1);
			FNumber=rs.getString(2);
			flevel =rs.getInt(3);
			FHeadline = rs.getString(4);
			FMessage = rs.getString(5);
			FPublisherID = rs.getString(6);
			FStartTime = rs.getString(7);
			FSocialGroupsID = rs.getString(8);
			FBillState = rs.getInt(9);
			ftradeid = rs.getString(10);
			System.out.println("供应Id："+FID+"供应标题："+FHeadline);
			ps = conn.prepareStatement("insert into cocmoredb.y_basic_socialgroupsdemand(FID,FNumber,flevel,FHeadline,FMessage,FPublisherID,FStartTime,FSocialGroupsID,FBillState,ftradeid) values(?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, FID);
			ps.setString(2, FNumber);
			ps.setInt(3, flevel);
			ps.setString(4, FHeadline);
			ps.setString(5, FMessage);
			ps.setString(6, FPublisherID);
			ps.setString(7, FStartTime);
			ps.setString(8, FSocialGroupsID);
			ps.setInt(9, FBillState);
			ps.setString(10, ftradeid);
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
