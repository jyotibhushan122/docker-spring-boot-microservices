package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.CustomerEntity;

public interface CustomerService {

	public void registerCustomer(CustomerEntity customerEntity) throws Exception;

	public Optional<CustomerEntity> getCustomerInfo(Integer mobileNumber);
}
