package diwinet.card;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TLeasePay;
import diwinet.wp.vo.WpCustomerBanding;
import util.DBUtil;
import util.DBUtilsTemplate;
import util.DateUtils;
/**
 * <p>标题：检查净水机数据</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2018 diwinet</p>
 * <p>日期：2018年6月11日</p>
 * @author	xing.jiang
 */
public class T_PLE_CHECK_WP_DATA {
	public static void main(String[] args) throws SQLException, IOException {
		List<String>  sensors = new ArrayList<>();
//		sensors.add("7090171278008842040");
//		sensors.add("7090170378008839161");
//		sensors.add("7090171178008868005");
//		sensors.add("7090171178008842043");
//		sensors.add("7090171178008893014");
//		sensors.add("7090171178008812022");
//		sensors.add("7090171178008879049");
//		sensors.add("7090171178008880045");
//		sensors.add("7090171178008823004");
//		sensors.add("7090171178008804027");
//		sensors.add("7090171278008860044");
//		sensors.add("7090170678008858019");
//		sensors.add("7090170678008878017");
		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		//查询源动力下面的 有效品牌的所有年费水机
		//源动力
		List<WpCustomerBanding> wpList = dbUtil.queryForList(" select * from T_WP_CUSTOMER_BANDING where ppbh = 1000509 and khsyms = 4 ",WpCustomerBanding.class);
		//帝尔
//		List<WpCustomerBanding> wpList = dbUtil.queryForList(" select * from T_WP_CUSTOMER_BANDING where ppbh = 1000485 and khsyms = 4 ",WpCustomerBanding.class);
		StringBuilder sb = null;
		List<LeaseWpVo> leaseWpList = new ArrayList<LeaseWpVo>();
		//查询经销商添加的品牌信息
		if(wpList!=null&&wpList.size()>0){
			WpCustomerBanding wp = null;
			Long khjsjbh = null;
			for(int i =0 ;i<wpList.size();i++){
				conn.setAutoCommit(false);
				try {
					wp = wpList.get(i);
					khjsjbh = wp.getKhjsjbh();
					
					sb = new StringBuilder(" select i.sbtm,b.khjsjbh,i.sbbh,r.cjsj,r.dqsj,m.syq from  ");
					sb.append(" T_SENSOR_INFO i inner join T_WP_CUSTOMER_BANDING b on i.sbbh = b.sbbh and b.KHJSJBH = ? ");
					sb.append(" inner join T_PMS_WP_CUSTOMER_PRICE p on p.khjsjbh = b.KHJSJBH  ");
					sb.append(" inner join T_PMS_FIX_PRICE_MODE m on m.zfbh = p.zfbh  ");
					sb.append(" inner join  T_LEASE_USER_REMAIN r on r.khjsjbh = b.khjsjbh ");
					LeaseWpVo leaseWp = dbUtil.findFirst(LeaseWpVo.class,sb.toString(),khjsjbh);
//					if(sensors.contains(leaseWp.getSbtm())) {
//						continue;
//					}
					Integer syq = leaseWp.getSyq();
					Integer zsl = 0;
					sb = new StringBuilder(" select * from T_LEASE_PAY where khjsjbh = ? and czzt = 1  ");
					
					List<TLeasePay> payList  = dbUtil.queryForList(sb.toString(), TLeasePay.class,khjsjbh);
					if(payList == null) {
						zsl = syq;
						
					}else {
						Double zgmsl = payList.stream().mapToDouble(p ->{
							if(p.getGmdw().equals("月")) {
								return p.getGmsl()*30;
							}else if(p.getGmdw().equals("年")) {
								return p.getGmsl()*365;
							}else {
								return p.getGmsl();
							}
								 
						}).sum();
						zsl = zgmsl.intValue() + syq;
					}
					Calendar c = Calendar.getInstance();
					c.setTime(leaseWp.getCjsj());
					c.add(Calendar.DAY_OF_YEAR, zsl);
					
					if(!DateUtils.getShortCompactString(leaseWp.getDqsj()).equals(DateUtils.getShortCompactString(c.getTime()))) {
						leaseWp.setSjdqsj(c.getTime());
						leaseWpList.add(leaseWp);
					}
				} catch (Exception e) {
					conn.rollback();
					System.out.println("客户净水机编号:【"+khjsjbh+"】同步客户商业模式失败	");
					e.printStackTrace();
				}
			}
			
			
			if(leaseWpList != null) {
				leaseWpList.forEach( leaseWp -> {
					System.out.println("设备条码："+ leaseWp.getSbtm()+" 是否一样："+DateUtils.getShortCompactString(leaseWp.getDqsj()).equals(DateUtils.getShortCompactString(leaseWp.getSjdqsj()))+
							" 到期时间：" + ""+DateUtils.getShortCompactString(leaseWp.getDqsj())+"计算出来："+DateUtils.getShortCompactString(leaseWp.getSjdqsj())+" 多了多少天 ："+DateUtils.betweenDays(leaseWp.getDqsj(), leaseWp.getSjdqsj()));
//					updateDue(dbUtil,leaseWp);
					
				});
			}
			System.out.println("共处理净水机绑定信息："+wpList.size()+"个");
//			conn.commit();
		}else{
			System.out.println("净水机绑定信息khsyms都已存在,完成处理");
		}
	}

	private static void updateDue(DBUtilsTemplate dbUtil, LeaseWpVo leaseWp) {
		try {
			dbUtil.update("UPDATE T_LEASE_USER_REMAIN SET DQSJ = ? WHERE KHJSJBH = ? AND DQSJ = ?", new Object[] {leaseWp.getSjdqsj(),leaseWp.getKhjsjbh(),leaseWp.getDqsj()});
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		try {
			dbUtil.insert("INSERT INTO T_LEASE_USER_REMAIN_HIS (KHJSJBH, SYZLL, GXSJ, DQSJ) VALUES (?, NULL, now(), ?) ", new Object[] {leaseWp.getKhjsjbh(),leaseWp.getDqsj()});
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
			
		}
		
	}
}
