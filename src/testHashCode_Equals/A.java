package testHashCode_Equals;

import java.util.Date;

public class A {
	private  String sbtm;

	private String sbmc;
	private Date  bssj;
	public A() {
		super();
		// TODO Auto-generated constructor stub
	}
	public A(String sbtm, String sbmc) {
		super();
		this.sbtm = sbtm;
		this.sbmc = sbmc;
	}
	public A(String sbtm, String sbmc, Date bssj) {
		super();
		this.sbtm = sbtm;
		this.sbmc = sbmc;
		this.bssj = bssj;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof A){
			A o = (A)obj;
			if(o.getSbtm().equals(this.sbtm)){
				return true;
			}
		}else{
			return false;
		}
		
		return super.equals(obj);
	}
	public Date getBssj() {
		return bssj;
	}
	public String getSbmc() {
		return sbmc;
	}
	public String getSbtm() {
		return sbtm;
	}
	@Override
	public int hashCode() {
		return this.sbtm.hashCode();
	}
	public void setBssj(Date bssj) {
		this.bssj = bssj;
	}
	public void setSbmc(String sbmc) {
		this.sbmc = sbmc;
	}
	public void setSbtm(String sbtm) {
		this.sbtm = sbtm;
	}
	
	@Override
	public String toString() {
		return "A [sbtm=" + sbtm + ", sbmc=" + sbmc + ", bssj=" + bssj + "]";
	}
}
