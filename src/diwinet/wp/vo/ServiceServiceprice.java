package diwinet.wp.vo; 

import java.io.Serializable;
import java.sql.Timestamp; 
/**
 * <p>标题：服务定价实体类</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年10月20日</p>
 * @author	maoyikun
 */
public class ServiceServiceprice implements Serializable { 

	private static final long serialVersionUID = 1L; 
	//定价id
	private Long zjid;
	//经销商/门店编号
	private Long djdxbh;
	//服务定价
	private Double fwdj;
	//好评服务费百分比
	private Short positive;
	//中评服务费百分比
	private Short moderate;
	//差评服务费百分比
	private Short negative;
	//定价类型（0、经销商，1、门店）
	private Short djlx;
	//处理人id
	private Long clrid;
	//是否有效
	private Integer sfyx;
	//创建时间
	private Timestamp cjsj;
	//服务定价编号
	private Long fwdjbh;
	//服务名称
	private String fwmc;

	public String getFwmc() {
		return fwmc;
	}
	public void setFwmc(String fwmc) {
		this.fwmc = fwmc;
	}
	public Long getFwdjbh() {
		return fwdjbh;
	}
	public void setFwdjbh(Long fwdjbh) {
		this.fwdjbh = fwdjbh;
	}
	public Double getFwdj() {
		return fwdj;
	}
	public void setFwdj(Double fwdj) {
		this.fwdj = fwdj;
	}
	public void setZjid(Long zjid) {
		this.zjid = zjid;
	}
	public Long getZjid() {
		return this.zjid;
	}
	public void setDjdxbh(Long djdxbh) {
		this.djdxbh = djdxbh;
	}
	public Long getDjdxbh() {
		return this.djdxbh;
	}
	public void setPositive(Short positive) {
		this.positive = positive;
	}
	public Short getPositive() {
		return this.positive;
	}
	public void setModerate(Short moderate) {
		this.moderate = moderate;
	}
	public Short getModerate() {
		return this.moderate;
	}
	public void setNegative(Short negative) {
		this.negative = negative;
	}
	public Short getNegative() {
		return this.negative;
	}
	public void setDjlx(Short djlx) {
		this.djlx = djlx;
	}
	public Short getDjlx() {
		return this.djlx;
	}
	public void setClrid(Long clrid) {
		this.clrid = clrid;
	}
	public Long getClrid() {
		return this.clrid;
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
