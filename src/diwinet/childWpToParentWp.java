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
 * <p>���⣺�������ľ�ˮ����Ϣȫ��ͬ����ԭ���ĸ���</p>
 * <p>������
 * 	1����ѯ�����е�T_DMS_WP_BRAND �޳��̱�ŵ�,��Ϊ������������ӵ�Ʒ�ƣ���Ʒ�ơ��ͺš���о��ѭ����ӵ�������
 * 	   ��ӳɹ�ʱ����Ʒ�ơ��ͺš���о���±�š��ڴ�ͬʱ����������������ϵ����
 *  2������������ӵ�T_WP_BRAND��,��ȡƷ�Ʊ�ŵ���������
 * </p>
 * <p>Copyright��Copyright(c) 2016 diwinet</p>
 * <p>���ڣ�2016��3��28��</p>
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
		//��ѯ��������ӵ�Ʒ����Ϣ
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
					//ͬ���������ӱ�ppbh�й�ϵ�����������
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
					System.out.println("��Ʒ�Ʊ��Ϊ����"+parentPpbh+"������ӱ�Ʒ�Ʊ��Ϊ����"+childPpbh+"���ɹ�");
					
					//��ѯԭ�ӱ���Ʒ�Ʊ�Ŷ�Ӧ�ľ�ˮ����ţ�ͬ�����µ�������
					sql = "select * from T_DMS_WP_INFO where ppbh = "+childPpbh;
					List<TWpInfo> wpInfos = dbUtil.queryForList(sql,TWpInfo.class);
					/**
					 * ѭ��Ʒ���µľ�ˮ��
					 */
					if(wpInfos!=null&&wpInfos.size()>0){
						Long childJsjbh = null;
						Long parentJsjbh = null;
						for(TWpInfo wpInfo : wpInfos){
							childJsjbh = wpInfo.getJsjbh();
							wpInfo.setJsjbh(null);
							sql = "insert into T_WP_INFO (CSBH,PPBH,CPMC,CPXH,CPHH,GZYL,SYWZ,CPGX,JSLL,JSGX,CPJG,BZTJ,CPZL,CPYT,DESY,GZWD,SFGD,HDGL,SYSM,BZNX,QTXN,CJSJ,SFYX,SFSX) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
							parentJsjbh = dbUtil.insert(sql, new Object[]{ wpInfo.getCsbh(),parentPpbh,//ʹ�ø����е�Ʒ�Ʊ��
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
							System.out.println("����Ʒ�ƣ�"+parentPpbh+"�¾�ˮ�����Ϊ����"+parentJsjbh+"������ӱ�ˮ�����Ϊ����"+childJsjbh+"���ɹ�");
							
							//��ѯԭ�ӱ���Ʒ�Ʊ�Ŷ�Ӧ�ľ�ˮ����ţ�ͬ�����µ�������
							sql = "select * from T_DMS_WP_FILTERELEMENT where jsjbh = "+childJsjbh;
							List<TWpFilterelement> filters = dbUtil.queryForList(sql,TWpFilterelement.class);
							/**
							 * ѭ����ˮ���µ���о
							 */
							if(filters!=null&&filters.size()>0){
								Long childLxbh = null;
								Long parentLxbh  = null;
								for(TWpFilterelement filter : filters){
									childLxbh = filter.getLxbh();
									filter.setLxbh(null);
									sql = "INSERT INTO T_WP_FILTERELEMENT (jsjbh,lxwz,lxzl,jszll,cpjg,cpzl,zlbh,SFYX) VALUES (?,?,?,?,?,?,?,?)";
									parentLxbh = dbUtil.insert(sql,new Object[]{parentJsjbh//ʹ�ø���ˮ�����
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
									System.out.println("����о���Ϊ����"+parentLxbh+"������ӱ���о���Ϊ����"+childLxbh+"���ɹ�");
								}
							}else{
								System.out.println("�ӱ���û�о�������ӵ��Զ�����о��Ϣ");
							}
						}
					}
				} catch (Exception e) {
					conn.rollback();
					System.out.println("��Ʒ�Ʊ��Ϊ:��"+childPpbh+"��ͬ��ʧ��");
					e.printStackTrace();
				}
				conn.commit();
			}
			System.out.println("�������������Ʒ�ƣ�"+brands.size()+"��");
		}else{
			System.out.println("�ӱ���û�о�������ӵ��Զ���Ʒ����Ϣ");
		}
		
	}
}
