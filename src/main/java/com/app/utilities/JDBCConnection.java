package com.app.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

public class JDBCConnection {
	
	volatile Logger log = Logger.getLogger(JDBCConnection.class);

	public static Object executeQuery(String query) {
		ResultSet rs = null;

		try {

			FileInputStream fis = new FileInputStream(new File("//C:/w3node.properties"));
			Properties prop = new Properties();
			prop.load(fis);

			Connection connection = DriverManager.getConnection(prop.get("instance1").toString(), Sprocket.USERNAME,
					Sprocket.ACCESS);
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				return rs.getObject(1);
			}

			fis.close();
			connection.close();

		} catch (Exception e) {
			e.toString(); 
		}

		return rs;

	}
}
