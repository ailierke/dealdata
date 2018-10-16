package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  商会供应
 * @author CC
 *
 */
public class y_basic_socialgroupssupply_15 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select id as FID,'1' as FNumber,1 as flevel,0 as FAreGuarantee,title as FHeadline,cont as FMessage,creator as FPublisherID,create_time as FPublisherTime ,business_id as FSocialGroupsID ,5 as  FBillState,'b11f8b53-d7d3-41b5-a690-06fc4a0cd281' as ftradeid from test.t_article where art_type =1 and creator in (select fid from cocmoredb.y_basic_member) and business_id in (select fid from cocmoredb.y_basic_socialgroups)");
		String FID =null;String FNumber =null;Integer flevel =null;
        Integer FAreGuarantee =null;String FHeadline =null;
        String FMessage =null;String FPublisherID =null;
        String FPublisherTime =null;String FSocialGroupsID =null;
        Integer FBillState =null;String ftradeid =null;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			FID =rs.getString(1);
			FNumber=rs.getString(2);
			flevel =rs.getInt(3);
			FAreGuarantee = rs.getInt(4);
			FHeadline = rs.getString(5);
			FMessage = rs.getString(6);
			FPublisherID = rs.getString(7);
			FPublisherTime = rs.getString(8);
			FSocialGroupsID = rs.getString(9);
			FBillState = rs.getInt(10);
			ftradeid = rs.getString(11);
			System.out.println("供应Id："+FID+"供应标题："+FHeadline);
			ps = conn.prepareStatement("insert into cocmoredb.y_basic_socialgroupssupply(FID,FNumber,flevel,FAreGuarantee,FHeadline,FMessage,FPublisherID,FPublisherTime,FSocialGroupsID,FBillState,ftradeid) values(?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, FID);
			ps.setString(2, FNumber);
			ps.setInt(3, flevel);
			ps.setInt(4, FAreGuarantee);
			ps.setString(5, FHeadline);
			ps.setString(6, FMessage);
			ps.setString(7, FPublisherID);
			ps.setString(8, FPublisherTime);
			ps.setString(9, FSocialGroupsID);
			ps.setInt(10, FBillState);
			ps.setString(11, ftradeid);
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
