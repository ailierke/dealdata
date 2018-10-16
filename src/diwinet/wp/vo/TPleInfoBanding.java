package diwinet.wp.vo; 

import java.io.Serializable;
import java.util.Date; 

public class TPleInfoBanding implements Serializable { 
	@Override
	public String toString() {
		return "TPleInfoBanding [cssl=" + cssl + ", sbtm=" + sbtm + "]";
	}
	public  static short PMLX_INDOOR =1; //室内模式机
	public  static short PMLX_SHARE =2; //共享商业机
	public  static int SENSOR_PROTECT =20; //室内模式机
	public  static int SENSOR_SHARE =30; //共享商业机
	private static final long serialVersionUID = 1L;
	private Date bdsj;
	private Double cssl;
	private Long czrid;
	private String czrm;

	private Long djbzbh;//定价标准标号
	private Long dwbh;
	private Long jtdwbh; 

	private String jtdwmc;
	private String jtdz;
	private String jtdz1;
	private Integer lch;
	private Long pmbh;
	private String pmbz;
	private Short pmlx;
	private Long sbbh;
	private String sbtm;
	private Boolean sfzx;//是否在线
	private String jtdw;
	
	public String getJtdw() {
		return jtdw;
	}
	public void setJtdw(String jtdw) {
		this.jtdw = jtdw;
	}
	public TPleInfoBanding() {
		super();
	}
	public TPleInfoBanding(Boolean sfzx, Date bdsj, Double cssl, Long czrid, String czrm, Long djbzbh, Long jtdwbh,
			Long pmbh, String pmbz, Short pmlx, Long sbbh, String sbtm, Long dwbh, String jtdz, String jtdz1,
			Integer lch, String jtdwmc) {
		super();
		this.sfzx = sfzx;
		this.bdsj = bdsj;
		this.cssl = cssl;
		this.czrid = czrid;
		this.czrm = czrm;
		this.djbzbh = djbzbh;
		this.jtdwbh = jtdwbh;
		this.pmbh = pmbh;
		this.pmbz = pmbz;
		this.pmlx = pmlx;
		this.sbbh = sbbh;
		this.sbtm = sbtm;
		this.dwbh = dwbh;
		this.jtdz = jtdz;
		this.jtdz1 = jtdz1;
		this.lch = lch;
		this.jtdwmc = jtdwmc;
	}
	public TPleInfoBanding(Long sbbh, String sbtm, Long jtdwbh, Long czrid, String czrm, String pmbz,
			Date bdsj, Short pmlx,Double cssl) {
		super();
		this.sbbh = sbbh;
		this.sbtm = sbtm;
		this.jtdwbh = jtdwbh;
		this.czrid = czrid;
		this.czrm = czrm;
		this.pmbz = pmbz;
		this.bdsj = bdsj;
		this.pmlx = pmlx;
		this.cssl = cssl;
	}
	public TPleInfoBanding(Long sbbh, String sbtm, Long jtdwbh, Long czrid, String czrm, String pmbz,
			Date bdsj, Short pmlx,Double cssl,Long djbzbh,Long dwbh) {
		super();
		this.sbbh = sbbh;
		this.sbtm = sbtm;
		this.jtdwbh = jtdwbh;
		this.czrid = czrid;
		this.czrm = czrm;
		this.pmbz = pmbz;
		this.bdsj = bdsj;
		this.pmlx = pmlx;
		this.cssl = cssl;
		this.djbzbh = djbzbh;
		this.dwbh = dwbh;
	}
	public Date getBdsj() {
		return bdsj;
	}
	public Double getCssl() {
		return cssl==null?0d:cssl;
	}
	public Long getCzrid() {
		return this.czrid;
	}
	public String getCzrm() {
		return this.czrm;
	}
	public Long getDjbzbh() {
		return djbzbh;
	}
	public Long getDwbh() {
		return dwbh;
	}
	public Long getJtdwbh() {
		return this.jtdwbh;
	}
	public String getJtdwmc() {
		return jtdwmc;
	}
	public String getJtdz() {
		return jtdz == null ? this.jtdz1 : this.jtdz;
	}
	public String getJtdz1() {
		return jtdz1;
	}
	public Integer getLch() {
		return lch;
	}
	public Long getPmbh() {
		return this.pmbh;
	}
	public String getPmbz() {
		return this.pmbz;
	}
	public Short getPmlx() {
		return this.pmlx;
	}

	public Long getSbbh() {
		return this.sbbh;
	}
	public String getSbtm() {
		return this.sbtm;
	}
	public Boolean getSfzx() {
		return sfzx;
	}
	public void setBdsj(Date bdsj) {
		this.bdsj = bdsj;
	}
	public void setCssl(Double cssl) {
		this.cssl = cssl;
	}
	public void setCzrid(Long czrid) {
		this.czrid = czrid;
	}
	public void setCzrm(String czrm) {
		this.czrm = czrm;
	}
	public void setDjbzbh(Long djbzbh) {
		this.djbzbh = djbzbh;
	}
	public void setDwbh(Long dwbh) {
		this.dwbh = dwbh;
	}
	public void setJtdwbh(Long jtdwbh) {
		this.jtdwbh = jtdwbh;
	}
	public void setJtdwmc(String jtdwmc) {
		this.jtdwmc = jtdwmc;
	}
	public void setJtdz(String jtdz) {
		this.jtdz = jtdz;
	}
	public void setJtdz1(String jtdz1) {
		this.jtdz1 = jtdz1;
	}
	public void setLch(Integer lch) {
		this.lch = lch;
	}
	public void setPmbh(Long pmbh) {
		this.pmbh = pmbh;
	}
	public void setPmbz(String pmbz) {
		this.pmbz = pmbz;
	}
	
	public void setPmlx(Short pmlx) {
		this.pmlx = pmlx;
	}
	public void setSbbh(Long sbbh) {
		this.sbbh = sbbh;
	}
	public void setSbtm(String sbtm) {
		this.sbtm = sbtm;
	}
	public void setSfzx(Boolean sfzx) {
		this.sfzx = sfzx;
	}
} 
