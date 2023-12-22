package com.app.controller.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.app.model.orm.dbo.wrapper.CountryDBO;
import com.app.service.DefaultService;

@Named("countryMB")
@ViewScoped
public class CountryMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4638448419735831225L;

	volatile Logger log = Logger.getLogger(CountryMB.class);

	private List<CountryDBO> countryList;
	private List<CountryDBO> countryListFilter;

	@PostConstruct
	public void initialize() {
		fetchCountries();
	}

	@Inject
	DefaultService defaultService;

	public void fetchCountries() {
		final FacesContext contextF = FacesContext.getCurrentInstance();
		try {
			countryList = defaultService.fetchCountries();
		} catch (Exception e) {
			log.info(e.toString());
			
			if (e.toString().contains("no such table")) {
				contextF.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Settings error", "contact system Admin"));
				PrimeFaces.current().ajax().update("headerForm:stat","stat");
			}
		}

	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public List<CountryDBO> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<CountryDBO> countryList) {
		this.countryList = countryList;
	}

	public List<CountryDBO> getCountryListFilter() {
		return countryListFilter;
	}

	public void setCountryListFilter(List<CountryDBO> countryListFilter) {
		this.countryListFilter = countryListFilter;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
