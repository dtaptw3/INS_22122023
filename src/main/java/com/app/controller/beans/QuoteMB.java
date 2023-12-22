package com.app.controller.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;

import com.app.controller.beans.Interface.UsersInterface;
import com.app.model.orm.dbo.wrapper.LinkedPlansDBO;
import com.app.model.orm.dbo.wrapper.MemberDBO;
import com.app.model.orm.dbo.wrapper.QuoteDBO;
import com.app.model.orm.dbo.wrapper.UserDBO;
import com.app.utilities.AppUtility;
import com.app.utilities.QueryBuilder;

@Named("quoteMB")
@ViewScoped
public class QuoteMB implements Serializable, UsersInterface /* ,EmployeeDetailsInterface, UserCategoryInterface */ {

	volatile Logger log = Logger.getLogger(QuoteMB.class);

	private static final long serialVersionUID = 6734856405833204490L;

	private List<QuoteDBO> quotes;
	private List<QuoteDBO> quotesFilter;

	private QuoteDBO quote;

	@Inject
	UserMB usersMB;
	
	@Inject 
	InsuranceProviderMB insproviderMB;

	@PostConstruct
	public void initialize() {

		// initiateQuote();

	}

	public void initiateQuote() {
		quote = new QuoteDBO();
		
		
		
		
		quote.setQuoteid(
				"INS-" + AppUtility.randomIDGenerator(3) + "-" + AppUtility.randomIDGenerator(5));
		quote.setStartdate(new Date());
		quote.setQrflink(AppUtility.randomIDGenerator(20));
		quote.setAdvisoremail(usersMB.getCurrentUser().getEmail()); 
		quote.setAdvisorname(usersMB.getCurrentUser().getName());
		quote.setQuoteddate(new Date());
		quote.setCreatedby(String.valueOf(usersMB.getCurrentUser().getId()));
		quote.setPhonecode("+971");
		quote.setCurrency("AED");
		quote.setNationality("ARE");
		quote.setResidency("ARE");
		
		quote.setAdvisoremail("advisor@testemail.com");


		// Members
		quote.setMembers(new ArrayList<>());

		if (usersMB.getDeveloper()) {
			quote.setFirstname("John");
			quote.setLastname("Wick");
			quote.setGender("M");
			quote.setMaritalstatus("S");
			quote.setEmail("john.wick@gmail.com");
			quote.setPhone("98234798");
			quote.setDateofbirth(new LocalDate(1987, 9, 02).toDate());

			// default members
			addMember("Daisy", "Donald", "S");
			addMember("Molly", "Donald", "C");

		}

		PrimeFaces.current().executeScript("quoteDetPanel.expand();");
		PrimeFaces.current().executeScript("quotesViewPanel.collapse();");

		// expand()
		PrimeFaces.current().ajax().update("quoteDetailsForm");

	}

	public void addMember(String firstname, String lastname, String relation) {

		MemberDBO memberInst = new MemberDBO();
		memberInst.setQuoteid(quote.getQuoteid());
		memberInst.setFirstname(firstname);
		memberInst.setLastname(lastname);
		memberInst.setRelation(relation);
		quote.getMembers().add(memberInst);
		PrimeFaces.current().ajax().update("quoteDetailsForm:memberPanel");

	}

	public void addPlan() { 

		if (quote.getLinkedplans() == null) {
			quote.setLinkedplans(new ArrayList<>());
		}
		
		LinkedPlansDBO planInst = new LinkedPlansDBO();
		planInst.setLinenumber(quote.getLinkedplans().size()+1);

		quote.getLinkedplans().add(planInst);
		
		PrimeFaces.current().ajax().update("quoteDetailsForm:planPanel");
	}

	
	
	
	
	public String onFlowProcess(FlowEvent event) {
		
		if(event.getNewStep().equals("plan")) {
			insproviderMB.fetchInsuranceProviders();
		}
		
		return event.getNewStep();
	}

	public void searchQuotes() {

		PrimeFaces.current().executeScript("quoteDetPanel.expand();");
		PrimeFaces.current().executeScript("quotesViewPanel.expand();");
	}

	public void fetchQuotes() {
		try {

			quotes = (List<QuoteDBO>) new QueryBuilder().select("*", "Quotes").executeFor(QuoteDBO.class);

		} catch (Exception e) {
			log.info(e);
		}
	}

	public void deleteQuote() {
		try {
			new QueryBuilder().delete("Quote").where("id", quote.getQuoteid());
		} catch (Exception e) {
			log.info(e);
		}
	}

	public void saveQuote() {

		// validations

		try {

			if (quote.getEmail() == null) {

				return;
			}

			quote.getMembers().add(copyPrimaryMember());

			QuoteDBO quoInst = (QuoteDBO) new QueryBuilder().select(quote,"id").where("id", quote.getQuoteid())
					.executeFor(QuoteDBO.class);

			if (quoInst != null) {
				new QueryBuilder().update(quoInst).where("id", quoInst.getQuoteid()).run();
			} else {
				System.out.println("No data for quote");
				new QueryBuilder().insert(quoInst).run();
			}

		} catch (Exception e) {
			log.info(e);
		}

	}

	private MemberDBO copyPrimaryMember() {
		// copy primary member
		MemberDBO primaryMember = new MemberDBO();
		primaryMember.setFirstname(quote.getFirstname());
		primaryMember.setLastname(quote.getLastname());
		primaryMember.setType("P");
		primaryMember.setGender(quote.getGender());

		primaryMember.setEmail(quote.getEmail());
		primaryMember.setPhonecode(quote.getPhonecode());
		primaryMember.setPhonenumber(quote.getPhone());

		primaryMember.setResidency(quote.getResidency());
		primaryMember.setNationality(quote.getNationality());

		return primaryMember;
	}

	public List<QuoteDBO> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<QuoteDBO> quotes) {
		this.quotes = quotes;
	}

	public List<QuoteDBO> getQuotesFilter() {
		return quotesFilter;
	}

	public void setQuotesFilter(List<QuoteDBO> quotesFilter) {
		this.quotesFilter = quotesFilter;
	}

	public QuoteDBO getQuote() {
		return quote;
	}

	public void setQuote(QuoteDBO quote) {
		this.quote = quote;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public void setUsers(UserDBO user) {
		// TODO Auto-generated method stub

	}

}
