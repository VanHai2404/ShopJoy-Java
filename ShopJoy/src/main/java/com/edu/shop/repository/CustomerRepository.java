package com.edu.shop.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edu.shop.domain.Customer;
import com.edu.shop.domain.Order;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	 Optional<Customer> findByUsername(String username);
	 Optional<Customer> findByEmail(String email);
	 Page<Customer> findByUsernameContaining(String username,Pageable pageable);
	 
	//@Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.orderDetails WHERE o.customer.username = :username")
	@Query("SELECT o FROM Order o JOIN FETCH o.orderDetails od JOIN o.customer c WHERE c.username = :username")
	 List<Order> findAllOrdersByUsername(@Param("username") String username);


}
