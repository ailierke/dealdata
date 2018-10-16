package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 * 导入原生产系统职位信息
 * @author CC
 *
 */
public class y_basic_position_2 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		Statement statement1 = conn.createStatement();
		//首先删除没有商会的职位（原系统职位不属于商会就没用）
		statement.executeUpdate("delete from t_info_position where business_id not in (select id from t_pri_business)");
		statement.close();
		/**
		 * 将商会的职位信息插入到cocmoredb中
		 */
		statement1 = conn.createStatement();
		
		ResultSet rs = statement1.executeQuery("select id as fid,name as FName,order_no as fseq,business_id as FSocialGroupsID ,'1' as FNumber,1 as fhide,5 as FBillState,1 as fversion from t_info_position");
		String fid =null;String FName =null;
		Integer fseq=null;String FSocialGroupsID =null;
		int fhide=1;String FNumber ="1";int FBillState =5;
		int fversion =1;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			fid =rs.getString(1);
			FName = rs.getString(2);
			fseq =rs.getInt(3);
			FSocialGroupsID=rs.getString(4);
			System.out.println("职位："+fid+"名称："+FName);
			ps = conn.prepareStatement("insert into cocmoredb.y_basic_position(fid,FName,fseq,FSocialGroupsID,FNumber,fhide,FBillState,fversion) values(?,?,?,?,?,?,?,?)");
			ps.setString(1, fid);
			ps.setString(2, FName);
			ps.setInt(3, fseq);
			ps.setString(4, FSocialGroupsID);
			ps.setString(5, FNumber);
			ps.setInt(6, fhide);
			ps.setInt(7, FBillState);
			ps.setInt(8, fversion);
			ps.executeUpdate();
			ps.close();
			count++;
		}
		rs.close();
		statement1.close();
		System.out.println("获取总条数："+count);
		conn.close();
	}
	public static void main(String[] args) throws SQLException {
		dealData();
	}
}
