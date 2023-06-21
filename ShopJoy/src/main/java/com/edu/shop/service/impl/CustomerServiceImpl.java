package com.edu.shop.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.edu.shop.domain.Customer;
import com.edu.shop.domain.Order;
import com.edu.shop.repository.CustomerRepository;
import com.edu.shop.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerRepository customerRepository;
	
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
      
	@Override
	public Optional<Customer> findByUsername(String username) {
		return customerRepository.findByUsername(username);
	}


	@Override
	public Optional<Customer> findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
	


	@Override
	public List<Order> findAllOrdersByUsername(String username) {
		return customerRepository.findAllOrdersByUsername(username);
	}


	@Override
	public Page<Customer> findByUsernameContaining(String username, Pageable pageable) {
		return customerRepository.findByUsernameContaining(username, pageable);
	}
	
	
	@Override
	public Customer login(String username, String password) {
		 Optional<Customer> optExist = customerRepository.findByEmail(username);
		if(optExist.isPresent()&& bCryptPasswordEncoder.matches(password, optExist.get().getPassword())) {		
			optExist.get().setPassword("");
			return optExist.get();
		}
		
		return null;
	}

	@Override
	public <S extends Customer> S save(S entity) {
		Optional<Customer> optExist =findById(entity.getCustomerId());
		System.out.println("Người Tim :" +optExist);
		
		if(!optExist.isEmpty()) {
			if(StringUtils.isEmpty(entity.getPassword())) {
				entity.setPassword(optExist.get().getPassword());
				System.out.println("Đây là update :---------------");
			}else {
				entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));					
			}
			if(StringUtils.isEmpty(entity.getImage())) {
				System.out.println("Update Lấy Lại image----------");
				entity.setImage(optExist.get().getImage());
			}		
		}else {
			System.out.println("Đây là Them Mới :---------------");
			entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
               
		}
		return customerRepository.save(entity);
	}
	

	@Override
	public <S extends Customer> List<S> saveAll(Iterable<S> entities) {
		return customerRepository.saveAll(entities);
	}

	@Override
	public <S extends Customer> Optional<S> findOne(Example<S> example) {
		return customerRepository.findOne(example);
	}

	@Override
	public List<Customer> findAll(Sort sort) {
		return customerRepository.findAll(sort);
	}

	@Override
	public Page<Customer> findAll(Pageable pageable) {
		return customerRepository.findAll(pageable);
	}

	@Override
	public <S extends Customer> S saveAndFlush(S entity) {
		return customerRepository.saveAndFlush(entity);
	}

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public List<Customer> findAllById(Iterable<Integer> ids) {
		return customerRepository.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<Customer> entities) {
		customerRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends Customer> Page<S> findAll(Example<S> example, Pageable pageable) {
		return customerRepository.findAll(example, pageable);
	}

	@Override
	public Optional<Customer> findById(Integer id) {
		return customerRepository.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<Customer> entities) {
		customerRepository.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return customerRepository.existsById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		customerRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends Customer> long count(Example<S> example) {
		return customerRepository.count(example);
	}

	@Override
	public void deleteAllInBatch() {
		customerRepository.deleteAllInBatch();
	}

	@Override
	public Customer getOne(Integer id) {
		return customerRepository.getOne(id);
	}

	@Override
	public long count() {
		return customerRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		customerRepository.deleteById(id);
	}

	@Override
	public Customer getById(Integer id) {
		return customerRepository.getById(id);
	}

	@Override
	public void delete(Customer entity) {
		customerRepository.delete(entity);
	}

	@Override
	public Customer getReferenceById(Integer id) {
		return customerRepository.getReferenceById(id);
	}

	@Override
	public <S extends Customer> List<S> findAll(Example<S> example) {
		return customerRepository.findAll(example);
	}

	@Override
	public <S extends Customer> List<S> findAll(Example<S> example, Sort sort) {
		return customerRepository.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		customerRepository.deleteAll();
	}
	
	
	

}
