package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  商会会员留言表
 * @author CC
 *
 */
public class y_basic_socialgroupsguestbook_6 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select id as FID,cocmsg as FMessage,creator as FGuestBookId,business_id as FSocialGroupsID ,create_time as FGuestBookTime  from test.t_info_cocmsg where business_id is not NULL and business_id in(select fid from cocmoredb.y_basic_socialgroups)  and creator in (select fid from cocmoredb.y_basic_member)");
		String FID =null;String FMessage =null;String FGuestBookId =null;
        String FSocialGroupsID =null;String FGuestBookTime =null;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			FID =rs.getString(1);
			FMessage=rs.getString(2);
			FGuestBookId =rs.getString(3);
			FSocialGroupsID = rs.getString(4);
			FGuestBookTime = rs.getString(5);
			System.out.println("商会留言Id："+FID+"标题："+FMessage);
			ps = conn.prepareStatement("insert into cocmoredb.y_basic_socialgroupsguestbook(FID,FMessage,FGuestBookId,FSocialGroupsID,FGuestBookTime) values(?,?,?,?,?)");
			ps.setString(1, FID);
			ps.setString(2, FMessage);
			ps.setString(3, FGuestBookId);
			ps.setString(4, FSocialGroupsID);
			ps.setString(5, FGuestBookTime);
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
