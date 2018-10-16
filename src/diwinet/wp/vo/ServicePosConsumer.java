package diwinet.wp.vo; 

import java.io.Serializable; 
/**
 * <p>标题：经销商客户信息</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年9月29日</p>
 * @author	maoyikun
 */
public class ServicePosConsumer implements Serializable { 

	private static final long serialVersionUID = 1L; 
	//客户编号
	private Long khbh;
	//网点编号
	private Long wdbh;
	//用户id
	private Long yhid;
	//用户名称
	private String yhmc;
	//联系电话
	private String lxdh;
	//邮箱地址
	private String email;
	//省份编号
	private Long sfbh;
	//地市编号
	private Long dsbh;
	//区县编号
	private Long qxbh;
	//省份名称
	private String sfmc;
	//地市名称
	private String dsmc;
	//区县名称
	private String qxmc;
	//具体地址
	private String jtdz;
	//邮政编码
	private String yzbm;
	//是否有效（0、无效，1、有效）
	private Integer sfyx = 1;
	//用户头像
	private String yhtx;
	//地图经度
	private Double dtjd;
	//地图纬度
	private Double dtwd;
	//用户token
	private String token;
	//品牌编号
	private Long ppbh;

	public Long getPpbh() {
		return ppbh;
	}
	public void setPpbh(Long ppbh) {
		this.ppbh = ppbh;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Double getDtjd() {
		return dtjd;
	}
	public void setDtjd(Double dtjd) {
		this.dtjd = dtjd;
	}
	public Double getDtwd() {
		return dtwd;
	}
	public void setDtwd(Double dtwd) {
		this.dtwd = dtwd;
	}
	public void setKhbh(Long khbh) {
		this.khbh = khbh;
	}
	public Long getKhbh() {
		return this.khbh;
	}
	public void setWdbh(Long wdbh) {
		this.wdbh = wdbh;
	}
	public Long getWdbh() {
		return this.wdbh;
	}
	public void setYhid(Long yhid) {
		this.yhid = yhid;
	}
	public Long getYhid() {
		return this.yhid;
	}
	public void setYhmc(String yhmc) {
		this.yhmc = yhmc;
	}
	public String getYhmc() {
		return this.yhmc;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getLxdh() {
		return this.lxdh;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return this.email;
	}
	public Long getSfbh() {
		return sfbh;
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
	public void setSfmc(String sfmc) {
		this.sfmc = sfmc;
	}
	public String getSfmc() {
		return this.sfmc;
	}
	public void setDsmc(String dsmc) {
		this.dsmc = dsmc;
	}
	public String getDsmc() {
		return this.dsmc;
	}
	public void setQxmc(String qxmc) {
		this.qxmc = qxmc;
	}
	public String getQxmc() {
		return this.qxmc;
	}
	public void setJtdz(String jtdz) {
		this.jtdz = jtdz;
	}
	public String getJtdz() {
		return this.jtdz;
	}
	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}
	public String getYzbm() {
		return this.yzbm;
	}
	public void setSfyx(Integer sfyx) {
		this.sfyx = sfyx;
	}
	public Integer getSfyx() {
		return this.sfyx;
	}
	public void setYhtx(String yhtx) {
		this.yhtx = yhtx;
	}
	public String getYhtx() {
		return this.yhtx;
	}
} 
