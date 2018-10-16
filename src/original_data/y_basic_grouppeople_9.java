package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  IM聊天群组成员表  查询的时候有点慢
 * @author CC
 *
 */
public class y_basic_grouppeople_9 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select property.tid as FID ,imaccount.fid as FIMID, CASE property.imrole WHEN 1 THEN 0 WHEN 0 THEN 1 ELSE NULL  END as fIscreater, property.nickname as FNickName, temp.tid  as fgroupId from test.t_group_user_property as  property,cocmoredb.y_basic_imaccount imaccount, (select tid ,group_id from test.t_group_property where business_id is not NULL and business_id in(select fid from cocmoredb.y_basic_socialgroups) ) temp where imusername in (select FIMkey from cocmoredb.y_basic_imaccount ) and imaccount.FIMkey=property.imusername and temp.group_id=property.group_id ");
		String FID =null;String FIMID =null;String fIscreater =null;
        String FNickName =null;String fgroupId =null;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			FID =rs.getString(1);
			FIMID=rs.getString(2);
			fIscreater =rs.getString(3);
			FNickName = rs.getString(4);
			fgroupId = rs.getString(5);
			System.out.println("群成员Id："+FIMID+"群组id："+fgroupId);
			ps = conn.prepareStatement("insert into cocmoredb.y_basic_grouppeople(FID,FIMID,fIscreater,FNickName,fgroupId) values(?,?,?,?,?)");
			ps.setString(1, FID);
			ps.setString(2, FIMID);
			ps.setString(3, fIscreater);
			ps.setString(4, FNickName);
			ps.setString(5, fgroupId);
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
