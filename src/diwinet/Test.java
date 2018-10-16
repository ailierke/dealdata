package diwinet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TWaterReportHistoryRT;
import diwinet.wp.vo.WpCustomerBanding;
import util.DBUtil;
import util.DBUtilsTemplate;

public class Test {
	public static void main(String[] args) throws SQLException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = null;
		TWaterReportHistoryRT reportHistory = dbUtil.findFirst(TWaterReportHistoryRT.class,"select * from T_WATER_REPORT_HISTORY_RT_TEST where sbtm = '7090160778008873050' ");
		sql = "insert into T_WATER_REPORT_HISTORY (SBTM,YSSL,CSSL,YSSZ,CSSZ,YSLS,CSLS,BSSJ,TBSJ,SYSJ) values (?,?,?,?,?,?,?,?,?,?)";
		dbUtil.insert(sql,
				new Object[] { reportHistory.getSbtm(),
						reportHistory.getYssl(), reportHistory.getCssl(),
						reportHistory.getYssz(), reportHistory.getCssz(),
						reportHistory.getYsls(), reportHistory.getCsls(),
						reportHistory.getBssj(), reportHistory.getTbsj(),
						reportHistory.getSysj()});
		System.out.println(reportHistory);
		
		//查询经销商添加的品牌信息
		
	}
}
