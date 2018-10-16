package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TWpBrand;
import diwinet.wp.vo.TWpFilterelement;
import diwinet.wp.vo.TWpInfo;
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
public class childWpToParentWp {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = null;
//		List<TWpBrand> brands = dbUtil.queryForList("select * from T_DMS_WP_BRAND where csbh is null",TWpBrand.class);
		List<TWpBrand> brands = dbUtil.queryForList("select * from T_DMS_WP_BRAND where csbh =1000001",TWpBrand.class);
		//查询经销商添加的品牌信息
		if(brands!=null&&brands.size()>0){
			TWpBrand brand = null;
			Long childPpbh = null;
			Long parentPpbh = null;
			for(int i =0 ;i<brands.size();i++){
				conn.setAutoCommit(false);
				try {
					brand = brands.get(i);
					childPpbh = brand.getPpbh();
					brand.setPpbh(null);
					sql = "INSERT into T_WP_BRAND (csbh,ppmc,ppls,ppjs,pptd,zcsj,ppzz,ppbz,pplg,sfyx,rksj,SXZM,PYJX,RKRID) VALUES (?,?,?,?,?,?,?,?,?,1,?,?,?,?)";
					parentPpbh = dbUtil.insert(sql,new Object[] { brand.getCsbh(), brand.getPpmc(),brand.getPpls(), brand.getPpjs(), brand.getPptd(),
									brand.getZcsj(),brand.getPpzz(), brand.getPpbz(),brand.getPplg(),brand.getRksj(),brand.getSxzm(),
									brand.getPyjx(),brand.getRkrid() });
					//同步跟新与子表ppbh有关系连得数据外键
					sql = "update T_ORDER_INFO set ppbh = "+parentPpbh +" where ppbh = "+childPpbh;
					dbUtil.update(sql);
					sql = "update T_SENSOR_BINDING set ppbh = "+parentPpbh +" where ppbh = "+childPpbh;
					dbUtil.update(sql);
					sql = "update T_SERVICE_COMPANY_BRAND set ppbh = "+parentPpbh +" where ppbh = "+childPpbh;
					dbUtil.update(sql);
					sql = "update T_SERVICE_POS_BRAND set ppbh = "+parentPpbh +" where ppbh = "+childPpbh;
					dbUtil.update(sql);
					sql = "update T_WP_CUSTOMER_BANDING set ppbh = "+parentPpbh +" where ppbh = "+childPpbh;
					dbUtil.update(sql);
					System.out.println("新品牌编号为：【"+parentPpbh+"】替代子表品牌编号为：【"+childPpbh+"】成功");
					
					//查询原子表中品牌编号对应的净水机编号，同步跟新到父表中
					sql = "select * from T_DMS_WP_INFO where ppbh = "+childPpbh;
					List<TWpInfo> wpInfos = dbUtil.queryForList(sql,TWpInfo.class);
					/**
					 * 循环品牌下的净水机
					 */
					if(wpInfos!=null&&wpInfos.size()>0){
						Long childJsjbh = null;
						Long parentJsjbh = null;
						for(TWpInfo wpInfo : wpInfos){
							childJsjbh = wpInfo.getJsjbh();
							wpInfo.setJsjbh(null);
							sql = "insert into T_WP_INFO (CSBH,PPBH,CPMC,CPXH,CPHH,GZYL,SYWZ,CPGX,JSLL,JSGX,CPJG,BZTJ,CPZL,CPYT,DESY,GZWD,SFGD,HDGL,SYSM,BZNX,QTXN,CJSJ,SFYX,SFSX) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
							parentJsjbh = dbUtil.insert(sql, new Object[]{ wpInfo.getCsbh(),parentPpbh,//使用父表中的品牌编号
									wpInfo.getCpmc(), wpInfo.getCpxh(), wpInfo.getCphh(),
									wpInfo.getGzyl(), wpInfo.getSywz(), wpInfo.getCpgx(),
									wpInfo.getJsll(), wpInfo.getJsgx(), wpInfo.getCpjg(),
									wpInfo.getBztj(), wpInfo.getCpzl(), wpInfo.getCpyt(),
									wpInfo.getDesy(), wpInfo.getGzwd(), wpInfo.getSfgd(),
									wpInfo.getHdgl(), wpInfo.getSysm(), wpInfo.getBznx(),
									wpInfo.getQtxn(),wpInfo.getCjsj(),wpInfo.getSfyx(),wpInfo.getSfsx() });
							sql = "update T_SENSOR_BINDING set jsjbh = "+parentJsjbh +" where jsjbh = "+childJsjbh;
							dbUtil.update(sql);
							sql = "update T_SENSOR_MONITOR set jsjbh = "+parentJsjbh +" where jsjbh = "+childJsjbh;
							dbUtil.update(sql);
							sql = "update T_SERVICE_FILTERPRICE set jsjbh = "+parentJsjbh +" where jsjbh = "+childJsjbh;
							dbUtil.update(sql);
							sql = "update T_SERVICE_WPPRICE set jsjbh = "+parentJsjbh +" where jsjbh = "+childJsjbh;
							dbUtil.update(sql);
							sql = "update T_WP_CUSTOMER_BANDING set jsjbh = "+parentJsjbh +" where jsjbh = "+childJsjbh;
							dbUtil.update(sql);
							sql = "update T_WP_FILTERELEMENT_RESET set jsjbh = "+parentJsjbh +" where jsjbh = "+childJsjbh;
							dbUtil.update(sql);
							sql = "update T_WP_INVENTORY set jsjbh = "+parentJsjbh +" where jsjbh = "+childJsjbh;
							dbUtil.update(sql);
							sql = "update T_WP_PURCHASE_REGISTRATION set jsjbh = "+parentJsjbh +" where jsjbh = "+childJsjbh;
							dbUtil.update(sql);
							sql = "update T_WP_SALE_REGISTRATION set jsjbh = "+parentJsjbh +" where jsjbh = "+childJsjbh;
							dbUtil.update(sql);
							System.out.println("父表品牌："+parentPpbh+"新净水机编号为：【"+parentJsjbh+"】替代子表净水机编号为：【"+childJsjbh+"】成功");
							
							//查询原子表中品牌编号对应的净水机编号，同步跟新到父表中
							sql = "select * from T_DMS_WP_FILTERELEMENT where jsjbh = "+childJsjbh;
							List<TWpFilterelement> filters = dbUtil.queryForList(sql,TWpFilterelement.class);
							/**
							 * 循环净水机下的滤芯
							 */
							if(filters!=null&&filters.size()>0){
								Long childLxbh = null;
								Long parentLxbh  = null;
								for(TWpFilterelement filter : filters){
									childLxbh = filter.getLxbh();
									filter.setLxbh(null);
									sql = "INSERT INTO T_WP_FILTERELEMENT (jsjbh,lxwz,lxzl,jszll,cpjg,cpzl,zlbh,SFYX) VALUES (?,?,?,?,?,?,?,?)";
									parentLxbh = dbUtil.insert(sql,new Object[]{parentJsjbh//使用父表净水机编号
																		,filter.getLxwz(),filter.getLxzl(),filter.getJszll(),filter.getCpjg(),
																		filter.getCpzl(),filter.getZlbh(),filter.getSfyx()});
									sql = "update T_ORDER_FILETERELEMENT_INFO set lxbh = "+parentLxbh +" where lxbh = "+childLxbh;
									dbUtil.update(sql);
									sql = "update T_SENSOR_MONITOR set lxbh = "+parentLxbh +" where lxbh = "+childLxbh;
									dbUtil.update(sql);
									sql = "update T_SERVICE_FILTERPRICE set lxbh = "+parentLxbh +" where lxbh = "+childLxbh;
									dbUtil.update(sql);
									sql = "update T_WP_FILTERELEMENT_RESET set lxbh = "+parentLxbh +" where lxbh = "+childLxbh;
									dbUtil.update(sql);
									System.out.println("新滤芯编号为：【"+parentLxbh+"】替代子表滤芯编号为：【"+childLxbh+"】成功");
								}
							}else{
								System.out.println("子表中没有经销商添加的自定义滤芯信息");
							}
						}
					}
				} catch (Exception e) {
					conn.rollback();
					System.out.println("旧品牌编号为:【"+childPpbh+"】同步失败");
					e.printStackTrace();
				}
				conn.commit();
			}
			System.out.println("共处理经销商添加品牌："+brands.size()+"个");
		}else{
			System.out.println("子表中没有经销商添加的自定义品牌信息");
		}
		
	}
}
