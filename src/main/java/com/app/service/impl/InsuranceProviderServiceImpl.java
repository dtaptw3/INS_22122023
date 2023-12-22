package com.app.service.impl;

import javax.enterprise.context.RequestScoped;

import org.apache.log4j.Logger;

import com.app.service.InsuranceProviderService;

@RequestScoped
public class InsuranceProviderServiceImpl implements InsuranceProviderService {
	
	volatile Logger log = Logger.getLogger(DefaultServiceImpl.class);

}
