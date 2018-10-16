package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TServiceCompanyBrand;
import diwinet.wp.vo.TServicePosBrand;
import diwinet.wp.vo.TWpBrand;
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
public class childWpToParentWp2 {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = null;
		List<TServiceCompanyBrand> brands = dbUtil.queryForList("select * from T_SERVICE_COMPANY_BRAND",TServiceCompanyBrand.class);
		//��ѯ��������ӵ�Ʒ����Ϣ
		if(brands!=null&&brands.size()>0){
			TServiceCompanyBrand brand = null;
			String rightPpmc = null;
			Long zjid = null;
			for(int i =0 ;i<brands.size();i++){
				conn.setAutoCommit(false);
				try {
					brand = brands.get(i);
					rightPpmc = brand.getPpmc();
					zjid = brand.getZjid();
					sql = "select * from T_WP_BRAND where ppmc = ?";
					TWpBrand wpBrand = dbUtil.findFirst(TWpBrand.class, sql, rightPpmc);
					if(wpBrand!=null&&wpBrand.getPpbh()!=null){
						sql = "update T_SERVICE_COMPANY_BRAND set ppbh = "+wpBrand.getPpbh() +" where zjid = "+zjid;
						dbUtil.update(sql);
						System.out.println("��Ʒ�Ʊ��Ϊ����"+wpBrand.getPpbh()+"������ӱ�Ʒ�Ʊ��Ϊ����"+brand.getPpbh()+"���ɹ�");
					}
					
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
