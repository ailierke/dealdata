package diwinet.wp.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>标题：净水机用户绑定信息</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年9月29日</p>
 * @author maoyikun
 */
public class WpCustomerBanding implements Serializable {

	private static final long serialVersionUID = 1L;
	// 用户净水机编号
	private Long khjsjbh;
	// 品牌编号
	private Long ppbh;
	// 净水机编号
	private Long jsjbh;
	// 净水机名称
	private String jsjmc;
	// 客户编号
	private Long khbh;
	// 网点编号
	private Long wdbh;
	// 购买时间
	private Date gmsj;
	// 购买备注
	private String gmbz;
	// 记录人员
	private Long jlry;
	// 是否绑定水先生
	private Integer sfbdsgj;
	// 设备编号
	private Long sbbh;
	// 用户token
	private String token;
	// 省份编号
	private Long sfbh;
	// 地市编号
	private Long dsbh;
	// 区县编号
	private Long qxbh;
	// 省份名称
	private String sfmc;
	// 地市名称
	private String dsmc;
	// 区县名称
	private String qxmc;
	// 具体地址
	private String jtdz;
	// 用户名称
	private String yhmc;
	// 联系电话
	private String lxdh;
	// 初装日期
	private String czrq;
	//添加时间
	private Date tjsj;
	// 用户id
	private Long yhid;
	//品牌名称
	private String ppmc;
	//净水机型号
	private String cpxh;
	//水先生信息
	private SensorInfo sensorInfo;
	//是否默认(0、非默认，1、默认)
	private Integer sfmr;
	//订单id
	private Long ddid;
	//是否支付
	private Integer sfzf;
	//订单状态
	private Integer ddzt;
	//是否复位
	private Integer sffw;
	// 按钮显示状态
	private Integer buttonType;
	//经纬度
	private Double zbx;
	private Double zby;
	//体验时间
	private String tysj;
	private Long tylxbh;
	private Integer yhlx;
	private Integer sfgz;
	//备用
	private String provineId;
	private String cityId;
	private String townId;

	private String townName;
	
	private String provineName;
	private String cityName;
	private String applogo;//厂商logo
	private Integer jsjlx;//净水机类型
	private Double gmjg;//购买价格
	
	
	public Double getGmjg() {
		return gmjg;
	}

	public void setGmjg(Double gmjg) {
		this.gmjg = gmjg;
	}

	public Integer getJsjlx() {
		return jsjlx;
	}

	public void setJsjlx(Integer jsjlx) {
		this.jsjlx = jsjlx;
	}

	public String getApplogo() {
		return applogo;
	}

	public void setApplogo(String applogo) {
		this.applogo = applogo;
	}

	public Integer getButtonType() {
		return buttonType;
	}

	public String getCityId() {
		return cityId;
	}

	public String getCityName() {
		return cityName;
	}
	public String getCpxh() {
		return cpxh;
	}
	public String getCzrq() {
		return czrq;
	}
	public Long getDdid() {
		return ddid;
	}
	public Integer getDdzt() {
		return ddzt;
	}
	public Long getDsbh() {
		return dsbh;
	}
	
	public Date getTjsj() {
		return tjsj;
	}

	public void setTjsj(Date tjsj) {
		this.tjsj = tjsj;
	}

	public String getDsmc() {
		return dsmc;
	}


	public String getGmbz() {
		return gmbz;
	}

	public Date getGmsj() {
		return gmsj;
	}

	public Long getJlry() {
		return jlry;
	}


	public Long getJsjbh() {
		return jsjbh;
	}

	public String getJsjmc() {
		return jsjmc;
	}

	public String getJtdz() {
		return jtdz;
	}

	public Long getKhbh() {
		return khbh;
	}

	public Long getKhjsjbh() {
		return khjsjbh;
	}

	public String getLxdh() {
		return lxdh;
	}


	public Long getPpbh() {
		return ppbh;
	}

	public String getPpmc() {
		return ppmc;
	}

	public String getProvineId() {
		return provineId;
	}

	public String getProvineName() {
		return provineName;
	}

	public Long getQxbh() {
		return qxbh;
	}

