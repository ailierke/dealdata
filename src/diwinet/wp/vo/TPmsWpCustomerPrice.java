package diwinet.wp.vo; 

import java.util.Date; 
import java.io.Serializable; 

public class TPmsWpCustomerPrice implements Serializable { 

	private static final long serialVersionUID = 1L; 

	private Long zjid;
	private Long zfbh;
	private Long khjsjbh;
	private Integer sfyx;
	private Date cjsj;

	public void setZjid(Long zjid) {
		this.zjid = zjid;
	}
	public Long getZjid() {
		return this.zjid;
	}
	public void setZfbh(Long zfbh) {
		this.zfbh = zfbh;
	}
	public Long getZfbh() {
		return this.zfbh;
	}
	public void setKhjsjbh(Long khjsjbh) {
		this.khjsjbh = khjsjbh;
	}
	public Long getKhjsjbh() {
		return this.khjsjbh;
	}
	public void setSfyx(Integer sfyx) {
		this.sfyx = sfyx;
	}
	public Integer getSfyx() {
		return this.sfyx;
	}
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	public Date getCjsj() {
		return this.cjsj;
	}
} 
