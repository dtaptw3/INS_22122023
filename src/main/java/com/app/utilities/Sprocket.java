package com.app.utilities;

import org.joda.time.LocalDate;

public abstract class Sprocket {
	
	

	// public static final String NODE_PROPS = "//C:/w3node.properties";
	public static final LocalDate lastbuilddate = new LocalDate(2022, 9, 8);    
	public static final Boolean autoSaveDocs = false;

	// *
	public static final String entity = "Quiver";
	public static final String entity_name = "Insura_UAE";
	public static final LocalDate SYSSTARTDATE = new LocalDate(2022, 8, 03);
	public static final LocalDate SYSENDDATE = lastbuilddate.plusDays(45);
	public static final String generatedEntityID = "029348AOA022";
	public static final Boolean ignoreSegmentation = true;
	public static final String instanceNo = "instance1";
	
	
	  public static final String CONNECTION_URL = "jdbc:sqlserver://INSURA;databaseName=INSURA;Integrated Security=SSPI;";
	  public static final String USERNAME = "aptw3"; public static final String
	  ACCESS = "a9832whiteSpace17";
	 

	
	public static String mainSysPath = "C:\\INSURA\\" + entity + "\\";
}
 