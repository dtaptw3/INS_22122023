package com.app.controller.beans;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.primefaces.PrimeFaces;

import com.app.controller.beans.Interface.UsersInterface;
import com.app.model.orm.dbo.wrapper.UserDBO;
import com.app.service.DefaultService;
import com.app.service.UserService;
import com.app.utilities.QueryBuilder;
import com.app.utilities.Sprocket;

@Named("usersMB")
@SessionScoped
public class UserMB implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 559679058509770040L;

	private Boolean Developer = true;

	private transient String username;
	private transient String password;
	
	private static String currentSessionID;

	private String incominglinkID;

	private String stateCode;

	private String timeOfDay;

	private UserDBO currentUser;

	private UserDBO dummyUser;

	private Boolean powerUser = false;

	private Boolean saveBtn = true;

	private Boolean undoBtn = true;

	private Boolean deleteBtn = false;

	private Boolean ursaveBtn = true;

	private Boolean userRightsBtn = true;

	private Boolean userRightsSaveBtn;

	private int rowNum;

	private String currentPwd;

	private String newPwd;

	private String confirmPwd; 

	private Boolean newBtn;

	private Boolean renderPage;
 
	private static final String generatedEntityID = Sprocket.generatedEntityID;
	private static final String entity = Sprocket.entity;
	private static final LocalDate lastBuildDate = Sprocket.lastbuilddate;
	private static final String instanceNo = Sprocket.instanceNo;

	@Inject
	UserService userService;

	private String clipboard;

	private String displayMessage;

	private boolean blockUI = false; // global and common

	private String currentID;

	private List<UserDBO> users;

	private UsersInterface userInterface;

	@Inject
	DefaultService defaultService;

	volatile Logger log = Logger.getLogger(UserMB.class);

	@PostConstruct
	public void initialize() {

		System.out.println("POST CONSTRUCT: UserMB initialized");
		// registersUsers();

	}

	public void setCurrentTimeofDay() {

		if (LocalTime.now().isBefore(LocalTime.of(12, 00))) {
			setTimeOfDay("Good Morning");
		} else if (LocalTime.now().isAfter(LocalTime.of(12, 00)) && LocalTime.now().isBefore(LocalTime.of(17, 00))) {
			setTimeOfDay("Good Afternoon");
		} else if (LocalTime.now().isAfter(LocalTime.of(17, 00))) {
			setTimeOfDay("Good Evening");
		}

	}

	private Integer loginCount = 0;

	public String checkAuthentication() {

		final FacesContext contextF = FacesContext.getCurrentInstance();
		String result = "";

		try {

			final ExternalContext externalContext = contextF.getExternalContext();
			final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest(); // request.getSession(false);

			currentUser = new UserDBO();
			currentUser.setUsername(username);
			currentUser.setPassword(password); //
			currentUser = userService.selectByUser(currentUser);

			log.info("Logging in to : " + Sprocket.entity_name);
			log.info("auth level 1 check");

			if (currentUser != null) {

				final InetAddress ip = InetAddress.getLocalHost();
				log.info("U/" + currentUser.getUsername() + " @ ! ");

				NetworkInterface.getByInetAddress(ip);
				final StringBuilder sb = new StringBuilder();

				registerLoginInfo(request);

				final FacesContext fCtx = FacesContext.getCurrentInstance();
				final HttpSession httpsession = (HttpSession) fCtx.getExternalContext().getSession(false);
				//httpsession.setMaxInactiveInterval(10); 
				
				currentUser.setSessionID(httpsession.getId()); //
				// currentUser.setSysname(sb.toString());
				System.out.println("HTTP REQUEST came from " + request.getRemoteAddr());

				setCurrentTimeofDay();

				setDeveloper(currentUser.getId() == 545 ? true : false);

				final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				FacesContext.getCurrentInstance().getExternalContext().addResponseCookie("startdate",
						df.format(Sprocket.SYSSTARTDATE.toDate()), null);
				FacesContext.getCurrentInstance().getExternalContext().addResponseCookie("enddate",
						df.format(Sprocket.SYSENDDATE.toDate()), null);

				updateUserOnlineStatus();
				System.out.println(username + " >> logged in");

				result = "/secured/home";

			} else

			{
				loginCount = loginCount + 1;
				System.out.println(loginCount + " failed attempts for " + username);

				result = "failure";

				if (currentUser != null && currentUser.getStatus().equals("Y")) {
					contextF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login already in use",
							"Contact System Admin"));
				} else {
					contextF.addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Authentication Failed", ""));
				}

				PrimeFaces.current().ajax().update("loginForm:stat");
			}

		} catch (Exception e) {
			log.info(e.toString());
			contextF.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Error", "Contact system administrator"));
			PrimeFaces.current().ajax().update("loginForm:stat");
		}

		return result;

	}

	private void registerLoginInfo(HttpServletRequest request) {
		String browserDetails = request.getHeader("User-Agent");
		log.info("\n\n\n\n///////////////////////////// ");
		// System.out.println(browserDetails);

		String userAgent = browserDetails;
		String user = userAgent.toLowerCase();

		String os = "";
		String browser = "";

		// log.info("User Agent for the request is >>>> " + browserDetails);
		// =================OS=======================
		if (userAgent.toLowerCase().indexOf("windows") >= 0) {
			os = "Windows";
		} else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
			os = "Mac";
		} else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
			os = "Unix";
		} else if (userAgent.toLowerCase().indexOf("android") >= 0) {
			os = "Android";
		} else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
			os = "IPhone";
		} else {
			os = "UnKnown, More-Info: " + userAgent;
		}
		// ===============Browser===========================
		if (user.contains("msie")) {
			String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
			browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
		} else if (user.contains("safari") && user.contains("version")) {
			browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-"
					+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		} else if (user.contains("opr") || user.contains("opera")) {
			if (user.contains("opera"))
				browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-"
						+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			else if (user.contains("opr"))
				browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
						.replace("OPR", "Opera");
		} else if (user.contains("chrome")) {
			browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
		} else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)
				|| (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1)
				|| (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
			// browser=(userAgent.substring(userAgent.indexOf("MSIE")).split("
			// ")[0]).replace("/", "-");
			browser = "Netscape-?";

		} else if (user.contains("firefox")) {
			browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("rv")) {
			browser = "IE-" + user.substring(user.indexOf("rv") + 3, user.indexOf(")"));
		} else {
			browser = "UnKnown, More-Info: " + userAgent;
		}
		System.out.println("OS >>>> " + os);
		System.out.println("Browser >>>> " + browser);

		log.info("////////////////////////////\n\n\n\n");

		// currentUser.setSysname(os + " - " + browser);

	}

	public void logout() throws IOException {

		System.out.println(username + " >> logging out");
		updateUserOfflineStatus();
		final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		externalContext.redirect(externalContext.getRequestContextPath() + "/index.html?faces-redirect=true");

	}

	private void updateUserOnlineStatus() {

		try {
			new QueryBuilder().update("Users").set("status", "Y").set("sessionID", currentUser.getSessionID())
					.where("id", currentUser.getId());
		} catch (Exception e) {
			log.info(e.toString());
		}
		
	
		currentUser.setRights();

	}

	private void updateUserOfflineStatus() {

		try {
			new QueryBuilder().update(new UserDBO()).set("status", "N").set("sessionID", null).where("id",
					currentUser.getId());
		} catch (Exception e) {
			log.info(e.toString());
		}

	}
	
	public void fetchUsers() {
		
		try {
			
			UserDBO userEx = new UserDBO();
			userEx.setStatus("A");
			//setUsers(new QueryBuilder().select(currentUser).where("status", "A").executeFor(UserDBO.class));
			setUsers(new QueryBuilder().select(userEx).where(userEx).executeFor(UserDBO.class));
			users.size();
			
		} catch (Exception e) {
			log.info(e.toString());
		}
	}

	

	

	public Boolean getDeveloper() {
		return Developer;
	}

	public void setDeveloper(Boolean developer) {
		Developer = developer;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIncominglinkID() {
		return incominglinkID;
	}

	public void setIncominglinkID(String incominglinkID) {
		this.incominglinkID = incominglinkID;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(String timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	public UserDBO getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserDBO currentUser) {
		this.currentUser = currentUser;
	}

	public UserDBO getDummyUser() {
		return dummyUser;
	}

	public void setDummyUser(UserDBO dummyUser) {
		this.dummyUser = dummyUser;
	}

	public Boolean getPowerUser() {
		return powerUser;
	}

	public void setPowerUser(Boolean powerUser) {
		this.powerUser = powerUser;
	}

	public Boolean getSaveBtn() {
		return saveBtn;
	}

	public void setSaveBtn(Boolean saveBtn) {
		this.saveBtn = saveBtn;
	}

	public Boolean getUndoBtn() {
		return undoBtn;
	}

	public void setUndoBtn(Boolean undoBtn) {
		this.undoBtn = undoBtn;
	}

	public Boolean getDeleteBtn() {
		return deleteBtn;
	}

	public void setDeleteBtn(Boolean deleteBtn) {
		this.deleteBtn = deleteBtn;
	}

	public Boolean getUrsaveBtn() {
		return ursaveBtn;
	}

	public void setUrsaveBtn(Boolean ursaveBtn) {
		this.ursaveBtn = ursaveBtn;
	}

	public Boolean getUserRightsBtn() {
		return userRightsBtn;
	}

	public void setUserRightsBtn(Boolean userRightsBtn) {
		this.userRightsBtn = userRightsBtn;
	}

	public Boolean getUserRightsSaveBtn() {
		return userRightsSaveBtn;
	}

	public void setUserRightsSaveBtn(Boolean userRightsSaveBtn) {
		this.userRightsSaveBtn = userRightsSaveBtn;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getCurrentPwd() {
		return currentPwd;
	}

	public void setCurrentPwd(String currentPwd) {
		this.currentPwd = currentPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public Boolean getNewBtn() {
		return newBtn;
	}

	public void setNewBtn(Boolean newBtn) {
		this.newBtn = newBtn;
	}

	public Boolean getRenderPage() {
		return renderPage;
	}

	public void setRenderPage(Boolean renderPage) {
		this.renderPage = renderPage;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getClipboard() {
		return clipboard;
	}

	public void setClipboard(String clipboard) {
		this.clipboard = clipboard;
	}

	public String getDisplayMessage() {
		return displayMessage;
	}

	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}

	public boolean isBlockUI() {
		return blockUI;
	}

	public void setBlockUI(boolean blockUI) {
		this.blockUI = blockUI;
	}

	public String getCurrentID() {
		return currentID;
	}

	public void setCurrentID(String currentID) {
		this.currentID = currentID;
	}

	public UsersInterface getUserInterface() {
		return userInterface;
	}

	public void setUserInterface(UsersInterface userInterface) {
		this.userInterface = userInterface;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public List<UserDBO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDBO> users) {
		this.users = users;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEntity() {
		return entity;
	}

	public LocalDate getLastBuildDate() {
		return lastBuildDate;
	}

	public String getGeneratedEntityID() {
		return generatedEntityID;
	}

	public String getInstanceNo() {
		return instanceNo;
	}

	public String getCurrentSessionID() {
		return currentSessionID;
	}

	public void setCurrentSessionID(String currentSessionID) {
		UserMB.currentSessionID = currentSessionID;
	}

	public static String getGeneratedentityid() {
		return generatedEntityID;
	}

	public static LocalDate getLastbuilddate() {
		return lastBuildDate;
	}

	public static String getInstanceno() {
		return instanceNo;
	}

}
