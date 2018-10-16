package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import util.DBUtil;
/**
 * <p>标题：原来这个表没有绑定地址信息,这里主要是增加这几个列，然后跑程序来更新</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年12月8日</p>
 * @author	jiangxing
 */
public class T_WP_CUSTOMER_BANDING_ADRESS {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		Statement statement2 = conn.createStatement();//T_SERVICE_POS_CONSUMER
		ResultSet  rs1 = statement.executeQuery("select khjsjbh,khbh from  T_WP_CUSTOMER_BANDING where sfbh is null AND dsbh is null AND qxbh is null");
		Long khjsjbh = null;
		Long  khbh = null;
		String sfbh=null;
		String dsbh = null;
		String qxbh = null;
		String sfmc = null;
		String dsmc = null;
		String qxmc = null;
		String jtdz = null;
		ResultSet  rs  = null;
		while(rs1.next()){
			khjsjbh = rs1.getLong(1);
			khbh = rs1.getLong(2);
				if(khjsjbh!=null&&khbh!=null){
					rs = statement1.executeQuery("select sfbh,dsbh,qxbh,sfmc,dsmc,qxmc,jtdz from T_SERVICE_POS_CONSUMER where khbh = "+khbh);
					if(rs.next()){
						sfbh=rs.getString(1);  
						dsbh = rs.getString(2);
						qxbh = rs.getString(3);
						sfmc = rs.getString(4);
						dsmc = rs.getString(5);
						qxmc = rs.getString(6);
						jtdz = rs.getString(7);
						statement2.executeUpdate("update  T_WP_CUSTOMER_BANDING  set sfbh ='"+sfbh+"',dsbh ='"+dsbh+"',qxbh ='"+qxbh+"',sfmc ='"+sfmc+"',dsmc ='"+dsmc+"',qxmc ='"+qxmc+"',jtdz ='"+jtdz+"' where khjsjbh = "+khjsjbh);
						System.out.println("客户净水机编号："+khjsjbh+" sfbh ='"+sfbh+"',dsbh ='"+dsbh+"',qxbh ='"+qxbh+"',sfmc ='"+sfmc+"',dsmc ='"+dsmc+"',qxmc ='"+qxmc+"',jtdz ='"+jtdz+" 更新成功！");
					}
				}
		}
		rs1.close();
		rs.close();
		statement.close();
		statement1.close();
		statement2.close();
		conn.close();
}
}
