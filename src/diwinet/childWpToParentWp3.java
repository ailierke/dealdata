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
 * <p>���⣺�������ľ�ˮ����Ϣȫ��ͬ����ԭ���ĸ���</p>
 * <p>������
 * 	1����ѯ�����е�T_DMS_WP_BRAND �޳��̱�ŵ�,��Ϊ������������ӵ�Ʒ�ƣ���Ʒ�ơ��ͺš���о��ѭ����ӵ�������
 * 	   ��ӳɹ�ʱ����Ʒ�ơ��ͺš���о���±�š��ڴ�ͬʱ����������������ϵ����
 *  2������������ӵ�T_WP_BRAND��,��ȡƷ�Ʊ�ŵ���������
 * </p>
 * <p>Copyright��Copyright(c) 2016 diwinet</p>
 * <p>���ڣ�2016��3��28��</p>
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
		
		//��ѯ��������ӵ�Ʒ����Ϣ
	}
}
