package diwinet.ple;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.TWpBrand;
import diwinet.wp.vo.TWpFilterelement;
import diwinet.wp.vo.TWpInfo;
import diwinet.wp.vo.WpCustomerBanding;
import util.DBUtil;
import util.DBUtilLocal;
import util.DBUtilsTemplate;
/**
 * <p>
 * ple商用水机数据处理
 * 1、停止所有的试用期，将试用期表T_PLE_INFO_EXPERIENCE 试用时间修改，观看sda日志信息，看是否job执行完毕
 * 2、在钱包/电子水卡表中加入虚拟金额记录
 * 3、在T_PLE_BUY_REMAIN 表中加入是否处理字段 。查询出T_PLE_BUY_REMAIN中 SYZLL+BDCSSL-DQCSSL 剩余的流量，
 *     如果是负数放入真实金额字段，如果是是非负数，放入另外的体验金额字段。处理完修改状态
 *     处理完之后：T_PLE_BUY_REMAIN T_PLE_RECHARGE_RECORD就此废弃
 * </p>
 * <p>Copyright：Copyright(c) 2016 diwinet</p>
 * <p>日期：2016年3月28日</p>
 * @author	jiangxing
 */
public class T_PLE_RECHARGE_WATER_TRANSFORM {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtilLocal.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = null;
		List<WpCustomerBanding> wpList = dbUtil.queryForList("",WpCustomerBanding.class);
		//查询经销商添加的品牌信息
		if(wpList!=null&&wpList.size()>0){
			WpCustomerBanding wp = null;
			Long jsjbh = null;
			Long khjsjbh = null;
			Integer jsjlx = null;
			for(int i =0 ;i<wpList.size();i++){
				conn.setAutoCommit(false);
				try {
					wp = wpList.get(i);
					jsjbh = wp.getJsjbh();
					khjsjbh = wp.getKhjsjbh();
					sql = "select * from T_WP_INFO where jsjbh = "+jsjbh;
					TWpInfo wpInfo = dbUtil.findFirst(TWpInfo.class,sql);
					/**
					 * 循环品牌下的净水机
					 */
					if(wpInfo!=null){
						jsjlx = wpInfo.getJsjlx();
						sql = "update T_WP_CUSTOMER_BANDING set khsyms ="+jsjlx+" where khjsjbh ="+khjsjbh;
					}else{
						sql = "update T_WP_CUSTOMER_BANDING set khsyms ="+0+" where khjsjbh ="+khjsjbh;
					}
					dbUtil.update(sql);
				} catch (Exception e) {
					conn.rollback();
					System.out.println("客户净水机编号:【"+khjsjbh+"】同步客户商业模式失败	");
					e.printStackTrace();
				}
			}
			conn.commit();
			System.out.println("共处理净水机绑定信息："+wpList.size()+"个");
		}else{
			System.out.println("净水机绑定信息khsyms都已存在,完成处理");
		}
	}
}
