package diwinet;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import diwinet.wp.vo.ServiceServiceprice;
import diwinet.wp.vo.TWpBrand;
import diwinet.wp.vo.TWpFilterelement;
import diwinet.wp.vo.TWpInfo;
import util.DBUtil;
import util.DBUtilsTemplate;
/**
 * <p>标题：运营商跑租赁服务定价数据</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2017 diwinet</p>
 * <p>日期：2017年6月22日</p>
 * @author	xing.jiang
 */
public class ServicePriceRunner{

	static Logger log = LoggerFactory.getLogger(ServicePriceRunner.class);
	public static void main(String[] args) throws SQLException{
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		conn.setAutoCommit(false);
		String sql="select tem.fwsbh,u.rybh from  (select * from T_SERVICE_COMPANY where fwsbh not in(select djdxbh from  T_SERVICE_SERVICEPRICE where djlx = 0 group by djdxbh) and yxms = 1 ) tem  left join T_SERVICE_COMPANY_USER u on u.fwsbh = tem.fwsbh  where u.rybh is not null GROUP BY tem.fwsbh  ";
		List<Map<String, Object>> serviceprice = dbUtil.queryForMapList(sql);

		sql = "INSERT INTO T_SERVICE_SERVICEPRICE (DJDXBH,FWDJ,DJLX,CLRID,SFYX,CJSJ,FWDJBH,FWMC,DJMS) VALUES (?,?,?,?,1,now(),?,?,1)";

		String sqlfwdj = "select * from T_DICT_FWDJ " ;
		List<Map<String, Object>> priceList = dbUtil.queryForMapList(sqlfwdj);
		Object[][] pra = null;
		Long fwsbh = null;
		Long clrid = null;
		Long fwdjbh = null;
		System.out.println("处理运营商个数"+serviceprice.size());
		try {
			String fwdjmc = null;
			if(serviceprice != null && serviceprice.size() > 0){
				for (int i = 0; i < serviceprice.size(); i++) {
					fwsbh = (Long) serviceprice.get(i).get("fwsbh");
					clrid = (Long) serviceprice.get(i).get("rybh");
					pra = new Object[priceList.size()][];
					for(int j=0;j<priceList.size();j++){
						fwdjbh = (Long) priceList.get(j).get("fwdjbh");
						fwdjmc = (String) priceList.get(j).get("fwdjmc");
						System.out.println("处理数据{}{"+String.format("%d", fwsbh)+" ,"+String.format("%d", clrid)+","+String.format("%d", fwdjbh)+","+fwdjmc);
						pra[j] = new Object[] { fwsbh,0, 0,clrid ,fwdjbh,fwdjmc};
					}
					dbUtil.batchUpdate(sql, pra);
				}
			}else{
				System.out.println("无数据");
			}
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		}
		conn.commit();
	}

}
