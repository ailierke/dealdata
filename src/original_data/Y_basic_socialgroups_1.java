package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 * 导入原生产系统商会信息
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
		//建立临时表来删除除了没有组织的商会
		statement.execute("create  table tmp as (select * from t_pri_business )");
		//以前的商会的上级全部默认没有，都是父节点
		statement2.addBatch("update  t_pri_business set business_region_id = NULL");
		//秘书长之家
		//东莞市秘书长联盟
		//湖南省秘书长之家
		//秘书长联盟
		//建立批处理，给以上5个特殊的商会赋予组织
		statement2.addBatch("update `test`.`t_pri_business` set `business_yzcompany_id` ='4028800046ccc0e40146ccc0e4580000' where `id` in ('402880d1451ca2a201451ca2a2d30001','8a216934476d22240147813a8f765df3')"); 
		statement2.addBatch("update `test`.`t_pri_business` set `business_yzcompany_id` ='4028800046ccc67a0146cccbbbcd0004' where `id`='8a21693446f6988a0146fb20942c2b0a'");
		statement2.addBatch("update `test`.`t_pri_business` set `business_yzcompany_id` ='4028800046ccc67a0146cccc507f0006' where `id`='8a2169344737c56301473ddac70d38fe'");
		statement2.addBatch("update `test`.`t_pri_business` set `business_yzcompany_id` ='8a2169344747d53e01475853aee5412f' where `id`='8a2169344747d53e01476ca6c7943cc8'");
		int[] returnStatue = statement2.executeBatch();
		System.out.println("执行批处理状态"+Arrays.toString(returnStatue));
		//删除没有组织的商会
		statement4.executeUpdate("delete from  t_pri_business where business_yzcompany_id not in (select fid from Y_Yunzo_Company)");
		//删除临时表
		statement1.execute("drop table tmp");
		statement.close();
		statement1.close();
		statement2.close();
		statement4.close();
		/**
		 * 将商会信息插入到cocmoredb中
		 */
		statement4 = conn.createStatement();
		ResultSet rs = statement3.executeQuery("select id as fid ,business_name as FName,image_url as Logo,remark as FComment,create_time as FCreateTime,business_region_id as FSuperSocialGroupsID,business_yzcompany_id as FCompaniesID,5 as fbillstate, CASE org_type_name WHEN '商会' THEN '101' WHEN '俱乐部' THEN '102' WHEN '民主党派' THEN '103' WHEN '协会' THEN '104' WHEN '服务中心' THEN '105' WHEN '天使投资人公司' THEN '106' WHEN '狮子会' THEN '107' WHEN '同乡会' THEN '108' WHEN '校友会' THEN '109' WHEN '论坛' THEN '110' WHEN '促进会' THEN '111' ELSE '101'  END as FTypeID ,'1' as allowadd,'1' as FNumber from t_pri_business");
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
			System.out.println("商会："+fid+"名称："+FName);
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
		System.out.println("获取总条数："+count);
		statement3.close();
		statement4.close();
		conn.close();
	}
	public static void main(String[] args) throws SQLException {
		dealData();
	}
}
