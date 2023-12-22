package com.app.model.orm.dbo.wrapper;

import javax.persistence.Entity;

@Entity
public class LinkedPlansDBO {
	
	@TSNO
	@PrimaryKey
	private Integer id;
	private String quoteid;
	private String providerid;
	
	private Integer linenumber;
	
	private Integer memberid;
	
	private Integer networkproviderid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuoteid() {
		return quoteid;
	}

	public void setQuoteid(String quoteid) {
		this.quoteid = quoteid;
	}

	public String getProviderid() {
		return providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public Integer getLinenumber() {
		return linenumber;
	}

	public void setLinenumber(Integer linenumber) {
		this.linenumber = linenumber;
	}

	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	public Integer getNetworkproviderid() {
		return networkproviderid;
	}

	public void setNetworkproviderid(Integer networkproviderid) {
		this.networkproviderid = networkproviderid;
	}

}
