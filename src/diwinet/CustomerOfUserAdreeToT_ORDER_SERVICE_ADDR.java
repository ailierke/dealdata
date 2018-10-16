package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.DBUtil;
/**
 * 客户的绑定净水机的地址到预约收货地址中去
 * <p>描述：
 * 		一、查询用户所有绑定的净水机地址信息
 * 		二、对比地址信息中是否已经有存在，如果已经有存在的就不加，如果没有就加入
 * </p>
 * @author	jiangxing
 */
public class CustomerOfUserAdreeToT_ORDER_SERVICE_ADDR {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(false);//设置成手动提交
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		Statement statement2 = null;
		/**
		 * 查询老数据中绑定了设备，并且不是关注用户，并且系统用户信息存在的信息 ,基础资料查询出来
		 */
		ResultSet  rs1 = statement.executeQuery("select u.yhid,c.yhmc,c.lxdh,b.sfbh,b.dsbh,b.qxbh,b.sfmc,b.dsmc,b.qxmc,b.jtdz,b.khjsjbh "
				+ "			from T_WP_CUSTOMER_BANDING b "
				+ "left join T_SERVICE_POS_CONSUMER c ON c.khbh = b.khbh "
				+ "left join T_SYS_USER u ON u.yhid = c.yhid "
				+ "where "
				+ "c.khbh is not null "
				+ "and b.sfbh is not null "
				+ "and b.dsbh is not null "
				+ "and b.qxbh  is not null "
				+ "and b.jtdz is not null "
				+ "and u.yhid is not null");
		Long yhid = null; String yhmc = null;//用户id//用户名称
		String lxdh = null;Long sfbh = null;//联系电话//省份编号
		String sfmc = null;Long dsbh= null;//省份名称//地市编号
		String dsmc= null;Long qxbh= null;//地市名称//区县编号
		String qxmc= null;String jtdz = null;//区县名称//具体地址
		Long khjsjbh = null;
		ResultSet  rs2 = null;
		while(rs1.next()){
			try {
				yhid = rs1.getLong(1);yhmc = rs1.getString(2);
				lxdh = rs1.getString(3);sfbh = rs1.getLong(4);
				dsbh = rs1.getLong(5);qxbh = rs1.getLong(6);
				sfmc = rs1.getString(7);dsmc = rs1.getString(8);
				qxmc = rs1.getString(9);jtdz = rs1.getString(10);
				khjsjbh = rs1.getLong(11);
				rs2 =  statement1.executeQuery("select * from T_ORDER_SERVICE_ADDR where lxdh='"+lxdh+"' and sfbh = "+sfbh+" And dsbh = "+dsbh+" And qxbh = "+qxbh+" And xxdz like '%"+jtdz+"%' And shrm = '"+yhmc+"'");
				if(rs2.next()){
					continue;
				}
				statement2 = conn.createStatement();
				//插入网点客户信息生成khbh
			
				statement2.executeUpdate("INSERT INTO T_ORDER_SERVICE_ADDR (YHBH,SHRM,SFBH,DSBH,QXBH,SFMC,DSMC,QXMC,XXDZ,LXDH,"
						+ "SFMR,SFYX,TJRQ,XGRQ) VALUES ("+yhid+",'"+yhmc+"',"+sfbh+","+dsbh+","+qxbh+",'"+sfmc+"','"+dsmc+"','"+qxmc+"','"+jtdz+"','"+lxdh+"','0',1,"
						+ "NOW(),NOW())");
				System.out.println("客户净水机编号："+khjsjbh+" 用户id："+yhid+" 添加成功！");
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				System.out.println("客户净水机编号："+khjsjbh+" 用户id："+yhid+" 添加失败！");
			}
				//提交
				conn.commit();
		}
		rs1.close();
		statement.close();
		statement1.close();
		statement2.close();
		conn.close();
	}
}
