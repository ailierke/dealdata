package diwinet.wp.vo; 

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * <p>标题：设备信息</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年3月25日</p>
 * @author	Shucheng Luo
 */
public class SensorInfo implements Serializable { 

	private static final long serialVersionUID = 1L; 

	private Long sbbh;
	private String sbtm;
	private String sbmc;
	private Integer pcxh;
	private String sbhh;
	private Float cpzl;
	private Float cpjg;
	private String bztj;
	private Integer sfgd;
	private String hdgl;
	private Integer cpsm;
	private Integer zbnx;
	private String qtxz;
	private Integer sbfl;
	private Timestamp scrq;
	private Long yhid;
	private String token;
	private Integer sblx;
	private String sbbm;
	private Integer yhlx;
	private String bdsj;
	private Integer sfnq;//是否内嵌（0.非内嵌，1.内嵌非租赁式，2.内嵌租赁式）
	private Integer wllx;
	private Integer tmzt;//条码状态
	
	
	public String getBdsj() {
		return bdsj;
	}
	public String getBztj() {
		return this.bztj;
	}
	public Float getCpjg() {
		return cpjg;
	}
	public Integer getCpsm() {
		return this.cpsm;
	}
	public Float getCpzl() {
		return cpzl;
	}
	public String getHdgl() {
		return this.hdgl;
	}
	public Integer getPcxh() {
		return this.pcxh;
	}
	public String getQtxz() {
		return this.qtxz;
	}
	public Long getSbbh() {
		return this.sbbh;
	}
	public String getSbbm() {
		return sbbm;
	}
	public Integer getSbfl() {
		return this.sbfl;
	}
	public String getSbhh() {
		return this.sbhh;
	}
	public Integer getSblx() {
		return sblx;
	}
	public String getSbmc() {
		return this.sbmc;
	}
	public String getSbtm() {
		return this.sbtm;
	}
	public Timestamp getScrq() {
		return this.scrq;
	}
	public Integer getSfgd() {
		return this.sfgd;
	}
	public Integer getSfnq() {
		return sfnq;
	}
	public Integer getTmzt() {
		return tmzt;
	}
	public String getToken() {
		return token;
	}
	public Integer getWllx() {
		return wllx;
	}
	public Long getYhid() {
		return yhid;
	}
	public Integer getYhlx() {
		return yhlx;
	}
	public Integer getZbnx() {
		return this.zbnx;
	}
	public void setBdsj(String bdsj) {
		this.bdsj = bdsj;
	}
	public void setBztj(String bztj) {
		this.bztj = bztj;
	}
	public void setCpjg(Float cpjg) {
		this.cpjg = cpjg;
	}
	public void setCpsm(Integer cpsm) {
		this.cpsm = cpsm;
	}
	public void setCpzl(Float cpzl) {
		this.cpzl = cpzl;
	}
	public void setHdgl(String hdgl) {
		this.hdgl = hdgl;
	}
	public void setPcxh(Integer pcxh) {
		this.pcxh = pcxh;
	}
	public void setQtxz(String qtxz) {
		this.qtxz = qtxz;
	}
	public void setSbbh(Long sbbh) {
		this.sbbh = sbbh;
	}
	public void setSbbm(String sbbm) {
		this.sbbm = sbbm;
	}
	public void setSbfl(Integer sbfl) {
		this.sbfl = sbfl;
	}
	public void setSbhh(String sbhh) {
		this.sbhh = sbhh;
	}
	public void setSblx(Integer sblx) {
		this.sblx = sblx;
	}
	public void setSbmc(String sbmc) {
		this.sbmc = sbmc;
	}
	public void setSbtm(String sbtm) {
		this.sbtm = sbtm;
	}
	public void setScrq(Timestamp scrq) {
		this.scrq = scrq;
	}
	public void setSfgd(Integer sfgd) {
		this.sfgd = sfgd;
	}
	public void setSfnq(Integer sfnq) {
		this.sfnq = sfnq;
	}
	public void setTmzt(Integer tmzt) {
		this.tmzt = tmzt;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setWllx(Integer wllx) {
		this.wllx = wllx;
	}
	public void setYhid(Long yhid) {
		this.yhid = yhid;
	}
	public void setYhlx(Integer yhlx) {
		this.yhlx = yhlx;
	}
	public void setZbnx(Integer zbnx) {
		this.zbnx = zbnx;
	}
} 
