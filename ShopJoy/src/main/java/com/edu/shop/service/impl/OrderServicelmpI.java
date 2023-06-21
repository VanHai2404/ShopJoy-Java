package com.edu.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.edu.shop.domain.Order;
import com.edu.shop.repository.OrderRepostory;
import com.edu.shop.service.OrderService;

@Service
public class OrderServicelmpI implements OrderService{
	
	@Autowired
	OrderRepostory repostory;

	@Override
	public <S extends Order> S save(S entity) {
		return repostory.save(entity);
	}

	@Override
	public <S extends Order> Optional<S> findOne(Example<S> example) {
		return repostory.findOne(example);
	}

	@Override
	public List<Order> findAll(Sort sort) {
		return repostory.findAll(sort);
	}

	@Override
	public void flush() {
		repostory.flush();
	}

	@Override
	public Page<Order> findAll(Pageable pageable) {
		return repostory.findAll(pageable);
	}

	@Override
	public List<Order> findAll() {
		return repostory.findAll();
	}

	@Override
	public Optional<Order> findById(Integer id) {
		return repostory.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return repostory.existsById(id);
	}

	@Override
	public void deleteAllInBatch() {
		repostory.deleteAllInBatch();
	}

	@Override
	public Order getOne(Integer id) {
		return repostory.getOne(id);
	}

	@Override
	public long count() {
		return repostory.count();
	}

	@Override
	public void deleteById(Integer id) {
		repostory.deleteById(id);
	}

	@Override
	public Order getById(Integer id) {
		return repostory.getById(id);
	}

	@Override
	public void delete(Order entity) {
		repostory.delete(entity);
	}

	@Override
	public void deleteAll() {
		repostory.deleteAll();
	}
	
	

}
