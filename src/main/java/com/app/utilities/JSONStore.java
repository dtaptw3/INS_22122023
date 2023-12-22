package com.app.utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.app.controller.beans.UserMB;

/*@Path("/invoice")*/
public class JSONStore {


	
	/*
	 * @GET
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 */
	public static String getDataAsJson(String weblink, HashMap<String, String> params) throws Exception {
		// set up the end point
		String rawResult = null;
		URIBuilder builder = new URIBuilder(weblink);
		builder.setParameter("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
	
		HttpGet get = new HttpGet(builder.build());

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = httpclient.execute(get);
		
		try {
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.printf("Bad response status code:%d%n", response.getStatusLine().getStatusCode());
			}

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				rawResult = EntityUtils.toString(entity, Charset.forName("utf-8"));
			}

		} finally {
			response.close();
		}

		return rawResult;

		/*
		 * JSONArray values = randomObject.getJSONArray("values");
		 * 
		 * 
		 * System.out.print(values);
		 * 
		 * System.out.printf("Date\tMaxTemp\tMinTemp\tChangeofPrecip%n"); for (int i =
		 * 0; i < values.length(); i++) { JSONObject forecastValue =
		 * values.getJSONObject(i); String datetimeString =
		 * forecastValue.getString("datetimeStr");
		 * 
		 * ZonedDateTime datetime = ZonedDateTime.parse(datetimeString,
		 * DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		 * 
		 * double maxtemp = forecastValue.getDouble("maxt"); double mintemp =
		 * forecastValue.getDouble("mint"); double pop = forecastValue.getDouble("pop");
		 * System.out.printf("%s\t%.1f\t%.1f\t%.0f%n",
		 * datetime.format(DateTimeFormatter.ISO_LOCAL_DATE), maxtemp, mintemp, pop); }
		 */

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static void postData() throws Exception {

		URIBuilder builder = new URIBuilder("https://my.gstzen.in/p/e-invoice-samples/json");
		HttpPost post = new HttpPost(builder.build());

		CloseableHttpClient httpclient = HttpClients.createDefault();
		post.addHeader("Token", "28ef3508-8450-4b98-b15c-a0af941a26cb");
		post.addHeader("Content-Type", "text/plain");

		CloseableHttpResponse response = httpclient.execute(post);

		System.out.printf("Status code:%d%n", response.getStatusLine().getStatusCode());

		//JSONObject entity = new JSONObject(response.getEntity());

		// Process the response
		// This part is up to you

		// Close the response
		response.close();

		// Close the client
		// client.close();

		final FacesContext contextF = FacesContext.getCurrentInstance();
		contextF.addMessage(null,
				new FacesMessage("Customer was found either null or invalid", "Please define a valid Customer"));
		PrimeFaces.current().ajax().update("quoteDetailsForm:payrollStat");
		return;

	}

	public static void postJSONData() throws Exception {

		try {
			URL url = new URL("http://ip:port/login");

			// Base64 b = new Base64();
			// String encoding = b.encodeAsString(new String("test1:test1").getBytes());

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			// connection.setRequestProperty ("Authorization", "Basic " + encoding);
			InputStream content = (InputStream) connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(content));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void postXData() {

	}

}
