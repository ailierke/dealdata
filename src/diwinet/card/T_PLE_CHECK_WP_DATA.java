package diwinet.card;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TLeasePay;
import diwinet.wp.vo.WpCustomerBanding;
import util.DBUtil;
import util.DBUtilsTemplate;
import util.DateUtils;
/**
 * <p>���⣺��龻ˮ������</p>
 * <p>������</p>
 * <p>Copyright��Copyright(c) 2018 diwinet</p>
 * <p>���ڣ�2018��6��11��</p>
 * @author	xing.jiang
 */
public class T_PLE_CHECK_WP_DATA {
	public static void main(String[] args) throws SQLException, IOException {
		List<String>  sensors = new ArrayList<>();
//		sensors.add("7090171278008842040");
//		sensors.add("7090170378008839161");
//		sensors.add("7090171178008868005");
//		sensors.add("7090171178008842043");
//		sensors.add("7090171178008893014");
//		sensors.add("7090171178008812022");
//		sensors.add("7090171178008879049");
//		sensors.add("7090171178008880045");
//		sensors.add("7090171178008823004");
//		sensors.add("7090171178008804027");
//		sensors.add("7090171278008860044");
//		sensors.add("7090170678008858019");
//		sensors.add("7090170678008878017");
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		//��ѯԴ��������� ��ЧƷ�Ƶ��������ˮ��
		//Դ����
		List<WpCustomerBanding> wpList = dbUtil.queryForList(" select * from T_WP_CUSTOMER_BANDING where ppbh = 1000509 and khsyms = 4 ",WpCustomerBanding.class);
		//�۶�
//		List<WpCustomerBanding> wpList = dbUtil.queryForList(" select * from T_WP_CUSTOMER_BANDING where ppbh = 1000485 and khsyms = 4 ",WpCustomerBanding.class);
		StringBuilder sb = null;
		List<LeaseWpVo> leaseWpList = new ArrayList<LeaseWpVo>();
		//��ѯ��������ӵ�Ʒ����Ϣ
		if(wpList!=null&&wpList.size()>0){
			WpCustomerBanding wp = null;
			Long khjsjbh = null;
			for(int i =0 ;i<wpList.size();i++){
				conn.setAutoCommit(false);
				try {
					wp = wpList.get(i);
					khjsjbh = wp.getKhjsjbh();
					
					sb = new StringBuilder(" select i.sbtm,b.khjsjbh,i.sbbh,r.cjsj,r.dqsj,m.syq from  ");
					sb.append(" T_SENSOR_INFO i inner join T_WP_CUSTOMER_BANDING b on i.sbbh = b.sbbh and b.KHJSJBH = ? ");
					sb.append(" inner join T_PMS_WP_CUSTOMER_PRICE p on p.khjsjbh = b.KHJSJBH  ");
					sb.append(" inner join T_PMS_FIX_PRICE_MODE m on m.zfbh = p.zfbh  ");
					sb.append(" inner join  T_LEASE_USER_REMAIN r on r.khjsjbh = b.khjsjbh ");
					LeaseWpVo leaseWp = dbUtil.findFirst(LeaseWpVo.class,sb.toString(),khjsjbh);
//					if(sensors.contains(leaseWp.getSbtm())) {
//						continue;
//					}
					Integer syq = leaseWp.getSyq();
					Integer zsl = 0;
					sb = new StringBuilder(" select * from T_LEASE_PAY where khjsjbh = ? and czzt = 1  ");
					
					List<TLeasePay> payList  = dbUtil.queryForList(sb.toString(), TLeasePay.class,khjsjbh);
					if(payList == null) {
						zsl = syq;
						
					}else {
						Double zgmsl = payList.stream().mapToDouble(p ->{
							if(p.getGmdw().equals("��")) {
								return p.getGmsl()*30;
							}else if(p.getGmdw().equals("��")) {
								return p.getGmsl()*365;
							}else {
								return p.getGmsl();
							}
								 
						}).sum();
						zsl = zgmsl.intValue() + syq;
					}
					Calendar c = Calendar.getInstance();
					c.setTime(leaseWp.getCjsj());
					c.add(Calendar.DAY_OF_YEAR, zsl);
					
					if(!DateUtils.getShortCompactString(leaseWp.getDqsj()).equals(DateUtils.getShortCompactString(c.getTime()))) {
						leaseWp.setSjdqsj(c.getTime());
						leaseWpList.add(leaseWp);
					}
				} catch (Exception e) {
					conn.rollback();
					System.out.println("�ͻ���ˮ�����:��"+khjsjbh+"��ͬ���ͻ���ҵģʽʧ��	");
					e.printStackTrace();
				}
			}
			
			
			if(leaseWpList != null) {
				leaseWpList.forEach( leaseWp -> {
					System.out.println("�豸���룺"+ leaseWp.getSbtm()+" �Ƿ�һ����"+DateUtils.getShortCompactString(leaseWp.getDqsj()).equals(DateUtils.getShortCompactString(leaseWp.getSjdqsj()))+
							" ����ʱ�䣺" + ""+DateUtils.getShortCompactString(leaseWp.getDqsj())+"���������"+DateUtils.getShortCompactString(leaseWp.getSjdqsj())+" ���˶����� ��"+DateUtils.betweenDays(leaseWp.getDqsj(), leaseWp.getSjdqsj()));
//					updateDue(dbUtil,leaseWp);
					
				});
			}
			System.out.println("������ˮ������Ϣ��"+wpList.size()+"��");
//			conn.commit();
		}else{
			System.out.println("��ˮ������Ϣkhsyms���Ѵ���,��ɴ���");
		}
	}

	private static void updateDue(DBUtilsTemplate dbUtil, LeaseWpVo leaseWp) {
		try {
			dbUtil.update("UPDATE T_LEASE_USER_REMAIN SET DQSJ = ? WHERE KHJSJBH = ? AND DQSJ = ?", new Object[] {leaseWp.getSjdqsj(),leaseWp.getKhjsjbh(),leaseWp.getDqsj()});
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		try {
			dbUtil.insert("INSERT INTO T_LEASE_USER_REMAIN_HIS (KHJSJBH, SYZLL, GXSJ, DQSJ) VALUES (?, NULL, now(), ?) ", new Object[] {leaseWp.getKhjsjbh(),leaseWp.getDqsj()});
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
			
		}
		
	}
}
