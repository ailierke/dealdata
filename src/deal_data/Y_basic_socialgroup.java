package deal_data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import util.DBUtil;
import util.EncryptionForTellPhone;
import util.IMGSize;

/**
 * Y_basic_socialgroup表图片的更换
 * @author ailierke
 *
 */
public class Y_basic_socialgroup {
	public static void main(String[] args) throws SQLException {
		Connection conn = DBUtil.getConnection();
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();

		ResultSet  rs = statement.executeQuery("select fid,logo from cocmoredb.y_basic_socialgroups");
		String fid =null;
		String logo = null;
		while(rs.next()){
			 String imgurl=null;
			 fid = rs.getString(1);
			 logo = rs.getString(2);
			 InputStream stream = null;
			System.out.println("主建："+fid+"头像地址"+logo);
				if(logo!=null&&logo.contains("http://yunzo.oss.aliyuncs.com/icon/")){
					String logoimage = "icon"+logo.substring(logo.lastIndexOf("/"), logo.length());
					//						stream = AliyunOSSUtils.downloadFile(logoimage);
					if(stream==null){
						imgurl = "http://yunzo.oss.aliyuncs.com/cocmore_product/addressbook_avatar.png";
					}else{
//							Map<String,Object> srcMap = AliyunOSSUtils.upload2ImgToAliyun(stream, logo, IMGSize.X88.value());
//							if((boolean)(srcMap.get("success"))==true){
//								imgurl =(String)srcMap.get("imgsrc")+",";
//							}else{
//								imgurl = "http://yunzo.oss.aliyuncs.com/cocmore_product/addressbook_avatar.png";
//							}
					}
					statement1.executeUpdate("update cocmoredb.y_basic_socialgroups set logo='"+imgurl+"' where fid ='"+fid+"'");
				}
				
		}
		rs.close();
		statement.close();
		statement1.close();
		conn.close();
	}
}
