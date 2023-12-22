package com.app.controller.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class CentralLayoutMB implements Serializable {

	private static final long serialVersionUID = -3539163623478002917L;

	private String centralLayout = "/secured/DashboardView";
	

	public String getCentralLayout() {

		return centralLayout;
	}

	public void setCentralLayout(final String centralLayout) {
		this.centralLayout = centralLayout;
	}

	
}
