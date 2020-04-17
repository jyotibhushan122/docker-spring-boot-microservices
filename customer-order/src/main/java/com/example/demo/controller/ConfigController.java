package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
public class ConfigController {

	@Value("${app.message: jscbdsjkb}")
	private String greetingMsg;
	/**
	 * reading DB configuration object from properties file
	 */
	/*
	 * @Value("${db.value}") private String dbValue;
	 * 
	 *//**
		 * reading DB configuration object from properties file as a SPEL
		 * 
		 * @return
		 */
			  //@Value("#{${db.value}}") private Map<String, String> dbValue_1;
			  
			  @Autowired private DbConfiguration dbConfiguration;
			 

	@GetMapping(value = "/message")
	private String getMessage() {
		return "DATA: " + greetingMsg+ " "+dbConfiguration.getHost();

	}
}
