package com.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class CustomerDeatails {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private WebClient.Builder webClient;

	@Autowired
	private DiscoveryClient discoveryClient;

	@GetMapping(value = "/getCustomerEntity")
	public String getCustomerDetails(@RequestParam(name = "id") long id) {

		// WAY:1
		/*
		 * CustomerEntity entiy =
		 * restTemplate.getForObject("http://localhost:8080/getCustomer?id=" + id,
		 * CustomerEntity.class);
		 */

		/**
		 * for Asynch. call
		 */
		CustomerEntity entiy = webClient.build().get()

				// WAY:2
				// .uri("http://localhost:8080/getCustomer?id=" + id)
				// updating with service discovery name

				// WAY:3
				.uri("http://CUSTOMER-ORDER/getCustomer?id=" + id).retrieve().bodyToMono(CustomerEntity.class).block();

		// WAY:4
		List<ServiceInstance> instance = getServiceInstance("CUSTOMER-ORDER");

		String host = instance.stream().findFirst().get().getHost();

		int port = instance.stream().findFirst().get().getPort();
		CustomerEntity cusEntity = restTemplate.getForObject("http://" + host + ":" + port + "/getCustomer?id=" + id,
				CustomerEntity.class);

		return entiy.getCustomerName() + " Found" + " data from discovery Client :" + cusEntity.getMobileNumber();
	}

	private List<ServiceInstance> getServiceInstance(String serviceName) {

		return discoveryClient.getInstances(serviceName);
	}
}
