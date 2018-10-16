package diwinet.wp.vo; 

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * <p>标题：用户信息类</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年3月24日</p>
 * @author	Shucheng Luo
 */
public class User implements Serializable { 

	private static final long serialVersionUID = 1L; 
	public static final int EFFECT = 1;
	public static final int UNEFFECT = 0;
	/** 用户编号 */
	private Long yhid;
	/** 用户名称 */
	private String yhmc;
	/** 用户账号 */
	private String yhzh;
	/** 登录密码 */
	private String dlmm;
	/** 用户密码（MD5） */
	private String yhmm;
	/** 联系电话 */
	private String lxdh;
	/** 邮箱地址 */
	private String email;
	/** 省份编号 */
	private String sfbh;
	/** 地市编号 */
	private String dsbh;
	/** 区县编号 */
	private String qxbh;
	/** 省份名称 */
	private String sfmc;
	/** 地市名称 */
	private String dsmc;
	/** 区县名称 */
	private String qxmc;
	/** 具体地址 */
	private String jtdz;
	/** 邮政编码 */
	private String yzbm;
	/** 是否有效 */
	private Integer sfyx;
	/** 用户类型 */
	private Integer yhlx;
	/** 微信标识 */
	private String openid;
	/** 创建时间 */
	private Timestamp cjsj;
	/** 用户头像 */
	private String yhtx;
	/** 访问标识 */
	private String token;
	/** 验证码 */
	private String code;
	/** 角色id */
	private Long jsid;
	/** 角色名称 */
	private String jsmc;
	/** 分页参数，当前页数 */
	private Integer page;
	/** 分页参数，每页显示条数 */
	private Integer row;
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public void setYhzh(String yhzh) {
		this.yhzh = yhzh;
	}
	public String getYhzh() {
		return this.yhzh;
	}
	public void setDlmm(String dlmm) {
		this.dlmm = dlmm;
	}
	public String getDlmm() {
		return this.dlmm;
	}
	public void setYhmm(String yhmm) {
		this.yhmm = yhmm;
	}
	public String getYhmm() {
		return this.yhmm;
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
	public void setYhlx(Integer yhlx) {
		this.yhlx = yhlx;
	}
	public Integer getYhlx() {
		return this.yhlx;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getOpenid() {
		return this.openid;
	}
	public void setCjsj(Timestamp cjsj) {
		this.cjsj = cjsj;
	}
	public Timestamp getCjsj() {
		return this.cjsj;
	}
	public void setYhtx(String yhtx) {
		this.yhtx = yhtx;
	}
	public String getYhtx() {
		return this.yhtx;
	}
	public Long getJsid() {
		return jsid;
	}
	public void setJsid(Long jsid) {
		this.jsid = jsid;
	}
	public String getJsmc() {
		return jsmc;
	}
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}
	@Override
	public String toString() {
		return "User [yhid=" + yhid + ", yhmc=" + yhmc + ", yhzh=" + yhzh + ", dlmm=" + dlmm + ", yhmm=" + yhmm
				+ ", lxdh=" + lxdh + ", email=" + email + ", sfbh=" + sfbh + ", dsbh=" + dsbh + ", qxbh=" + qxbh
				+ ", sfmc=" + sfmc + ", dsmc=" + dsmc + ", qxmc=" + qxmc + ", jtdz=" + jtdz + ", yzbm=" + yzbm
				+ ", sfyx=" + sfyx + ", yhlx=" + yhlx + ", openid=" + openid + ", cjsj=" + cjsj + ", yhtx=" + yhtx
				+ ", token=" + token + ", code=" + code + ", jsid=" + jsid + ", jsmc=" + jsmc + ", page=" + page
				+ ", row=" + row + "]";
	}
	
} 
