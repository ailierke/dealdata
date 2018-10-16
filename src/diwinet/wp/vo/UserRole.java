package diwinet.wp.vo; 

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * <p>标题：用户角色类</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年3月24日</p>
 * @author	Shucheng Luo
 */
public class UserRole implements Serializable { 

	private static final long serialVersionUID = 1L; 
	/** 用户角色编号 */
	private Long roleid;
	/** 用户编号  */
	private Long yhid;
	/** 角色编号 */
	private Long jsid;
	/** 用户角色名称 */
	private String jsmc;
	/** 是否有效 */
	private Integer sfyx;
	/** 创建时间 */
	private Timestamp cjsj;

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
	public Long getRoleid() {
		return this.roleid;
	}
	public void setYhid(Long yhid) {
		this.yhid = yhid;
	}
	public Long getYhid() {
		return this.yhid;
	}
	public void setJsid(Long jsid) {
		this.jsid = jsid;
	}
	public Long getJsid() {
		return this.jsid;
	}
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}
	public String getJsmc() {
		return this.jsmc;
	}
	public void setSfyx(Integer sfyx) {
		this.sfyx = sfyx;
	}
	public Integer getSfyx() {
		return this.sfyx;
	}
	public void setCjsj(Timestamp cjsj) {
		this.cjsj = cjsj;
	}
	public Timestamp getCjsj() {
		return this.cjsj;
	}
} 
