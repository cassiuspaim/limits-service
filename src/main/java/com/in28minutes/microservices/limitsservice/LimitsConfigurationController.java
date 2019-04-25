package com.in28minutes.microservices.limitsservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.microservices.limitsservice.bean.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitsConfigurationController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitsFromConfigurations() {
		logger.info("{}", configuration);
		return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum()); 
	}
	
	@GetMapping("/fault-tolerance-example")
	@HystrixCommand(fallbackMethod="fallbackRetrieveConfiguration")
	public LimitConfiguration retrieveConfigurations() {
		throw new RuntimeException("Not available");
	}
	
	public LimitConfiguration fallbackRetrieveConfiguration() {
		return new LimitConfiguration(999, 9);
	}
	
}
