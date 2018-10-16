package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.alibaba.fastjson.JSON;

import diwinet.wp.vo.TSensorProductDetail;
import diwinet.wp.vo.TSensorStorageDetail;
import diwinet.wp.vo.TWpFilterelement;
import diwinet.wp.vo.TWpInfo;
import util.DBUtil;
import util.DBUtilsTemplate;
/**
 * ������Ϣ
 */
public class T_SENSOR_STORAGE_DETAIL {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = null;
		List<TSensorProductDetail> brands = dbUtil.queryForList("select * from T_SENSOR_PRODUCT_DETAIL where wllx = 0",TSensorProductDetail.class);
		//��ѯ��������ӵ�Ʒ����Ϣ
		List<String> list = new ArrayList<String>();
		if(brands!=null&&brands.size()>0){
			System.out.println("�����Ϣ����:"+brands.size());
			for(int i =0 ;i<brands.size();i++){
				list.add(brands.get(i).getSbtm());
			}
		}
		System.out.println(JSON.toJSONString(list));
	}

}
