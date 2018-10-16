package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TWpBrand;
import diwinet.wp.vo.TWpInfo;
import diwinet.wp.vo.WpCustomerBanding;
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
public class childWpToParentWp1 {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = null;
		StringBuilder sql1 = null;
		List<WpCustomerBanding> wps = dbUtil.queryForList("select * from T_WP_CUSTOMER_BANDING where ppmc is null or jsjmc is null ",WpCustomerBanding.class);
		//查询经销商添加的品牌信息
		if(wps!=null&&wps.size()>0){
			WpCustomerBanding wp = null;
			for(int i =0 ;i<wps.size();i++){
				conn.setAutoCommit(false);
				try {
					TWpInfo info = null;
					TWpBrand brand  = null;
					wp = wps.get(i);
					sql1 = new StringBuilder("update T_WP_CUSTOMER_BANDING set");
					if(wp.getJsjmc()==null||"".equals(wp.getJsjmc())){
						sql = "select * from T_WP_INFO where jsjbh ="+wp.getJsjbh();
						 info = dbUtil.findFirst(TWpInfo.class, sql);
						if(info!=null&&info.getCpxh()!=null){
							sql1.append(" JSJMC = '"+info.getCpxh()+"' ,");
						}
					}
					if(wp.getPpmc()==null||"".equals(wp.getPpmc())){
						sql = "select * from T_WP_BRAND where ppbh = "+wp.getPpbh();
						 brand = dbUtil.findFirst(TWpBrand.class, sql);
						if(brand!=null&&brand.getPpmc()!=null){
							sql1.append(" PPMC = '"+brand.getPpmc()+"' ,");
						}
					}
					
					if(brand==null&&info==null){
						continue;
					}
					sql1 = new StringBuilder(sql1.substring(0, sql1.length()-1));
					sql1.append(" where KHJSJBH = "+wp.getKhjsjbh());
					System.out.println(sql1.toString());
					dbUtil.update(sql1.toString());
					System.out.println("客户编号为："+wp.getKhjsjbh()+"更新成功");
					
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
