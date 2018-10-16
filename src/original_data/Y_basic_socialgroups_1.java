package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 * ����ԭ����ϵͳ�̻���Ϣ
 * @author CC
 *
 */
public class Y_basic_socialgroups_1 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		Statement statement1 = conn.createStatement();
		Statement statement2 = conn.createStatement();
		Statement statement4 = conn.createStatement();
		Statement statement3 = conn.createStatement();
		//������ʱ����ɾ������û����֯���̻�
		statement.execute("create  table tmp as (select * from t_pri_business )");
		//��ǰ���̻���ϼ�ȫ��Ĭ��û�У����Ǹ��ڵ�
		statement2.addBatch("update  t_pri_business set business_region_id = NULL");
		//���鳤֮��
		//��ݸ�����鳤����
		//����ʡ���鳤֮��
		//���鳤����
		//����������������5��������̻ḳ����֯
		statement2.addBatch("update `test`.`t_pri_business` set `business_yzcompany_id` ='4028800046ccc0e40146ccc0e4580000' where `id` in ('402880d1451ca2a201451ca2a2d30001','8a216934476d22240147813a8f765df3')"); 
		statement2.addBatch("update `test`.`t_pri_business` set `business_yzcompany_id` ='4028800046ccc67a0146cccbbbcd0004' where `id`='8a21693446f6988a0146fb20942c2b0a'");
		statement2.addBatch("update `test`.`t_pri_business` set `business_yzcompany_id` ='4028800046ccc67a0146cccc507f0006' where `id`='8a2169344737c56301473ddac70d38fe'");
		statement2.addBatch("update `test`.`t_pri_business` set `business_yzcompany_id` ='8a2169344747d53e01475853aee5412f' where `id`='8a2169344747d53e01476ca6c7943cc8'");
		int[] returnStatue = statement2.executeBatch();
		System.out.println("ִ��������״̬"+Arrays.toString(returnStatue));
		//ɾ��û����֯���̻�
		statement4.executeUpdate("delete from  t_pri_business where business_yzcompany_id not in (select fid from Y_Yunzo_Company)");
		//ɾ����ʱ��
		statement1.execute("drop table tmp");
		statement.close();
		statement1.close();
		statement2.close();
		statement4.close();
		/**
		 * ���̻���Ϣ���뵽cocmoredb��
		 */
		statement4 = conn.createStatement();
		ResultSet rs = statement3.executeQuery("select id as fid ,business_name as FName,image_url as Logo,remark as FComment,create_time as FCreateTime,business_region_id as FSuperSocialGroupsID,business_yzcompany_id as FCompaniesID,5 as fbillstate, CASE org_type_name WHEN '�̻�' THEN '101' WHEN '���ֲ�' THEN '102' WHEN '��������' THEN '103' WHEN 'Э��' THEN '104' WHEN '��������' THEN '105' WHEN '��ʹͶ���˹�˾' THEN '106' WHEN 'ʨ�ӻ�' THEN '107' WHEN 'ͬ���' THEN '108' WHEN 'У�ѻ�' THEN '109' WHEN '��̳' THEN '110' WHEN '�ٽ���' THEN '111' ELSE '101'  END as FTypeID ,'1' as allowadd,'1' as FNumber from t_pri_business");
		String fid =null;String Logo =null;String FComment =null;
		String FName=null;String FCreateTime =null;String FSuperSocialGroupsID =null;
		String FCompaniesID =null;;String FTypeID=null;
		String FNumber ="1";int fbillstate = 5;String allowadd ="1";
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			fid =rs.getString(1);
			FName=rs.getString(2);
			Logo =rs.getString(3);
			FComment = rs.getString(4);
			FCreateTime = rs.getString(5);
			FSuperSocialGroupsID=rs.getString(6);
			FCompaniesID =rs.getString(7);
			FTypeID=rs.getString(9);
			System.out.println("�̻᣺"+fid+"���ƣ�"+FName);
			ps = conn.prepareStatement("insert into cocmoredb.y_basic_socialgroups(fid,FName,Logo,FComment,FCreateTime,FSuperSocialGroupsID,FCompaniesID,fbillstate,FTypeID,allowadd,FNumber) values(?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, fid);
			ps.setString(2, FName);
			ps.setString(3, Logo);
			ps.setString(4, FComment);
			ps.setString(5, FCreateTime);
			ps.setString(6, FSuperSocialGroupsID);
			ps.setString(7, FCompaniesID);
			ps.setInt(8, fbillstate);
			ps.setString(9, FTypeID);
			ps.setString(10, allowadd);
			ps.setString(11, FNumber);
			ps.executeUpdate();
			ps.close();
			count++;
		}
		System.out.println("��ȡ��������"+count);
		statement3.close();
		statement4.close();
		conn.close();
	}
	public static void main(String[] args) throws SQLException {
		dealData();
	}
}
