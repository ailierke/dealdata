package original_data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import util.DBUtil;
/**
 *  ��������е��û���Ϣ��ְλid ��ְλid��  t_info_position ���д��ڵ����ݴ浽�м��
 * @author CC
 *
 */
public class y_basicentries_memberdistribution_4 {
	public static void dealData() throws SQLException{
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		
		ResultSet rs = statement.executeQuery("select id as fid, position_id as FPositionID, id as FMemberID ,ifnull(order_no, 0) as Fseq ,'��' as FKeyPost from t_member_info where id in(select fid from cocmoredb.y_basic_member)");
		String fid =null;String FPositionID =null;String FMemberID =null;
		Integer Fseq=null;String FKeyPost ="��";
		int count =0;
		PreparedStatement  ps =null;
		while(rs.next()){
			fid =rs.getString(1);
			FPositionID=rs.getString(2);
			FMemberID =rs.getString(3);
			Fseq = rs.getInt(4);
			FKeyPost = rs.getString(5);
			System.out.println("�м��id��"+fid+"ְλID��"+FPositionID);
			ps = conn.prepareStatement("insert into cocmoredb.y_basicentries_memberdistribution(fid,FPositionID,FMemberID,Fseq,FKeyPost) values(?,?,?,?,?)");
			ps.setString(1, fid);
			ps.setString(2, FPositionID);
			ps.setString(3, FMemberID);
			ps.setInt(4, Fseq);
			ps.setString(5, FKeyPost);
			ps.executeUpdate();
			ps.close();
			count++;
		}
		rs.close();
		statement.close();
		System.out.println("��ȡ��������"+count);
		conn.close();
	}
	public static void main(String[] args) throws SQLException {
		dealData();
	}
}
