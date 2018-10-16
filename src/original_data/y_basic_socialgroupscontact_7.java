package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  商会联系信息表
 * @author CC
 *
 */
public class y_basic_socialgroupscontact_7 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select id as fid,phone as tell,address as adress,email as mail ,web_site as uri, business_id  as FSocialGroupID from test.t_info_contact where business_id is not NULL and business_id in(select fid from cocmoredb.y_basic_socialgroups)");
		String FID =null;String tell =null;String adress =null;
        String mail =null;String uri =null;
        String FSocialGroupID =null;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			FID =rs.getString(1);
			tell=rs.getString(2);
			adress =rs.getString(3);
			mail = rs.getString(4);
			uri = rs.getString(5);
			FSocialGroupID = rs.getString(6);
			System.out.println("商会留言Id："+FID+"电话号码："+tell);
			ps = conn.prepareStatement("insert into cocmoredb.y_basic_socialgroupscontact(FID,tell,adress,mail,uri,FSocialGroupID) values(?,?,?,?,?,?)");
			ps.setString(1, FID);
			ps.setString(2, tell);
			ps.setString(3, adress);
			ps.setString(4, mail);
			ps.setString(5, uri);
			ps.setString(6, FSocialGroupID);
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
