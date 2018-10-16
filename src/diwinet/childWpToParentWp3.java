package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;

import util.DBUtil;
import util.DBUtilsTemplate;
/**
 * 
 * <p>标题：将新增的净水机信息全部同步到原来的父表</p>
 * <p>描述：
 * 	1、查询出所有的T_DMS_WP_BRAND 无厂商编号的,即为经销商自主添加的品牌，将品牌、型号、滤芯都循环添加到父表中
 * 	   添加成功时生成品牌、型号、滤芯的新编号、在此同时进行其他库的外键关系更新
 *  2、将此数据添加到T_WP_BRAND中,获取品牌编号的新主键。
 * </p>
 * <p>Copyright：Copyright(c) 2016 diwinet</p>
 * <p>日期：2016年3月28日</p>
 * @author	jiangxing
 */
public class childWpToParentWp3 {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = "select b.qdid from T_SENSOR_USER_RELATION r join T_SYS_USER  u ON u.yhid=r.yhid left join T_SYS_USER_BAIDU_PUSH_BINDING b on b.YHID = u.YHID  where r.sbbh = 1  and u.jsid = 3 and qdid is not null";
		List<Integer>  list = dbUtil.queryForListByKey(sql,"qdid",Integer.class);
		System.out.println(Arrays.toString(list.toArray()));
		
		//查询经销商添加的品牌信息
	}
}
