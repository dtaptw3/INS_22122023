package com.app.model.orm.dbo.wrapper;

import javax.persistence.Entity;

@Entity(name = "InsurancePlans") 
public class InsurancePlanDBO {
	
	// parent is provider for plans
	private String providerid;
	@ExternalProperty
	private String providername;
	
	private String plannid;
	@ExternalProperty 
	private String planname;
	
	/*
	 * private String networkproviderid;
	 * 
	 * @ExternalProperty private String networkprovidername;
	 * 
	 * private String areaofcoverid;
	 * 
	 * @ExternalProperty private String areaofcoverdesc;
	 */
	
	/* private String copay; */
	
	private String patienttreatment = "IO";
	private String enhancedmaternity;
	private String dental;
	private String wellness;
	private String optical;
	private String status = "A";
	
	private BenefitsDBO benefits;
	
	
	public InsurancePlanDBO() {}
	

	public String getProviderid() {
		return providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public String getProvidername() {
		return providername;
	}

	public void setProvidername(String providername) {
		this.providername = providername;
	}

	public String getPlannid() {
		return plannid;
	}

	public void setPlannid(String plannid) {
		this.plannid = plannid;
	}

	public String getPlanname() {
		return planname;
	}

	public void setPlanname(String planname) {
		this.planname = planname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPatienttreatment() {
		return patienttreatment;
	}

	public void setPatienttreatment(String patienttreatment) {
		this.patienttreatment = patienttreatment;
	}

	public String getEnhancedmaternity() {
		return enhancedmaternity;
	}

	public void setEnhancedmaternity(String enhancedmaternity) {
		this.enhancedmaternity = enhancedmaternity;
	}

	public String getDental() {
		return dental;
	}

	public void setDental(String dental) {
		this.dental = dental;
	}

	public String getWellness() {
		return wellness;
	}

	public void setWellness(String wellness) {
		this.wellness = wellness;
	}

	public String getOptical() {
		return optical;
	}

	public void setOptical(String optical) {
		this.optical = optical;
	}

	public BenefitsDBO getBenefits() {
		return benefits;
	}

	public void setBenefits(BenefitsDBO benefits) {
		this.benefits = benefits;
	}
	

}
