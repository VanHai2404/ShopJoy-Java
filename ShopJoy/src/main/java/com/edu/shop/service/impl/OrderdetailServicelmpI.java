package com.edu.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.edu.shop.domain.OrderDetail;
import com.edu.shop.repository.OrderDetailRepository;
import com.edu.shop.service.OrderdetailService;

@Service
public class OrderdetailServicelmpI implements OrderdetailService{
	
	@Autowired
	OrderDetailRepository repository;

	@Override
	public <S extends OrderDetail> S save(S entity) {
		return repository.save(entity);
	}

	@Override
	public <S extends OrderDetail> List<S> saveAll(Iterable<S> entities) {
		return repository.saveAll(entities);
	}

	@Override
	public List<OrderDetail> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	@Override
	public void flush() {
		repository.flush();
	}

	@Override
	public Page<OrderDetail> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public <S extends OrderDetail> S saveAndFlush(S entity) {
		return repository.saveAndFlush(entity);
	}

	@Override
	public List<OrderDetail> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(Iterable<OrderDetail> entities) {
		repository.deleteInBatch(entities);
	}

	@Override
	public Optional<OrderDetail> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<OrderDetail> entities) {
		repository.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return repository.existsById(id);
	}

	@Override
	public void deleteAllInBatch() {
		repository.deleteAllInBatch();
	}

	@Override
	public OrderDetail getOne(Integer id) {
		return repository.getOne(id);
	}

	@Override
	public long count() {
		return repository.count();
	}

	@Override
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public OrderDetail getById(Integer id) {
		return repository.getById(id);
	}

	@Override
	public void delete(OrderDetail entity) {
		repository.delete(entity);
	}

	@Override
	public OrderDetail getReferenceById(Integer id) {
		return repository.getReferenceById(id);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		repository.deleteAllById(ids);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}
	
	
	

}
