package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TWpBrand;
import diwinet.wp.vo.TWpFilterelement;
import diwinet.wp.vo.TWpInfo;
import diwinet.wp.vo.WpCustomerBanding;
import util.DBUtil;
import util.DBUtilsTemplate;
/**
 * 
 * <p>标题：将新增的净水机信息全部同步到原来的父表</p>
 * <p>描述：净水机类型khsyms</p>
 * <p>
 * 首先查出update T_WP_CUSTOMER_BANDING set yxms=0 where yxms is null  将所有的yxms 设置为营销模式 0--传统模式 1--物业模式
 * 
 * 程序运行找出原来的select  * from T_WP_CUSTOMER_BANDING where khsyms is  null 
 * 然后查出净水机类型赋值给净水机绑定表
 * </p>
 * <p>Copyright：Copyright(c) 2016 diwinet</p>
 * <p>日期：2016年3月28日</p>
 * @author	jiangxing
 */
public class T_WP_CUSTOMER_BANDING_JSJLX {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = null;
		List<WpCustomerBanding> wpList = dbUtil.queryForList("select * from T_WP_CUSTOMER_BANDING where khsyms is  null",WpCustomerBanding.class);
		//查询经销商添加的品牌信息
		if(wpList!=null&&wpList.size()>0){
			WpCustomerBanding wp = null;
			Long jsjbh = null;
			Long khjsjbh = null;
			Integer jsjlx = null;
			for(int i =0 ;i<wpList.size();i++){
				conn.setAutoCommit(false);
				try {
					wp = wpList.get(i);
					jsjbh = wp.getJsjbh();
					khjsjbh = wp.getKhjsjbh();
					sql = "select * from T_WP_INFO where jsjbh = "+jsjbh;
					TWpInfo wpInfo = dbUtil.findFirst(TWpInfo.class,sql);
					/**
					 * 循环品牌下的净水机
					 */
					if(wpInfo!=null){
						jsjlx = wpInfo.getJsjlx();
						sql = "update T_WP_CUSTOMER_BANDING set khsyms ="+jsjlx+" where khjsjbh ="+khjsjbh;
					}else{
						sql = "update T_WP_CUSTOMER_BANDING set khsyms ="+0+" where khjsjbh ="+khjsjbh;
					}
					dbUtil.update(sql);
				} catch (Exception e) {
					conn.rollback();
					System.out.println("客户净水机编号:【"+khjsjbh+"】同步客户商业模式失败	");
					e.printStackTrace();
				}
			}
			conn.commit();
			System.out.println("共处理净水机绑定信息："+wpList.size()+"个");
		}else{
			System.out.println("净水机绑定信息khsyms都已存在,完成处理");
		}
	}
}
