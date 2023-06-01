package com.edu.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.edu.shop.domain.Account;

public interface AccountService {

	void deleteAll();

	Account getReferenceById(String id);

	void delete(Account entity);

	Account getById(String id);

	void deleteById(String id);

	long count();

	Account getOne(String id);

	void deleteAllInBatch();

	<S extends Account> boolean exists(Example<S> example);

	void deleteAllByIdInBatch(Iterable<String> ids);

	boolean existsById(String id);

	Optional<Account> findById(String id);

	void deleteInBatch(Iterable<Account> entities);

	List<Account> findAllById(Iterable<String> ids);

	List<Account> findAll();

	<S extends Account> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends Account> S saveAndFlush(S entity);

	Page<Account> findAll(Pageable pageable);

	void flush();

	List<Account> findAll(Sort sort);

	<S extends Account> List<S> saveAll(Iterable<S> entities);

	<S extends Account> S save(S entity);

	Account login(String username, String password);

}
