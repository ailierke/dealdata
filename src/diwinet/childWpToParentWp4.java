package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.ServicePosConsumer;
import diwinet.wp.vo.User;
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
public class childWpToParentWp4 {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = null;
		StringBuilder sql1 = null;
		List<ServicePosConsumer> consumers = dbUtil.queryForList("select * from T_SERVICE_POS_CONSUMER where LXDH is null or YHMC is null ",ServicePosConsumer.class);
		//��ѯ��������ӵ�Ʒ����Ϣ
		if(consumers!=null&&consumers.size()>0){
			ServicePosConsumer consumer = null;
			Long yhid = null;
			for(int i =0 ;i<consumers.size();i++){
				conn.setAutoCommit(false);
				try {
					consumer = consumers.get(i);
					yhid = consumer.getYhid();
					sql = "select * from T_SYS_USER where yhid = ?";
					User user = dbUtil.findFirst(User.class, sql, yhid);
					if(user!=null){
						sql1 = new StringBuilder("update T_SERVICE_POS_CONSUMER set");
						if(consumer.getLxdh()==null||"".equals(consumer.getLxdh())){
							sql1.append(" lxdh = '"+user.getLxdh()+"' ,");
						}
						if(consumer.getYhmc()==null||"".equals(consumer.getYhmc())){
							sql1.append(" yhmc = '"+user.getYhmc()+"' ,");
						}
						sql1 = new StringBuilder(sql1.substring(0, sql1.length()-1));
						sql1.append(" where khbh = "+consumer.getKhbh());
						System.out.println(sql1.toString());
						dbUtil.update(sql1.toString());
						System.out.println("�ͻ����Ϊ��"+consumer.getKhbh()+"���³ɹ�");
					}else{
						System.out.println("�ͻ����Ϊ��"+consumer.getKhbh()+"������");
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
