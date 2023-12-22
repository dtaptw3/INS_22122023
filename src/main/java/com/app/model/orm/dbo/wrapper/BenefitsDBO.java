package com.app.model.orm.dbo.wrapper;

import javax.persistence.Entity;

@Entity(name = "Benefits") 
public class BenefitsDBO {
	
	//NOTE: all fields begin as caps: getter ERROR ?
	@TSNO @PrimaryKey
	private Integer id;
	private String Plan_Name;
	private String Direct_Billing_Network;
	private String Deductible_or_Copay;
	//Benefits_Guide;
	private String Annual_Limit;
	private String Geographical_Coverage;
	private String Inpatient_Hospitalisation;
	private String Out_Patient;
	private String Physiotherapy;
	private String Emergency_Evacuation;
	private String Chronic_Conditions;
	private String Preexisting_Cover;
	//Maternity_Benefits;
	private String Routine_Maternity;
	private String Maternity_Waiting_Period;
	//Dental_Benefits;
	private String Dental;
	private String Dental_Waiting_Period;
	//Wellness_Benefits;
	private String Optical_Benefits;
	private String Wellness;
	//Payment_Information;
	private String Semi_Annual_Surcharge;
	private String Quarterly_Surcharge;
	private String Monthly_Surcharge;
	
	private Integer Age;
	private Double Height;
	private Double Weight;
	private Double Factor;
	
	//Total_Premium;
	private Double UAE_Dirham_excluding_TAX;
	private Double UAE_Dirham_including_TAX;
	private String Comment;
	
	
	
	public String getPlan_Name() {
		return Plan_Name;
	}
	public void setPlan_Name(String plan_Name) {
		Plan_Name = plan_Name;
	}
	public String getDirect_Billing_Network() {
		return Direct_Billing_Network;
	}
	public void setDirect_Billing_Network(String direct_Billing_Network) {
		Direct_Billing_Network = direct_Billing_Network;
	}
	public String getDeductible_or_Copay() {
		return Deductible_or_Copay;
	}
	public void setDeductible_or_Copay(String deductible_or_Copay) {
		Deductible_or_Copay = deductible_or_Copay;
	}
	public String getAnnual_Limit() {
		return Annual_Limit;
	}
	public void setAnnual_Limit(String annual_Limit) {
		Annual_Limit = annual_Limit;
	}
	public String getGeographical_Coverage() {
		return Geographical_Coverage;
	}
	public void setGeographical_Coverage(String geographical_Coverage) {
		Geographical_Coverage = geographical_Coverage;
	}
	public String getInpatient_Hospitalisation() {
		return Inpatient_Hospitalisation;
	}
	public void setInpatient_Hospitalisation(String inpatient_Hospitalisation) {
		Inpatient_Hospitalisation = inpatient_Hospitalisation;
	}
	public String getOut_Patient() {
		return Out_Patient;
	}
	public void setOut_Patient(String out_Patient) {
		Out_Patient = out_Patient;
	}
	public String getPhysiotherapy() {
		return Physiotherapy;
	}
	public void setPhysiotherapy(String physiotherapy) {
		Physiotherapy = physiotherapy;
	}
	public String getEmergency_Evacuation() {
		return Emergency_Evacuation;
	}
	public void setEmergency_Evacuation(String emergency_Evacuation) {
		Emergency_Evacuation = emergency_Evacuation;
	}
	public String getChronic_Conditions() {
		return Chronic_Conditions;
	}
	public void setChronic_Conditions(String chronic_Conditions) {
		Chronic_Conditions = chronic_Conditions;
	}
	public String getPreexisting_Cover() {
		return Preexisting_Cover;
	}
	public void setPreexisting_Cover(String preexisting_Cover) {
		Preexisting_Cover = preexisting_Cover;
	}
	public String getRoutine_Maternity() {
		return Routine_Maternity;
	}
	public void setRoutine_Maternity(String routine_Maternity) {
		Routine_Maternity = routine_Maternity;
	}
	public String getMaternity_Waiting_Period() {
		return Maternity_Waiting_Period;
	}
	public void setMaternity_Waiting_Period(String maternity_Waiting_Period) {
		Maternity_Waiting_Period = maternity_Waiting_Period;
	}
	public String getDental() {
		return Dental;
	}
	public void setDental(String dental) {
		Dental = dental;
	}
	public String getDental_Waiting_Period() {
		return Dental_Waiting_Period;
	}
	public void setDental_Waiting_Period(String dental_Waiting_Period) {
		Dental_Waiting_Period = dental_Waiting_Period;
	}
	public String getOptical_Benefits() {
		return Optical_Benefits;
	}
	public void setOptical_Benefits(String optical_Benefits) {
		Optical_Benefits = optical_Benefits;
	}
	public String getWellness() {
		return Wellness;
	}
	public void setWellness(String wellness) {
		Wellness = wellness;
	}
	public String getSemi_Annual_Surcharge() {
		return Semi_Annual_Surcharge;
	}
	public void setSemi_Annual_Surcharge(String semi_Annual_Surcharge) {
		Semi_Annual_Surcharge = semi_Annual_Surcharge;
	}
	public String getQuarterly_Surcharge() {
		return Quarterly_Surcharge;
	}
	public void setQuarterly_Surcharge(String quarterly_Surcharge) {
		Quarterly_Surcharge = quarterly_Surcharge;
	}
	public String getMonthly_Surcharge() {
		return Monthly_Surcharge;
	}
	public void setMonthly_Surcharge(String monthly_Surcharge) {
		Monthly_Surcharge = monthly_Surcharge;
	}
	public Integer getAge() {
		return Age;
	}
	public void setAge(Integer age) {
		Age = age;
	}
	public Double getHeight() {
		return Height;
	}
	public void setHeight(Double height) {
		Height = height;
	}
	public Double getWeight() {
		return Weight;
	}
	public void setWeight(Double weight) {
		Weight = weight;
	}
	public Double getFactor() {
		return Factor;
	}
	public void setFactor(Double factor) {
		Factor = factor;
	}
	public Double getUAE_Dirham_excluding_TAX() {
		return UAE_Dirham_excluding_TAX;
	}
	public void setUAE_Dirham_excluding_TAX(Double uAE_Dirham_excluding_TAX) {
		UAE_Dirham_excluding_TAX = uAE_Dirham_excluding_TAX;
	}
	public Double getUAE_Dirham_including_TAX() {
		return UAE_Dirham_including_TAX;
	}
	public void setUAE_Dirham_including_TAX(Double uAE_Dirham_including_TAX) {
		UAE_Dirham_including_TAX = uAE_Dirham_including_TAX;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
