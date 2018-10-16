package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  商会通知表
 * @author CC
 *
 */
public class y_basic_socialgroupsinform_10 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select id as FID ,description as FMessage,'1' as FNumber, 5 as FBillState,title as FHeadline,image_url as FLogoImage,business_id as FSocialGroupsID,'402' as FTypeId  from test.t_info_activity where business_id in (select fid from cocmoredb.y_basic_socialgroups) ");
		String FID =null;String FMessage =null;String FNumber =null;
        Integer FBillState =null;String FHeadline =null;String FLogoImage= null;
        String FSocialGroupsID =null;String FTypeId= null;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			FID =rs.getString(1);
			FMessage=rs.getString(2);
			FNumber =rs.getString(3);
			FBillState = rs.getInt(4);
			FHeadline = rs.getString(5);
			FLogoImage = rs.getString(6);
			FSocialGroupsID = rs.getString(7);
			FTypeId = rs.getString(8);
			System.out.println("通知Id："+FID+"通知标题："+FHeadline);
			ps = conn.prepareStatement("insert into cocmoredb.y_basic_socialgroupsinform(FID,FMessage,FNumber,FBillState,FHeadline,FLogoImage,FSocialGroupsID,FTypeId) values(?,?,?,?,?,?,?,?)");
			ps.setString(1, FID);
			ps.setString(2, FMessage);
			ps.setString(3, FNumber);
			ps.setInt(4, FBillState);
			ps.setString(5, FHeadline);
			ps.setString(6, FLogoImage);
			ps.setString(7, FSocialGroupsID);
			ps.setString(8, FTypeId);
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
