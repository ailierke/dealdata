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
 * member表获取图片的url然后生成图片和缩略图 /cocmore
 * @author CC
 *
 */
public class Y_basic_member_img {
	public static void main(String[] args) throws SQLException {
//		Connection conn = DBUtil.getConnection();
//		conn.setAutoCommit(true);
//		Statement statement = conn.createStatement();
//		Statement statement1 = conn.createStatement();
//		
//		ResultSet  rs = statement.executeQuery("select fid,fheadimage from cocmoredb.y_basic_member");
//		String fid =null;
//		String headimage = null;
//		while(rs.next()){
//			 String imgurl=null;
//			 fid = rs.getString(1);
//			 headimage = rs.getString(2);
//			 InputStream stream = null;
//			System.out.println("主建："+fid+"头像地址"+headimage);
//				if(headimage!=null&&headimage.contains("http://yunzo.oss.aliyuncs.com/member/")){
//					String logoimage = "member"+headimage.substring(headimage.lastIndexOf("/"), headimage.length());
//					try {
//						stream = AliyunOSSUtils.downloadFile(logoimage);
//						if(stream==null){
//							imgurl = "http://yunzo.oss.aliyuncs.com/cocmore_product/addressbook_avatar.png";
//						}else{
//							Map<String,Object> srcMap = AliyunOSSUtils.upload2ImgToAliyun(stream, headimage, IMGSize.X88.value());
//							if((boolean)(srcMap.get("success"))==true){
//								imgurl =(String)srcMap.get("imgsrc")+",";
//							}else{
//								imgurl = "http://yunzo.oss.aliyuncs.com/cocmore_product/addressbook_avatar.png";
//							}
//						}
//						
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					statement1.executeUpdate("update cocmoredb.y_basic_member set fheadimage='"+imgurl+"' where fid ='"+fid+"'");
//				}
//				
//		}
//		rs.close();
//		statement.close();
//		statement1.close();
//		conn.close();
	}
}
