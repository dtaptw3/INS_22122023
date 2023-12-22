package com.app.controller.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.app.model.orm.dbo.wrapper.InsurancePlanDBO;
import com.app.service.DefaultService;
import com.app.utilities.QueryBuilder;

@Named("insPlanMB")
@ViewScoped
public class InsurancePlanMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7604621209414476731L;
	
	volatile Logger log = Logger.getLogger(InsuranceProviderMB.class);
	
	
	private List<InsurancePlanDBO> insurancePlanList;
	private InsurancePlanDBO insurancePlan;

	@Inject
	DefaultService defaultService;
	
	@PostConstruct
	public void initialize() {
		fetchInsurancePlans();
		initiateInsurancePlan();
	}

	public void initiateInsurancePlan() {
		insurancePlan = new InsurancePlanDBO();
		insurancePlan.setStatus("A");
		
		
		
		//PrimeFaces.current().executeScript("PF('insuranceProviderDlg').show();");
		PrimeFaces.current().ajax().update("iplanForm:iplanDetails");
		
		
		PrimeFaces.current().executeScript("iplanDetPanel.expand();");
		PrimeFaces.current().executeScript("iplanViewPanel.collapse();");

		// expand()
		PrimeFaces.current().ajax().update("ipForm:ipDetails");
		
	}

	public void fetchInsurancePlans() {
		try {
			setInsurancePlanList(new QueryBuilder().select(new InsurancePlanDBO()).executeFor(InsurancePlanDBO.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public List<InsurancePlanDBO> getInsurancePlanList() {
		return insurancePlanList;
	}

	public void setInsurancePlanList(List<InsurancePlanDBO> insurancePlanList) {
		this.insurancePlanList = insurancePlanList;
	}

	public InsurancePlanDBO getInsurancePlan() {
		return insurancePlan;
	}

	public void setInsurancePlan(InsurancePlanDBO insurancePlan) {
		this.insurancePlan = insurancePlan;
	}

	public DefaultService getDefaultService() {
		return defaultService;
	}

	public void setDefaultService(DefaultService defaultService) {
		this.defaultService = defaultService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
