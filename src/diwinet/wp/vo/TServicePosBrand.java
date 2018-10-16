package diwinet.wp.vo; 

import java.io.Serializable;
import java.util.Date; 

public class TServicePosBrand implements Serializable { 

	private static final long serialVersionUID = 1L; 

	private Long fwid;
	private Long wdbh;
	private Long ppbh;
	private String ppmc;
	private Integer sfyx;
	private Date cjrq;

	public void setFwid(Long fwid) {
		this.fwid = fwid;
	}
	public Long getFwid() {
		return this.fwid;
	}
	public void setWdbh(Long wdbh) {
		this.wdbh = wdbh;
	}
	public Long getWdbh() {
		return this.wdbh;
	}
	public void setPpbh(Long ppbh) {
		this.ppbh = ppbh;
	}
	public Long getPpbh() {
		return this.ppbh;
	}
	public void setPpmc(String ppmc) {
		this.ppmc = ppmc;
	}
	public String getPpmc() {
		return this.ppmc;
	}
	public void setSfyx(Integer sfyx) {
		this.sfyx = sfyx;
	}
	public Integer getSfyx() {
		return this.sfyx;
	}
	public void setCjrq(Date cjrq) {
		this.cjrq = cjrq;
	}
	public Date getCjrq() {
		return this.cjrq;
	}
} 
