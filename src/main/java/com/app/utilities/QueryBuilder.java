package com.app.utilities;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import com.app.model.orm.dbo.wrapper.ExternalProperty;
import com.app.model.orm.dbo.wrapper.PrimaryKey;
import com.app.model.orm.dbo.wrapper.TSNO;

/*
Behold mortals! here's my first crack
at creating an ORM like thing which could technically 
work for most relational(not sure of non rel) databases 
other than the one currently being used.
WARNING: This is pure EVIL!!!
*/

public class QueryBuilder {

	private static String currentQuery = "";

	private int blockCount = 0;
	private LinkedList<String> blockOrder;

	private static Connection connection = null;
	private static Statement statement = null;

	public QueryBuilder() throws Exception {
		currentQuery = "";

		if (connection != null) {
			connection.close();
		}

		if (statement != null) {
			statement.close();
		}

		try {

			if((new Date()).compareTo(Sprocket.SYSENDDATE.toDate()) > 0) {
				AppUtility.addLog("usage expiry");
				System.exit(0);
			}
			
			connection = DriverManager.getConnection("jdbc:sqlite:C:/insura/insura.db");

		} catch (Exception e) {
			System.out.println("access to db failed ");
			throw e;
		}
	}

	public ResultSet execute() throws SQLException {

		ResultSet result = null;
		// Statement statement = null;

		try {

			statement = connection.createStatement();
			result = statement.executeQuery(currentQuery + ";");

			currentQuery = "";
			blockCount = 0;

		} catch (Exception e) {
			throw e;
		}

		return result;

	}

