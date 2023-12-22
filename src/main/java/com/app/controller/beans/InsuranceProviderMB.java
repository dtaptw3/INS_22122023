package com.app.controller.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import com.app.model.orm.dbo.wrapper.InsuranceProviderDBO;
import com.app.service.DefaultService;
import com.app.utilities.QueryBuilder;

@Named("insProMB")
@ViewScoped
public class InsuranceProviderMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5934200545743306842L;

	private List<InsuranceProviderDBO> insuranceProviderList;
	private List<InsuranceProviderDBO> insuranceProviderListFilter;

	private List<Map<String, Object>> insuranceProviderMapList;

	private InsuranceProviderDBO insuranceProvider;

	volatile Logger log = Logger.getLogger(InsuranceProviderMB.class);

	@Inject
	DefaultService defaultService;

	@PostConstruct
	public void initialize() {
		fetchInsuranceProviders();
		initiateInsuranceProvider();
	}

	public void fetchInsuranceProviders() {
		final FacesContext contextF = FacesContext.getCurrentInstance();
		try {

			insuranceProviderList = defaultService.fetchInsuranceProviders();

		} catch (Exception e) {
			System.out.println(e.toString());

			if (e.toString().contains("no such table")) {
				contextF.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Settings error", "contact system Admin"));
				PrimeFaces.current().ajax().update("headerForm:stat", "stat");
			}

		}

	}

	public void initiateInsuranceProvider() {

		insuranceProvider = new InsuranceProviderDBO();
		insuranceProvider.setId(0);
		insuranceProvider.setStatus("A");
		
		insuranceProviderList.forEach(item -> item.setLogo("insura".getBytes()));
		
		
		//PrimeFaces.current().executeScript("PF('insuranceProviderDlg').show();");
		PrimeFaces.current().ajax().update("ipForm:ipDetails");
		
		
		PrimeFaces.current().executeScript("ipDetPanel.expand();");
		PrimeFaces.current().executeScript("ipViewPanel.collapse();");

		// expand()
		PrimeFaces.current().ajax().update("ipForm:ipDetails");
		
		

	}

	public void selectInsuranceProvider(final SelectEvent event) {

		insuranceProvider = (InsuranceProviderDBO) event.getObject();

		if (insuranceProvider.getLogo() == null) {
			insuranceProvider.setLogo("insura".getBytes());
		}

		
		PrimeFaces.current().executeScript("ipDetPanel.expand();");
		PrimeFaces.current().executeScript("ipViewPanel.collapse();"); 
		
		//PrimeFaces.current().executeScript("PF('insuranceProviderDlg').show();");
		PrimeFaces.current().ajax().update("ipForm:ipDetails");

	}

	public void handleFileUpload(final FileUploadEvent eventUp) {

		insuranceProvider.setLogo(eventUp.getFile().getContent());

		PrimeFaces.current().ajax().update("insuranceProviderDetForm:gridImg",
				"insuranceProviderDetForm:insuranceProviderDetails");
		// saveInsuranceProviderSettings();
	}

	public void saveInsuranceProvider() {

		try {

			List<?> resultMap = new QueryBuilder().select("*", "InsuranceProvider")
					.where("id", insuranceProvider.getId()).executeFor(InsuranceProviderDBO.class);

			if (resultMap != null && resultMap.size() > 0) {
				new QueryBuilder().update(insuranceProvider).where("id", insuranceProvider.getId()).run();
			} else {
				new QueryBuilder().insert(insuranceProvider).run();
			}

			log.info(insuranceProvider.getName() + " :  updated");

		} catch (Exception e) {
			log.info(e.toString());
		}

	}

	public List<InsuranceProviderDBO> getInsuranceProviderList() {
		return insuranceProviderList;
	}

	public void setInsuranceProviderList(List<InsuranceProviderDBO> insuranceProviderList) {
		this.insuranceProviderList = insuranceProviderList;
	}

	public List<InsuranceProviderDBO> getInsuranceProviderListFilter() {
		return insuranceProviderListFilter;
	}

	public void setInsuranceProviderListFilter(List<InsuranceProviderDBO> insuranceProviderListFilter) {
		this.insuranceProviderListFilter = insuranceProviderListFilter;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public List<Map<String, Object>> getInsuranceProviderMapList() {
		return insuranceProviderMapList;
	}

	public void setInsuranceProviderMapList(List<Map<String, Object>> insuranceProviderMapList) {
		this.insuranceProviderMapList = insuranceProviderMapList;
	}

	public InsuranceProviderDBO getInsuranceProvider() {
		return insuranceProvider;
	}

	public void setInsuranceProvider(InsuranceProviderDBO insuranceProvider) {
		this.insuranceProvider = insuranceProvider;
	}

}
