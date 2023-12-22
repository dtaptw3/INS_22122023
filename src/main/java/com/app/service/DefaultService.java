package com.app.service;

import java.util.List;

import com.app.model.orm.dbo.wrapper.CountryDBO;
import com.app.model.orm.dbo.wrapper.InsuranceProviderDBO;
import com.app.model.orm.dbo.wrapper.ModuleDBO;
import com.app.model.orm.dbo.wrapper.NetworkProviderDBO;
import com.app.model.orm.dbo.wrapper.UserDBO;

public interface DefaultService {

	void registerDefaults() throws Exception;

	List<InsuranceProviderDBO> fetchInsuranceProviders() throws Exception;

	List<UserDBO> fetchUsers() throws Exception;

	List<CountryDBO> fetchCountries() throws Exception;

	List<NetworkProviderDBO> fetchNetworkProviders() throws Exception;

	List<ModuleDBO> fetchModules() throws Exception;

}
