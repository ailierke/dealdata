package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.DBUtil;
/**
 * 将用户没有角色的将角色添加上
 * 
 * </p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年12月8日</p>
 * @author	jiangxing
 */
public class T_SYS_USER_ROLE {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement2 = conn.createStatement();
		ArrayList<Long> yhidList = new ArrayList<Long>();
		ResultSet  rs1 = statement.executeQuery("select  yhid from T_SYS_USER_ROLE where yhid not in (select yhid from T_SYS_USER)");
		Long yhid = null;

		while(rs1.next()){
			yhid = rs1.getLong(1);
			yhidList.add(yhid);
		}
		rs1.close();
		statement.close();
		
		for(Long id : yhidList){
			statement2.execute("delete from T_SYS_USER_ROLE where yhid = "+id);
			System.out.println("yhid："+yhid+" 删除成功！");
		}
		
		statement2.close();
		conn.close();
	}
}
