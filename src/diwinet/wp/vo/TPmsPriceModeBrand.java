package diwinet.wp.vo; 

import java.util.Date; 
import java.io.Serializable; 

public class TPmsPriceModeBrand implements Serializable { 

	private static final long serialVersionUID = 1L; 

	private Long gxbh;
	private Long zfbh;
	private Long ppbh;
	private Long jsjbh;
	private Integer sfyx;
	private Date gxsj;

	public void setGxbh(Long gxbh) {
		this.gxbh = gxbh;
	}
	public Long getGxbh() {
		return this.gxbh;
	}
	public void setZfbh(Long zfbh) {
		this.zfbh = zfbh;
	}
	public Long getZfbh() {
		return this.zfbh;
	}
	public void setPpbh(Long ppbh) {
		this.ppbh = ppbh;
	}
	public Long getPpbh() {
		return this.ppbh;
	}
	public void setJsjbh(Long jsjbh) {
		this.jsjbh = jsjbh;
	}
	public Long getJsjbh() {
		return this.jsjbh;
	}
	public void setSfyx(Integer sfyx) {
		this.sfyx = sfyx;
	}
	public Integer getSfyx() {
		return this.sfyx;
	}
	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}
	public Date getGxsj() {
		return this.gxsj;
	}
} 
