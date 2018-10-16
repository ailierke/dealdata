package diwinet.wp.vo; 

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * <p>标题：绑定设备信息</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年3月25日</p>
 * @author	Shucheng Luo
 */
public class SensorBinding implements Serializable { 

	private static final long serialVersionUID = 1L; 
	/** 绑定编号 */
	private Long bdbh;
	private Long sbbh;
	private Long newSbbh;
	private String sbtm;
	private String newSbtm;
	private Long jsjbh;
	private String cpxh;
	private Long ppbh;
	private String ppmc;
	private Long csbh;
	private String csjc;
	private Integer cssl;
	private Integer jssl;
	private Long sfbh;
	private Long dsbh;
	private Long qxbh;
	private String sfmc;
	private String dsmc;
	private String qxmc;
	private String xxdz;
	private Double zbx;
	private Double zby;
	private Date bdsj;
	private Date gxsj;
	private Date czrq;
	private Long wlbh;
	private Long yhid;
	private String token;
	private String sbbm;
	//设备类型
	private Integer sblx;
	//是否匹配
	private Integer sfpp;
	private String yhmc;
	private String lxdh;
	private Long khjsjbh;
	private String applogo;//厂商logo
	
	
	public String getApplogo() {
		return applogo;
	}
	public void setApplogo(String applogo) {
		this.applogo = applogo;
	}
	public Long getBdbh() {
		return this.bdbh;
	}
	public Date getBdsj() {
		return this.bdsj;
	}
	public String getCpxh() {
		return cpxh;
	}
	public Long getCsbh() {
		return this.csbh;
	}
	public String getCsjc() {
		return csjc;
	}
	public Integer getCssl() {
		return this.cssl;
	}
	public Date getCzrq() {
		return this.czrq;
	}
	public Long getDsbh() {
		return dsbh;
	}
	public String getDsmc() {
		return this.dsmc;
	}
	public Date getGxsj() {
		return this.gxsj;
	}
	public Long getJsjbh() {
		return this.jsjbh;
	}
	public Integer getJssl() {
		return this.jssl;
	}
	public Long getKhjsjbh() {
		return khjsjbh;
	}
	public String getLxdh() {
		return lxdh;
	}
	public Long getNewSbbh() {
		return newSbbh;
	}
	public String getNewSbtm() {
		return newSbtm;
	}
	public Long getPpbh() {
		return ppbh;
	}
	public String getPpmc() {
		return ppmc;
	}
	public Long getQxbh() {
		return qxbh;
	}
	public String getQxmc() {
		return this.qxmc;
	}
	public Long getSbbh() {
		return this.sbbh;
	}
	public String getSbbm() {
		return sbbm;
	}
	public Integer getSblx() {
		return sblx;
	}
	public String getSbtm() {
		return this.sbtm;
	}
	public Long getSfbh() {
		return sfbh;
	}
	public String getSfmc() {
		return this.sfmc;
	}
	public Integer getSfpp() {
		return sfpp;
	}
	public String getToken() {
		return token;
	}
	public Long getWlbh() {
		return this.wlbh;
	}
	public String getXxdz() {
		return this.xxdz;
	}
	public Long getYhid() {
		return yhid;
	}
	public String getYhmc() {
		return yhmc;
	}
	public Double getZbx() {
		return this.zbx;
	}
	public Double getZby() {
		return this.zby;
	}
	public void setBdbh(Long bdbh) {
		this.bdbh = bdbh;
	}
	public void setBdsj(Date bdsj) {
		this.bdsj = bdsj;
	}
	public void setCpxh(String cpxh) {
		this.cpxh = cpxh;
	}
	public void setCsbh(Long csbh) {
		this.csbh = csbh;
	}
	public void setCsjc(String csjc) {
		this.csjc = csjc;
	}
	public void setCssl(Integer cssl) {
		this.cssl = cssl;
	}
	public void setCzrq(Date czrq) {
		this.czrq = czrq;
	}
	public void setDsbh(Long dsbh) {
		this.dsbh = dsbh;
	}
	public void setDsmc(String dsmc) {
		this.dsmc = dsmc;
	}
	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}
	public void setJsjbh(Long jsjbh) {
		this.jsjbh = jsjbh;
	}
	public void setJssl(Integer jssl) {
		this.jssl = jssl;
	}
	public void setKhjsjbh(Long khjsjbh) {
		this.khjsjbh = khjsjbh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public void setNewSbbh(Long newSbbh) {
		this.newSbbh = newSbbh;
	}
	public void setNewSbtm(String newSbtm) {
		this.newSbtm = newSbtm;
	}
	public void setPpbh(Long ppbh) {
		this.ppbh = ppbh;
	}
	public void setPpmc(String ppmc) {
		this.ppmc = ppmc;
	}
	public void setQxbh(Long qxbh) {
		this.qxbh = qxbh;
	}
	public void setQxmc(String qxmc) {
		this.qxmc = qxmc;
	}
	public void setSbbh(Long sbbh) {
		this.sbbh = sbbh;
	}
	public void setSbbm(String sbbm) {
		this.sbbm = sbbm;
	}
	public void setSblx(Integer sblx) {
		this.sblx = sblx;
	}
	public void setSbtm(String sbtm) {
		this.sbtm = sbtm;
	}
	public void setSfbh(Long sfbh) {
		this.sfbh = sfbh;
	}
	public void setSfmc(String sfmc) {
		this.sfmc = sfmc;
	}
	public void setSfpp(Integer sfpp) {
		this.sfpp = sfpp;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setWlbh(Long wlbh) {
		this.wlbh = wlbh;
	}
	public void setXxdz(String xxdz) {
		this.xxdz = xxdz;
	}
	public void setYhid(Long yhid) {
		this.yhid = yhid;
	}
	public void setYhmc(String yhmc) {
		this.yhmc = yhmc;
	}
	public void setZbx(Double zbx) {
		this.zbx = zbx;
	}
	public void setZby(Double zby) {
		this.zby = zby;
	}
} 
