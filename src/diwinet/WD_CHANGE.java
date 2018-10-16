package diwinet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import diwinet.wp.vo.ServiceCompany;
import diwinet.wp.vo.TServicePosJob;
import util.DBUtil;
import util.DBUtilsTemplate;
/**
 * <p>标题：物业模式后，原来经销商新增售后服务中心，网点更新</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2017 diwinet</p>
 * <p>日期：2017年2月13日</p>
 * @author	Feiyang Chen
 */
public class WD_CHANGE {
	public static void main(String[] args) throws SQLException, IOException {
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(false);
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);
		String sql = null;
		//查询经销商信息
		List<ServiceCompany> serviceCompanys = dbUtil.queryForList("SELECT * FROM T_SERVICE_COMPANY ",ServiceCompany.class);
		if(serviceCompanys!=null&&serviceCompanys.size()>0){
			ServiceCompany service = null;
			List<TServicePosJob> jobs = null;
			Long gwbh = null;
			Long wdbh = null;
			List<Long> oldwdbhs = null;
			int shwdNum = 0;
			try {
				for(int i =0 ;i<serviceCompanys.size();i++){
					try{
						service = serviceCompanys.get(i);
						//该经销商下原来售后网点列表
//						oldwdbhs = dbUtil.queryForList("SELECT A.WDBH FROM T_SERVICE_POS A WHERE A.FWSBH = ? ", Long.class, service.getFwsbh());
						oldwdbhs = dbUtil.queryForListByKey("SELECT A.WDBH as wdbh FROM T_SERVICE_POS A WHERE A.FWSBH = ? ", "wdbh", Long.class,service.getFwsbh());
						//0.判断该经销商下是否有售后服务网点
						shwdNum = dbUtil.queryForInt("SELECT COUNT(1) FROM T_SERVICE_POS A WHERE A.FWSBH = ? AND A.WDLX = 1 ", service.getFwsbh());
						if(shwdNum > 0){
							System.out.println("编号为【"+service.getFwsbh()+"】的经销商，已存在售后服务网点，跳过更新！");
							continue;
						}
						
						//1.新增售后服务网点信息
						sql = "INSERT into T_SERVICE_POS (WDLX,WDMC,WDDZ,LXDH,LXRM,FWSBH,SFBH,DSBH,QXBH,SFMC,DSMC,QXMC,JDSX,SFYX,CJSJ) VALUES (1,?,?,?,?,?,?,?,?,?,?,?,100,1,NOW())";
						wdbh = dbUtil.insert(sql,new Object[] {service.getFwsmc()+"售后服务网点",service.getFwsdz(),service.getFwsrx(),service.getLxr(),service.getFwsbh(),
								service.getSfbh(),service.getDsbh(),service.getQxbh(),service.getSfmc(),service.getDsmc(),service.getQxmc()});
						
						//4.根据服务商编号和角色ID查询原来网点岗位表
						jobs = dbUtil.queryForList("SELECT A.* FROM T_SERVICE_POS_JOB A LEFT JOIN T_SERVICE_POS B ON A.WDBH=B.WDBH WHERE B.FWSBH=? AND A.JSID=5 ", TServicePosJob.class, service.getFwsbh());
						
						//2.根据经销商删除售后岗位
						for(TServicePosJob job : jobs){
							dbUtil.delete("DELETE FROM T_SERVICE_POS_JOB WHERE GWBH = ? ", job.getGwbh());
							System.out.println("经销商编号为:【"+service.getFwsbh()+"】删除了【"+job.getGwbh()+"】编号售后岗位。");
						}
						//3.为经销商新增售后服务岗位
						gwbh = dbUtil.insert("INSERT INTO T_SERVICE_POS_JOB(WDBH,GWMC,SFYX,CJSJ,CJRY,CJRM,JSID,SFMRGW) VALUES (?,?,1,NOW(),?,?,5,1)", new Object[]{wdbh,"安装售后",32,"系统管理员"});
						
						//5.根据岗位编号将原来售后人员的网点编号和岗位编号更新
						if(jobs!=null&&jobs.size()>0){
							for(TServicePosJob job : jobs){
								int num_pos_user = dbUtil.update("UPDATE T_SERVICE_POS_USER A SET A.GWBH=?,A.WDBH=? WHERE A.GWBH = ? ", new Object[]{gwbh,wdbh,job.getGwbh()});
								System.out.println("根据原岗位编号【"+job.getGwbh()+"】更新了:【"+num_pos_user+"】个售后人员信息");
							}
						}
						
						//6.将订单的网点更新为售后服务网点
						if(oldwdbhs!=null&&oldwdbhs.size()>0){
							for(Long oldwdbh : oldwdbhs){
								dbUtil.update("UPDATE T_ORDER_AREA_POS A SET A.WDBH = ? WHERE A.WDBH = ? ", new Object[]{wdbh,oldwdbh});
							}
						}
					}catch(Exception e){
						conn.rollback();
						System.out.println("经销商编号为:【"+service.getFwsbh()+"】的网点更新同步失败");
						e.printStackTrace();
					}
					
				}
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				System.out.println("【网点更新失败，请检查程序！】");
				e.printStackTrace();
			}
		}else{
			System.out.println("无经销商信息");
		}

	}
}
