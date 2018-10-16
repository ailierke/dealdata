package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  新闻头条
 * @author CC
 *
 */
public class t_news_headline_18 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select tid as ftid ,title as ftitle,image_url as fimage_url,t_image_url as ft_image_url,news_content as fnews_content,source as fsource,release_time as frelease_time,details_url as fdetails_url,classification as fclassification,headline.describe as fdescribe,type as ftype,create_time as fcreate_time,is_push as fis_push from test.t_news_headline headline");
		String ftid =null;String ftitle =null;String fimage_url =null;
        String ft_image_url =null;String fnews_content =null;String fsource =null;
        String frelease_time =null;String fdetails_url =null;String fclassification =null;
        String fdescribe =null;Integer ftype =null;String fcreate_time =null;
        Integer fis_push =null;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			ftid =rs.getString(1);
			ftitle=rs.getString(2);
			fimage_url =rs.getString(3);
			ft_image_url = rs.getString(4);
			fnews_content = rs.getString(5);
			fsource = rs.getString(6);
			frelease_time = rs.getString(7);
			fdetails_url = rs.getString(8);
			fclassification = rs.getString(9);
			fdescribe = rs.getString(10);
			ftype = rs.getInt(11);
			fcreate_time = rs.getString(12);
			fis_push = rs.getInt(13);
			System.out.println("新闻头条Id："+ftid+"标题："+ftitle);
			ps = conn.prepareStatement("insert into cocmoredb.t_news_headline(ftid,ftitle,fimage_url,ft_image_url,fnews_content,fsource,frelease_time,fdetails_url,fclassification,fdescribe,ftype,fcreate_time,fis_push) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, ftid);
			ps.setString(2, ftitle);
			ps.setString(3, fimage_url);
			ps.setString(4, ft_image_url);
			ps.setString(5, fnews_content);
			ps.setString(6, fsource);
			ps.setString(7, frelease_time);
			ps.setString(8, fdetails_url);
			ps.setString(9, fclassification);
			ps.setString(10, fdescribe);
			ps.setInt(11, ftype);
			ps.setString(12, fcreate_time);
			ps.setInt(13, fis_push);
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
