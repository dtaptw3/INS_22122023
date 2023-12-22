package com.app.controller.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.app.model.orm.dbo.wrapper.UserDBO;
import com.app.service.DefaultService;

@Named("spa")
@ViewScoped
public class SPA implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2609147956861971118L;
	private String spaname;
	private String spakey;
	
	@Inject
	DefaultService defaultService;
	
	@Inject 
	UserMB usersMB;
	
	public String checkSPA() {
		
		if(spaname.equals("dt98345") && spakey.equals("309459009w340983458340859UYGSuyg378")) {
			final FacesContext contextF = FacesContext.getCurrentInstance();
			final HttpSession httpsession = (HttpSession) contextF.getExternalContext().getSession(false);

			usersMB.setCurrentUser(new UserDBO(spaname,"Y","D"));
			usersMB.getCurrentUser().setSessionID(httpsession.getId());
			return "/secured/SPA";
		} else {
			return "/login";
		}
		
	}
	
	public void exitSPA() {
		System.out.println(spaname + " >> logging out");
		final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		try {
			externalContext.redirect(externalContext.getRequestContextPath() + "/index.html?faces-redirect=true");
		} catch (IOException e) {
			System.err.println("failed exit"); 
		}
	}

	
	public void executeDefaults() {
		final FacesContext contextF = FacesContext.getCurrentInstance();
		
		try {
			/*
			 * defaultService.registerDefaultUsers();
			 * defaultService.registerDefaultInsuranceProviders();
			 * defaultService.registerDefaultCountries();
			 */
			
			defaultService.registerDefaults();
			
			contextF.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Defaults Applied!", ""));
			PrimeFaces.current().ajax().update("spaForm:stat");
			
			
		} catch (Exception e) {
			
			contextF.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed setting defaults!", ""));
			PrimeFaces.current().ajax().update("spaForm:stat");
			
			System.out.println(e.toString());
			System.err.println("Failed setting defaults");
		}
	}
	
	
	public String getSpaname() {
		return spaname;
	}
	public void setSpaname(String spaname) {
		this.spaname = spaname;
	}
	public String getSpakey() {
		return spakey;
	}
	public void setSpakey(String spakey) {
		this.spakey = spakey;
	}
	
}
