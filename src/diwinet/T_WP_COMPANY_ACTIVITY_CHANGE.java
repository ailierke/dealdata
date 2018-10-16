package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TWpCompany;
import diwinet.wp.vo.TWpInfo;
import diwinet.wp.vo.User;
import diwinet.wp.vo.UserRole;
import diwinet.wp.vo.WpCompanyLeasePrice;
import util.DBUtil;
import util.DBUtilsTemplate;
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
public class T_WP_COMPANY_ACTIVITY_CHANGE {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		conn.setAutoCommit(false);
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		List<TWpCompany> companys = dbUtil.queryForList("select  * from  T_WP_COMPANY",TWpCompany.class);
		Integer sfyx = null;
		Long sfbh = null;
		Long dsbh = null;
		Long qxbh = null;
		String sfmc = null;
		String dsmc = null;
		String qxmc = null;
		String jtdz = null;
		Long wybh = null;
		String wymc = null;
		int zfmsCount = 0;//资费模式生成条数
		//查询经销商添加的品牌信息
		if(companys!=null&&companys.size()>0){
			for(TWpCompany company : companys){
				try {
					System.out.println("开始处理厂商："+company.getCsbh()+"厂商名称："+company.getCsmc());
					sfyx = company.getSfyx();
					
					List<User> users = dbUtil.queryForList("select u.* from T_SYS_USER u left join T_COMPANY_USER cu on u.yhid = cu.rybh where cu.xtjs = 1 AND cu.CSBH = "+company.getCsbh(),User.class);
					//获取厂商管理员的省市区县地址
					if(users!=null&&!users.isEmpty()){
						try {
							sfbh = new Long(users.get(0).getSfbh());
							dsbh = new Long(users.get(0).getDsbh());
							qxbh = new Long(users.get(0).getQxbh());
							sfmc = (users.get(0).getSfmc()==null?"":users.get(0).getSfmc());
							dsmc = (users.get(0).getDsmc()==null?"":users.get(0).getDsmc());
							qxmc = (users.get(0).getQxmc()==null?"":users.get(0).getQxmc());
							jtdz = (users.get(0).getJtdz()==null?"":users.get(0).getJtdz());
						} catch (Exception e) {
							
						}
						
					}
					wymc = (company.getCsmc()==null?"":company.getCsmc())+"(运营商)";
					//营销模式2、服务商类型1 添加厂商运营商
					wybh = dbUtil.insert("insert into T_SERVICE_COMPANY(FWSMC,LXR,FWLX,FWSJJ,FWSDZ,FWSRX,SHZT,SFYX,CJSJ,SFBH,DSBH,QXBH,SFMC,DSMC,QXMC,JTDZ,YXMS,FWSLX,SFCZ)"
							+ " VALUES('"+wymc+"','"+(company.getLxr()==null?"":company.getLxr())+"','滤芯更换','"+(company.getCsmc()==null?"":company.getCsmc())+"(运营商)'"
									+ ",'"+(company.getCsdz()==null?"":company.getCsdz())+"','"+(company.getLxdh()==null?"":company.getLxdh())+"',1,"+sfyx+",now(),"+sfbh+","+dsbh+","+qxbh+",'"+sfmc+"'"
											+ ",'"+dsmc+"','"+qxmc+"','"+jtdz+"',2,0,1)");
					//添加厂商运营商管理员  解除原来t_service_company_user yhi唯一性限制 由程序控制
					if(users!=null&&!users.isEmpty()){
						for(User us : users){
							UserRole role = dbUtil.findFirst(UserRole.class,"select * from T_SYS_USER_ROLE where yhid = "+us.getYhid()+" AND jsid = 8");
							if(role==null){
								dbUtil.insert("insert into  T_SYS_USER_ROLE(YHID,JSID,JSMC,SFYX,CJSJ) VALUES("+us.getYhid()+",8,'经销商管理员',1,now())");
							}
							dbUtil.insert("insert into T_SERVICE_COMPANY_USER(RYBH,fwsbh,CJSJ,SFYX) VALUES("+us.getYhid()+","+wybh+",now(),"+sfyx+")");
						}
					}
					//添加厂商和运营商的关系  
					dbUtil.insert("insert into T_PMS_WP_COMPANY_RELATION(CSBH,YYSBH,CJSJ,SFYX) VALUES("+company.getCsbh()+","+wybh+",now(),1)");
					//生成厂商资费定价信息T_PMS_PRICE_MODE_BRAND(资费水机绑定)  T_PMS_FIX_PRICE_MODE
					List<WpCompanyLeasePrice> prices = dbUtil.queryForList("select * from T_WP_COMPANY_LEASE_PRICE WHERE CSBH = "+company.getCsbh(),WpCompanyLeasePrice.class);
					if(prices!=null&&!prices.isEmpty()){
						//ZFFS  0--自定义充值（流量） 1--定额充值（按期数多期充值）循环资费 
						//jflx 0、流量 1、年卡
						String zfxlh  = null;
						String zfmsmc = null;
						Integer zffs = null;
						Integer jflx = null;
						Integer syq = 0;//试用期默认为0
						Double djje = null;
						Date sxrq = null;//生效时间，生效日期
						Integer yjsz = null;
						Integer zfzt = 0;//资费状态使用中
						Double sqzdcz = null;
						Long ppbh = null;
						Long jsjbh = null;
						//服务商不限制，型号需要限制
						Long zfbh = null;
						for(WpCompanyLeasePrice price : prices){
							TWpInfo wp = dbUtil.findFirst(TWpInfo.class,"select * from T_WP_INFO where JSJBH = "+price.getJsjbh());
							if(wp==null){
								System.out.println("此净水机已经被删除,净水机编号为："+price.getJsjbh()+":"+price.getJsjmc());
								continue;
							}
							 jsjbh = wp.getJsjbh();
							 ppbh = wp.getPpbh();
							 zfxlh = createModeNum(wybh, dbUtil);
							 TWpInfo info =  dbUtil.findFirst(TWpInfo.class,"select * from T_WP_INFO where JSJBH="+price.getJsjbh());
							 if(info!=null){
								 zfmsmc = (info.getCpmc()==null?"":info.getCpmc())+(info.getCpxh()==null?"":info.getCpxh());
							 }else{
								 zfmsmc = zfxlh+"定价(数据处理)";
							 }
							 jflx = price.getJffs();
							 zffs = 0;//旧数据跑出来只有一期所有默认为0 
							 //现在月卡、流量都没有半价金额
							 sxrq = (price.getTjsj()==null?new Date():price.getTjsj());
							 String sxrqStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sxrq);
							 yjsz = price.getYjsz();
							 sqzdcz = price.getZdcze();
							 if(jflx == 0){//流量
								 if(price.getSxsj()!=null&&(price.getSxsj().before(new Date()))){
										djje = price.getDjje1();
									} else {
										djje = price.getDjje();
									}
							 }else if(jflx == 1){//年费
								 if(price.getSxsj()!=null&&(price.getSxsj().before(new Date()))){
									 djje = (price.getDjje1()==null?0:price.getDjje1())*12;//月卡变为年
									} else {
										 djje = (price.getDjje()==null?0:price.getDjje())*12;//月卡变为年
									}
							 }else{
								 System.out.println("原计费方式不存在，检查数据净水机:["+price.getJsjbh()+"]的定价");
								 continue;
							 }
							 //生成资费定价模式  数据跑过来的不在显示
							zfbh =  dbUtil.insert("insert into T_PMS_FIX_PRICE_MODE(ZFXLH,WYBH,WYMC,ZFMSMC,ZFFS,JFLX,SYQ,SXSJ,YJSZ,ZFZT,SQZDCZ,TJSJ,XHXZ,FWSXZ,SFXS) "
							 		+ "VALUES('"+zfxlh+"',"+wybh+",'"+wymc+"','"+zfmsmc+"',"+zffs+","+jflx+","+syq+",'"+sxrqStr+"',"+yjsz+","+zfzt+","+sqzdcz+",'"+sxrqStr+"',1,0,0)");
							//设置资费定价层级BZDW  0-L  1-年   2-月
							dbUtil.insert("insert into T_PMS_PRICE_STANDARD(ZFBH,RANK,BZDJ,BZDW) VALUES("+zfbh+",1,"+djje+","+jflx+")");
							
							//生成对应的净水机型号限制
							dbUtil.insert("insert into T_PMS_PRICE_MODE_BRAND(ZFBH,PPBH,JSJBH,SFYX,GXSJ) VALUES("+zfbh+","+ppbh+","+jsjbh+",1,now())");
							zfmsCount++;
						}
					}
				} catch (Exception e) {
					System.out.println("厂商处理失败:"+company.getCsbh());
					e.printStackTrace();
				}
			}
		}
		conn.commit();
		System.out.println("总共生成资费模式："+zfmsCount+"条;");
	}
	
	private static String createModeNum(Long wybh,DBUtilsTemplate dbUtil) throws SQLException{
		String mc = "";
		Integer num = dbUtil.queryForInt("SELECT COUNT(1) FROM T_PMS_FIX_PRICE_MODE WHERE WYBH = " + wybh);
		for (int i = 0; i < 4 - num.toString().length(); i++) {
			mc += "0";
		}
		mc += (num + 1);
		return "P" + wybh + mc;
	}
}
