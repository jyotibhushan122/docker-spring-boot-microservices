package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CustomerEntity;
import com.example.demo.service.CustomerService;

@RestController
@CrossOrigin
public class CustomerResource {
	@Autowired
	private CustomerService customerService;

	@PostMapping(value = "/create-Cutomer")
	public String createStudent(@RequestBody CustomerEntity customerEntity) {
		try {
			customerService.registerCustomer(customerEntity);
			return "Customer Created";
		} catch (Exception e) {
			return "Customer not created" + e.getMessage();
		}
	}

	@GetMapping(value = "/getCustomer")
	public CustomerEntity getCustomer(@RequestParam(name = "id") Long id) {
		try {
			Optional<CustomerEntity> entity = customerService.getCustomerInfo(id);

			return (entity.isPresent()) ? entity.get() : null;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@GetMapping(value = "/getAllCustomer")
	public List<CustomerEntity> getAllCustomer() {
		return customerService.getAllCustomer();
	}
}
