package diwinet.wp.vo; 

import java.util.Date; 
import java.io.Serializable; 

public class TPmsWpCompanyRelation implements Serializable { 

	private static final long serialVersionUID = 1L; 

	private Long gxbh;
	private Long csbh;
	private Long yysbh;
	private Date cjsj;
	private Integer sfyx;

	public void setGxbh(Long gxbh) {
		this.gxbh = gxbh;
	}
	public Long getGxbh() {
		return this.gxbh;
	}
	public void setCsbh(Long csbh) {
		this.csbh = csbh;
	}
	public Long getCsbh() {
		return this.csbh;
	}
	public void setYysbh(Long yysbh) {
		this.yysbh = yysbh;
	}
	public Long getYysbh() {
		return this.yysbh;
	}
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	public Date getCjsj() {
		return this.cjsj;
	}
	public void setSfyx(Integer sfyx) {
		this.sfyx = sfyx;
	}
	public Integer getSfyx() {
		return this.sfyx;
	}
} 
