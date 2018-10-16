package diwinet.wp.vo;

import java.io.Serializable;
import java.util.Date;

public class TWaterReportHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 报送编号 */
	private Integer bsbh;
	/** 纯水水质排序序号 */
	private Integer pxxh;
	/** 设备条码 */
	private String sbtm;
	/** 设备编号 */
	private Long sbbh;
	/** 原水水量 */
	private Integer yssl;
	/** 纯水水量 */
	private Double cssl;
	/** 原水水质 */
	private Integer yssz;
	/** 纯水水质 */
	private Integer cssz;
	/** 原水流速 */
	private Integer ysls;
	/** 纯水流速 */
	private Integer csls;
	/** 报送时间 */
	private Date bssj;
	/** 同步时间 */
	private Date tbsj;
	private Integer sblx;
	private String bsrq;//报送日期

	
	public String getBsrq() {
		return bsrq;
	}

	public void setBsrq(String bsrq) {
		this.bsrq = bsrq;
	}

	public Integer getSblx() {
		return sblx;
	}

	public void setSblx(Integer sblx) {
		this.sblx = sblx;
	}

	public Integer getPxxh() {
		return pxxh;
	}

	public void setPxxh(Integer pxxh) {
		this.pxxh = pxxh;
	}

	public Long getSbbh() {
		return sbbh;
	}

	public void setSbbh(Long sbbh) {
		this.sbbh = sbbh;
	}

	public Integer getBsbh() {
		return bsbh;
	}

	public void setBsbh(Integer bsbh) {
		this.bsbh = bsbh;
	}

	public String getSbtm() {
		return sbtm;
	}

	public void setSbtm(String sbtm) {
		this.sbtm = sbtm;
	}

	public Integer getYssl() {
		return yssl;
	}

	public void setYssl(Integer yssl) {
		this.yssl = yssl;
	}

	public Double getCssl() {
		return cssl;
	}

	public void setCssl(Double cssl) {
		this.cssl = cssl;
	}

	public Integer getYssz() {
		return yssz;
	}

	public void setYssz(Integer yssz) {
		this.yssz = yssz;
	}

	public Integer getCssz() {
		return cssz;
	}

	public void setCssz(Integer cssz) {
		this.cssz = cssz;
	}

	public Integer getYsls() {
		return ysls;
	}

	public void setYsls(Integer ysls) {
		this.ysls = ysls;
	}

	public Integer getCsls() {
		return csls;
	}

	public void setCsls(Integer csls) {
		this.csls = csls;
	}

	public Date getBssj() {
		return bssj;
	}

	public void setBssj(Date bssj) {
		this.bssj = bssj;
	}

	public Date getTbsj() {
		return tbsj;
	}

	public void setTbsj(Date tbsj) {
		this.tbsj = tbsj;
	}

}
