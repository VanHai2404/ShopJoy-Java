package com.edu.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.edu.shop.domain.Account;
import com.edu.shop.repository.AccountReposttory;
import com.edu.shop.service.AccountService;

@Service
public class AccountServiceImpl  implements AccountService{
	
	@Autowired
	AccountReposttory accountReposttory;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	@Override
	public Account login(String username, String password) {
		
		Optional<Account> optExist =findById(username);
		if(optExist.isPresent()&& bCryptPasswordEncoder.matches(password, optExist.get().getPassword())) {
	
			optExist.get().setPassword("");
			return optExist.get() ;
		}
		
		return null;
		
	}
	

	@Override
	public <S extends Account> S save(S entity) {
		Optional<Account> optExist =findById(entity.getUsername());
		if(optExist.isEmpty()) {
			if(StringUtils.isEmpty(entity.getPassword())) {
				entity.setPassword(optExist.get().getPassword());
			}else {
				entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));					
			}
		}else {
			entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
               
		}
		return accountReposttory.save(entity);
	}

	@Override
	public <S extends Account> List<S> saveAll(Iterable<S> entities) {
		return accountReposttory.saveAll(entities);
	}

	@Override
	public List<Account> findAll(Sort sort) {
		return accountReposttory.findAll(sort);
	}

	@Override
	public void flush() {
		accountReposttory.flush();
	}

	@Override
	public Page<Account> findAll(Pageable pageable) {
		return accountReposttory.findAll(pageable);
	}

	@Override
	public <S extends Account> S saveAndFlush(S entity) {
		return accountReposttory.saveAndFlush(entity);
	}

	@Override
	public <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities) {
		return accountReposttory.saveAllAndFlush(entities);
	}

	@Override
	public List<Account> findAll() {
		return accountReposttory.findAll();
	}

	@Override
	public List<Account> findAllById(Iterable<String> ids) {
		return accountReposttory.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<Account> entities) {
		accountReposttory.deleteInBatch(entities);
	}

	@Override
	public Optional<Account> findById(String id) {
		return accountReposttory.findById(id);
	}

	@Override
	public boolean existsById(String id) {
		return accountReposttory.existsById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<String> ids) {
		accountReposttory.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends Account> boolean exists(Example<S> example) {
		return accountReposttory.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		accountReposttory.deleteAllInBatch();
	}

	@Override
	public Account getOne(String id) {
		return accountReposttory.getOne(id);
	}

	@Override
	public long count() {
		return accountReposttory.count();
	}

	@Override
	public void deleteById(String id) {
		accountReposttory.deleteById(id);
	}

	@Override
	public Account getById(String id) {
		return accountReposttory.getById(id);
	}

	@Override
	public void delete(Account entity) {
		accountReposttory.delete(entity);
	}

	@Override
	public Account getReferenceById(String id) {
		return accountReposttory.getReferenceById(id);
	}

	@Override
	public void deleteAll() {
		accountReposttory.deleteAll();
	}
	
	

}
