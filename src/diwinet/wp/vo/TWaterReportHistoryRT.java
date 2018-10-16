package diwinet.wp.vo;

import java.io.Serializable;
import java.util.Date;

public class TWaterReportHistoryRT implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 设备条码 */
	private String sbtm;
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
	private Long avgtds;
	private Long sysj;



	public Long getSysj() {
		return sysj;
	}

	public void setSysj(Long sysj) {
		this.sysj = sysj;
	}

	public String getSbtm() {
		return sbtm;
	}

	public Long getAvgtds() {
		return avgtds;
	}

	public void setAvgtds(Long avgtds) {
		this.avgtds = avgtds;
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
		return cssl == null?0d:this.cssl;
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

	@Override
	public String toString() {
		return "TWaterReportHistoryRT [sbtm=" + sbtm + ", yssl=" + yssl + ", cssl=" + cssl + ", yssz=" + yssz
				+ ", cssz=" + cssz + ", ysls=" + ysls + ", csls=" + csls + ", bssj=" + bssj + ", tbsj=" + tbsj
				+ ", avgtds=" + avgtds + ", sysj=" + sysj + "]";
	}

}
