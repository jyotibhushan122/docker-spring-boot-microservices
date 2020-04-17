package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CustomerEntity;
import com.example.demo.repo.CustomerRepo;
import com.example.demo.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public void registerCustomer(CustomerEntity customerEntity) throws Exception {
		customerRepo.save(customerEntity);

	}

	@Override
	public Optional<CustomerEntity> getCustomerInfo(Integer mobileNumber) {
		return customerRepo.findByMobileNumber(mobileNumber);

	}

	@Override
	public List<CustomerEntity> getAllCustomer() {

		return customerRepo.findAll();
	}

}
