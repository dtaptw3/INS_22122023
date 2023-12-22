package com.app.controller.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.app.model.orm.dbo.wrapper.DynamicLayout;
import com.app.model.orm.dbo.wrapper.GridLayout;
import com.app.model.orm.dbo.wrapper.Layout;

@Named("layoutMB")
@SessionScoped
public class LayoutMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7332510123282216454L;

	private String currentID;

	private Layout currentLayout;
	
	private GridLayout gridLayout;

	private String currentPageType = "/secured/dashboard"; 
	
	public String getCurrentPageType() {
		return currentPageType;
	}

	public void setCurrentPageType(String currentPageType) {
		this.currentPageType = currentPageType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@PostConstruct
	public void initialise() {
		currentLayout = new Layout();
		currentLayout.setView("/secured/dashboard");
		currentLayout.setOrder("1");
		currentLayout.setVisibility(1);

	}

	public void switchLayout() {
		final FacesContext contextF = FacesContext.getCurrentInstance();
		final UIComponent component = UIComponent.getCurrentComponent(contextF);
		setCurrentID(component.getId());

		if (currentID.contains("max")) {
			//currentLayout.setView(currentID);
		} else if (currentID.contains("split")) {

		}
	}

	// possible options: max | split view
	public void switchLayout(Map<String, String> requestedPages) {
		final FacesContext contextF = FacesContext.getCurrentInstance();
		final UIComponent component = UIComponent.getCurrentComponent(contextF);
		setCurrentID(component.getId());

		/*
		 * if(currentID.contains("max")) {
		 * 
		 * setMasterTable(null); setDetails(requestedPages.get("details").toString());
		 * 
		 * } else if(currentID.contains("split")) {
		 * 
		 * if(requestedPages.get("details") != null) {
		 * setDetails(requestedPages.get("details").toString()); }
		 * 
		 * }
		 */

	}
	
	
	public void createLayout(List<String> pages, DynamicLayout dlayout) {
		if(dlayout == DynamicLayout.GRID) {
			
			currentPageType = "/secured/GridLayout";
			
			gridLayout = new GridLayout();
			gridLayout.setColumns(pages.size());
			gridLayout.setColumnClasses(gridLayout.generateColClasses(gridLayout.getColumns()));
			gridLayout.setPages(pages); 
		} else if(dlayout == DynamicLayout.VFLOW) {
			
			currentPageType = "/secured/GridLayout";
			gridLayout = new GridLayout();
			gridLayout.setColumns(1);
			gridLayout.setColumnClasses(gridLayout.generateColClasses(gridLayout.getColumns()));
			gridLayout.setPages(pages); 
			
			
		}
		
		
		PrimeFaces.current().ajax().update("");
		
		
	}
	
	
	
	
	
	
	
	
	
	

	public String getCurrentID() {
		return currentID;
	}

	public void setCurrentID(String currentID) {
		this.currentID = currentID;
	}

	public Layout getCurrentLayout() {
		return currentLayout;
	}

	public void setCurrentLayout(Layout currentLayout) {
		this.currentLayout = currentLayout;
	}

	public GridLayout getGridLayout() {
		return gridLayout;
	}

	public void setGridLayout(GridLayout gridLayout) {
		this.gridLayout = gridLayout;
	}

	

}
