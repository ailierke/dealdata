package diwinet.wp.vo;

import java.io.Serializable;
import java.util.Date;

public class WpCompanyLeasePrice implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long csbh;//厂商编号
	private String csmc;//厂商名称
	private Long jsjbh;//净水机编号
	private String jsjmc;//净水机名称
	private Double djje;//单价
	private Double djje1;//修改更新后
	private Double zdcze;//最低充值额
	
	
	public Double getZdcze() {
		return zdcze;
	}
	public void setZdcze(Double zdcze) {
		this.zdcze = zdcze;
	}
	public Double getDjje1() {
		return djje1;
	}
	public void setDjje1(Double djje1) {
		this.djje1 = djje1;
	}
	public Double getBjje1() {
		return bjje1;
	}
	public void setBjje1(Double bjje1) {
		this.bjje1 = bjje1;
	}
	public Date getSxsj() {
		return sxsj;
	}
	public void setSxsj(Date sxsj) {
		this.sxsj = sxsj;
	}
	private Integer jffs;//计费方式
	private Double bjje;//半价金额
	private Double bjje1;//修改更新后
	private Date tjsj;//添加时间
	private Integer yjsz;//预警设置 天/L
	private Date sxsj;//修改后生效时间
	public Long getCsbh() {
		return csbh;
	}
	public void setCsbh(Long csbh) {
		this.csbh = csbh;
	}
	public String getCsmc() {
		return csmc;
	}
	public void setCsmc(String csmc) {
		this.csmc = csmc;
	}
	public Long getJsjbh() {
		return jsjbh;
	}
	public void setJsjbh(Long jsjbh) {
		this.jsjbh = jsjbh;
	}
	public String getJsjmc() {
		return jsjmc;
	}
	public void setJsjmc(String jsjmc) {
		this.jsjmc = jsjmc;
	}
	public Double getDjje() {
		return djje;
	}
	public void setDjje(Double djje) {
		this.djje = djje;
	}
	public Integer getJffs() {
		return jffs;
	}
	public void setJffs(Integer jffs) {
		this.jffs = jffs;
	}
	public Double getBjje() {
		return bjje;
	}
	public void setBjje(Double bjje) {
		this.bjje = bjje;
	}
	public Date getTjsj() {
		return tjsj;
	}
	public void setTjsj(Date tjsj) {
		this.tjsj = tjsj;
	}
	public Integer getYjsz() {
		return yjsz;
	}
	public void setYjsz(Integer yjsz) {
		this.yjsz = yjsz;
	}
	
	
}
