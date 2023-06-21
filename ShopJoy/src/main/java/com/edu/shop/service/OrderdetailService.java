package com.edu.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.edu.shop.domain.OrderDetail;

public interface OrderdetailService {

	void deleteAll();

	void deleteAllById(Iterable<? extends Integer> ids);

	OrderDetail getReferenceById(Integer id);

	void delete(OrderDetail entity);

	OrderDetail getById(Integer id);

	void deleteById(Integer id);

	long count();

	OrderDetail getOne(Integer id);

	void deleteAllInBatch();

	boolean existsById(Integer id);

	void deleteAllInBatch(Iterable<OrderDetail> entities);

	Optional<OrderDetail> findById(Integer id);

	void deleteInBatch(Iterable<OrderDetail> entities);

	List<OrderDetail> findAll();

	<S extends OrderDetail> S saveAndFlush(S entity);

	Page<OrderDetail> findAll(Pageable pageable);

	void flush();

	List<OrderDetail> findAll(Sort sort);

	<S extends OrderDetail> List<S> saveAll(Iterable<S> entities);

	<S extends OrderDetail> S save(S entity);

}
