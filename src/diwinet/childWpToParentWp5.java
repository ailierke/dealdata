package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.SensorBinding;
import diwinet.wp.vo.SensorInfo;
import diwinet.wp.vo.SensorUserBinding;
import diwinet.wp.vo.WpCustomerBanding;
import util.DBUtil;
import util.DBUtilsTemplate;
/**
 * 
 * <p>标题：将新增的净水机信息全部同步到原来的父表</p>
 * <p>描述：
 * 		数据库数据恢复t_sensor-Banding 
 * 
 * </p>
 * <p>Copyright：Copyright(c) 2016 diwinet</p>
 * <p>日期：2016年3月28日</p>
 * @author	jiangxing
 */
public class childWpToParentWp5 {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = null;
		List<SensorUserBinding> brands = dbUtil.queryForList("select * from T_SENSOR_USER_BINDING where yhlx = 1 group by SBBH",SensorUserBinding.class);
		Long sbbh = null;
		for(SensorUserBinding  banding : brands){
			sbbh = banding.getSbbh();
			SensorBinding sensorBanding = dbUtil.findFirst(SensorBinding.class,"select * from T_SENSOR_BINDING where sbbh ="+sbbh);
			WpCustomerBanding wp = dbUtil.findFirst(WpCustomerBanding.class,"select * from T_WP_CUSTOMER_BANDING where sbbh ="+sbbh);
			SensorInfo sensor = dbUtil.findFirst(SensorInfo.class,"select * from T_SENSOR_INFO where sbbh ="+sbbh);
			if(sensorBanding==null&&wp!=null&&sensor!=null){
				try {
					String date = null;
					System.out.println(sensor.getSbbh());
					if(banding.getBdsj()!=null){
						 date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(banding.getBdsj());
					}else{
						date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					}
					sql = "insert into T_SENSOR_BINDING(SBBH,SBTM,JSJBH,PPBH,SFBH,DSBH,QXBH,SFMC,DSMC,QXMC,XXDZ,BDSJ,CZRQ,SFTBSL) "
							+ "VALUES("+sbbh+",'"+sensor.getSbtm()+"',"+wp.getJsjbh()+","+wp.getPpbh()+","+wp.getSfbh()+","+wp.getDsbh()+",'"+wp.getQxbh()+"','"+wp.getSfmc()+"','"+wp.getDsmc()+"','"+wp.getQxmc()+"','"+wp.getJtdz()+"','"+date+"','"+date+"',0)";
					dbUtil.insert(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
