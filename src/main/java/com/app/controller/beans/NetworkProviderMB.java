package com.app.controller.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.app.model.orm.dbo.wrapper.NetworkProviderDBO;
import com.app.service.DefaultService;

@Named("nwproviderMB")
@ViewScoped
public class NetworkProviderMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8331743525568642263L;
	
	
	List<NetworkProviderDBO> networkProviderList;
	
	@Inject
	DefaultService defaultService;

	public void fetchNetworkProviders() {

		final FacesContext contextF = FacesContext.getCurrentInstance();
		try {

			networkProviderList = defaultService.fetchNetworkProviders();

		} catch (Exception e) {
			System.out.println(e.toString());

			if (e.toString().contains("no such table")) {
				contextF.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Settings error", "contact system Admin"));
				PrimeFaces.current().ajax().update("headerForm:stat", "stat");
			}

		}

	}

}
