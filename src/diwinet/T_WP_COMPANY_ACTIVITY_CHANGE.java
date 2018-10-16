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
 * <p>����:���̻ͳһ��Ӫ�����������</p>
 * <p>������
 * 	1����ѯ�����еĳ��̣�����������Ӫ�̡����ɳ�����Ӫ�˺ţ�
 *  2����ѯ���г��̶��ۣ����������ʷѶ��ۣ���ˮ�����ƣ��ʷѶ��۲��ɿ��������ƾ�����
 *  3������t_wp_customer_banding ��sbbh ��Ϊnull khsyms Ϊ3��4  yxms Ϊ0��ͳģʽ 
 *  ��ѯ�þ�ˮ������ʷѶ����Ƿ���ڣ�������ڽ��� �ͻ�ˮ�����ʷѶ����м��ϵ��
 * </p>
 * 
 * <p>Copyright��Copyright(c) 2016 diwinet</p>
 * <p>���ڣ�2016��3��28��</p>
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
		int zfmsCount = 0;//�ʷ�ģʽ��������
		//��ѯ��������ӵ�Ʒ����Ϣ
		if(companys!=null&&companys.size()>0){
			for(TWpCompany company : companys){
				try {
					System.out.println("��ʼ�����̣�"+company.getCsbh()+"�������ƣ�"+company.getCsmc());
					sfyx = company.getSfyx();
					
					List<User> users = dbUtil.queryForList("select u.* from T_SYS_USER u left join T_COMPANY_USER cu on u.yhid = cu.rybh where cu.xtjs = 1 AND cu.CSBH = "+company.getCsbh(),User.class);
					//��ȡ���̹���Ա��ʡ�����ص�ַ
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
					wymc = (company.getCsmc()==null?"":company.getCsmc())+"(��Ӫ��)";
					//Ӫ��ģʽ2������������1 ��ӳ�����Ӫ��
					wybh = dbUtil.insert("insert into T_SERVICE_COMPANY(FWSMC,LXR,FWLX,FWSJJ,FWSDZ,FWSRX,SHZT,SFYX,CJSJ,SFBH,DSBH,QXBH,SFMC,DSMC,QXMC,JTDZ,YXMS,FWSLX,SFCZ)"
							+ " VALUES('"+wymc+"','"+(company.getLxr()==null?"":company.getLxr())+"','��о����','"+(company.getCsmc()==null?"":company.getCsmc())+"(��Ӫ��)'"
									+ ",'"+(company.getCsdz()==null?"":company.getCsdz())+"','"+(company.getLxdh()==null?"":company.getLxdh())+"',1,"+sfyx+",now(),"+sfbh+","+dsbh+","+qxbh+",'"+sfmc+"'"
											+ ",'"+dsmc+"','"+qxmc+"','"+jtdz+"',2,0,1)");
					//��ӳ�����Ӫ�̹���Ա  ���ԭ��t_service_company_user yhiΨһ������ �ɳ������
					if(users!=null&&!users.isEmpty()){
						for(User us : users){
							UserRole role = dbUtil.findFirst(UserRole.class,"select * from T_SYS_USER_ROLE where yhid = "+us.getYhid()+" AND jsid = 8");
							if(role==null){
								dbUtil.insert("insert into  T_SYS_USER_ROLE(YHID,JSID,JSMC,SFYX,CJSJ) VALUES("+us.getYhid()+",8,'�����̹���Ա',1,now())");
							}
							dbUtil.insert("insert into T_SERVICE_COMPANY_USER(RYBH,fwsbh,CJSJ,SFYX) VALUES("+us.getYhid()+","+wybh+",now(),"+sfyx+")");
						}
					}
					//��ӳ��̺���Ӫ�̵Ĺ�ϵ  
					dbUtil.insert("insert into T_PMS_WP_COMPANY_RELATION(CSBH,YYSBH,CJSJ,SFYX) VALUES("+company.getCsbh()+","+wybh+",now(),1)");
					//���ɳ����ʷѶ�����ϢT_PMS_PRICE_MODE_BRAND(�ʷ�ˮ����)  T_PMS_FIX_PRICE_MODE
					List<WpCompanyLeasePrice> prices = dbUtil.queryForList("select * from T_WP_COMPANY_LEASE_PRICE WHERE CSBH = "+company.getCsbh(),WpCompanyLeasePrice.class);
					if(prices!=null&&!prices.isEmpty()){
						//ZFFS  0--�Զ����ֵ�������� 1--�����ֵ�����������ڳ�ֵ��ѭ���ʷ� 
						//jflx 0������ 1���꿨
						String zfxlh  = null;
						String zfmsmc = null;
						Integer zffs = null;
						Integer jflx = null;
						Integer syq = 0;//������Ĭ��Ϊ0
						Double djje = null;
						Date sxrq = null;//��Чʱ�䣬��Ч����
						Integer yjsz = null;
						Integer zfzt = 0;//�ʷ�״̬ʹ����
						Double sqzdcz = null;
						Long ppbh = null;
						Long jsjbh = null;
						//�����̲����ƣ��ͺ���Ҫ����
						Long zfbh = null;
						for(WpCompanyLeasePrice price : prices){
							TWpInfo wp = dbUtil.findFirst(TWpInfo.class,"select * from T_WP_INFO where JSJBH = "+price.getJsjbh());
							if(wp==null){
								System.out.println("�˾�ˮ���Ѿ���ɾ��,��ˮ�����Ϊ��"+price.getJsjbh()+":"+price.getJsjmc());
								continue;
							}
							 jsjbh = wp.getJsjbh();
							 ppbh = wp.getPpbh();
							 zfxlh = createModeNum(wybh, dbUtil);
							 TWpInfo info =  dbUtil.findFirst(TWpInfo.class,"select * from T_WP_INFO where JSJBH="+price.getJsjbh());
							 if(info!=null){
								 zfmsmc = (info.getCpmc()==null?"":info.getCpmc())+(info.getCpxh()==null?"":info.getCpxh());
							 }else{
								 zfmsmc = zfxlh+"����(���ݴ���)";
							 }
							 jflx = price.getJffs();
							 zffs = 0;//�������ܳ���ֻ��һ������Ĭ��Ϊ0 
							 //�����¿���������û�а�۽��
							 sxrq = (price.getTjsj()==null?new Date():price.getTjsj());
							 String sxrqStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sxrq);
							 yjsz = price.getYjsz();
							 sqzdcz = price.getZdcze();
							 if(jflx == 0){//����
								 if(price.getSxsj()!=null&&(price.getSxsj().before(new Date()))){
										djje = price.getDjje1();
									} else {
										djje = price.getDjje();
									}
							 }else if(jflx == 1){//���
								 if(price.getSxsj()!=null&&(price.getSxsj().before(new Date()))){
									 djje = (price.getDjje1()==null?0:price.getDjje1())*12;//�¿���Ϊ��
									} else {
										 djje = (price.getDjje()==null?0:price.getDjje())*12;//�¿���Ϊ��
									}
							 }else{
								 System.out.println("ԭ�Ʒѷ�ʽ�����ڣ�������ݾ�ˮ��:["+price.getJsjbh()+"]�Ķ���");
								 continue;
							 }
							 //�����ʷѶ���ģʽ  �����ܹ����Ĳ�����ʾ
							zfbh =  dbUtil.insert("insert into T_PMS_FIX_PRICE_MODE(ZFXLH,WYBH,WYMC,ZFMSMC,ZFFS,JFLX,SYQ,SXSJ,YJSZ,ZFZT,SQZDCZ,TJSJ,XHXZ,FWSXZ,SFXS) "
							 		+ "VALUES('"+zfxlh+"',"+wybh+",'"+wymc+"','"+zfmsmc+"',"+zffs+","+jflx+","+syq+",'"+sxrqStr+"',"+yjsz+","+zfzt+","+sqzdcz+",'"+sxrqStr+"',1,0,0)");
							//�����ʷѶ��۲㼶BZDW  0-L  1-��   2-��
							dbUtil.insert("insert into T_PMS_PRICE_STANDARD(ZFBH,RANK,BZDJ,BZDW) VALUES("+zfbh+",1,"+djje+","+jflx+")");
							
							//���ɶ�Ӧ�ľ�ˮ���ͺ�����
							dbUtil.insert("insert into T_PMS_PRICE_MODE_BRAND(ZFBH,PPBH,JSJBH,SFYX,GXSJ) VALUES("+zfbh+","+ppbh+","+jsjbh+",1,now())");
							zfmsCount++;
						}
					}
				} catch (Exception e) {
					System.out.println("���̴���ʧ��:"+company.getCsbh());
					e.printStackTrace();
				}
			}
		}
		conn.commit();
		System.out.println("�ܹ������ʷ�ģʽ��"+zfmsCount+"��;");
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
