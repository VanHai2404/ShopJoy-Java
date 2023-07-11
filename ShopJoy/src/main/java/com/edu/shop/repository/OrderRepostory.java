package com.edu.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.shop.domain.Customer;
import com.edu.shop.domain.Order;


@Repository
public interface OrderRepostory extends JpaRepository<Order, Integer>{
	
    List<Order> findByCustomer(Customer customer);

	

}
