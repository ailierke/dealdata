package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TPleInfoBanding;
import diwinet.wp.vo.TWaterReportHistory;
import diwinet.wp.vo.TWaterReportHistoryRT;
import util.DBUtil;
import util.DBUtilsTemplate;
/**
 * 
 * <p>���⣺�������ľ�ˮ����Ϣȫ��ͬ����ԭ���ĸ���</p>
 * <p>��������ˮ������khsyms</p>
 * <p>
 * ���Ȳ��update T_WP_CUSTOMER_BANDING set yxms=0 where yxms is null  �����е�yxms ����ΪӪ��ģʽ 0--��ͳģʽ 1--��ҵģʽ
 * 
 * ���������ҳ�ԭ����select  * from T_WP_CUSTOMER_BANDING where khsyms is  null 
 * Ȼ������ˮ�����͸�ֵ����ˮ���󶨱�
 * </p>
 * <p>Copyright��Copyright(c) 2016 diwinet</p>
 * <p>���ڣ�2016��3��28��</p>
 * @author	jiangxing
 */
public class T_WP_CUSTOMER_BANDING_RECOVER {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(false);
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		/**
		 * ��ѯ����������ǰ�͹�100��ˮ�Ĳ�����4��2�Ż�û��������100��ˮ���豸
		 */
		ArrayList<String> ignoreList = new ArrayList<String>();
		ArrayList<String> updateList = new ArrayList<String>();
		ArrayList<String> erroList = new ArrayList<String>();
		ArrayList<String> useOffList = new ArrayList<String>();
		List<TPleInfoBanding> dealList = new ArrayList<TPleInfoBanding>();
		try {
			List<TPleInfoBanding> bdList = dbUtil.queryForList("SELECT b.*, IFNULL(r.syzll, 0) + IFNULL(b.cssl, 0) - IFNULL(rt.cssl, 0) AS m,CONCAT(I.xmmc,P.lch,P.jtdwmc,'��') as jtdw"
					+ " FROM T_PLE_INFO_BANDING b LEFT JOIN T_PLE_BUY_REMAIN r ON b.pmbh = r.pmbh LEFT JOIN dwater_all.T_WATER_REPORT_HISTORY_RT rt ON rt.sbtm = b.sbtm "
					+ " INNER JOIN dwater_ple.T_PLE_UNIT_POSITION P ON b.jtdwbh = P.jtdwbh AND P.JTDWZT = 1 INNER JOIN dwater_ple.T_PLE_UNIT_ITEM I ON P.xmbh = I.xmbh "
					+ " AND I.XMZT = 1 INNER JOIN dwater_ple.T_PLE_SERVICE_UNIT u ON u.dwbh = I.dwbh INNER JOIN dwater_ple.T_SENSOR_INFO_PLE sp ON sp.SBBH = b.SBBH "
					+ " WHERE b.dwbh = 326 and b.sbtm in( select b.sbtm from T_PLE_RECHARGE_RECORD r INNER JOIN T_PLE_INFO_BANDING b on b.pmbh = r.pmbh and b.dwbh = 326 and r.czfs = 6 and gmsl =100 group by r.pmbh) "
					+ " HAVING m > 0 ",TPleInfoBanding.class);
			/**
			 * ��ѯ��ʱ����߰�ʱ��ǰ�ϱ������Ƿ���ڰ�ʱ���ʼˮ��
			 * ������ڽ�����һ������
			 * ���������ֶ��޸Ĺ������ּ�¼�ȵ���һ������
			 */
			if(bdList != null && bdList.size() > 0){
				System.out.println("bdList ��"+Arrays.toString(bdList.toArray()));
				TPleInfoBanding bd = null;
				Double cssl = null;
				Double bdcssl = 0d;
				Double wysl = 0d;
				Double ysysl = 0d;
				for(int i = 0;i<bdList.size() ;i++){
					bd = bdList.get(i);
					bdcssl = bd.getCssl(); 
					TWaterReportHistory his = dbUtil.findFirst(TWaterReportHistory.class, "select * from dwater_all.T_WATER_REPORT_HISTORY where sbtm = ? and bssj <=? order by bssj desc ",new Object[]{bd.getSbtm(),bd.getBdsj()});
					if(his != null){
						cssl = his.getCssl();
					}
					String jtdw = bd.getJtdw();
					if((bdcssl.doubleValue() != cssl.doubleValue() )&&
							!(jtdw.contains("23091")||jtdw.contains("25011")||
									jtdw.contains("2304")||jtdw.contains("2007")||jtdw.contains("1905"))
							){
//						System.out.println("�豸���룺��"+bd.getSbtm()+"���󶨳�ʼˮ���Ͱ�ʱ���ϱ�ˮ�������ϣ��ֶ��޸Ĺ�,���Բ���");
						
//						if(){
//							System.out.println("pmbh��"+bd.getPmbh()+"�豸���룺"+bd.getSbtm()+"�豸���룺"+bd.getSbtm()+"���Ե�¥�����ƣ�"+bd.getJtdw());
//						}
						ignoreList.add(bd.getSbtm());
					}else{
						if(bd.getSbtm().equals("7090170978008884125")){
							continue;
						}
						TWaterReportHistoryRT rt = dbUtil.findFirst(TWaterReportHistoryRT.class, "select * from dwater_all.T_WATER_REPORT_HISTORY_RT where sbtm = ?  ",bd.getSbtm());
						if(rt == null){
							if(his == null){
								ysysl = 0d;
								//ˮ��δ��ˮ���޸İ󶨱�İ󶨳�ʼˮ��
							}
						}else{
							Double dqcssl = rt.getCssl();
							ysysl = dqcssl - cssl;
							if(ysysl < 0){
//								System.out.println("rt���ϱ����ݱȰ�ʱ��С,�����ϱ������豸����δ"+bd.getSbtm());
								erroList.add(bd.getSbtm());
								continue;
							}
							
						}
						if(ysysl > 100){
//							System.out.println("����Ҫ�۳�ˮ�����Ѿ�ʹ����ϣ��豸���룺"+bd.getSbtm());
							useOffList.add(bd.getSbtm());
						}else{
							updateList.add(bd.getSbtm());
							wysl = 100d - ysysl;
							bd.setCssl(bd.getCssl()-wysl);
							dealList.add(bd);
						}
//						System.out.println("�豸���룺��"+bd.getSbtm()+"��δ��ˮ��δ:"+wysl);
					}
				}
			}
			
			if(dealList !=null &&dealList.size()>0){
				for(int i =0;i<dealList.size();i++){
					dbUtil.update("update T_PLE_INFO_BANDING set cssl = ? where pmbh = ?",new Object[]{dealList.get(i).getCssl(),dealList.get(i).getPmbh()});
				}
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}
		
		
		System.out.println("dealList ��"+Arrays.toString(dealList.toArray()));
		System.out.println("updateList ��"+Arrays.toString(updateList.toArray()));
		System.out.println("erroList ��"+Arrays.toString(erroList.toArray()));
		System.out.println("useOfList ��"+Arrays.toString(useOffList.toArray()));
		System.out.println("igonreList ��"+Arrays.toString(ignoreList.toArray()));
	}
}