	public <Elephant> List<Elephant> executeFor(Class<Elephant> currentClass) throws SQLException {

		// List<Map<String, Object>> dataList = new ArrayList<>();
		List<Elephant> objectList = new ArrayList<>();
		ResultSet rs = execute(); // Execute QUERY

		try {

			Field[] fields = currentClass.getDeclaredFields();
			// Map<String, Object> map = new LinkedHashMap<>();
			while (rs.next()) {

				Elephant fieldObject = currentClass.getDeclaredConstructor().newInstance();

				int index = 1;
				for (Field field : fields) {
					// System.out.print("result set has data");
					if (!field.isAnnotationPresent(ExternalProperty.class)) {
						// map.put(field.getName(), rs.getString(index));
						if (rs.getString(index) != null & !rs.getString(index).toString().equals("null")) {

							field.setAccessible(true);

							if (field.getType().equals(Integer.class)) {
								field.set(fieldObject, rs.getInt(index));
							} else if (field.getType().equals(Double.class)) {
								field.set(fieldObject, Double.valueOf(rs.getString(index).toString()));
							} else if (field.getType().equals(byte[].class)) { //
								field.set(fieldObject, AppUtility.objectToByteArray(rs.getString(index)));
							} else if (field.getType().equals(BigDecimal.class)) { //
								field.set(fieldObject, new BigDecimal(rs.getString(index).toString()));
							} else if (field.getType().equals(Date.class)) {
								Date formattedDate = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
										.parse(rs.getString(index));
								field.set(fieldObject, formattedDate);
							} else {
								field.set(fieldObject, rs.getString(index));
							}

							field.setAccessible(false);

						}

					}
					index += 1;
				}

				objectList.add(fieldObject);

			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return objectList;

	}

	public String build() {
		String returnpart = currentQuery;
		currentQuery = "";
		blockCount = 0;
		return returnpart;
	}

	public void run() throws SQLException {

		Statement statement = null;
		try {

			statement = connection.createStatement();
			statement.execute(currentQuery);

			currentQuery = "";
			blockCount = 0;

		} catch (Exception e) {
			statement.close();
			connection.close();
			throw e;
		}
	}

	public QueryBuilder(String query) {
		QueryBuilder.currentQuery = QueryBuilder.currentQuery + query;
	}

	public void createTable(String query) throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute(query);
		statement.close();
		connection.close();

		statement = null;
		connection = null;
		currentQuery = "";
		blockCount = 0;

	}

	public QueryBuilder createTable(Object currentObject)
			throws IOException, IllegalArgumentException, IllegalAccessException {

		String createQuery = "CREATE TABLE IF NOT EXISTS " + getEntityName(currentObject) + " (";

		// Field[] fields = currentObject.getClass().getDeclaredFields();

		List<Field> fieldList = Arrays.asList(currentObject.getClass().getDeclaredFields());

		List<Field> fieldListFinal = fieldList.stream().filter(field -> {

			field.setAccessible(true);
			return !field.isAnnotationPresent(ExternalProperty.class);

		}).collect(Collectors.toList());

		int index = 0;
		for (Field field : fieldListFinal) {

			index += 1;
			field.setAccessible(true);

			if (!field.isAnnotationPresent(ExternalProperty.class)) {

				if (field.getType().equals(Integer.class)) {
					createQuery = createQuery + field.getName() + " INTEGER";
				} else if (field.getType().equals(Double.class)) {
					createQuery = createQuery + field.getName() + " DOUBLE";
				} else if (field.getType().equals(byte[].class)) { //
					createQuery = createQuery + field.getName() + " BLOB";
				} else if (field.getType().equals(BigDecimal.class)) { //
					createQuery = createQuery + field.getName() + " DECIMAL(10,5)";
				} else if (field.getType().equals(Date.class)) { //
					createQuery = createQuery + field.getName() + " DATETIME";
				}

				else {
					createQuery = createQuery + field.getName() + " TEXT";
				}

				if (field.isAnnotationPresent(PrimaryKey.class) || field.isAnnotationPresent(TSNO.class)) {
					createQuery = createQuery + " PRIMARY KEY ";
				}

				if (field.isAnnotationPresent(TSNO.class)) {
					createQuery = createQuery + " AUTOINCREMENT ";
				}

				if (field.isAnnotationPresent(TSNO.class) && !field.isAnnotationPresent(PrimaryKey.class)) {
					createQuery = createQuery + " NOT NULL ";
				}

				if (index == fieldListFinal.size()) {
					createQuery = createQuery + "); ";
				} else {
					createQuery = createQuery + ", ";
				}

			}

		}

		// System.out.println(createQuery);

		return new QueryBuilder(createQuery);
	}

	/*
	 * sending a blank or class param indicates all fields/columns are to be
	 * selected.
	 * 
	 * 
	 */
	public QueryBuilder select(Object currentObject, String... fieldSequence) {
		// queryType = "select";

		if (blockOrder == null) {
			blockOrder = new LinkedList<>();
		}

		blockOrder.add("select");

		String fields = Arrays.toString(fieldSequence).replace("[", "").replace("]", "");

		if (fields == null || fields.isBlank() || fields.isEmpty()) {
			fields = "*";
		} else {
			fields = String.join(",", fields);
		}

		return new QueryBuilder("SELECT " + fields + " FROM " + getEntityName(currentObject));
	}

	// currenObject's defined values will be params for where clause
	public QueryBuilder where(Object currentObject) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

		if (blockOrder == null) {
			blockOrder = new LinkedList<>();
		}
		blockOrder.add("where");

		Set<String> whereSet = generateColumnValueSet(currentObject);

		String whereBlock = "";
		if (whereSet.size() > 0) {
			whereBlock = " WHERE "
					+ Arrays.toString(whereSet.toArray()).replace("[", "").replace("]", "").replace(",", " and ");
		}

		// return new QueryBuilder("SELECT " + fields + " FROM " +
		// getEntityName(currentObject) + whereBlock + ";");
		return new QueryBuilder(whereBlock + ";");
	}

	/*
	 * takes entity and spits out it's non null key value fields
	 */
	public Set<String> generateColumnValueSet(Object currentObject)
			throws IllegalArgumentException, IllegalAccessException {

		final Field[] objfields = currentObject.getClass().getDeclaredFields();
		Set<String> whereList = new LinkedHashSet<>();

		for (Field field : objfields) {

			if (!field.isAnnotationPresent(ExternalProperty.class)) {
				field.setAccessible(true);

				if (field.get(currentObject) != null) {

					if (field.getType().equals(Integer.class)) {
						whereList.add(field.getName() + " = " + field.get(currentObject));
					} else {
						whereList.add(field.getName() + " = " + "'" + field.get(currentObject) + "'");
					}

				}

			}

		}
		return whereList;

	}

	public QueryBuilder count(String fields, String tablename) {
		return new QueryBuilder("SELECT COUNT (" + fields + ")" + " FROM " + tablename);
	}

	public QueryBuilder where(String field, Object value) {
		return new QueryBuilder(" WHERE " + field + " = " + "'" + String.valueOf(value) + "'");
	}

