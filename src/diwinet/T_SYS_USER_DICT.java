package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TDictXzqh;
import diwinet.wp.vo.User;
import util.DBUtil;
import util.DBUtilsTemplate;
/**
 * <p>���⣺���ݴ����û����������෴�����</p>
 * <p>������</p>
 * <p>Copyright��Copyright(c) 2016 diwinet</p>
 * <p>���ڣ�2016��11��7��</p>
 * @author	jiangxing
 */
public class T_SYS_USER_DICT {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		try {
			List<User> users = dbUtil.queryForList("select * from T_SYS_USER where dsbh is not null and qxbh is not null",User.class);
			if(users!=null&&users.size()>0){
				User user = null;
				String dsbh = null;//���б��
				String qxbh = null;//���ر��
				String dsmc = "";
				String qxmc = "";
				Long yhid = null;
				TDictXzqh tDictXzqh = null;
				for(int i =0 ;i<users.size();i++){
					conn.setAutoCommit(false);
					user = users.get(i);
					dsbh = user.getDsbh();
					qxbh = user.getQxbh();
					dsmc = user.getDsmc();
					qxmc = user.getQxmc();
					yhid = user.getYhid();
					//��ѯ�����б�� ������б�ŵĸ�������� �����������ر�ŵĻ���˵���Ǵ淴��
					tDictXzqh = dbUtil.findFirst(TDictXzqh.class, "select * from T_DICT_XZQH where xzqhbh = "+dsbh);
					if(tDictXzqh!=null&&tDictXzqh.getXzqhfj()!=null&&tDictXzqh.getXzqhfj().toString().equals(qxbh)){
						System.out.println("��������yhid��"+user.getYhid()+"���б��Ϊ��"+dsbh+" ���ر��Ϊ��"+qxbh);
						dbUtil.update("update T_SYS_USER set dsbh = "+new Long(qxbh)+",qxbh = "+new Long(dsbh)+",qxmc = '"+dsmc+"',dsmc='"+qxmc+"' where yhid ="+yhid);
					}
				}
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
	}
}
