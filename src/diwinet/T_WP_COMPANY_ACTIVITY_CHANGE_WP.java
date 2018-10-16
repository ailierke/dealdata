package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TPmsPriceModeBrand;
import diwinet.wp.vo.TPmsWpCustomerPrice;
import diwinet.wp.vo.WpCustomerBanding;
import util.DBUtil;
import util.DBUtilsTemplate;
/**
 * 
 * <p>标题:厂商活动统一运营商批处理程序</p>
 * <p>描述：此程序必须同步经销商系统上线、运营商系统上线，厂商系统上线同步更新跑数据。并且在T_WP_COMPANY_ACTIVITY_CHANGE.java程序跑批量之后在进行跑数据
 *  根据t_wp_customer_banding 表sbbh 不为null khsyms 为3、4  yxms 为0传统模式 
 *  通过jsjbh 查询 T_PMS_PRICE_MODE_BRAND 如果存在直接将zfbh和khjsjbh关系建立
 *  查询该净水机编号资费定价是否存在，如果存在建立 客户水机和资费定价中间关系表
 * </p>
 * 
 * <p>Copyright：Copyright(c) 2016 diwinet</p>
 * <p>日期：2016年3月28日</p>
 * @author	jiangxing
 */
public class T_WP_COMPANY_ACTIVITY_CHANGE_WP {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		conn.setAutoCommit(false);
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		List<WpCustomerBanding> wpList = dbUtil.queryForList("SELECT  * FROM T_WP_CUSTOMER_BANDING WHERE khsyms IN (3, 4) AND sbbh IS NOT NULL AND yxms = 0",WpCustomerBanding.class);
		StringBuilder sb = new StringBuilder("水机净水机型号未设置资费的有：khjsjbh:");
		StringBuilder sb1 = new StringBuilder("水机净水机型号设置了多个资费的有：khjsjbh:");
		//查询经销商添加的品牌信息
		if(wpList!=null&&wpList.size()>0){
			List<TPmsPriceModeBrand> bmodeBrandList = null;
			for(WpCustomerBanding wp : wpList){
				try {
					bmodeBrandList = dbUtil.queryForList("select * from T_PMS_PRICE_MODE_BRAND where JSJBH = "+wp.getJsjbh(), TPmsPriceModeBrand.class);
					if(bmodeBrandList!=null && !bmodeBrandList.isEmpty()){
						if(bmodeBrandList.size()==1){
							TPmsPriceModeBrand mb  =  bmodeBrandList.get(0);
							TPmsWpCustomerPrice wpPrice =  dbUtil.findFirst(TPmsWpCustomerPrice.class,"select * from  T_PMS_WP_CUSTOMER_PRICE where khjsjbh = "+wp.getKhjsjbh());
							if(wpPrice==null){
								dbUtil.insert("insert into T_PMS_WP_CUSTOMER_PRICE(ZFBH,KHJSJBH,SFYX,CJSJ) VALUES("+mb.getZfbh()+","+wp.getKhjsjbh()+",1,now())");
								System.out.println("添加资费定价"+wp.getKhjsjbh());
							}else{
								continue;
							}
						}else{
							sb1.append(" "+wp.getKhjsjbh());
							System.out.println("水机净水机型号设置了多个资费有：khjsjbh"+wp.getKhjsjbh());
						}
					}else{
						sb.append(" "+wp.getKhjsjbh());
						System.out.println("水机净水机型号未设置资费：khjsjbh"+wp.getKhjsjbh());
					}
				} catch (Exception e) {
					System.out.println("wp处理失败:"+wp.getKhjsjbh());
					e.printStackTrace();
				}
			}
		}
		System.out.println(sb1.toString());
		System.out.println(sb.toString());
		conn.commit();
	}
}
