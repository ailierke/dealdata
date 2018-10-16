package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  商会动态表
 * @author CC
 *
 */
public class y_basic_socialgroupsdynamic_12 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select id as FID,title as fheadline,image_url as flogoimage,description as fmessage,CASE status WHEN 1 THEN 5  WHEN 0 THEN 6 ELSE 5 END as FBillState,'1' as fnumber ,create_time as fpublishtime,business_id as FSocialGroupsID,'301' as FTypeID from t_info_news where business_id in (select fid from cocmoredb.y_basic_socialgroups) and  business_id is  not  null");
		String FID =null;String fheadline =null;String flogoimage =null;
        String fmessage =null;Integer FBillState =null;String fnumber =null;
        String fpublishtime =null;String FSocialGroupsID =null;String FTypeID =null;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			FID =rs.getString(1);
			fheadline=rs.getString(2);
			flogoimage =rs.getString(3);
			fmessage = rs.getString(4);
			FBillState = rs.getInt(5);
			fnumber = rs.getString(6);
			fpublishtime = rs.getString(7);
			FSocialGroupsID = rs.getString(8);
			FTypeID = rs.getString(9);
			System.out.println("动态Id："+FID+"动态标题："+fheadline);
			ps = conn.prepareStatement("insert into cocmoredb.y_basic_socialgroupsdynamic(FID,fheadline,flogoimage,fmessage,FBillState,fnumber,fpublishtime,FSocialGroupsID,FTypeID) values(?,?,?,?,?,?,?,?,?)");
			ps.setString(1, FID);
			ps.setString(2, fheadline);
			ps.setString(3, flogoimage);
			ps.setString(4, fmessage);
			ps.setInt(5, FBillState);
			ps.setString(6, fnumber);
			ps.setString(7, fpublishtime);
			ps.setString(8, FSocialGroupsID);
			ps.setString(9, FTypeID);
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
