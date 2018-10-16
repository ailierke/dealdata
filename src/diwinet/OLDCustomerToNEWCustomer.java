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
 * �û�����Ӧ�����пͻ��Ķ����ջ���ַ ͬ���� T_ORDER_SERVICE_ADDR����  �����ͬ��yhid ��ַ�Ѿ����ھͲ����ظ��ӡ���������ھ��ظ����
 * <p>������
 * ��ѯ�ϵ�ˮ�ܼ��û����ݡ�ͨ��T_SENSOR_BINDING���еĵ���λ�á��󶨵ľ�ˮ���ͺš�T_SYS_USER ��Ļ�������,
 * 		һ����ѯ���еĿͻ�x
 * 		�����Ϳͻ���ˮ������Ϣ
 * 		�����״�ʹ��ʱ����Ϣ��
 * 		�ġ���λ��Ϣ��
 * 		�塢�����Ϣ��
 * 	          ����������Ϣ��
 * </p>
 * @author	jiangxing
 */
public class OLDCustomerToNEWCustomer {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(false);//���ó��ֶ��ύ
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		Statement statement2 = null;
		Long wdbh = 241L;//��������������
		/**
		 * ��ѯ�������а����豸�����Ҳ��ǹ�ע�û�������ϵͳ�û���Ϣ���ڵ���Ϣ ,�������ϲ�ѯ����
		 */
		ResultSet  rs1 = statement.executeQuery("SELECT"
				+ " u.yhid ,u.yhmc,u.lxdh,wpinfo.jsjbh,wpinfo.cpxh as jsjmc,wp.ppbh,wp.ppmc,"
				+ "sb.sfbh,sb.dsbh,sb.qxbh,sb.sfmc,sb.dsmc,sb.qxmc,sb.xxdz,sb.zbx,"
				+ "sb.zby,sb.sbbh,u.cjsj from T_SENSOR_USER_BINDING ub "
				+ "LEFT JOIN T_SENSOR_BINDING sb ON sb.SBBH = ub.SBBH "
				+ "LEFT JOIN T_SYS_USER u ON u.yhid = ub.yhbh "
				+ "LEFT JOIN T_DMS_WP_BRAND wp ON sb.ppbh = wp.ppbh "
				+ "LEFT JOIN T_DMS_WP_INFO wpinfo ON wpinfo.jsjbh = sb.jsjbh "
				+ "WHERE ub.yhlx = 1 and u.yhid is not null and wpinfo.jsjbh is not null "
				+ "and wp.ppbh is not null  and  sb.sbbh not in "
				+ " (select sbbh from T_WP_CUSTOMER_BANDING where sbbh is not null )");
		Long yhid = null; String yhmc = null;//�û�id//�û�����
		String lxdh = null;Long jsjbh = null;//��ϵ�绰//��ˮ�����
		String jsjmc = null;Long ppbh= null;//��ˮ������//Ʒ�Ʊ��
		String ppmc= null;Long sfbh= null;//Ʒ������//ʡ�ݱ��
		Long dsbh= null;Long qxbh= null;//���б��//���ر��
		String sfmc= null;String dsmc= null;//ʡ������//��������
		String qxmc = null;String xxdz = null;//��������//��ϸ��ַ
		Float zbx = null;Float zby = null;//����x//����y
		Long sbbh = null;Date cjsj = null;
		String cjsjStr = null;
		Integer khbh =null;
		Integer khjsjbh = null;
		ResultSet rs = null;
		while(rs1.next()){
			yhid = rs1.getLong(1);yhmc = rs1.getString(2);
			lxdh = rs1.getString(3);jsjbh = rs1.getLong(4);
			jsjmc = rs1.getString(5);ppbh = rs1.getLong(6);
			ppmc = rs1.getString(7);sfbh = rs1.getLong(8);
			dsbh = rs1.getLong(9);qxbh = rs1.getLong(10);
			sfmc = rs1.getString(11);dsmc = rs1.getString(12);
			qxmc = rs1.getString(13);xxdz = rs1.getString(14);
			zbx = rs1.getFloat(15);zby = rs1.getFloat(16);
			sbbh = rs1.getLong(17);cjsj = rs1.getTimestamp(18);
			//ת���״�ʹ��ʱ�������Ҫ��ʱ�� (���豸֮����ʵ�ǲ���Ҫʹ�������Ϊ���ڵ�һ����׼��ֱ�ӵ��������ݲ鿴�״�ʹ��ʱ��)
			try{
				cjsjStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cjsj) ;
			}catch(Exception e){
				cjsjStr =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			}
			statement2 = conn.createStatement();
			//��������ͻ���Ϣ����khbh
			if(sbbh!=null&&yhid!=null){
				try {
					statement2.executeUpdate("insert into T_SERVICE_POS_CONSUMER(WDBH,YHID,YHMC,LXDH,SFBH,DSBH,QXBH,SFMC,DSMC,QXMC,JTDZ,SFYX) values ("+wdbh+","+yhid+",'"+yhmc+"','"+lxdh+"',"+sfbh+","+dsbh+","+qxbh+",'"+sfmc+"','"+dsmc+"','"+qxmc+"','"+xxdz+"',1)",Statement.RETURN_GENERATED_KEYS);
					rs = statement2.getGeneratedKeys(); 
					if( rs.next() ) { 
						khbh = rs.getInt(1);
					}
					System.out.println("�ͻ���ţ�"+khbh+" �豸��ţ�"+sbbh+"  �û�id��"+yhid+" ��ӳɹ���");
					//����ͻ���ˮ������Ϣ
					statement2.executeUpdate("INSERT INTO T_WP_CUSTOMER_BANDING(PPBH,JSJBH,KHBH,WDBH,GMSJ,JSJMC,PPMC,TJSJ,ZBX,ZBY,SFBDSGJ,SBBH,SFMR) values("+ppbh+","+jsjbh+","+khbh+","+wdbh+",'"+cjsjStr+"','"+jsjmc+"','"+ppmc+"',NOW(),"+zbx+","+zby+",1,"+sbbh+",1)",Statement.RETURN_GENERATED_KEYS);
					rs = statement2.getGeneratedKeys(); 
					if( rs.next() ) { 
						khjsjbh = rs.getInt(1);
					}
					System.out.println("�ͻ���ˮ����ţ�"+khjsjbh+" �豸��ţ�"+sbbh+"  �û�id��"+yhid+" ��ӳɹ���");
					//�����״�ʹ��ʱ��
					statement2.executeUpdate("insert into T_WP_FIRSTUSE_INFO(KHJSJBH,SCSYSJ) values("+khjsjbh+",'"+cjsjStr+"')");
					System.out.println("�ͻ���ˮ�����Ϊ����"+khjsjbh+"�������״�ʹ��ʱ��ɹ�");
					//��λ����Ϣ
					statement2.executeUpdate("update T_WP_FILTERELEMENT_RESET set KHJSJBH = "+khjsjbh+" where SBBH = "+sbbh);
					System.out.println("�豸���Ϊ"+sbbh+"��������khjsjbh����"+khjsjbh+"�������״�ʹ��ʱ��ɹ�");
					//�����Ϣ��
					statement2.executeUpdate("update T_SENSOR_MONITOR set KHJSJBH = "+khjsjbh+" where SBBH = "+sbbh);
					System.out.println("�豸���Ϊ"+sbbh+"��ر����khjsjbh����"+khjsjbh+"�������Ϣ��");
					//������Ϣ��
					statement2.executeUpdate("update T_ORDER_INFO set KHJSJBH = "+khjsjbh+" where SBBH = "+sbbh);
					System.out.println("�豸���Ϊ"+sbbh+"����������khjsjbh����"+khjsjbh+"��������Ϣ��");
				} catch (Exception e) {
					conn.rollback();
					e.printStackTrace();
					System.out.println("�ͻ���ţ�"+khbh+" �豸��ţ�"+sbbh+"  �û�id��"+yhid+" ���ʧ�ܣ�");
				}
				//�ύ
				conn.commit();
			}
		}
		rs1.close();
		statement.close();
		statement1.close();
		statement2.close();
		conn.close();
	}
}
