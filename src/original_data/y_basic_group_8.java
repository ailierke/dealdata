package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBUtil;
/**
 *  IM聊天群组
 * @author CC
 *
 */
public class y_basic_group_8 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select tid as FID,group_name as FGroupName,icon_url as FGroupHeadImage,max_member as FGroupMaxPeople,business_id as businessId from test.t_group_property where business_id is not NULL and business_id in(select fid from cocmoredb.y_basic_socialgroups)");
		String FID =null;String FGroupName =null;String FGroupHeadImage =null;
        String FGroupMaxPeople =null;String businessId =null;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			FID =rs.getString(1);
			FGroupName=rs.getString(2);
			FGroupHeadImage =rs.getString(3);
			FGroupMaxPeople = rs.getString(4);
			businessId = rs.getString(5);
			System.out.println("群组Id："+FID+"群组名字："+FGroupName);
			ps = conn.prepareStatement("insert into cocmoredb.y_basic_group(FID,FGroupName,FGroupHeadImage,FGroupMaxPeople,businessId) values(?,?,?,?,?)");
			ps.setString(1, FID);
			ps.setString(2, FGroupName);
			ps.setString(3, FGroupHeadImage);
			ps.setString(4, FGroupMaxPeople);
			ps.setString(5, businessId);
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
