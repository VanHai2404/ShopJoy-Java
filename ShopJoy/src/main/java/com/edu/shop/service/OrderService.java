package com.edu.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.edu.shop.domain.Order;

public interface OrderService {

	void deleteAll();

	void delete(Order entity);

	Order getById(Integer id);

	void deleteById(Integer id);

	long count();

	Order getOne(Integer id);

	void deleteAllInBatch();

	boolean existsById(Integer id);

	Optional<Order> findById(Integer id);

	List<Order> findAll();

	Page<Order> findAll(Pageable pageable);

	void flush();

	List<Order> findAll(Sort sort);

	<S extends Order> Optional<S> findOne(Example<S> example);

	<S extends Order> S save(S entity);

}
