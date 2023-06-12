package com.edu.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.shop.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	 Page<Customer> findByUsernameContaining(String username,Pageable pageable);
	
}
