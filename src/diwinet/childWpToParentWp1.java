package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TWpBrand;
import diwinet.wp.vo.TWpInfo;
import diwinet.wp.vo.WpCustomerBanding;
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
public class childWpToParentWp1 {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = null;
		StringBuilder sql1 = null;
		List<WpCustomerBanding> wps = dbUtil.queryForList("select * from T_WP_CUSTOMER_BANDING where ppmc is null or jsjmc is null ",WpCustomerBanding.class);
		//��ѯ��������ӵ�Ʒ����Ϣ
		if(wps!=null&&wps.size()>0){
			WpCustomerBanding wp = null;
			for(int i =0 ;i<wps.size();i++){
				conn.setAutoCommit(false);
				try {
					TWpInfo info = null;
					TWpBrand brand  = null;
					wp = wps.get(i);
					sql1 = new StringBuilder("update T_WP_CUSTOMER_BANDING set");
					if(wp.getJsjmc()==null||"".equals(wp.getJsjmc())){
						sql = "select * from T_WP_INFO where jsjbh ="+wp.getJsjbh();
						 info = dbUtil.findFirst(TWpInfo.class, sql);
						if(info!=null&&info.getCpxh()!=null){
							sql1.append(" JSJMC = '"+info.getCpxh()+"' ,");
						}
					}
					if(wp.getPpmc()==null||"".equals(wp.getPpmc())){
						sql = "select * from T_WP_BRAND where ppbh = "+wp.getPpbh();
						 brand = dbUtil.findFirst(TWpBrand.class, sql);
						if(brand!=null&&brand.getPpmc()!=null){
							sql1.append(" PPMC = '"+brand.getPpmc()+"' ,");
						}
					}
					
					if(brand==null&&info==null){
						continue;
					}
					sql1 = new StringBuilder(sql1.substring(0, sql1.length()-1));
					sql1.append(" where KHJSJBH = "+wp.getKhjsjbh());
					System.out.println(sql1.toString());
					dbUtil.update(sql1.toString());
					System.out.println("�ͻ����Ϊ��"+wp.getKhjsjbh()+"���³ɹ�");
					
				} catch (Exception e) {
					conn.rollback();
					e.printStackTrace();
				}
				conn.commit();
			}
		}else{
			System.out.println("�ӱ���û�о�������ӵ��Զ���Ʒ����Ϣ");
		}
		
	}
}
