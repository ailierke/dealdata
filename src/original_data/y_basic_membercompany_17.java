package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  商会公司 查询的时候有点慢 运行时间比较久
 * @author CC
 *
 */
public class y_basic_membercompany_17 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select members.id as Fcid ,members.id as FMemberID,members.business_id as FSocialGroupsID ,company.name as FCName,company.phone as FCTelphone,company.email as FCEmail ,company.description as FCIntroduction, company.address as FCLocation,company.url as FCUrl,company.main_products as FCProducts ,'b11f8b53-d7d3-41b5-a690-06fc4a0cd281' as ftradeid from test.t_pri_company as company ,test.t_member_info as members   where (company.business_id in (select fid from cocmoredb.y_basic_socialgroups) or company.business_id is null) and company.id=members.company_id  and members.id in (select fid from cocmoredb.y_basic_member)");
		String Fcid =null;String FMemberID =null;String FSocialGroupsID =null;
        String FCName =null;String FCTelphone =null;String FCEmail =null;
        String FCIntroduction =null;String FCLocation =null;String FCUrl =null;
        String FCProducts =null;String ftradeid =null;
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			Fcid =rs.getString(1);
			FMemberID=rs.getString(2);
			FSocialGroupsID =rs.getString(3);
			FCName = rs.getString(4);
			FCTelphone = rs.getString(5);
			FCEmail = rs.getString(6);
			FCIntroduction = rs.getString(7);
			FCLocation = rs.getString(8);
			FCUrl = rs.getString(9);
			FCProducts = rs.getString(10);
			ftradeid = rs.getString(11);
			System.out.println("公司Id："+Fcid+"公司名字："+FCName);
			ps = conn.prepareStatement("insert into cocmoredb.y_basic_membercompany(Fcid,FMemberID,FSocialGroupsID,FCName,FCTelphone,FCEmail,FCIntroduction,FCLocation,FCUrl,FCProducts,ftradeid) values(?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, Fcid);
			ps.setString(2, FMemberID);
			ps.setString(3, FSocialGroupsID);
			ps.setString(4, FCName);
			ps.setString(5, FCTelphone);
			ps.setString(6, FCEmail);
			ps.setString(7, FCIntroduction);
			ps.setString(8, FCLocation);
			ps.setString(9, FCUrl);
			ps.setString(10, FCProducts);
			ps.setString(11, ftradeid);
			ps.executeUpdate();
			ps.close();
			count++;
		}
		rs.close();
		statement.close();
		System.out.println("获取总条数："+count);
		conn.close();
	}
	public static void main(String[] args) throws SQLException {
		dealData();
	}
}
