package diwinet.wp.vo; 

import java.io.Serializable;
import java.util.Date; 

public class TDictTdsArea implements Serializable { 

	private static final long serialVersionUID = 1L; 

	private Integer jlbh;
	private String sfbh;
	private String dsbh;
	private String qxbh;
	private Integer tds;
	private Double ylz;
	private Integer tbr;
	private Date tbsj;
	private Integer cstds;

	public void setJlbh(Integer jlbh) {
		this.jlbh = jlbh;
	}
	public Integer getJlbh() {
		return this.jlbh;
	}
	public void setSfbh(String sfbh) {
		this.sfbh = sfbh;
	}
	public String getSfbh() {
		return this.sfbh;
	}
	public void setDsbh(String dsbh) {
		this.dsbh = dsbh;
	}
	public String getDsbh() {
		return this.dsbh;
	}
	public void setQxbh(String qxbh) {
		this.qxbh = qxbh;
	}
	public String getQxbh() {
		return this.qxbh;
	}
	public void setTds(Integer tds) {
		this.tds = tds;
	}
	public Integer getTds() {
		return this.tds;
	}
	public void setYlz(Double ylz) {
		this.ylz = ylz;
	}
	public Double getYlz() {
		return this.ylz;
	}
	public void setTbr(Integer tbr) {
		this.tbr = tbr;
	}
	public Integer getTbr() {
		return this.tbr;
	}
	public void setTbsj(Date tbsj) {
		this.tbsj = tbsj;
	}
	public Date getTbsj() {
		return this.tbsj;
	}
	public void setCstds(Integer cstds) {
		this.cstds = cstds;
	}
	public Integer getCstds() {
		return this.cstds;
	}
} 
