package com.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.RequestScoped;

import org.apache.log4j.Logger;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

import com.app.model.orm.dbo.wrapper.BenefitsDBO;
import com.app.model.orm.dbo.wrapper.CountryDBO;
import com.app.model.orm.dbo.wrapper.InsurancePlanDBO;
import com.app.model.orm.dbo.wrapper.InsuranceProviderDBO;
import com.app.model.orm.dbo.wrapper.MemberDBO;
import com.app.model.orm.dbo.wrapper.ModuleDBO;
import com.app.model.orm.dbo.wrapper.NetworkProviderDBO;
import com.app.model.orm.dbo.wrapper.QuoteDBO;
import com.app.model.orm.dbo.wrapper.UserDBO;
import com.app.service.DefaultService;
import com.app.utilities.JSONStore;
import com.app.utilities.QueryBuilder;
import com.owlike.genson.Genson;

@RequestScoped
final class DefaultServiceImpl implements DefaultService {

	volatile Logger log = Logger.getLogger(DefaultServiceImpl.class);

	public final void registerDefaults() throws SQLException, Exception {

		try {

			new QueryBuilder().createTable(new UserDBO()).run();
			System.out.println("Users Table created");
			registerDefaultUsers();

			new QueryBuilder().createTable(new CountryDBO()).run();
			System.out.println("Countries Table created");
			registerDefaultCountries();

			new QueryBuilder().createTable(new ModuleDBO()).run();
			System.out.println("Module Table created");
			registerDefaultModules();

			new QueryBuilder().createTable(new InsuranceProviderDBO()).run();
			System.out.println("InsuranceProviders Table created");
			registerDefaultInsuranceProviders();

			new QueryBuilder().createTable(new InsurancePlanDBO()).run();
			System.out.println("InsurancePlans Table created");

			new QueryBuilder().createTable(new BenefitsDBO()).run();
			System.out.println("Benefits Table created");

			new QueryBuilder().createTable(new MemberDBO()).run(); 
			System.out.println("Members Table created");

			new QueryBuilder().createTable(new QuoteDBO()).run();
			System.out.println("Quotes Table registered");

		} catch (Exception e) {
			throw e;
		}

	}

