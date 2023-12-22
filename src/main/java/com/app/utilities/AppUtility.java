package com.app.utilities;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.joda.time.DateTime;

public class AppUtility {

	public static Map<Boolean, String> hasDuplicate(final List<String> ecodeList) {

		boolean ind = false;
		Map<Boolean, String> mapValue = new HashMap<Boolean, String>();

		final Set<String> seenValues = new HashSet<String>();

		for (final String ecodeRow : ecodeList) {
			if (seenValues.contains(ecodeRow)) {
				ind = true;
				mapValue.put(ind, ecodeRow);
				return mapValue;

			} else {
				seenValues.add(ecodeRow);
			}
		}
		return null;

	}

	public static String randomIDGenerator(int maxlength) {
		// Connection ID generator
		final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		final String lower = upper.toLowerCase(Locale.ROOT);

		final String digits = "0123456789";

		final String chars = upper + lower + digits;

		final Random rand = new Random();
		final StringBuilder buf = new StringBuilder();
		for (int i = 0; i < maxlength; i++) {
			buf.append(chars.charAt(rand.nextInt(chars.length())));
		}

		return buf.toString();
	}

	public static Date addTimeToDate(Date date, String timeRange) {

		// timeRange : min | max |

		DateTime dateTimeFormat = null;

		if (timeRange.contains("min")) {
			dateTimeFormat = new DateTime(date).withTime(00, 00, 00, 000);
		} else if (timeRange.contains("max")) {
			dateTimeFormat = new DateTime(date).withTime(23, 59, 59, 999);
		}

		return dateTimeFormat.toDate();

	}

	public static Integer findMonthInt(final String firstMSplit) {

		if (firstMSplit.equalsIgnoreCase("Jan")) {
			return 1;
		} else if (firstMSplit.equalsIgnoreCase("Feb")) {
			return 2;
		} else if (firstMSplit.equalsIgnoreCase("Mar")) {
			return 3;
		} else if (firstMSplit.equalsIgnoreCase("Apr")) {
			return 4;
		} else if (firstMSplit.equalsIgnoreCase("May")) {
			return 5;
		} else if (firstMSplit.equalsIgnoreCase("Jun")) {
			return 6;
		} else if (firstMSplit.equalsIgnoreCase("Jul")) {
			return 7;
		} else if (firstMSplit.equalsIgnoreCase("Aug")) {
			return 8;
		} else if (firstMSplit.equalsIgnoreCase("Sep")) {
			return 9;
		} else if (firstMSplit.equalsIgnoreCase("Oct")) {
			return 10;
		} else if (firstMSplit.equalsIgnoreCase("Nov")) {
			return 11;
		} else if (firstMSplit.equalsIgnoreCase("Dec")) {
			return 12;
		} else {
			return 1;
		}
	}

