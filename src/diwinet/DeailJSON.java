package diwinet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;

import com.alibaba.fastjson.JSON;

import diwinet.wp.vo.TDictXzqh;
import util.DBUtil;
import util.DBUtilsTemplate;

public class DeailJSON {
	public static void main(String[] args) throws SQLException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		
		//省份
		List<String> sbtmList = dbUtil.queryForListByKey("select sbtm from T_SENSOR_STORAGE_DETAIL where rkbh in(SELECT rkbh FROM T_SENSOR_STORAGE WHERE RKBH >=107 and rkbh <=128)", "SBTM", String.class);	
		String json = JSON.toJSONString(sbtmList);
		System.out.println(json);
	}




}
