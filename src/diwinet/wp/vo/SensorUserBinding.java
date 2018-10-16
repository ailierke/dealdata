package diwinet.wp.vo; 

import java.io.Serializable;
import java.util.Date;
/**
 * <p>标题：用户设备绑定信息</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年3月25日</p>
 * @author	Shucheng Luo
 */
public class SensorUserBinding implements Serializable { 
	public static final int BDYH = 1;
	public static final int GZYH = 2;
	private static final long serialVersionUID = 1L;
	private String bdbz;
	private Date bdsj; 
	//按钮显示状态
	private Integer buttonType;
	//净水机型号
	private String cpxh;
	//订单id
	private Long ddid;
	//订单状态
	private Integer ddzt;
	//净水机编号
	private Long jsjbh;
	//客户净水机编号
	private Long khjsjbh;
	//联系电话
	private String lxdh;
	private Long newSbbh;
	private String newSbtm;
	private Integer page;
	//品牌名称
	private String ppmc;
	private Integer row;
	//设备编号
	private Long sbbh;
	//设备别名
	private String sbbm;
	//设备货号
	private String sbhh;
	//设备类型
	private Integer sblx;
	//设备条码
	private String sbtm;
	//设备绑定设备编号
	private Long sensorBindingSbbh;
	//设备编号
	private Long sensorSbbh;
	//是否绑定水先生
	private Integer sfbdsgj;
	//是否复位
	private Integer sffw;
	private Integer sfmr = 0;//默认为非默认
	//是否匹配
	private Integer sfpp;
	private Integer sftz;
	//是否支付
	private Integer sfzf;
	private String token;
	private Integer tyzq;
	//网络编号
	private Long wlbh;
	//用户绑定编号
	private Long yhbdbh;
	//用户编号
	private Long yhbh;
	private Long yhid;
	//用户类型
	private Integer yhlx;
	private String yhmc;
	public SensorUserBinding() {
		super();
	}
	public SensorUserBinding(Long yhbh, Long sbbh) {
		super();
		this.yhbh = yhbh;
		this.sbbh = sbbh;
	}
	
	public SensorUserBinding(Long yhbh, Long sbbh, String sbbm, Integer yhlx, String lxdh, Integer sftz,
			Integer sfmr) {
		super();
		this.yhbh = yhbh;
		this.sbbh = sbbh;
		this.sbbm = sbbm;
		this.yhlx = yhlx;
		this.lxdh = lxdh;
		this.sftz = sftz;
		this.sfmr = sfmr;
	}
	public String getBdbz() {
		return this.bdbz;
	}
	public Date getBdsj() {
		return this.bdsj;
	}
	public Integer getButtonType() {
		return buttonType;
	}
	public String getCpxh() {
		return cpxh;
	}
	public Long getDdid() {
		return ddid;
	}
	public Integer getDdzt() {
		return ddzt;
	}
	public Long getJsjbh() {
		return jsjbh;
	}
	public Long getKhjsjbh() {
		return khjsjbh;
	}
	public String getLxdh() {
		return this.lxdh;
	}
	public Long getNewSbbh() {
		return newSbbh;
	}
	public String getNewSbtm() {
		return newSbtm;
	}
	public Integer getPage() {
		return page;
	}
	public String getPpmc() {
		return ppmc;
	}
	public Integer getRow() {
		return row;
	}
	public Long getSbbh() {
		return this.sbbh;
	}
	public String getSbbm() {
		return this.sbbm;
	}
	public String getSbhh() {
		return sbhh;
	}
	public Integer getSblx() {
		return sblx;
	}
	public String getSbtm() {
		return sbtm;
	}
	public Long getSensorBindingSbbh() {
		return sensorBindingSbbh;
	}
	public Long getSensorSbbh() {
		return sensorSbbh;
	}
	public Integer getSfbdsgj() {
		return sfbdsgj;
	}
	public Integer getSffw() {
		return sffw;
	}
	public Integer getSfmr() {
		return this.sfmr;
	}
	public Integer getSfpp() {
		return sfpp;
	}
	public Integer getSftz() {
		return this.sftz;
	}
	public Integer getSfzf() {
		return sfzf;
	}
	public String getToken() {
		return token;
	}
	public Integer getTyzq() {
		return this.tyzq;
	}
	public Long getWlbh() {
		return wlbh;
	}
	public Long getYhbdbh() {
		return this.yhbdbh;
	}
	public Long getYhbh() {
		return this.yhbh;
	}
	public Long getYhid() {
		return yhid;
	}
	public Integer getYhlx() {
		return this.yhlx;
	}
	public String getYhmc() {
		return yhmc;
	}
	public void setBdbz(String bdbz) {
		this.bdbz = bdbz;
	}
	public void setBdsj(Date bdsj) {
		this.bdsj = bdsj;
	}
	public void setButtonType(Integer buttonType) {
		this.buttonType = buttonType;
	}
	public void setCpxh(String cpxh) {
		this.cpxh = cpxh;
	}
	public void setDdid(Long ddid) {
		this.ddid = ddid;
	}
	public void setDdzt(Integer ddzt) {
		this.ddzt = ddzt;
	}
	public void setJsjbh(Long jsjbh) {
		this.jsjbh = jsjbh;
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
	public void setPage(Integer page) {
		this.page = page;
	}
	public void setPpmc(String ppmc) {
		this.ppmc = ppmc;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public void setSbbh(Long sbbh) {
		this.sbbh = sbbh;
	}
	public void setSbbm(String sbbm) {
		this.sbbm = sbbm;
	}
	public void setSbhh(String sbhh) {
		this.sbhh = sbhh;
	}
	public void setSblx(Integer sblx) {
		this.sblx = sblx;
	}
	public void setSbtm(String sbtm) {
		this.sbtm = sbtm;
	}
	public void setSensorBindingSbbh(Long sensorBindingSbbh) {
		this.sensorBindingSbbh = sensorBindingSbbh;
	}
	public void setSensorSbbh(Long sensorSbbh) {
		this.sensorSbbh = sensorSbbh;
	}
	public void setSfbdsgj(Integer sfbdsgj) {
		this.sfbdsgj = sfbdsgj;
	}
	public void setSffw(Integer sffw) {
		this.sffw = sffw;
	}
	public void setSfmr(Integer sfmr) {
		this.sfmr = sfmr;
	}
	public void setSfpp(Integer sfpp) {
		this.sfpp = sfpp;
	}
	public void setSftz(Integer sftz) {
		this.sftz = sftz;
	}
	public void setSfzf(Integer sfzf) {
		this.sfzf = sfzf;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setTyzq(Integer tyzq) {
		this.tyzq = tyzq;
	}
	public void setWlbh(Long wlbh) {
		this.wlbh = wlbh;
	}
	public void setYhbdbh(Long yhbdbh) {
		this.yhbdbh = yhbdbh;
	}
	public void setYhbh(Long yhbh) {
		this.yhbh = yhbh;
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
	
} 
