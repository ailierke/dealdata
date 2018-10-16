package deal_data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBUtil;
import util.EncryptionForTellPhone;

/**
 * 1�����ܲ�������顱�͡��᳤���Ĺ����Ĺ���Ա��¼��ȥ��Ȼ����ҳ��������Ȩ��
 * 2�����鲻���С����顱�͡��᳤�������ļ�¼��excel+��ԭ����t_pri_user��Ƚ�t_pri_bussines��û�е��̻�Ҳ��û�й���Ա��Ҳ��¼��excel
 * ���Ժ��̻���Ҫ��ʱ���ֶ���ȥ�����̻��һ����Ա����Ϊ����Ա���Ҹ���Ȩ�ޡ�
 * @author CC
 *
 */
public class Y_system_users {
	public static void main(String[] args) throws SQLException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement2 = conn.createStatement();
		
		ResultSet  rs = statement.executeQuery("select t_pri_user.id,temp.id as business_id from t_pri_user  ,(select id,business_name from t_pri_business where id  in (select  t_member_info.business_id from  t_member_info , t_info_position where t_member_info.position_id=t_info_position.id and (t_info_position.`name` like '%����%' or t_info_position.`name` like '%�᳤%') group by t_member_info.business_id)) temp where temp.business_name = t_pri_user.real_name");
		String fid ="";
		String business_id ="";
		while(rs.next()){
			 fid = rs.getString(1);
			 business_id=rs.getString(2);
			Statement statement1 = conn.createStatement();
			ResultSet rs1 = statement1.executeQuery("select  t_member_info.id,real_name from  t_member_info , t_info_position where t_member_info.position_id=t_info_position.id and (t_info_position.`name` like '%����%' or t_info_position.`name` like '%�᳤%') and t_member_info.business_id ='"+business_id+"' group by t_member_info.business_id");
			if(rs1!=null&&rs1.next()==true){
				String memberId = rs1.getString(1);
				String real_name = rs1.getString(2);
				System.out.println("����Աid"+fid+" �̻�id��"+business_id+"������Ա��"+real_name+"  �ɹ�");
				statement2.executeUpdate("update cocmoredb.y_basic_member set FAdminID = '"+fid+"',isAdmin=1 where fid='"+memberId+"'");
			}else{
				System.out.println("����ԱidΪ��"+fid+"û�в鵽������Ա��");
			}
			rs1.close();
			statement1.close();
		}
		statement2.close();
		rs.close();
		statement.close();
		conn.close();
	}
}
