package com.app.controller.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.app.model.orm.dbo.wrapper.ModuleDBO;
import com.app.service.DefaultService;

@Named
@SessionScoped
public class ModulesMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4939033717637926257L;

	volatile Logger log = Logger.getLogger(ModulesMB.class);

	private MenuModel model;

	private String currentID;

	private Integer totalQuotes;

	private Integer totalCompanies;

	@Inject
	UserMB userMB;

	@Inject
	LayoutMB layoutMB;

	@Inject
	DefaultService defaultService;

	private List<ModuleDBO> modules;

	@PostConstruct
	public void initialize() {
		fetchModules();

		generateModMenu("#{modulesMB.navigateToPage}");
		log.info("PostConstruct : ModulesMB ");
	}

	private void fetchModules() {

		try {
			setModules(defaultService.fetchModules());
		} catch (Exception e) {
			log.info(e.toString());
		}
	}

	public void generateModMenu(String currentCommand) {

		List<ModuleDBO> modulesList;

		model = new DefaultMenuModel();

		try {
			modulesList = defaultService.fetchModules();

			int index = 0;
			for (final ModuleDBO modRow : modulesList) {

				index += 1;
				final DefaultSubMenu alphaMenu = DefaultSubMenu.builder().label(modRow.getName()).icon(currentCommand)
						.build();

				Map<String, List<String>> paramMap = new HashMap<>();
				List<String> paramList = new ArrayList<>();
				paramList.add(modRow.getName());
				paramMap.put("clientID", paramList);

				List<String> paramList2 = new ArrayList<>();
				paramList2.add(modRow.getName().toString());
				paramMap.put("modCode", paramList2);

				DefaultMenuItem menuitem;
				if (currentCommand.contains("Current")) {

					menuitem = DefaultMenuItem.builder().value(modRow.getName()).target("_blank")
							.command(currentCommand).includeViewParams(true).params(paramMap).build();

				} else {

					menuitem = DefaultMenuItem.builder().value(modRow.getName()).command(currentCommand)
							.includeViewParams(true).params(paramMap).target("_blank").ajax(false).build();

				}

				alphaMenu.getElements().add(menuitem);
				model.getElements().add(alphaMenu);

			}

		} catch (Exception e) {
			log.info(e.toString());
		}

	}

	public void navigateToPage() {
		final ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		final CentralLayoutMB centralLayoutMB = (CentralLayoutMB) FacesContext.getCurrentInstance().getApplication()
				.getELResolver().getValue(elContext, null, "centralLayoutMB");

		final FacesContext contextF = FacesContext.getCurrentInstance();
		final UIComponent component = UIComponent.getCurrentComponent(contextF);
		setCurrentID(component.getId());

		final String itemID = contextF.getExternalContext().getRequestParameterMap().get("clientID");
		final String modCode = contextF.getExternalContext().getRequestParameterMap().get("modCode");

		
		
		for (ModuleDBO modRow : modules) {
			
			if(itemID.equals(modRow.getName())) {
				centralLayoutMB.setCentralLayout("/secured/"+modRow.getName()+"View");
			}

		}

		/*
		 * BASIC NAVIGATION
		 * 
		 * List<String> pages = new ArrayList<>();
		 * 
		 * if (currentID.equals("settings")) {
		 * centralLayoutMB.setCentralLayout("/secured/settings"); userMB.fetchUsers();
		 * 
		 * } else if (currentID.equals("dashboard") ||
		 * currentID.equals("aptw3logolink")) {
		 * centralLayoutMB.setCentralLayout("/secured/dashboard"); } else if
		 * (currentID.equals("quotes") || currentID.equals("viewQuotesBtn")) {
		 * centralLayoutMB.setCentralLayout("/secured/QuotesView"); final QuoteMB
		 * quoteMB = (QuoteMB)
		 * FacesContext.getCurrentInstance().getApplication().getELResolver()
		 * .getValue(elContext, null, "quoteMB"); quoteMB.initiateQuote();
		 * 
		 * pages.add("/secured/QuoteView"); pages.add("/secured/QuoteDetails");
		 * layoutMB.createLayout(pages,DynamicLayout.VFLOW);
		 * 
		 * } else if (currentID.equals("companies") ||
		 * currentID.equals("viewCompaniesBtn")) {
		 * centralLayoutMB.setCentralLayout("/secured/InsuranceProvidersView");
		 * //layoutMB.getCurrentLayout().setView("/secured/InsuranceProvidersView");
		 * 
		 * 
		 * //pages.add("/secured/InsuranceProviderDetails");
		 * pages.add("/secured/InsuranceProviders");
		 * 
		 * layoutMB.createLayout(pages,DynamicLayout.GRID);
		 * 
		 * 
		 * }
		 */

	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

	public String getCurrentID() {
		return currentID;
	}

	public void setCurrentID(String currentID) {
		this.currentID = currentID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getTotalQuotes() {
		return totalQuotes;
	}

	public void setTotalQuotes(Integer totalQuotes) {
		this.totalQuotes = totalQuotes;
	}

	public Integer getTotalCompanies() {
		return totalCompanies;
	}

	public void setTotalCompanies(Integer totalCompanies) {
		this.totalCompanies = totalCompanies;
	}

	public List<ModuleDBO> getModules() {
		return modules;
	}

	public void setModules(List<ModuleDBO> modules) {
		this.modules = modules;
	}

}
