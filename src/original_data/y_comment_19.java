package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  新闻头条 评论表
 * @author CC
 *
 */
public class y_comment_19 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select tid as fid,comment_content as FContents,release_time as FTime,newsid as FForeignID,mid as FMemberID,'3101' as FType from test.t_news_comment where mid in (select FID from cocmoredb.y_basic_member)");
		String fid =null;String FContents =null;String FTime =null;
        String FForeignID =null;String FMemberID =null;String FType =null;
        Integer fis_push =null;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			fid =rs.getString(1);
			FContents=rs.getString(2);
			FTime =rs.getString(3);
			FForeignID = rs.getString(4);
			FMemberID = rs.getString(5);
			FType = rs.getString(6);
			System.out.println("新闻头条评论Id："+fid+"用户id："+FMemberID);
			ps = conn.prepareStatement("insert into cocmoredb.y_comment(fid,FContents,FTime,FForeignID,FMemberID,FType) values(?,?,?,?,?,?)");
			ps.setString(1, fid);
			ps.setString(2, FContents);
			ps.setString(3, FTime);
			ps.setString(4, FForeignID);
			ps.setString(5, FMemberID);
			ps.setString(6, FType);
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
