package com.app.utilities;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.net.ssl.SSLParameters;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

public class SqlConnection {

	private static SqlSessionFactory W3SessionFactory = null;

	private static String resource = "com/aptw3/mybatis/mybatis-config.xml";

	public static SqlSessionFactory getW3Session() throws IOException {

		final Logger log = Logger.getLogger(SqlConnection.class);

		try {

			final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

			if (new LocalDate().compareTo(Sprocket.SYSENDDATE) > 0) { 

				System.out.println("Error 89E42Tx");
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				externalContext.redirect(externalContext.getRequestContextPath() + "/index.html?faces-redirect=true");
				return null;
			}

			if (W3SessionFactory == null) {
				// String resource = "com/aptw3/mybatis/mybatis-config.xml";
				Reader reader = Resources.getResourceAsReader(resource);

				SSLParameters sslParameters = new SSLParameters();
				sslParameters.setProtocols(new String[] { "TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3" });

				System.setProperty("javax.net.ssl.keyStore", "clientKeyStore.key");
				System.setProperty("javax.net.ssl.keyStorePassword", "qwerty");
				System.setProperty("javax.net.ssl.trustStore", "clientTrustStore.key");
				System.setProperty("javax.net.ssl.trustStorePassword", "qwerty");

				final Properties props = System.getProperties();
				props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());

				if (Sprocket.entity.equals("APTW3_9999")) {
					W3SessionFactory = new SqlSessionFactoryBuilder().build(reader, "W3_Default");
					log.info("W3_Default Resource File, Sucessfully Read! ");
				} else {
					W3SessionFactory = new SqlSessionFactoryBuilder().build(reader, props);
					log.info("W3_Default Resource File, Sucessfully Read! ");
				}

			}
		} catch (final Exception e) {
			log.info(e.toString());
		}

		return W3SessionFactory;
	}

	public static Connection getLiteConnection() throws Exception {

		Connection connection = null;

		try {

			connection = DriverManager.getConnection("jdbc:sqlite:C:/insura/insura.db");
			// Class.forName("org.sqlite.JDBC");
			// connection = DriverManager.getConnection("jdbc:sqlite:insura.db");
			// connection.setAutoCommit(false);
			System.out.println("access to db >> 1 ");

		} catch (SQLException e) {
			System.out.println("access to db >> 0 ");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return connection;

	}

}