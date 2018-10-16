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

public class DeailCity {
	public static void main(String[] args) throws SQLException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		Map<String,String> proviceMap = new HashMap<String,String>();
		Map<String,ArrayList<ArrayList<String>>> cityMap = new HashMap<String,ArrayList<ArrayList<String>>>();
		Map<String,ArrayList<ArrayList<String>>> contryMap = new HashMap<String,ArrayList<ArrayList<String>>>();
		
		//省份
		List<TDictXzqh> provices = dbUtil.queryForList("select * from T_DICT_XZQH where XZQHFJ = 100000",TDictXzqh.class);	
		if(provices!=null&&provices.size()>0){
			for(TDictXzqh provice :provices ){
				proviceMap.put(provice.getXzqhbh().toString(), provice.getXzqhmc());
				//地市
				List<TDictXzqh> citys = dbUtil.queryForList("select * from T_DICT_XZQH where XZQHFJ ="+provice.getXzqhbh(),TDictXzqh.class);	
				if(citys!=null&&citys.size()>0){
					ArrayList<ArrayList<String> > a = new ArrayList<ArrayList<String> >();
					ArrayList<String> b = null;
					for(TDictXzqh city :citys ){
						b = new ArrayList<String>();
						b.add(city.getXzqhbh().toString());
						b.add(city.getXzqhmc());
						a.add(b);
						cityMap.put(provice.getXzqhbh().toString(), a);
						//区县
						List<TDictXzqh> coutrys = dbUtil.queryForList("select * from T_DICT_XZQH where XZQHFJ ="+city.getXzqhbh(),TDictXzqh.class);	
						if(coutrys!=null&&coutrys.size()>0){
							ArrayList<ArrayList<String> > a1 = new ArrayList<ArrayList<String> >();
							ArrayList<String> b1 = null;
							for(TDictXzqh coutry :coutrys ){
								b1 = new ArrayList<String>();
								b1.add(coutry.getXzqhbh().toString());
								b1.add(coutry.getXzqhmc());
								a1.add(b1);
								contryMap.put(city.getXzqhbh().toString(), a1);
							}
						}
					}
				}
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("area0", proviceMap);
		map.put("area1", cityMap);
		map.put("area2", contryMap);
		System.out.println(JSON.toJSONString(map));
	}




}
