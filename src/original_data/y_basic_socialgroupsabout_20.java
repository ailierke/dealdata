package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  关于商会
 * @author CC
 *
 */
public class y_basic_socialgroupsabout_20 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select id as fid,name as title, description as content ,order_no as sequenceNumber,business_id as FSocialGroupID from t_info_about_coc where business_id in (select fid from cocmoredb.y_basic_socialgroups)");
		String fid =null;String title =null;String content =null;
		Integer sequenceNumber =null;
        String FSocialGroupID =null;
        
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			fid =rs.getString(1);
			title=rs.getString(2);
			content =rs.getString(3);
			sequenceNumber = rs.getInt(4);
			FSocialGroupID = rs.getString(5);
			System.out.println("商会关于Id："+fid+"标题："+title);
			ps = conn.prepareStatement("insert into cocmoredb.y_basic_socialgroupsabout(fid,title,content,sequenceNumber,FSocialGroupID) values(?,?,?,?,?)");
			ps.setString(1, fid);
			ps.setString(2, title);
			ps.setString(3, content);
			ps.setInt(4, sequenceNumber);
			ps.setString(5, FSocialGroupID);
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