	public void selectData() {

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:insura.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY;");

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String address = rs.getString("address");
				float salary = rs.getFloat("salary");

				System.out.println("ID = " + id);
				System.out.println("NAME = " + name);
				System.out.println("AGE = " + age);
				System.out.println("ADDRESS = " + address);
				System.out.println("SALARY = " + salary);
				System.out.println();
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());

		}
		System.out.println("Operation done successfully");

		/*
		 * String query = "UPDATE userSettings SET coins=? WHERE user=?";
		 * PreparedStatement statement = connection.prepareStatement(query);
		 * statement.setInt(1, 10); statement.setString(2, lastUser);
		 * statement.executeUpdate();
		 */

	}

	public void insertDataSample() {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:insura.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
					+ "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " + "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " + "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
					+ "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());

		}
		System.out.println("Records created successfully");

	}

	/*
	 * public static String beanToString(Object object) throws IOException {
	 * 
	 * 
	 * for(Field field : usersRow.getClass().getDeclaredFields()) { Object
	 * objectValue = field.get(usersRow); }
	 * 
	 * 
	 * ObjectMapper objectMapper = new ObjectMapper(); StringWriter stringEmp = new
	 * StringWriter(); objectMapper.configure(SerializationFeature.INDENT_OUTPUT,
	 * true); objectMapper.writeValue(stringEmp, object); return
	 * stringEmp.toString(); }
	 */

	public static byte[] objectToByteArray(Object object) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(object);
			out.flush();
			return bos.toByteArray();

		} catch (IOException e) {
			throw e;
		} finally {
			try {
				bos.close();
			} catch (IOException ex) {
				// ignore close exception
			}
		}

		/*
		 * 
		 * 
		 * BufferedImage originalImage = ImageIO.read(new File(
		 * "resources/image/Test.png"));
		 * 
		 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 * ImageIO.write(originalImage, "jpg", baos); baos.flush(); byte[] imageInByte =
		 * baos.toByteArray();
		 * 
		 * 
		 */

	}
	
	
	public static String randomIDGenerator(int maxlength, boolean includeChar, boolean includeNum,
			boolean includeSpecChar) {
		// Connection ID generator
		final String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		final String lowerChars = upperChars.toLowerCase(Locale.ROOT);

		final String digits = "0123456789";

		final String specialChars = "@&*^&!$#_";

		final String chars;

		chars = (includeNum ? digits : "") + (includeChar ? upperChars + lowerChars : "")
				+ (includeSpecChar ? specialChars : "");

		/*
		 * if (includeNum) { chars = upper + lower + digits; } else { chars = upper +
		 * lower; }
		 */

		final Random rand = new Random();
		final StringBuilder buf = new StringBuilder();
		for (int i = 0; i < maxlength; i++) {
			buf.append(chars.charAt(rand.nextInt(chars.length())));
		}

		return buf.toString();
	}


	public static List<HashMap<String, Object>> reportGrouping(String groupByField,
			List<HashMap<String, Object>> documentList, String[] totalFooterFields, String groupHeaderkeyword,
			String groupHeaderDesc, String totalHeaderKeyword) throws RuntimeException {

		Collections.sort(documentList, (object1, object2) -> object1.get(groupByField).toString()
				.compareTo(object2.get(groupByField).toString()));

		String currentGroupByFieldName;
		Boolean addGroupToList = true;
		HashMap<String, BigDecimal> groupTotalMap = new HashMap<String, BigDecimal>();

		HashMap<String, Object> docEditInst = new HashMap<String, Object>();

		List<HashMap<String, Object>> documentPrintList = new ArrayList<>();

		for (int i = 0; i < documentList.size(); i++) {

			// Job Group Header
			currentGroupByFieldName = documentList.get(i).get(groupByField).toString();
			if (addGroupToList && documentList.get(i).get(groupByField).toString().equals(currentGroupByFieldName)) {
				docEditInst = new HashMap<String, Object>();
				docEditInst.put(groupByField,
						documentList.get(i).get(groupByField) != null ? documentList.get(i).get(groupByField).toString()
								: "");

				if (documentList.get(i).get(groupByField) != null) {
					docEditInst.put(groupHeaderkeyword, " " + documentList.get(i).get(groupByField).toString() + " - "
							+ documentList.get(i).get(groupHeaderDesc).toString());
				}

				docEditInst.put(totalHeaderKeyword, "Group Of " + currentGroupByFieldName);

				documentPrintList.add(docEditInst);
				addGroupToList = false;

			}

			// Detailed Line of MD or SD
			documentPrintList.add(documentList.get(i));

			if (groupTotalMap != null && !groupTotalMap.isEmpty()) {

				for (int g = 0; g < totalFooterFields.length; g++) {

					if (groupTotalMap.containsKey(totalFooterFields[g])) {

						for (int a = 0; a < totalFooterFields.length; a++) {

							if (totalFooterFields[a] == totalFooterFields[g]) {

								groupTotalMap.replace(totalFooterFields[a], groupTotalMap.get(totalFooterFields[a]),
										groupTotalMap.get(totalFooterFields[a])
												.add(documentList.get(i).get(totalFooterFields[a]) != null
														? (BigDecimal) documentList.get(i).get(totalFooterFields[a])
														: BigDecimal.ZERO));
							}
						}

					}

				}

			} else {

				for (int a = 0; a < totalFooterFields.length; a++) {

					groupTotalMap.put(totalFooterFields[a],
							documentList.get(i).get(totalFooterFields[a]) != null
									? (BigDecimal) documentList.get(i).get(totalFooterFields[a])
									: BigDecimal.ZERO);

				}

			}

			if (i < documentList.size() - 1) {

				// check for next Job group
				if (!documentList.get(i + 1).get(groupByField).toString().equals(currentGroupByFieldName)) {
					addGroupToList = true;

					// sum of current job
					docEditInst = new HashMap<String, Object>();
					docEditInst.put(totalHeaderKeyword, "Total Of " + currentGroupByFieldName + ": ");

					for (int a = 0; a < totalFooterFields.length; a++) {
						docEditInst.put(totalFooterFields[a], groupTotalMap.get(totalFooterFields[a]));
					}

					documentPrintList.add(docEditInst);

					groupTotalMap = new HashMap<String, BigDecimal>();

				}

			} else {

				// expected to be last total of job
				docEditInst = new HashMap<String, Object>();

				for (int a = 0; a < totalFooterFields.length; a++) {
					docEditInst.put(totalFooterFields[a], groupTotalMap.get(totalFooterFields[a]));
				}
				docEditInst.put(totalHeaderKeyword, "Total Of " + currentGroupByFieldName + ": ");
				documentPrintList.add(docEditInst);

			}

		}

		documentList = new ArrayList<>();
		documentList.addAll(documentPrintList);

		System.out.println("End of Grouping. Final printlist size :" + documentPrintList.size());
		return documentPrintList;

	}

	public static void addLog(String logtxt) {

		try (FileWriter fw = new FileWriter(Sprocket.mainSysPath + "/System/Logs/" + Sprocket.entity + "_"
				+ LocalDate.now().toString() + "_log.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw)) {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
			pw.println(LocalDate.now().toString() + " @ " + LocalTime.now().format(dtf) + " >> " + logtxt);

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	/*
	 * public void preRenderDoc(String docpath) { InputStream fs; try {
	 * 
	 * fs = new
	 * FileInputStream("C:\\APTW3\\APTW3_1000\\Procurement\\IEMSC_terms.docx");
	 * final byte[] outArray = fs.readAllBytes();
	 * 
	 * agreementContent = DefaultStreamedContent.builder().stream(() -> new
	 * ByteArrayInputStream(outArray)) .contentType("application/pdf").build();
	 * 
	 * if (agreementContent != null) {
	 * PrimeFaces.current().executeScript("PF('docViewerDlg').show();");
	 * PrimeFaces.current().ajax().update("docPanel"); }
	 * 
	 * } catch (final Exception e) { System.out.println(e.toString()); }
	 * 
	 * }
	 */

	public static String findComponentValue(UIComponent component, int target) {
		// target is current : 0 | previous : -1 | next : 1
		int index = 0;
		int targetIndex = 0;
		for (UIComponent comp : component.getParent().getChildren()) {

			String currentID = component.getId();

			if (comp.getId().equals(currentID)) {

				if (target < 0) {
					targetIndex = index - 1;
				} else if (target > 0) {
					targetIndex = index + 1;
				} else {
					targetIndex = index;
				}

				break;
			}

			index += 1;
		}

		UIComponent labelComponent = component.getParent().getChildren().get(targetIndex);

		String targetComponentClass = labelComponent.getClass().getSimpleName();

		if (targetComponentClass.equals("HtmlOutputText")) {
			HtmlOutputText opTxtComp = (HtmlOutputText) labelComponent;
			return String.valueOf(opTxtComp.getValue());
		}

		return null;

	}

	public static boolean checkIfInvalidEmail(String emailid) {

		try {
			final InternetAddress emailAddr = new InternetAddress(emailid);
			emailAddr.validate();
		} catch (final AddressException ex) {
			return true;
		}

		return false;

	}

	public static boolean checkIfInvalidNumber(String currentNumber) {
		/*
		 * String patterns =
		 * "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" +
		 * "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" +
		 * "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
		 * 
		 * String[] validPhoneNumbers = {"2055550125","202 555 0125", "(202) 555-0125",
		 * "+111 (202) 555-0125", "636 856 789", "+111 636 856 789", "636 85 67 89",
		 * "+111 636 85 67 89"};
		 */

		String patternString = "[a-z]+|[A-Z]+";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(currentNumber);
		boolean matchFound = matcher.find();
		if (matchFound) {
			return true;
		} else {
			return false;
		}

	}


}
