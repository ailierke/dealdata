package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import util.DBUtil;
import util.DBUtilTest;
import util.DBUtilsTemplate;
/**
 * 将用户没有角色的将角色添加上
 * 
 * </p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年12月8日</p>
 * @author	jiangxing
 */
public class T_SYS_USER {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtilTest.getConnection();
		conn.setAutoCommit(false);
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = "select * from  T_ANALYSIS_LEASEPAY_DAY  where jlbh = 2 for update ";
		dbUtil.findFirst(sql);
		List<Integer>  list = dbUtil.queryForListByKey(sql,"qdid",Integer.class);
		System.out.println(Arrays.toString(list.toArray()));
		statement1.close();
		statement2.close();
		conn.close();
}
}
