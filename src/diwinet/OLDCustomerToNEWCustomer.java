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
 * 用户所对应的所有客户的订单收货地址 同步到 T_ORDER_SERVICE_ADDR表中  如果相同的yhid 地址已经存在就不用重复加。如果不存在就重复添加
 * <p>描述：
 * 查询老的水管家用户数据、通过T_SENSOR_BINDING表中的地理位置、绑定的净水机型号、T_SYS_USER 表的基本资料,
 * 		一、查询所有的客户x
 * 		二、和客户净水机绑定信息
 * 		三、首次使用时间信息表
 * 		四、复位信息表
 * 		五、监控信息表
 * 	          六、订单信息表
 * </p>
 * @author	jiangxing
 */
public class OLDCustomerToNEWCustomer {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(false);//设置成手动提交
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		Statement statement2 = null;
		Long wdbh = 241L;//云网虚拟网点编号
		/**
		 * 查询老数据中绑定了设备，并且不是关注用户，并且系统用户信息存在的信息 ,基础资料查询出来
		 */
		ResultSet  rs1 = statement.executeQuery("SELECT"
				+ " u.yhid ,u.yhmc,u.lxdh,wpinfo.jsjbh,wpinfo.cpxh as jsjmc,wp.ppbh,wp.ppmc,"
				+ "sb.sfbh,sb.dsbh,sb.qxbh,sb.sfmc,sb.dsmc,sb.qxmc,sb.xxdz,sb.zbx,"
				+ "sb.zby,sb.sbbh,u.cjsj from T_SENSOR_USER_BINDING ub "
				+ "LEFT JOIN T_SENSOR_BINDING sb ON sb.SBBH = ub.SBBH "
				+ "LEFT JOIN T_SYS_USER u ON u.yhid = ub.yhbh "
				+ "LEFT JOIN T_DMS_WP_BRAND wp ON sb.ppbh = wp.ppbh "
				+ "LEFT JOIN T_DMS_WP_INFO wpinfo ON wpinfo.jsjbh = sb.jsjbh "
				+ "WHERE ub.yhlx = 1 and u.yhid is not null and wpinfo.jsjbh is not null "
				+ "and wp.ppbh is not null  and  sb.sbbh not in "
				+ " (select sbbh from T_WP_CUSTOMER_BANDING where sbbh is not null )");
		Long yhid = null; String yhmc = null;//用户id//用户名称
		String lxdh = null;Long jsjbh = null;//联系电话//净水机编号
		String jsjmc = null;Long ppbh= null;//净水机名称//品牌编号
		String ppmc= null;Long sfbh= null;//品牌名称//省份编号
		Long dsbh= null;Long qxbh= null;//地市编号//区县编号
		String sfmc= null;String dsmc= null;//省份名称//地市名称
		String qxmc = null;String xxdz = null;//区县名称//详细地址
		Float zbx = null;Float zby = null;//坐标x//坐标y
		Long sbbh = null;Date cjsj = null;
		String cjsjStr = null;
		Integer khbh =null;
		Integer khjsjbh = null;
		ResultSet rs = null;
		while(rs1.next()){
			yhid = rs1.getLong(1);yhmc = rs1.getString(2);
			lxdh = rs1.getString(3);jsjbh = rs1.getLong(4);
			jsjmc = rs1.getString(5);ppbh = rs1.getLong(6);
			ppmc = rs1.getString(7);sfbh = rs1.getLong(8);
			dsbh = rs1.getLong(9);qxbh = rs1.getLong(10);
			sfmc = rs1.getString(11);dsmc = rs1.getString(12);
			qxmc = rs1.getString(13);xxdz = rs1.getString(14);
			zbx = rs1.getFloat(15);zby = rs1.getFloat(16);
			sbbh = rs1.getLong(17);cjsj = rs1.getTimestamp(18);
			//转换首次使用时间表所需要的时间 (绑定设备之后其实是不需要使用这个作为过期的一个标准，直接到报送数据查看首次使用时间)
			try{
				cjsjStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cjsj) ;
			}catch(Exception e){
				cjsjStr =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			}
			statement2 = conn.createStatement();
			//插入网点客户信息生成khbh
			if(sbbh!=null&&yhid!=null){
				try {
					statement2.executeUpdate("insert into T_SERVICE_POS_CONSUMER(WDBH,YHID,YHMC,LXDH,SFBH,DSBH,QXBH,SFMC,DSMC,QXMC,JTDZ,SFYX) values ("+wdbh+","+yhid+",'"+yhmc+"','"+lxdh+"',"+sfbh+","+dsbh+","+qxbh+",'"+sfmc+"','"+dsmc+"','"+qxmc+"','"+xxdz+"',1)",Statement.RETURN_GENERATED_KEYS);
					rs = statement2.getGeneratedKeys(); 
					if( rs.next() ) { 
						khbh = rs.getInt(1);
					}
					System.out.println("客户编号："+khbh+" 设备编号："+sbbh+"  用户id："+yhid+" 添加成功！");
					//插入客户净水机绑定信息
					statement2.executeUpdate("INSERT INTO T_WP_CUSTOMER_BANDING(PPBH,JSJBH,KHBH,WDBH,GMSJ,JSJMC,PPMC,TJSJ,ZBX,ZBY,SFBDSGJ,SBBH,SFMR) values("+ppbh+","+jsjbh+","+khbh+","+wdbh+",'"+cjsjStr+"','"+jsjmc+"','"+ppmc+"',NOW(),"+zbx+","+zby+",1,"+sbbh+",1)",Statement.RETURN_GENERATED_KEYS);
					rs = statement2.getGeneratedKeys(); 
					if( rs.next() ) { 
						khjsjbh = rs.getInt(1);
					}
					System.out.println("客户净水机编号："+khjsjbh+" 设备编号："+sbbh+"  用户id："+yhid+" 添加成功！");
					//插入首次使用时间
					statement2.executeUpdate("insert into T_WP_FIRSTUSE_INFO(KHJSJBH,SCSYSJ) values("+khjsjbh+",'"+cjsjStr+"')");
					System.out.println("客户净水机编号为：【"+khjsjbh+"】插入首次使用时间成功");
					//复位表信息
					statement2.executeUpdate("update T_WP_FILTERELEMENT_RESET set KHJSJBH = "+khjsjbh+" where SBBH = "+sbbh);
					System.out.println("设备编号为"+sbbh+"服务表更新khjsjbh：【"+khjsjbh+"】插入首次使用时间成功");
					//监控信息表
					statement2.executeUpdate("update T_SENSOR_MONITOR set KHJSJBH = "+khjsjbh+" where SBBH = "+sbbh);
					System.out.println("设备编号为"+sbbh+"监控表更新khjsjbh：【"+khjsjbh+"】监控信息表");
					//订单信息表
					statement2.executeUpdate("update T_ORDER_INFO set KHJSJBH = "+khjsjbh+" where SBBH = "+sbbh);
					System.out.println("设备编号为"+sbbh+"订单表表更新khjsjbh：【"+khjsjbh+"】订单信息表");
				} catch (Exception e) {
					conn.rollback();
					e.printStackTrace();
					System.out.println("客户编号："+khbh+" 设备编号："+sbbh+"  用户id："+yhid+" 添加失败！");
				}
				//提交
				conn.commit();
			}
		}
		rs1.close();
		statement.close();
		statement1.close();
		statement2.close();
		conn.close();
	}
}
