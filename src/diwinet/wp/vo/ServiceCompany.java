package diwinet.wp.vo; 

import java.io.Serializable;

import util.StringUtil;
/**
 * <p>标题：服务商信息</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年4月14日</p>
 * @author	Shucheng Luo
 */
public class ServiceCompany implements Serializable { 

	private static final long serialVersionUID = 1L; 

	private Long fwsbh;
	private String fwsmc;
	private String lxr;
	private String fwlx;
	private String fwqy;
	private String fwsjj;
	private String fwsdz;
	private String fwsrx;
	private String fwsry;
	private String fwsbz;
	private Integer shzt;
	private Long yhid;
	private Long sfbh;
	private Long dsbh;
	private Long qxbh;
	private String sfmc;
	private String dsmc;
	private String qxmc;
	private String jtdz;

	public Long getSfbh() {
		return sfbh;
	}
	public String getSfmc() {
		return sfmc;
	}
	public void setSfmc(String sfmc) {
		this.sfmc = sfmc;
	}
	public String getDsmc() {
		return dsmc;
	}
	public void setDsmc(String dsmc) {
		this.dsmc = dsmc;
	}
	public String getQxmc() {
		return qxmc;
	}
	public void setQxmc(String qxmc) {
		this.qxmc = qxmc;
	}
	public String getJtdz() {
		return jtdz;
	}
	public void setJtdz(String jtdz) {
		this.jtdz = jtdz;
	}
	public void setSfbh(Long sfbh) {
		this.sfbh = sfbh;
	}
	public Long getDsbh() {
		return dsbh;
	}
	public void setDsbh(Long dsbh) {
		this.dsbh = dsbh;
	}
	public Long getQxbh() {
		return qxbh;
	}
	public void setQxbh(Long qxbh) {
		this.qxbh = qxbh;
	}
	public Long getYhid() {
		return yhid;
	}
	public void setYhid(Long yhid) {
		this.yhid = yhid;
	}
	public void setFwsbh(Long fwsbh) {
		this.fwsbh = fwsbh;
	}
	public Long getFwsbh() {
		return this.fwsbh;
	}
	public void setFwsmc(String fwsmc) {
		this.fwsmc = fwsmc;
	}
	public String getFwsmc() {
		return this.fwsmc;
	}
	public void setLxr(String lxr) {
		this.lxr = lxr;
	}
	public String getLxr() {
		return this.lxr;
	}
	public void setFwlx(String fwlx) {
		this.fwlx = fwlx;
	}
	public String getFwlx() {
		return this.fwlx;
	}
	public void setFwqy(String fwqy) {
		this.fwqy = fwqy;
	}
	public String getFwqy() {
		return this.fwqy;
	}
	public void setFwsjj(String fwsjj) {
		this.fwsjj = fwsjj;
	}
	public String getFwsjj() {
		return this.fwsjj;
	}
	public void setFwsdz(String fwsdz) {
		this.fwsdz = fwsdz;
	}
	public String getFwsdz() {
		return this.fwsdz;
	}
	public void setFwsrx(String fwsrx) {
		this.fwsrx = fwsrx;
	}
	public String getFwsrx() {
		return this.fwsrx;
	}
	public void setFwsry(String fwsry) {
		this.fwsry = fwsry;
	}
	public String getFwsry() {
		return this.fwsry;
	}
	public void setFwsbz(String fwsbz) {
		this.fwsbz = fwsbz;
	}
	public String getFwsbz() {
		return this.fwsbz;
	}
	public void setShzt(Integer shzt) {
		this.shzt = shzt;
	}
	public Integer getShzt() {
		return this.shzt;
	}
	public String getAdress(){
		StringBuilder adress = new StringBuilder();
		if(!StringUtil.isEmpty(this.sfmc)){
			adress.append(this.sfmc);
		}
		if(!StringUtil.isEmpty(this.dsmc)){
			if(!this.dsmc.equals(this.sfmc)){
				adress.append(this.dsmc);
			}
		}
		if(!StringUtil.isEmpty(this.qxmc)){
			adress.append(this.qxmc);
		}
		return adress.toString();
	}
} 
