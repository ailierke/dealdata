package diwinet.wp.vo; 

import java.io.Serializable;
import java.util.Date; 

public class TServiceCompanyBrand implements Serializable { 

	private static final long serialVersionUID = 1L; 

	private Long zjid;
	private Long fwsbh;
	private Long ppbh;
	private String ppmc;
	private String jsjmc;
	private Long jsjbh;
	private Short dllx;
	private Integer sfyx;
	private Date cjsj;

	public void setZjid(Long zjid) {
		this.zjid = zjid;
	}
	public Long getZjid() {
		return this.zjid;
	}
	public void setFwsbh(Long fwsbh) {
		this.fwsbh = fwsbh;
	}
	public Long getFwsbh() {
		return this.fwsbh;
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
	public void setJsjmc(String jsjmc) {
		this.jsjmc = jsjmc;
	}
	public String getJsjmc() {
		return this.jsjmc;
	}
	public void setJsjbh(Long jsjbh) {
		this.jsjbh = jsjbh;
	}
	public Long getJsjbh() {
		return this.jsjbh;
	}
	public void setDllx(Short dllx) {
		this.dllx = dllx;
	}
	public Short getDllx() {
		return this.dllx;
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
