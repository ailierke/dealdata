package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.DBUtil;
/**
 * �ͻ��İ󶨾�ˮ���ĵ�ַ��ԤԼ�ջ���ַ��ȥ
 * <p>������
 * 		һ����ѯ�û����а󶨵ľ�ˮ����ַ��Ϣ
 * 		�����Աȵ�ַ��Ϣ���Ƿ��Ѿ��д��ڣ�����Ѿ��д��ڵľͲ��ӣ����û�оͼ���
 * </p>
 * @author	jiangxing
 */
public class CustomerOfUserAdreeToT_ORDER_SERVICE_ADDR {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(false);//���ó��ֶ��ύ
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		Statement statement2 = null;
		/**
		 * ��ѯ�������а����豸�����Ҳ��ǹ�ע�û�������ϵͳ�û���Ϣ���ڵ���Ϣ ,�������ϲ�ѯ����
		 */
		ResultSet  rs1 = statement.executeQuery("select u.yhid,c.yhmc,c.lxdh,b.sfbh,b.dsbh,b.qxbh,b.sfmc,b.dsmc,b.qxmc,b.jtdz,b.khjsjbh "
				+ "			from T_WP_CUSTOMER_BANDING b "
				+ "left join T_SERVICE_POS_CONSUMER c ON c.khbh = b.khbh "
				+ "left join T_SYS_USER u ON u.yhid = c.yhid "
				+ "where "
				+ "c.khbh is not null "
				+ "and b.sfbh is not null "
				+ "and b.dsbh is not null "
				+ "and b.qxbh  is not null "
				+ "and b.jtdz is not null "
				+ "and u.yhid is not null");
		Long yhid = null; String yhmc = null;//�û�id//�û�����
		String lxdh = null;Long sfbh = null;//��ϵ�绰//ʡ�ݱ��
		String sfmc = null;Long dsbh= null;//ʡ������//���б��
		String dsmc= null;Long qxbh= null;//��������//���ر��
		String qxmc= null;String jtdz = null;//��������//�����ַ
		Long khjsjbh = null;
		ResultSet  rs2 = null;
		while(rs1.next()){
			try {
				yhid = rs1.getLong(1);yhmc = rs1.getString(2);
				lxdh = rs1.getString(3);sfbh = rs1.getLong(4);
				dsbh = rs1.getLong(5);qxbh = rs1.getLong(6);
				sfmc = rs1.getString(7);dsmc = rs1.getString(8);
				qxmc = rs1.getString(9);jtdz = rs1.getString(10);
				khjsjbh = rs1.getLong(11);
				rs2 =  statement1.executeQuery("select * from T_ORDER_SERVICE_ADDR where lxdh='"+lxdh+"' and sfbh = "+sfbh+" And dsbh = "+dsbh+" And qxbh = "+qxbh+" And xxdz like '%"+jtdz+"%' And shrm = '"+yhmc+"'");
				if(rs2.next()){
					continue;
				}
				statement2 = conn.createStatement();
				//��������ͻ���Ϣ����khbh
			
				statement2.executeUpdate("INSERT INTO T_ORDER_SERVICE_ADDR (YHBH,SHRM,SFBH,DSBH,QXBH,SFMC,DSMC,QXMC,XXDZ,LXDH,"
						+ "SFMR,SFYX,TJRQ,XGRQ) VALUES ("+yhid+",'"+yhmc+"',"+sfbh+","+dsbh+","+qxbh+",'"+sfmc+"','"+dsmc+"','"+qxmc+"','"+jtdz+"','"+lxdh+"','0',1,"
						+ "NOW(),NOW())");
				System.out.println("�ͻ���ˮ����ţ�"+khjsjbh+" �û�id��"+yhid+" ��ӳɹ���");
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				System.out.println("�ͻ���ˮ����ţ�"+khjsjbh+" �û�id��"+yhid+" ���ʧ�ܣ�");
			}
				//�ύ
				conn.commit();
		}
		rs1.close();
		statement.close();
		statement1.close();
		statement2.close();
		conn.close();
	}
}
