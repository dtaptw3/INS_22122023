package com.app.model.orm.dbo.wrapper;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

@Entity(name = "Quotes")
public class QuoteDBO {
	
	@PrimaryKey
	private String quoteid;
	private String firstname;
	private String lastname;
	
	//private String relation;
	private String gender;
	
	private String email;
	private String phonecode;
	private String phone;

	private Date dateofbirth;
	private String maritalstatus;

	private String residency;
	private String nationality;

	private Date startdate;

	private String advisorname;
	private String advisoremail;
	private String currency;
	private Date quoteddate;

	private String qrflink;

	private Date createddate;
	private String createdby;

	private String insurercode;
	private String insurername;
	
	
	@ExternalProperty
	private List<MemberDBO> members;
	
	/*
	 * @ExternalProperty private List<LinkedMembersDBO> linkedMembers;
	 */
	
	
	@ExternalProperty
	private List<LinkedPlansDBO> linkedplans;
	
	
	private Double quoteAmount;
	
	public QuoteDBO() {}

	public QuoteDBO(String quoteid, String firstname, String lastname, String email, String phonecode, String phone,
			Date dateofbirth, String maritalstatus, String residency, String nationality, Date startdate,
			String advisorname, String advisoremail, String currency, Date quoteddate, String qrflink, Date createddate,
			String createdby, String insurercode, String insurername) {
		super();
		this.quoteid = quoteid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phonecode = phonecode;
		this.phone = phone;
		this.dateofbirth = dateofbirth;
		this.maritalstatus = maritalstatus;
		this.residency = residency;
		this.nationality = nationality;
		this.startdate = startdate;
		this.advisorname = advisorname;
		this.advisoremail = advisoremail;
		this.currency = currency;
		this.quoteddate = quoteddate;
		this.qrflink = qrflink;
		this.createddate = createddate;
		this.createdby = createdby;
		this.insurercode = insurercode;
		this.insurername = insurername;
		
	}

	public String getQuoteid() {
		return quoteid;
	}

	public void setQuoteid(String quoteid) {
		this.quoteid = quoteid;
	}

	public String getAdvisorname() {
		return advisorname;
	}

	public void setAdvisorname(String advisorname) {
		this.advisorname = advisorname;
	}

	public String getAdvisoremail() {
		return advisoremail;
	}

	public void setAdvisoremail(String advisoremail) {
		this.advisoremail = advisoremail;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getQrflink() {
		return qrflink;
	}

	public void setQrflink(String qrflink) {
		this.qrflink = qrflink;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getInsurercode() {
		return insurercode;
	}

	public void setInsurercode(String insurercode) {
		this.insurercode = insurercode;
	}

	public String getInsurername() {
		return insurername;
	}

	public void setInsurername(String insurername) {
		this.insurername = insurername;
	}

	public List<MemberDBO> getMembers() {
		return members;
	}

	public void setMembers(List<MemberDBO> members) {
		this.members = members;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getMaritalstatus() {
		return maritalstatus;
	}

	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	public String getResidency() {
		return residency;
	}

	public void setResidency(String residency) {
		this.residency = residency;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getQuoteddate() {
		return quoteddate;
	}

	public void setQuoteddate(Date quoteddate) {
		this.quoteddate = quoteddate;
	}

	public String getPhonecode() {
		return phonecode;
	}

	public void setPhonecode(String phonecode) {
		this.phonecode = phonecode;
	}


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Double getQuoteAmount() {
		return quoteAmount;
	}

	public void setQuoteAmount(Double quoteAmount) {
		this.quoteAmount = quoteAmount;
	}

	public List<LinkedPlansDBO> getLinkedplans() {
		return linkedplans;
	}

	public void setLinkedplans(List<LinkedPlansDBO> linkedplans) {
		this.linkedplans = linkedplans;
	}

	

}
