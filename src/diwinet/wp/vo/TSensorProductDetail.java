package diwinet.wp.vo; 

import java.util.Date; 
import java.io.Serializable; 

public class TSensorProductDetail implements Serializable { 

	private static final long serialVersionUID = 1L; 

	private Long scbh;
	private Long sbbh;
	private String sbtm;
	private String sqpc;
	private Integer sblx;
	private Integer wllx;
	private Integer sbds;
	private Integer pcxh;
	private Integer sbbb;
	private Date scrq;
	private Date jlsj;

	public void setScbh(Long scbh) {
		this.scbh = scbh;
	}
	public Long getScbh() {
		return this.scbh;
	}
	public void setSbbh(Long sbbh) {
		this.sbbh = sbbh;
	}
	public Long getSbbh() {
		return this.sbbh;
	}
	public void setSbtm(String sbtm) {
		this.sbtm = sbtm;
	}
	public String getSbtm() {
		return this.sbtm;
	}
	public void setSqpc(String sqpc) {
		this.sqpc = sqpc;
	}
	public String getSqpc() {
		return this.sqpc;
	}
	public void setSblx(Integer sblx) {
		this.sblx = sblx;
	}
	public Integer getSblx() {
		return this.sblx;
	}
	public void setWllx(Integer wllx) {
		this.wllx = wllx;
	}
	public Integer getWllx() {
		return this.wllx;
	}
	public void setSbds(Integer sbds) {
		this.sbds = sbds;
	}
	public Integer getSbds() {
		return this.sbds;
	}
	public void setPcxh(Integer pcxh) {
		this.pcxh = pcxh;
	}
	public Integer getPcxh() {
		return this.pcxh;
	}
	public void setSbbb(Integer sbbb) {
		this.sbbb = sbbb;
	}
	public Integer getSbbb() {
		return this.sbbb;
	}
	public void setScrq(Date scrq) {
		this.scrq = scrq;
	}
	public Date getScrq() {
		return this.scrq;
	}
	public void setJlsj(Date jlsj) {
		this.jlsj = jlsj;
	}
	public Date getJlsj() {
		return this.jlsj;
	}
} 
