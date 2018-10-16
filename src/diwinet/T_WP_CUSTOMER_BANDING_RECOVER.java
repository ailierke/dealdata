package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TPleInfoBanding;
import diwinet.wp.vo.TWaterReportHistory;
import diwinet.wp.vo.TWaterReportHistoryRT;
import util.DBUtil;
import util.DBUtilsTemplate;
/**
 * 
 * <p>标题：将新增的净水机信息全部同步到原来的父表</p>
 * <p>描述：净水机类型khsyms</p>
 * <p>
 * 首先查出update T_WP_CUSTOMER_BANDING set yxms=0 where yxms is null  将所有的yxms 设置为营销模式 0--传统模式 1--物业模式
 * 
 * 程序运行找出原来的select  * from T_WP_CUSTOMER_BANDING where khsyms is  null 
 * 然后查出净水机类型赋值给净水机绑定表
 * </p>
 * <p>Copyright：Copyright(c) 2016 diwinet</p>
 * <p>日期：2016年3月28日</p>
 * @author	jiangxing
 */
public class T_WP_CUSTOMER_BANDING_RECOVER {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(false);
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		/**
		 * 查询建正中心以前送过100升水的并且在4月2号还没有用完这100升水的设备
		 */
		ArrayList<String> ignoreList = new ArrayList<String>();
		ArrayList<String> updateList = new ArrayList<String>();
		ArrayList<String> erroList = new ArrayList<String>();
		ArrayList<String> useOffList = new ArrayList<String>();
		List<TPleInfoBanding> dealList = new ArrayList<TPleInfoBanding>();
		try {
			List<TPleInfoBanding> bdList = dbUtil.queryForList("SELECT b.*, IFNULL(r.syzll, 0) + IFNULL(b.cssl, 0) - IFNULL(rt.cssl, 0) AS m,CONCAT(I.xmmc,P.lch,P.jtdwmc,'号') as jtdw"
					+ " FROM T_PLE_INFO_BANDING b LEFT JOIN T_PLE_BUY_REMAIN r ON b.pmbh = r.pmbh LEFT JOIN dwater_all.T_WATER_REPORT_HISTORY_RT rt ON rt.sbtm = b.sbtm "
					+ " INNER JOIN dwater_ple.T_PLE_UNIT_POSITION P ON b.jtdwbh = P.jtdwbh AND P.JTDWZT = 1 INNER JOIN dwater_ple.T_PLE_UNIT_ITEM I ON P.xmbh = I.xmbh "
					+ " AND I.XMZT = 1 INNER JOIN dwater_ple.T_PLE_SERVICE_UNIT u ON u.dwbh = I.dwbh INNER JOIN dwater_ple.T_SENSOR_INFO_PLE sp ON sp.SBBH = b.SBBH "
					+ " WHERE b.dwbh = 326 and b.sbtm in( select b.sbtm from T_PLE_RECHARGE_RECORD r INNER JOIN T_PLE_INFO_BANDING b on b.pmbh = r.pmbh and b.dwbh = 326 and r.czfs = 6 and gmsl =100 group by r.pmbh) "
					+ " HAVING m > 0 ",TPleInfoBanding.class);
			/**
			 * 查询绑定时候或者绑定时间前上报增量是否等于绑定时候初始水量
			 * 如果等于进行下一步操作
			 * 不等于则手动修改过，保持记录等到下一步操作
			 */
			if(bdList != null && bdList.size() > 0){
				System.out.println("bdList ："+Arrays.toString(bdList.toArray()));
				TPleInfoBanding bd = null;
				Double cssl = null;
				Double bdcssl = 0d;
				Double wysl = 0d;
				Double ysysl = 0d;
				for(int i = 0;i<bdList.size() ;i++){
					bd = bdList.get(i);
					bdcssl = bd.getCssl(); 
					TWaterReportHistory his = dbUtil.findFirst(TWaterReportHistory.class, "select * from dwater_all.T_WATER_REPORT_HISTORY where sbtm = ? and bssj <=? order by bssj desc ",new Object[]{bd.getSbtm(),bd.getBdsj()});
					if(his != null){
						cssl = his.getCssl();
					}
					String jtdw = bd.getJtdw();
					if((bdcssl.doubleValue() != cssl.doubleValue() )&&
							!(jtdw.contains("23091")||jtdw.contains("25011")||
									jtdw.contains("2304")||jtdw.contains("2007")||jtdw.contains("1905"))
							){
//						System.out.println("设备条码：【"+bd.getSbtm()+"】绑定初始水量和绑定时间上报水量不符合，手动修改过,忽略操作");
						
//						if(){
//							System.out.println("pmbh："+bd.getPmbh()+"设备条码："+bd.getSbtm()+"设备条码："+bd.getSbtm()+"忽略的楼层名称："+bd.getJtdw());
//						}
						ignoreList.add(bd.getSbtm());
					}else{
						if(bd.getSbtm().equals("7090170978008884125")){
							continue;
						}
						TWaterReportHistoryRT rt = dbUtil.findFirst(TWaterReportHistoryRT.class, "select * from dwater_all.T_WATER_REPORT_HISTORY_RT where sbtm = ?  ",bd.getSbtm());
						if(rt == null){
							if(his == null){
								ysysl = 0d;
								//水机未用水，修改绑定表的绑定初始水量
							}
						}else{
							Double dqcssl = rt.getCssl();
							ysysl = dqcssl - cssl;
							if(ysysl < 0){
//								System.out.println("rt表上报数据比绑定时候还小,数据上报错误设备条码未"+bd.getSbtm());
								erroList.add(bd.getSbtm());
								continue;
							}
							
						}
						if(ysysl > 100){
//							System.out.println("不需要扣除水量，已经使用完毕，设备条码："+bd.getSbtm());
							useOffList.add(bd.getSbtm());
						}else{
							updateList.add(bd.getSbtm());
							wysl = 100d - ysysl;
							bd.setCssl(bd.getCssl()-wysl);
							dealList.add(bd);
						}
//						System.out.println("设备条码：【"+bd.getSbtm()+"】未用水量未:"+wysl);
					}
				}
			}
			
			if(dealList !=null &&dealList.size()>0){
				for(int i =0;i<dealList.size();i++){
					dbUtil.update("update T_PLE_INFO_BANDING set cssl = ? where pmbh = ?",new Object[]{dealList.get(i).getCssl(),dealList.get(i).getPmbh()});
				}
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}
		
		
		System.out.println("dealList ："+Arrays.toString(dealList.toArray()));
		System.out.println("updateList ："+Arrays.toString(updateList.toArray()));
		System.out.println("erroList ："+Arrays.toString(erroList.toArray()));
		System.out.println("useOfList ："+Arrays.toString(useOffList.toArray()));
		System.out.println("igonreList ："+Arrays.toString(ignoreList.toArray()));
	}
}
