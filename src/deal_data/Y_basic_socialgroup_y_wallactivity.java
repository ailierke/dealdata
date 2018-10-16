package deal_data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.UUID;

import util.DBUtil;

/**
 *
 * @author ailierke
 *
 */
public class Y_basic_socialgroup_y_wallactivity {
	public static void main(String[] args) throws SQLException {
		Connection conn = DBUtil.getConnection();
		Statement statement = conn.createStatement();
		PreparedStatement statement1 = conn.prepareStatement("insert into cocmoredbTest.y_wallactivity(FID,FTheme,FTheUrl,FCommerceID,FState,FCreateTime,ftype) values(?,?,?,?,?,?,?)");
		ResultSet  rs = statement.executeQuery("select fid,fname from cocmoredbTest.y_basic_socialgroups");
		String fid =null;
		String fname = null;
		String url="http://114.215.201.200:8086/cocmore-web/webApp/shangqiang.html";
		String fomart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.sql.Date(new java.util.Date().getTime()));
		String ftype="默认类型";
		while(rs.next()){
			fid = rs.getString(1);
			fname = rs.getString(2);
			if(fname==null||fname.equals("")){
				fname="商会默认主题";
			}
			System.out.println(fid+"成功");
//			FID ,FTheme,FTheUrl,FCommerceID,FState,FCreateTime,ftype
			statement1.setString(1, UUID.randomUUID().toString());
			statement1.setString(2, fname);
			statement1.setString(3, url);
			statement1.setString(4, fid);
			statement1.setInt(5,14);
			statement1.setString(6, fomart);
			statement1.setString(7, ftype);
			statement1.executeUpdate();				
		}
		rs.close();
		statement.close();
		statement1.close();
		conn.close();
	}
}
