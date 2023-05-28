package com.edu.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.edu.shop.domain.Category;

public interface CategoryService {

	void deleteAll();

	void delete(Category entity);

	Category getById(Long id);

	void deleteById(Long id);

	long count();

	void deleteAllInBatch();

	<S extends Category> boolean exists(Example<S> example);

	boolean existsById(Long id);

	void deleteAllInBatch(Iterable<Category> entities);

	Optional<Category> findById(Long id);

	void deleteInBatch(Iterable<Category> entities);

	List<Category> findAllById(Iterable<Long> ids);

	List<Category> findAll();

	<S extends Category> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends Category> S saveAndFlush(S entity);

	Page<Category> findAll(Pageable pageable);

	void flush();

	List<Category> findAll(Sort sort);

	<S extends Category> List<S> saveAll(Iterable<S> entities);

	<S extends Category> S save(S entity);

}
