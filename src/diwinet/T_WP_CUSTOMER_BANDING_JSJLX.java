package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TWpBrand;
import diwinet.wp.vo.TWpFilterelement;
import diwinet.wp.vo.TWpInfo;
import diwinet.wp.vo.WpCustomerBanding;
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
public class T_WP_CUSTOMER_BANDING_JSJLX {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = null;
		List<WpCustomerBanding> wpList = dbUtil.queryForList("select * from T_WP_CUSTOMER_BANDING where khsyms is  null",WpCustomerBanding.class);
		//��ѯ��������ӵ�Ʒ����Ϣ
		if(wpList!=null&&wpList.size()>0){
			WpCustomerBanding wp = null;
			Long jsjbh = null;
			Long khjsjbh = null;
			Integer jsjlx = null;
			for(int i =0 ;i<wpList.size();i++){
				conn.setAutoCommit(false);
				try {
					wp = wpList.get(i);
					jsjbh = wp.getJsjbh();
					khjsjbh = wp.getKhjsjbh();
					sql = "select * from T_WP_INFO where jsjbh = "+jsjbh;
					TWpInfo wpInfo = dbUtil.findFirst(TWpInfo.class,sql);
					/**
					 * ѭ��Ʒ���µľ�ˮ��
					 */
					if(wpInfo!=null){
						jsjlx = wpInfo.getJsjlx();
						sql = "update T_WP_CUSTOMER_BANDING set khsyms ="+jsjlx+" where khjsjbh ="+khjsjbh;
					}else{
						sql = "update T_WP_CUSTOMER_BANDING set khsyms ="+0+" where khjsjbh ="+khjsjbh;
					}
					dbUtil.update(sql);
				} catch (Exception e) {
					conn.rollback();
					System.out.println("�ͻ���ˮ�����:��"+khjsjbh+"��ͬ���ͻ���ҵģʽʧ��	");
					e.printStackTrace();
				}
			}
			conn.commit();
			System.out.println("������ˮ������Ϣ��"+wpList.size()+"��");
		}else{
			System.out.println("��ˮ������Ϣkhsyms���Ѵ���,��ɴ���");
		}
	}
}