	public QueryBuilder and(String field, Object value) {

		blockCount += 1;

		if (currentQuery.contains("WHERE")) {
			return new QueryBuilder(" AND " + field + " = " + "'" + String.valueOf(value) + "'");
		} else {
			return where(field, value);
		}

	}

	public QueryBuilder insert(Object currentObject)
			throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException,
			SecurityException, InstantiationException, InvocationTargetException, NoSuchMethodException {

		blockOrder = new LinkedList<>();
		blockOrder.add("insert");

		List<String> objectFieldList = new ArrayList<>();
		List<String> objectFieldValueList = new ArrayList<>();

		Field[] fields = currentObject.getClass().getDeclaredFields();

		for (Field field : fields) {
			if (!field.isAnnotationPresent(ExternalProperty.class) && !field.isAnnotationPresent(TSNO.class)) {
				objectFieldList.add(field.getName());
				field.setAccessible(true);

				if (field.getType().equals(byte[].class)) {
					byte[] imgData = AppUtility.objectToByteArray(field.get(currentObject));
					objectFieldValueList.add("\"" + Base64.getEncoder().encodeToString(imgData) + "\"");
				} else {
					objectFieldValueList.add("\"" + String.valueOf(field.get(currentObject)) + "\"");
				}

				field.setAccessible(false);

			}
		}
		String fieldNames = Arrays.toString(objectFieldList.toArray()).replace("[", "(").replace("]", ")");

		String fieldValues = Arrays.toString(objectFieldValueList.toArray()).replace("[", "(").replace("]", ")");

		return new QueryBuilder(" INSERT INTO " + getEntityName(currentObject) + fieldNames + "VALUES " + fieldValues);

	}

	public boolean isEntity(Object currentObject) {
		if (currentObject.getClass().isAnnotationPresent(Entity.class)) {
			return true;
		} else {
			throw new RuntimeException(
					"Current object is not a valid entity >> : " + currentObject.getClass().getName());
		}
	}

	private String getEntityName(Object currentObject) {
		if (currentObject.getClass().isAnnotationPresent(Entity.class)) {
			Entity et = currentObject.getClass().getAnnotation(Entity.class);
			return et.name();
		} else {
			throw new RuntimeException(
					"Current object is not a valid entity >> : " + currentObject.getClass().getName());
		}
	}

	public QueryBuilder update(Object currentObject)
			throws IOException, IllegalArgumentException, IllegalAccessException {

		blockOrder = new LinkedList<>();
		blockOrder.add("update");

		Field[] fields = currentObject.getClass().getDeclaredFields();
		Map<String, Object> currentFieldMap = new LinkedHashMap<String, Object>();

		for (Field field : fields) {
			if (!field.isAnnotationPresent(ExternalProperty.class) && !field.isAnnotationPresent(TSNO.class)) {
				field.setAccessible(true);

				if (field.getType().equals(byte[].class)) {
					byte[] imgData = AppUtility.objectToByteArray(field.get(currentObject));
					currentFieldMap.put(field.getName(), "\"" + Base64.getEncoder().encodeToString(imgData) + "\"");
				} else {
					currentFieldMap.put(field.getName(), "\"" + String.valueOf(field.get(currentObject)) + "\"");
				}

			}

		}

		String fieldValueSet = currentFieldMap.toString().replace(":", " = ").replace("{", "").replace("}", "");

		return new QueryBuilder(" UPDATE " + getEntityName(currentObject) + " SET " + fieldValueSet);

	}

	public QueryBuilder fields(String... fields) {

		String fieldsString = String.join(",", fields);
		return new QueryBuilder("(" + fieldsString + ")");
	}

	public QueryBuilder values(Object... values) {

		String valuesString = String.join(",", Arrays.stream(values).toArray(String[]::new));
		return new QueryBuilder(" values (" + valuesString + ")");
	}

	public QueryBuilder update(String tablename, String fieldsWithValues) {

		return new QueryBuilder(" UPDATE " + tablename + " SET " + fieldsWithValues);

	}

	public QueryBuilder update(String tablename) {
		return new QueryBuilder(" UPDATE  " + tablename + " SET ");
	}

	public QueryBuilder delete(String tablename) {
		return new QueryBuilder(" DELETE from  " + tablename);
	}

	public QueryBuilder set(String field, Object value) {
		blockCount += 1;
		return new QueryBuilder((blockCount > 1 ? " , " : " ") + field + " = " + String.valueOf(value));
	}

}
