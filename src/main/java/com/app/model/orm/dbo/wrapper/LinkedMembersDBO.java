package com.app.model.orm.dbo.wrapper;

public class LinkedMembersDBO {

	
	private int tsno;
	public int getTsno() {
		return tsno;
	}
	public void setTsno(int tsno) {
		this.tsno = tsno;
	}
	public String getquoteid() {
		return quoteid;
	}
	public void setquoteid(String quoteid) {
		this.quoteid = quoteid;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	private String quoteid;
	private String memberid;
	
	
}