	public String getQxmc() {
		return qxmc;
	}

	public Long getSbbh() {
		return sbbh;
	}

	public SensorInfo getSensorInfo() {
		return sensorInfo;
	}


	public Integer getSfbdsgj() {
		return sfbdsgj;
	}

	public Long getSfbh() {
		return sfbh;
	}

	public Integer getSffw() {
		return sffw;
	}

	public Integer getSfgz() {
		return sfgz;
	}


	public String getSfmc() {
		return sfmc;
	}

	public Integer getSfmr() {
		return sfmr;
	}

	public Integer getSfzf() {
		return sfzf;
	}

	public String getToken() {
		return token;
	}

	public String getTownId() {
		return townId;
	}

	public String getTownName() {
		return townName;
	}

	public Long getTylxbh() {
		return tylxbh;
	}

	public String getTysj() {
		return tysj;
	}

	public Long getWdbh() {
		return wdbh;
	}

	public Long getYhid() {
		return yhid;
	}

	public Integer getYhlx() {
		return yhlx;
	}

	public String getYhmc() {
		return yhmc;
	}

	public Double getZbx() {
		return zbx;
	}

	public Double getZby() {
		return zby;
	}

	public void setButtonType(Integer buttonType) {
		this.buttonType = buttonType;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setCpxh(String cpxh) {
		this.cpxh = cpxh;
	}

	public void setCzrq(String czrq) {
		this.czrq = czrq;
	}

	public void setDdid(Long ddid) {
		this.ddid = ddid;
	}

	public void setDdzt(Integer ddzt) {
		this.ddzt = ddzt;
	}

	public void setDsbh(Long dsbh) {
		this.dsbh = dsbh;
	}

	public void setDsmc(String dsmc) {
		this.dsmc = dsmc;
	}



	public void setGmbz(String gmbz) {
		this.gmbz = gmbz;
	}

	public void setGmsj(Date gmsj) {
		this.gmsj = gmsj;
	}

	public void setJlry(Long jlry) {
		this.jlry = jlry;
	}

	public void setJsjbh(Long jsjbh) {
		this.jsjbh = jsjbh;
	}

	public void setJsjmc(String jsjmc) {
		this.jsjmc = jsjmc;
	}

	public void setJtdz(String jtdz) {
		this.jtdz = jtdz;
	}

	public void setKhbh(Long khbh) {
		this.khbh = khbh;
	}

	public void setKhjsjbh(Long khjsjbh) {
		this.khjsjbh = khjsjbh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}


	public void setPpbh(Long ppbh) {
		this.ppbh = ppbh;
	}

	public void setPpmc(String ppmc) {
		this.ppmc = ppmc;
	}

	public void setProvineId(String provineId) {
		this.provineId = provineId;
	}

	public void setProvineName(String provineName) {
		this.provineName = provineName;
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

	public void setSensorInfo(SensorInfo sensorInfo) {
		this.sensorInfo = sensorInfo;
	}


	public void setSfbdsgj(Integer sfbdsgj) {
		this.sfbdsgj = sfbdsgj;
	}

	public void setSfbh(Long sfbh) {
		this.sfbh = sfbh;
	}

	public void setSffw(Integer sffw) {
		this.sffw = sffw;
	}

	public void setSfgz(Integer sfgz) {
		this.sfgz = sfgz;
	}

	public void setSfmc(String sfmc) {
		this.sfmc = sfmc;
	}

	public void setSfmr(Integer sfmr) {
		this.sfmr = sfmr;
	}

	public void setSfzf(Integer sfzf) {
		this.sfzf = sfzf;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setTownId(String townId) {
		this.townId = townId;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public void setTylxbh(Long tylxbh) {
		this.tylxbh = tylxbh;
	}

	public void setTysj(String tysj) {
		this.tysj = tysj;
	}

	public void setWdbh(Long wdbh) {
		this.wdbh = wdbh;
	}

	public void setYhid(Long yhid) {
		this.yhid = yhid;
	}

	public void setYhlx(Integer yhlx) {
		this.yhlx = yhlx;
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
