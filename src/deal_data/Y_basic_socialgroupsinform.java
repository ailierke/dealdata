package deal_data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import util.DBUtil;
import util.IMGSize;

/**
 * �̻�֪ͨ��flogoimage ��ͼƬѭ���ϴ����������ݿ�����
 * @author CC
 *
 */
public class Y_basic_socialgroupsinform {
	public static void main(String[] args) throws SQLException {
		Connection conn = DBUtil.getConnection();
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();

		ResultSet  rs = statement.executeQuery("select fid,flogoimage from cocmoredb.y_basic_socialgroupsinform");
		String fid =null;
		String headimage = null;
		String imgurl="";
		while(rs.next()){
			fid = rs.getString(1);
			headimage = rs.getString(2);
			InputStream stream = null;
			System.out.println("������"+fid+"�̻�֪ͨͼƬ��ַ"+headimage);
				if(headimage!=null&&headimage.contains("http://yunzo.oss.aliyuncs.com/icon")){//������������崻�������ж��Ƿ��Ѿ��޸�ͨ��url���Ƿ����icon��û�о��������޸ĵġ�
					String logoimage = "icon"+headimage.substring(headimage.lastIndexOf("/"), headimage.length());
//					stream = AliyunOSSUtils.downloadFile(logoimage);
//					try {
//						Map<String,Object> srcMap = AliyunOSSUtils.upload2ImgToAliyun(stream, headimage, IMGSize.X88.value());
//						if((boolean)(srcMap.get("success"))==true){
//							imgurl =(String)srcMap.get("imgsrc")+",";
//						}
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
					statement1.executeUpdate("update cocmoredb.y_basic_socialgroupsinform set flogoimage='"+imgurl+"'where fid ='"+fid+"'");
				}


			//		tel=EncryptionForTellPhone.encryptToABC(tel);
			//		statement1.executeUpdate("update y_basic_imaccount set fimtel = '"+tel+"' where fid ='"+fid+"'");
		}
		rs.close();
		statement.close();
		conn.close();
	}
}
