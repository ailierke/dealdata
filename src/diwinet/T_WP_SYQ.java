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
public class T_WP_SYQ {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		conn.setAutoCommit(false);
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		//�۶�  1000243�����գ�1000030
		List<Map<String,Object>> companys = dbUtil.queryForMapList("select b.*,m.syq ,m.zfmsmc from T_WP_COMPANY_INVITE i left join T_SERVICE_COMPANY c on c.fwsbh = i.fwsbh left join T_SERVICE_POS p on p.fwsbh = c.fwsbh left join T_SERVICE_POS_CONSUMER pc on pc.wdbh=p.wdbh INNER JOIN T_WP_CUSTOMER_BANDING b on b.khbh = pc.khbh inner join T_PMS_WP_CUSTOMER_PRICE pr on pr.khjsjbh = b.khjsjbh inner join  T_PMS_FIX_PRICE_MODE m on m.zfbh = pr.zfbh where i.csbh = 1000243 and i.sfty =1 and i.sfyx = 1 and b.khsyms in (3,4)");
		for(Map<String,Object> m : companys){
			Long khjsjbh = (Long) m.get("khjsjbh");
			Integer syq = (Integer) m.get("syq");
			String zfmc = (String)m.get("zfmsmc");
			LeaseUserRemain remain = dbUtil.findFirst(LeaseUserRemain.class,"select * from T_LEASE_USER_REMAIN where khjsjbh = ?",khjsjbh);
			if(remain == null&&syq!=null&&syq.intValue()!=0){
				System.out.println("�ͻ���ˮ�����Ϊ��"+khjsjbh+"�����ڱ����"+syq);
			}else if(remain == null&&(syq==null||syq.intValue()==0)){
				System.out.println("�ͻ���ˮ�����Ϊ��"+khjsjbh+"������δ�����ok");
			}else{
				Integer y = dbUtil.queryForDoubleInt("select sum(gmsl) from T_LEASE_PAY where khjsjbh = ? and czzt = 1 and gmdw ='��' ",new Object[]{khjsjbh});
				Integer t = dbUtil.queryForDoubleInt("select sum(gmsl) from T_LEASE_PAY where khjsjbh = ? and czzt = 1 and gmdw ='��' ",new Object[]{khjsjbh});
				
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
						System.out.println("�ͻ���ˮ���Լ����Ϊ��"+khjsjbh+" "+DateUtils.getChineseFullString(wp.getGmsj())+"���������"+mid);
					}else{
						System.out.println("ˮ���ѱ����");
					}
				}else{
					System.out.println("�ͻ���ˮ���Լ����Ϊ��"+khjsjbh+" "+DateUtils.getChineseFullString(wp.getGmsj())+"���������"+mid);
				}
			}
			
		}
	}
}
