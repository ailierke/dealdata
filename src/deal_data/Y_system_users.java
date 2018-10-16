package deal_data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBUtil;
import util.EncryptionForTellPhone;

/**
 * 1、将能查出“秘书”和“会长”的关联的管理员记录进去，然后在页面点击给予权限
 * 2、将查不出有“秘书”和“会长”关联的记录到excel+在原生产t_pri_user表比较t_pri_bussines中没有的商会也算没有管理员的也记录到excel
 * 等以后商会需要的时候手动的去将此商会的一个会员申请为管理员并且赋予权限。
 * @author CC
 *
 */
public class Y_system_users {
	public static void main(String[] args) throws SQLException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement2 = conn.createStatement();
		
		ResultSet  rs = statement.executeQuery("select t_pri_user.id,temp.id as business_id from t_pri_user  ,(select id,business_name from t_pri_business where id  in (select  t_member_info.business_id from  t_member_info , t_info_position where t_member_info.position_id=t_info_position.id and (t_info_position.`name` like '%秘书%' or t_info_position.`name` like '%会长%') group by t_member_info.business_id)) temp where temp.business_name = t_pri_user.real_name");
		String fid ="";
		String business_id ="";
		while(rs.next()){
			 fid = rs.getString(1);
			 business_id=rs.getString(2);
			Statement statement1 = conn.createStatement();
			ResultSet rs1 = statement1.executeQuery("select  t_member_info.id,real_name from  t_member_info , t_info_position where t_member_info.position_id=t_info_position.id and (t_info_position.`name` like '%秘书%' or t_info_position.`name` like '%会长%') and t_member_info.business_id ='"+business_id+"' group by t_member_info.business_id");
			if(rs1!=null&&rs1.next()==true){
				String memberId = rs1.getString(1);
				String real_name = rs1.getString(2);
				System.out.println("管理员id"+fid+" 商会id："+business_id+"关联会员："+real_name+"  成功");
				statement2.executeUpdate("update cocmoredb.y_basic_member set FAdminID = '"+fid+"',isAdmin=1 where fid='"+memberId+"'");
			}else{
				System.out.println("管理员id为："+fid+"没有查到关联会员！");
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