	public void registerDefaultModules() {
		List<ModuleDBO> defaultModuleList = new ArrayList<>();
		defaultModuleList.add(new ModuleDBO(801, "Dashboard", "Dashboard", 0, "1", new Date(), 1, 1));
		defaultModuleList.add(new ModuleDBO(802, "Quotes", "Quotes", 0, "1", new Date(), 1, 1));
		defaultModuleList
				.add(new ModuleDBO(803, "InsuranceProviders", "Insurance Providers", 0, "1", new Date(), 1, 1));
		defaultModuleList.add(new ModuleDBO(804, "InsurancePlans", "Insurance Plans", 0, "1", new Date(), 1, 1));
		defaultModuleList.add(new ModuleDBO(805, "Reports", "Reports", 0, "1", new Date(), 1, 1));
		defaultModuleList.add(new ModuleDBO(900, "Settings", "Settings", 0, "1", new Date(), 1, 1));
		defaultModuleList.add(new ModuleDBO(901, "Privacy", "Privacy", 0, "1", new Date(), 1, 1));

		try {

			for (ModuleDBO currentRow : defaultModuleList) {

				List<ModuleDBO> moduleInst = (List<ModuleDBO>) new QueryBuilder().select(currentRow)
						.where("code", currentRow.getCode()).executeFor(ModuleDBO.class);

				if (moduleInst != null && !moduleInst.isEmpty()) {
					new QueryBuilder().update(currentRow).where(currentRow).run();
				} else {
					new QueryBuilder().insert(currentRow).run();
				}
			}

			System.out.println("Default Modules added");

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public void registerDefaultInsuranceProviders() throws SQLException, Exception {

		List<InsuranceProviderDBO> defaultInsuranceProviderList = new ArrayList<>();

		defaultInsuranceProviderList.add(new InsuranceProviderDBO("Allianz", null, null, null, null, null, "Y"));
		defaultInsuranceProviderList.add(new InsuranceProviderDBO("Cigna", null, null, null, null, null, "Y"));
		defaultInsuranceProviderList
				.add(new InsuranceProviderDBO("Orient(Health Plus)", null, null, null, null, null, "Y"));
		defaultInsuranceProviderList.add(new InsuranceProviderDBO("Bupa", null, null, null, null, null, "Y"));

		try {

			for (InsuranceProviderDBO currentRow : defaultInsuranceProviderList) {

				List<InsuranceProviderDBO> ipInst = (List<InsuranceProviderDBO>) new QueryBuilder().select(currentRow)
						.where(currentRow).executeFor(InsuranceProviderDBO.class);

				if (ipInst != null && !ipInst.isEmpty()) {
					new QueryBuilder().update(currentRow).where(currentRow).run();
				} else {
					new QueryBuilder().insert(currentRow).run();
				}

				if (currentRow.getLogo() == null) {
					currentRow.setLogo("insura".getBytes());
				}

			}

			System.out.println("Default providers added");

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	@Override
	public List<InsuranceProviderDBO> fetchInsuranceProviders() throws SQLException, Exception {
		return (List<InsuranceProviderDBO>) new QueryBuilder().select(new InsuranceProviderDBO())
				.executeFor(InsuranceProviderDBO.class);
	}

	public void registerDefaultUsers() throws SQLException, Exception {
		List<UserDBO> users = new ArrayList<>();
		users.add(new UserDBO(51, "sabu", "sabu2022", "Sabu", "Y", null, null, null));
		users.add(new UserDBO(52, "alfred", "sabu2022", "Alfred", "Y", null, null, null));
		users.add(new UserDBO(53, "jack", "sabu2022", "Jack", "Y", null, null, null));
		users.add(new UserDBO(54, "ben", "sabu2022", "Ben", "Y", null, null, null));
		users.add(new UserDBO(55, "mark", "sabu2022", "Mark", "Y", null, null, null));
		users.add(new UserDBO(66, "Dennis", "d12345", "Dennis", "Y", null, null, null));

		try {

			for (UserDBO currentRow : users) {

				List<UserDBO> userInst = (List<UserDBO>) new QueryBuilder().select(currentRow).where(currentRow)
						.executeFor(UserDBO.class);

				if (userInst != null && !userInst.isEmpty()) {
					currentRow.setSessionID(null);
					new QueryBuilder().update(currentRow).where(currentRow).run();
				} else {
					// System.out.println("No data");
					new QueryBuilder().insert(currentRow).run();
				}
			}
			log.info("default users added");

		} catch (Exception e) {
			log.info(e.toString());
		}

		// return fetchUsers();

	}

	@Override
	public List<UserDBO> fetchUsers() throws SQLException, Exception {
		return (List<UserDBO>) new QueryBuilder().select(new UserDBO()).executeFor(UserDBO.class);
	}

	public void registerDefaultCountries() throws Exception {

		List<CountryDBO> countryList = loadCountriesFromAPI();
		int exeIndex = 0;
		try {

			for (CountryDBO currentRow : countryList) {

				/*
				 * if (exeIndex > 121) { log.info(">>>> at index " + exeIndex); }
				 */

				List<CountryDBO> countryInst = (List<CountryDBO>) new QueryBuilder().select(currentRow)
						.where(currentRow).executeFor(CountryDBO.class);

				if (countryInst != null && !countryInst.isEmpty()) {
					new QueryBuilder().update(currentRow).where(currentRow).run();
				} else {
					// System.out.println("No data");
					new QueryBuilder().insert(currentRow).run();
				}

				exeIndex += 1;

			}

			log.info("Default countries added");

		} catch (Exception e) {
			log.info(e.toString());
			log.info(">>>> Error after " + exeIndex);
		}

		// return fetchCountries();
	}

	@Override
	public List<CountryDBO> fetchCountries() throws Exception {

		return (List<CountryDBO>) new QueryBuilder().select(new CountryDBO()).executeFor(CountryDBO.class);

	}

	public List<CountryDBO> loadCountriesFromAPI() {

		List<CountryDBO> countryList = new ArrayList<>();
		try {
			JSONObject jsonObject = null;
			JSONArray jsonArray = null;
			String rawResult = JSONStore.getDataAsJson("https://restcountries.com/v2/all", null);
			// JSONStore.getDataAsJson("https://api.first.org/data/v1/countries", null);

			if (rawResult.startsWith("[")) {
				jsonArray = new JSONArray(rawResult);

				countryList = new ArrayList<>();
				if (jsonArray != null) {

					List<Object> jsonDataList = jsonArray.toList();

					for (Object jsonObjectRow : jsonDataList) {

						HashMap<String, String> currentObject = (HashMap) jsonObjectRow;

						CountryDBO countryInst = new CountryDBO();
						countryInst.setId(currentObject.get("alpha3Code"));
						countryInst.setName(currentObject.get("name").contains("(")
								? currentObject.get("name").substring(0, currentObject.get("name").indexOf("("))
								: currentObject.get("name"));

						countryInst.setName(countryInst.getName().replace("'", ""));

						Genson genson = new Genson();

						if (currentObject.get("name").equalsIgnoreCase("india")) {
							String codes = genson.serialize(currentObject.get("callingCodes"));
						}
						String codes = genson.serialize(currentObject.get("callingCodes"));
						countryInst.setCountrycode(codes.replace("[", "+").replace("]", "").replace("\"", ""));

						countryList.add(countryInst);

					}

				}

			} else {
				jsonObject = new JSONObject(rawResult);

				if (jsonObject != null) {

					JSONObject countryData = jsonObject.getJSONObject("data");

					Genson genson = new Genson();
					HashMap<String, Object> countryMap = genson.deserialize(countryData.toString(), HashMap.class);

					if (countryMap != null) {

						countryList = new ArrayList<>();
						for (Entry<String, Object> countryRow : countryMap.entrySet()) {

							Map<String, String> objVal = (HashMap<String, String>) countryRow.getValue();
							CountryDBO countryInst = new CountryDBO();
							countryInst.setId(countryRow.getKey());
							countryInst.setName(objVal.get("country"));

							countryList.add(countryInst);
						}

					}

				}

			}

			// PrimeFaces.current().ajax().update("countryForm");
			log.info("Countries loaded from API endpoint");

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return countryList;

	}

	@Override
	public List<NetworkProviderDBO> fetchNetworkProviders() throws Exception {
		return (List<NetworkProviderDBO>) new QueryBuilder().select(new NetworkProviderDBO())
				.executeFor(NetworkProviderDBO.class);
	}

	@Override
	public List<ModuleDBO> fetchModules() throws Exception {
		return (List<ModuleDBO>) new QueryBuilder().select(new ModuleDBO()).executeFor(ModuleDBO.class);
	}

}
