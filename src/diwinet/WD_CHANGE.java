package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.ServiceCompany;
import diwinet.wp.vo.TServicePosJob;
import util.DBUtil;
import util.DBUtilsTemplate;
/**
 * <p>���⣺��ҵģʽ��ԭ�������������ۺ�������ģ��������</p>
 * <p>������</p>
 * <p>Copyright��Copyright(c) 2017 diwinet</p>
 * <p>���ڣ�2017��2��13��</p>
 * @author	Feiyang Chen
 */
public class WD_CHANGE {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(false);
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = null;
		//��ѯ��������Ϣ
		List<ServiceCompany> serviceCompanys = dbUtil.queryForList("SELECT * FROM T_SERVICE_COMPANY ",ServiceCompany.class);
		if(serviceCompanys!=null&&serviceCompanys.size()>0){
			ServiceCompany service = null;
			List<TServicePosJob> jobs = null;
			Long gwbh = null;
			Long wdbh = null;
			List<Long> oldwdbhs = null;
			int shwdNum = 0;
			try {
				for(int i =0 ;i<serviceCompanys.size();i++){
					try{
						service = serviceCompanys.get(i);
						//�þ�������ԭ���ۺ������б�
//						oldwdbhs = dbUtil.queryForList("SELECT A.WDBH FROM T_SERVICE_POS A WHERE A.FWSBH = ? ", Long.class, service.getFwsbh());
						oldwdbhs = dbUtil.queryForListByKey("SELECT A.WDBH as wdbh FROM T_SERVICE_POS A WHERE A.FWSBH = ? ", "wdbh", Long.class,service.getFwsbh());
						//0.�жϸþ��������Ƿ����ۺ��������
						shwdNum = dbUtil.queryForInt("SELECT COUNT(1) FROM T_SERVICE_POS A WHERE A.FWSBH = ? AND A.WDLX = 1 ", service.getFwsbh());
						if(shwdNum > 0){
							System.out.println("���Ϊ��"+service.getFwsbh()+"���ľ����̣��Ѵ����ۺ�������㣬�������£�");
							continue;
						}
						
						//1.�����ۺ����������Ϣ
						sql = "INSERT into T_SERVICE_POS (WDLX,WDMC,WDDZ,LXDH,LXRM,FWSBH,SFBH,DSBH,QXBH,SFMC,DSMC,QXMC,JDSX,SFYX,CJSJ) VALUES (1,?,?,?,?,?,?,?,?,?,?,?,100,1,NOW())";
						wdbh = dbUtil.insert(sql,new Object[] {service.getFwsmc()+"�ۺ��������",service.getFwsdz(),service.getFwsrx(),service.getLxr(),service.getFwsbh(),
								service.getSfbh(),service.getDsbh(),service.getQxbh(),service.getSfmc(),service.getDsmc(),service.getQxmc()});
						
						//4.���ݷ����̱�źͽ�ɫID��ѯԭ�������λ��
						jobs = dbUtil.queryForList("SELECT A.* FROM T_SERVICE_POS_JOB A LEFT JOIN T_SERVICE_POS B ON A.WDBH=B.WDBH WHERE B.FWSBH=? AND A.JSID=5 ", TServicePosJob.class, service.getFwsbh());
						
						//2.���ݾ�����ɾ���ۺ��λ
						for(TServicePosJob job : jobs){
							dbUtil.delete("DELETE FROM T_SERVICE_POS_JOB WHERE GWBH = ? ", job.getGwbh());
							System.out.println("�����̱��Ϊ:��"+service.getFwsbh()+"��ɾ���ˡ�"+job.getGwbh()+"������ۺ��λ��");
						}
						//3.Ϊ�����������ۺ�����λ
						gwbh = dbUtil.insert("INSERT INTO T_SERVICE_POS_JOB(WDBH,GWMC,SFYX,CJSJ,CJRY,CJRM,JSID,SFMRGW) VALUES (?,?,1,NOW(),?,?,5,1)", new Object[]{wdbh,"��װ�ۺ�",32,"ϵͳ����Ա"});
						
						//5.���ݸ�λ��Ž�ԭ���ۺ���Ա�������ź͸�λ��Ÿ���
						if(jobs!=null&&jobs.size()>0){
							for(TServicePosJob job : jobs){
								int num_pos_user = dbUtil.update("UPDATE T_SERVICE_POS_USER A SET A.GWBH=?,A.WDBH=? WHERE A.GWBH = ? ", new Object[]{gwbh,wdbh,job.getGwbh()});
								System.out.println("����ԭ��λ��š�"+job.getGwbh()+"��������:��"+num_pos_user+"�����ۺ���Ա��Ϣ");
							}
						}
						
						//6.���������������Ϊ�ۺ��������
						if(oldwdbhs!=null&&oldwdbhs.size()>0){
							for(Long oldwdbh : oldwdbhs){
								dbUtil.update("UPDATE T_ORDER_AREA_POS A SET A.WDBH = ? WHERE A.WDBH = ? ", new Object[]{wdbh,oldwdbh});
							}
						}
					}catch(Exception e){
						conn.rollback();
						System.out.println("�����̱��Ϊ:��"+service.getFwsbh()+"�����������ͬ��ʧ��");
						e.printStackTrace();
					}
					
				}
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				System.out.println("���������ʧ�ܣ�������򣡡�");
				e.printStackTrace();
			}
		}else{
			System.out.println("�޾�������Ϣ");
		}

	}
}
