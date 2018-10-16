package deal_data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBUtil;

/**
 * 1、将能查出“秘书”和“会长”的关联的管理员记录进去，然后在页面点击给予权限
 * 2、将查不出有“秘书”和“会长”关联的记录到excel+在原生产t_pri_user表比较t_pri_bussines中没有的商会也算没有管理员的也记录到excel
 * 等以后商会需要的时候手动的去将此商会的一个会员申请为管理员并且赋予权限。
 * 3、如果没有匹配就看有没有会员，有的话就直接关联，一个会员都没有的就记录
 * @author CC
 *
 */
public class Y_system_users2 {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement2 = conn.createStatement();
		
		ResultSet  rs = statement.executeQuery("select t_pri_user.id,temp.id as business_id from t_pri_user  ,(select id,business_name from t_pri_business where id  not in (select  t_member_info.business_id from  t_member_info , t_info_position where t_member_info.position_id=t_info_position.id and (t_info_position.`name` like '%秘书%' or t_info_position.`name` like '%会长%') group by t_member_info.business_id)) temp where temp.business_name = t_pri_user.real_name");
		String fid ="";
		String business_id ="";
		while(rs.next()){
			 fid = rs.getString(1);
			 business_id=rs.getString(2);
			Statement statement1 = conn.createStatement();
			ResultSet rs1 = statement1.executeQuery("select  t_member_info.id,real_name from  t_member_info  where  t_member_info.business_id ='"+business_id+"' group by t_member_info.business_id");
			if(rs1!=null&&rs1.next()==true){
				String memberId = rs1.getString(1);
				String real_name = rs1.getString(2);
				System.out.println("管理员id"+fid+" 商会id："+business_id+"关联会员："+real_name+"  成功");
				statement2.executeUpdate("update cocmoredb.y_basic_member set FAdminID = '"+fid+"',isAdmin=1 where fid='"+memberId+"'");
			}else{
				System.out.println("管理员id为："+fid+"没有查到关联会员！");
				try {
					Statement statement3 = conn.createStatement();
					ResultSet rs2 = statement3.executeQuery("select  t_pri_user.user_name,t_pri_user.password,t_pri_user.real_name from t_pri_user where id ='"+fid+"'");
					if(rs2==null||rs2.next()==false){
						String user_name = rs2.getString(1);
						String password = rs2.getString(2);
						String groupname = rs2.getString(3);
						FileWriter wt = new FileWriter("E:"+File.pathSeparator+"groups.txt");
						wt.write("商会id： "+business_id+"   商会名： "+groupname+"  管理员名： "+user_name+"  管理员密码： "+password+"\t\n");
						wt.flush();
					}
					rs2.close();
					statement3.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
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
