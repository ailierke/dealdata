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
 * <p>����:���̻ͳһ��Ӫ�����������</p>
 * <p>�������˳������ͬ��������ϵͳ���ߡ���Ӫ��ϵͳ���ߣ�����ϵͳ����ͬ�����������ݡ�������T_WP_COMPANY_ACTIVITY_CHANGE.java����������֮���ڽ���������
 *  ����t_wp_customer_banding ��sbbh ��Ϊnull khsyms Ϊ3��4  yxms Ϊ0��ͳģʽ 
 *  ͨ��jsjbh ��ѯ T_PMS_PRICE_MODE_BRAND �������ֱ�ӽ�zfbh��khjsjbh��ϵ����
 *  ��ѯ�þ�ˮ������ʷѶ����Ƿ���ڣ�������ڽ��� �ͻ�ˮ�����ʷѶ����м��ϵ��
 * </p>
 * 
 * <p>Copyright��Copyright(c) 2016 diwinet</p>
 * <p>���ڣ�2016��3��28��</p>
 * @author	jiangxing
 */
public class T_WP_COMPANY_ACTIVITY_CHANGE_WP {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		conn.setAutoCommit(false);
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		List<WpCustomerBanding> wpList = dbUtil.queryForList("SELECT  * FROM T_WP_CUSTOMER_BANDING WHERE khsyms IN (3, 4) AND sbbh IS NOT NULL AND yxms = 0",WpCustomerBanding.class);
		StringBuilder sb = new StringBuilder("ˮ����ˮ���ͺ�δ�����ʷѵ��У�khjsjbh:");
		StringBuilder sb1 = new StringBuilder("ˮ����ˮ���ͺ������˶���ʷѵ��У�khjsjbh:");
		//��ѯ��������ӵ�Ʒ����Ϣ
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
								System.out.println("����ʷѶ���"+wp.getKhjsjbh());
							}else{
								continue;
							}
						}else{
							sb1.append(" "+wp.getKhjsjbh());
							System.out.println("ˮ����ˮ���ͺ������˶���ʷ��У�khjsjbh"+wp.getKhjsjbh());
						}
					}else{
						sb.append(" "+wp.getKhjsjbh());
						System.out.println("ˮ����ˮ���ͺ�δ�����ʷѣ�khjsjbh"+wp.getKhjsjbh());
					}
				} catch (Exception e) {
					System.out.println("wp����ʧ��:"+wp.getKhjsjbh());
					e.printStackTrace();
				}
			}
		}
		System.out.println(sb1.toString());
		System.out.println(sb.toString());
		conn.commit();
	}
}
