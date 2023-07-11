package com.edu.shop.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.edu.shop.domain.Customer;
import com.edu.shop.domain.Order;

public interface CustomerService {

	void deleteAll();

	<S extends Customer> List<S> findAll(Example<S> example, Sort sort);

	<S extends Customer> List<S> findAll(Example<S> example);

	Customer getReferenceById(Integer id);

	void delete(Customer entity);

	Customer getById(Integer id);

	void deleteById(Integer id);

	long count();

	Customer getOne(Integer id);

	void deleteAllInBatch();

	<S extends Customer> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	boolean existsById(Integer id);

	void deleteAllInBatch(Iterable<Customer> entities);

	Optional<Customer> findById(Integer id);

	<S extends Customer> Page<S> findAll(Example<S> example, Pageable pageable);

	void deleteInBatch(Iterable<Customer> entities);

	List<Customer> findAllById(Iterable<Integer> ids);

	List<Customer> findAll();

	<S extends Customer> S saveAndFlush(S entity);

	Page<Customer> findAll(Pageable pageable);

	List<Customer> findAll(Sort sort);

	<S extends Customer> Optional<S> findOne(Example<S> example);

	<S extends Customer> List<S> saveAll(Iterable<S> entities);

	<S extends Customer> S save(S entity);

	Page<Customer> findByUsernameContaining(String username, Pageable pageable);

	Customer login(String username, String password);

	Optional<Customer> findByEmail(String email);

	Optional<Customer> findByUsername(String username);

	List<Order> findAllOrdersByUsername(String username);

}
