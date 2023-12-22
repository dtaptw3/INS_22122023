package com.app.model.orm.dbo.wrapper;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

@Entity(name = "InsuranceProviders") 
public class InsuranceProviderDBO {

	@TSNO @PrimaryKey
	private Integer id;
	private String name; 

	private Double rate;
	private Date currentratestartdate;
	private Date currentrateenddate; 

	private Date updatedratestartdate;
	private Date updatedrateenddate;

	private String status;

	@ExternalProperty
	private byte[] logo;

	@ExternalProperty
	private List<InsurancePlanDBO> plans;
	
	
	public InsuranceProviderDBO() {}

	public InsuranceProviderDBO(String name, Double rate, 
			Date currentratestartdate, Date currentrateenddate,
			Date updatedratestartdate, Date updatedrateenddate,
			String status/*, byte[] logo*/) {
		//this.id = id;
		this.name = name;
		this.rate = rate;
		this.currentratestartdate = currentratestartdate;
		this.currentrateenddate = currentrateenddate;
		this.updatedratestartdate = updatedratestartdate;
		this.updatedrateenddate = updatedrateenddate;
		this.status = status;
		//this.logo = logo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Date getCurrentratestartdate() {
		return currentratestartdate;
	}

	public void setCurrentratestartdate(Date currentratestartdate) {
		this.currentratestartdate = currentratestartdate;
	}

	public Date getCurrentrateenddate() {
		return currentrateenddate;
	}

	public void setCurrentrateenddate(Date currentrateenddate) {
		this.currentrateenddate = currentrateenddate;
	}

	public Date getUpdatedratestartdate() {
		return updatedratestartdate;
	}

	public void setUpdatedratestartdate(Date updatedratestartdate) {
		this.updatedratestartdate = updatedratestartdate;
	}

	public Date getUpdatedrateenddate() {
		return updatedrateenddate;
	}

	public void setUpdatedrateenddate(Date updatedrateenddate) {
		this.updatedrateenddate = updatedrateenddate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<InsurancePlanDBO> getPlans() {
		return plans;
	}

	public void setPlans(List<InsurancePlanDBO> plans) {
		this.plans = plans;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

}
