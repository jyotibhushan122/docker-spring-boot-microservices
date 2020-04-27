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

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

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

	@GetMapping(value = "/getCustomerDetails_1")
	@HystrixCommand(fallbackMethod = "getCustomerDetails_fallBackeTest", commandProperties = {

			/**
			 * to control the thread , to calculate error percentage on a given request
			 * threshold to do circuit breaker and checks service to be up.
			 */
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "500"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000") })

	/**
	 * NOTE: @HystrixCommand command not applicable on sub caller method. to work
	 * with sub caller method annoted with @HystrixCommand need to work with new
	 * class and @Autowired in main caller class to call that @HystrixCommand
	 * annoted service.
	 * 
	 * @param id
	 * @return
	 */
	public String getCustomerDetails_1(@RequestParam(name = "id") long id) {
		CustomerEntity entiy = webClient.build().get()

				// WAY:2
				// .uri("http://localhost:8080/getCustomer?id=" + id)
				// updating with service discovery name

				// WAY:3
				.uri("http://CUSTOMER-ORDER/getCustomer?id=" + id).retrieve().bodyToMono(CustomerEntity.class).block();
		return entiy.getCustomerName();
	}

	private String getCustomerDetails_fallBackeTest(long id) {
		return "Response from fallback";
	}
}
