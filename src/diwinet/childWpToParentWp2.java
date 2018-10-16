package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TServiceCompanyBrand;
import diwinet.wp.vo.TServicePosBrand;
import diwinet.wp.vo.TWpBrand;
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
public class childWpToParentWp2 {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = null;
		List<TServiceCompanyBrand> brands = dbUtil.queryForList("select * from T_SERVICE_COMPANY_BRAND",TServiceCompanyBrand.class);
		//查询经销商添加的品牌信息
		if(brands!=null&&brands.size()>0){
			TServiceCompanyBrand brand = null;
			String rightPpmc = null;
			Long zjid = null;
			for(int i =0 ;i<brands.size();i++){
				conn.setAutoCommit(false);
				try {
					brand = brands.get(i);
					rightPpmc = brand.getPpmc();
					zjid = brand.getZjid();
					sql = "select * from T_WP_BRAND where ppmc = ?";
					TWpBrand wpBrand = dbUtil.findFirst(TWpBrand.class, sql, rightPpmc);
					if(wpBrand!=null&&wpBrand.getPpbh()!=null){
						sql = "update T_SERVICE_COMPANY_BRAND set ppbh = "+wpBrand.getPpbh() +" where zjid = "+zjid;
						dbUtil.update(sql);
						System.out.println("新品牌编号为：【"+wpBrand.getPpbh()+"】替代子表品牌编号为：【"+brand.getPpbh()+"】成功");
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
