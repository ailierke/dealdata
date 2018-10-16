package diwinet.card;

import java.io.Serializable;
import java.util.Date;

public class LeaseWpVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date sjdqsj;
	public Date getSjdqsj() {
		return sjdqsj;
	}
	public void setSjdqsj(Date sjdqsj) {
		this.sjdqsj = sjdqsj;
	}
	private Long khjsjbh ;
	private Long sbbh;
	private Date cjsj;
	private Date dqsj;
	private Integer syq;
	private String sbtm ;
	public String getSbtm() {
		return sbtm;
	}
	public void setSbtm(String sbtm) {
		this.sbtm = sbtm;
	}
	public Long getKhjsjbh() {
		return khjsjbh;
	}
	public void setKhjsjbh(Long khjsjbh) {
		this.khjsjbh = khjsjbh;
	}
	public Long getSbbh() {
		return sbbh;
	}
	public void setSbbh(Long sbbh) {
		this.sbbh = sbbh;
	}
	public Date getCjsj() {
		return cjsj;
	}
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	public Date getDqsj() {
		return dqsj;
	}
	public void setDqsj(Date dqsj) {
		this.dqsj = dqsj;
	}
	public Integer getSyq() {
		return syq == null ?0:syq;
	}
	public void setSyq(Integer syq) {
		this.syq = syq;
	}
}
