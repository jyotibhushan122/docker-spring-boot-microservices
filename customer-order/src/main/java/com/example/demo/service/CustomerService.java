package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.CustomerEntity;

public interface CustomerService {

	public void registerCustomer(CustomerEntity customerEntity) throws Exception;

	public Optional<CustomerEntity> getCustomerInfo(Long id);

	public List<CustomerEntity> getAllCustomer();
}
