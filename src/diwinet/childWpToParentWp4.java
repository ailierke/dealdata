package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.ServicePosConsumer;
import diwinet.wp.vo.User;
import util.DBUtil;
import util.DBUtilsTemplate;
/**
 * 
 * <p>标题：将新增的净水机信息全部同步到原来的父表</p>
 * <p>描述：
 * 	1、查询出所有的T_DMS_WP_BRAND 无厂商编号的,即为经销商自主添加的品牌，将品牌、型号、滤芯都循环添加到父表中
 * 	   添加成功时生成品牌、型号、滤芯的新编号、在此同时进行其他库的外键关系更新
 *  2、将此数据添加到T_WP_BRAND中,获取品牌编号的新主键。
 * </p>
 * <p>Copyright：Copyright(c) 2016 diwinet</p>
 * <p>日期：2016年3月28日</p>
 * @author	jiangxing
 */
public class childWpToParentWp4 {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = null;
		StringBuilder sql1 = null;
		List<ServicePosConsumer> consumers = dbUtil.queryForList("select * from T_SERVICE_POS_CONSUMER where LXDH is null or YHMC is null ",ServicePosConsumer.class);
		//查询经销商添加的品牌信息
		if(consumers!=null&&consumers.size()>0){
			ServicePosConsumer consumer = null;
			Long yhid = null;
			for(int i =0 ;i<consumers.size();i++){
				conn.setAutoCommit(false);
				try {
					consumer = consumers.get(i);
					yhid = consumer.getYhid();
					sql = "select * from T_SYS_USER where yhid = ?";
					User user = dbUtil.findFirst(User.class, sql, yhid);
					if(user!=null){
						sql1 = new StringBuilder("update T_SERVICE_POS_CONSUMER set");
						if(consumer.getLxdh()==null||"".equals(consumer.getLxdh())){
							sql1.append(" lxdh = '"+user.getLxdh()+"' ,");
						}
						if(consumer.getYhmc()==null||"".equals(consumer.getYhmc())){
							sql1.append(" yhmc = '"+user.getYhmc()+"' ,");
						}
						sql1 = new StringBuilder(sql1.substring(0, sql1.length()-1));
						sql1.append(" where khbh = "+consumer.getKhbh());
						System.out.println(sql1.toString());
						dbUtil.update(sql1.toString());
						System.out.println("客户编号为："+consumer.getKhbh()+"更新成功");
					}else{
						System.out.println("客户编号为："+consumer.getKhbh()+"不存在");
					}
					
				} catch (Exception e) {
					conn.rollback();
					e.printStackTrace();
				}
				conn.commit();
			}
		}else{
			System.out.println("子表中没有经销商添加的自定义品牌信息");
		}
		
	}
}
