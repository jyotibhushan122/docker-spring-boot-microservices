package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CustomerEntity;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, Long> {

	Optional<CustomerEntity> findByMobileNumber(Integer mobileNumber);
	
	@Query(value = "Select customerName from customer_entity where mobile_Number= ?",nativeQuery = true )
	Optional<CustomerEntity> findByMobileNumber_test(Integer mobileNumber);

}
