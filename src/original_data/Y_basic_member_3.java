package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 * 导入原生产系统会员
 * @author CC
 *
 */
public class Y_basic_member_3 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		Statement statement1 = conn.createStatement();
		//如果这个用户不属于任何商会或者他所在商会id在商会表中不存在，删掉这个用户
		statement.executeUpdate("delete  from t_member_info where business_id not in (select id from t_pri_business) or business_id is null");
		statement.close();
		//如果positionid在position表中不存在就设置为NULL 
		statement=conn.createStatement();
		statement.executeUpdate("update t_member_info set position_id = NULL where position_id not in ( select id from  t_info_position )");
		statement.close();
		/**
		 * 将商会的职位信息插入到cocmoredb中
		 */
		statement1 = conn.createStatement();
		
		ResultSet rs = statement1.executeQuery("select id as fid,tel as FMobilePhone,password as FPassword ,url as FHeadImage,real_name as FName , business_id as FSocialGroupsID, CASE sex WHEN 1 THEN 1 WHEN 0 THEN 1 ELSE 0  END as FSex, email as FEmail,1 as fishidephone, address as FSite,5 as fbillstate ,0 as isAdmin, 1 as FNumber from t_member_info");
		String fid =null;String FMobilePhone =null;String FPassword =null;
		String FHeadImage=null;String FName =null;String FSocialGroupsID =null;
		String FSex =null;;String FEmail=null;
		int FIsHidePhone =1;String FSite = null;int fbillstate =5;int isAdmin=0;String FNumber ="1";
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			fid =rs.getString(1);
			FMobilePhone=rs.getString(2);
			FPassword =rs.getString(3);
			FHeadImage = rs.getString(4);
			FName = rs.getString(5);
			FSocialGroupsID=rs.getString(6);
			FSex =rs.getString(7);
			FEmail=rs.getString(8);
			FSite=rs.getString(10);
			System.out.println("用户："+fid+"名称："+FName);
			ps = conn.prepareStatement("insert into cocmoredb.y_basic_member(fid,FMobilePhone,FPassword,FHeadImage,FName,FSocialGroupsID,FSex,FEmail,FSite,FIsHidePhone,fbillstate,isAdmin,FNumber) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, fid);
			ps.setString(2, FMobilePhone);
			ps.setString(3, FPassword);
			ps.setString(4, FHeadImage);
			ps.setString(5, FName);
			ps.setString(6, FSocialGroupsID);
			ps.setString(7, FSex);
			ps.setString(8, FEmail);
			ps.setString(9, FSite);
			ps.setInt(10, FIsHidePhone);
			ps.setInt(11, fbillstate);
			ps.setInt(12, isAdmin);
			ps.setString(13, FNumber);
			ps.executeUpdate();
			ps.close();
			count++;
		}
		rs.close();
		statement1.close();
		System.out.println("获取总条数："+count);
		conn.close();
	}
	public static void main(String[] args) throws SQLException {
		dealData();
	}
}
