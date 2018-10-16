package diwinet.wp.vo;

import java.io.Serializable;
import java.util.Date;

public class LeaseUserRemain implements Serializable{
	public static final int FLOW = 0;
	private static final long serialVersionUID = 1L;
	public static final int  YEARLY  = 1;
	private Date cjsj;//创建时间
	private String cpmc;//产品名称
	private String cpxh;//产品型号
	private Date dqsj;//到期时间
	private Date gxsj;//记录更新时间
	private Integer jflx;//0、流量 1、年卡
	private Long jsjbh;//净水机编号
	private Integer jsjlx;//净水机类型
	private Long khjsjbh;
	private Long ppbh;//品牌编号
	private String ppmc;//品牌名称
	private Double syzll;//购买总流量（总购买流量）
	private Long zjbh;
	
	public LeaseUserRemain() {
		super();
	}
	public LeaseUserRemain(Long khjsjbh, Double syzll, Date dqsj, Integer jflx, Date gxsj, Date cjsj) {
		super();
		this.khjsjbh = khjsjbh;
		this.syzll = syzll;
		this.dqsj = dqsj;
		this.jflx = jflx;
		this.gxsj = gxsj;
		this.cjsj = cjsj;
	}
	public Date getCjsj() {
		return cjsj;
	}
	public String getCpmc() {
		return cpmc;
	}
	
	public String getCpxh() {
		return cpxh;
	}
	public Date getDqsj() {
		return dqsj;
	}
	public Date getGxsj() {
		return gxsj;
	}
	public Integer getJflx() {
		return jflx;
	}
	public Long getJsjbh() {
		return jsjbh;
	}
	public Integer getJsjlx() {
		return jsjlx;
	}
	public Long getKhjsjbh() {
		return khjsjbh;
	}
	public Long getPpbh() {
		return ppbh;
	}
	public String getPpmc() {
		return ppmc;
	}
	public Double getSyzll() {
		return syzll;
	}
	public Long getZjbh() {
		return zjbh;
	}
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	public void setCpmc(String cpmc) {
		this.cpmc = cpmc;
	}
	public void setCpxh(String cpxh) {
		this.cpxh = cpxh;
	}
	public void setDqsj(Date dqsj) {
		this.dqsj = dqsj;
	}
	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}
	public void setJflx(Integer jflx) {
		this.jflx = jflx;
	}
	public void setJsjbh(Long jsjbh) {
		this.jsjbh = jsjbh;
	}
	public void setJsjlx(Integer jsjlx) {
		this.jsjlx = jsjlx;
	}
	public void setKhjsjbh(Long khjsjbh) {
		this.khjsjbh = khjsjbh;
	}
	public void setPpbh(Long ppbh) {
		this.ppbh = ppbh;
	}
	public void setPpmc(String ppmc) {
		this.ppmc = ppmc;
	}
	public void setSyzll(Double syzll) {
		this.syzll = syzll;
	}
	public void setZjbh(Long zjbh) {
		this.zjbh = zjbh;
	}
	
}
