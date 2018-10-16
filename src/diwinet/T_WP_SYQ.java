package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.LeaseUserRemain;
import diwinet.wp.vo.WpCustomerBanding;
import util.DBUtil;
import util.DBUtilsTemplate;
import util.DateUtils;
/**
 * 
 * <p>标题:厂商活动统一运营商批处理程序</p>
 * <p>描述：
 * 	1、查询出所有的厂商，创建厂商运营商、生成厂商运营账号；
 *  2、查询所有厂商定价，反向生成资费定价，净水机限制，资费定价不可看。不限制经销商
 *  3、根据t_wp_customer_banding 表sbbh 不为null khsyms 为3、4  yxms 为0传统模式 
 *  查询该净水机编号资费定价是否存在，如果存在建立 客户水机和资费定价中间关系表
 * </p>
 * 
 * <p>Copyright：Copyright(c) 2016 diwinet</p>
 * <p>日期：2016年3月28日</p>
 * @author	jiangxing
 */
public class T_WP_SYQ {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		conn.setAutoCommit(false);
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		//帝尔  1000243爱华普：1000030
		List<Map<String,Object>> companys = dbUtil.queryForMapList("select b.*,m.syq ,m.zfmsmc from T_WP_COMPANY_INVITE i left join T_SERVICE_COMPANY c on c.fwsbh = i.fwsbh left join T_SERVICE_POS p on p.fwsbh = c.fwsbh left join T_SERVICE_POS_CONSUMER pc on pc.wdbh=p.wdbh INNER JOIN T_WP_CUSTOMER_BANDING b on b.khbh = pc.khbh inner join T_PMS_WP_CUSTOMER_PRICE pr on pr.khjsjbh = b.khjsjbh inner join  T_PMS_FIX_PRICE_MODE m on m.zfbh = pr.zfbh where i.csbh = 1000243 and i.sfty =1 and i.sfyx = 1 and b.khsyms in (3,4)");
		for(Map<String,Object> m : companys){
			Long khjsjbh = (Long) m.get("khjsjbh");
			Integer syq = (Integer) m.get("syq");
			String zfmc = (String)m.get("zfmsmc");
			LeaseUserRemain remain = dbUtil.findFirst(LeaseUserRemain.class,"select * from T_LEASE_USER_REMAIN where khjsjbh = ?",khjsjbh);
			if(remain == null&&syq!=null&&syq.intValue()!=0){
				System.out.println("客户净水机编号为："+khjsjbh+"试用期变更："+syq);
			}else if(remain == null&&(syq==null||syq.intValue()==0)){
				System.out.println("客户净水机编号为："+khjsjbh+"试用期未变更：ok");
			}else{
				Integer y = dbUtil.queryForDoubleInt("select sum(gmsl) from T_LEASE_PAY where khjsjbh = ? and czzt = 1 and gmdw ='月' ",new Object[]{khjsjbh});
				Integer t = dbUtil.queryForDoubleInt("select sum(gmsl) from T_LEASE_PAY where khjsjbh = ? and czzt = 1 and gmdw ='天' ",new Object[]{khjsjbh});
				
				Integer all = t+(y/12)*365;
				Date cjsj = remain.getCjsj();
				Date dqsj = remain.getDqsj();
				if(dqsj==null||cjsj==null){
					continue;
				}
				Integer betweenDay = DateUtils.betweenDays(dqsj,cjsj);

				Integer mid = betweenDay-all-(syq==null?0:syq);
				WpCustomerBanding wp = dbUtil.findFirst(WpCustomerBanding.class,"select * from T_WP_CUSTOMER_BANDING where khjsjbh = ?",khjsjbh);
				if(mid!=-1&&mid!=0&&mid!=1){
//					System.out.println(khjsjbh);
					if(wp!=null){
						System.out.println("客户净水机自己编号为："+khjsjbh+" "+DateUtils.getChineseFullString(wp.getGmsj())+"相差天数："+mid);
					}else{
						System.out.println("水机已被解绑");
					}
				}else{
					System.out.println("客户净水机自己编号为："+khjsjbh+" "+DateUtils.getChineseFullString(wp.getGmsj())+"相差天数："+mid);
				}
			}
			
		}
	}
}
