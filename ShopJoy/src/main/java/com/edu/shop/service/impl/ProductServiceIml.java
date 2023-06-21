package com.edu.shop.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.edu.shop.domain.Product;
import com.edu.shop.repository.ProductRepository;
import com.edu.shop.service.ProductService;

@Service
public class ProductServiceIml implements ProductService{
	@Autowired
	private ProductRepository productRepository;

	@Override
	public <S extends Product> S save(S entity) {
		
		Optional<Product> optExist =findById(entity.getProductId());
		System.out.println("Người Tim :" +optExist);
		
		if(!optExist.isEmpty()) {
			if(StringUtils.isEmpty(entity.getImage())) {
				System.out.println("Update Lấy Lại image----------");
				entity.setImage(optExist.get().getImage());
			}
			entity.setEnterdDate(optExist.get().getEnterdDate());
		}
		
		return productRepository.save(entity);
	}
	
	

	@Override
	public List<Product> findByNameContaining(String Name) {
		return productRepository.findByNameContaining(Name);
	}



	@Override
	public Page<Product> findByNameContaining(String Name, Pageable pageable) {
		return productRepository.findByNameContaining(Name, pageable);
	}
	



	@Override
	public List<Product> findByCategory_CategoryIdIn(List<Long> categoryIds) {
		return productRepository.findByCategory_CategoryIdIn(categoryIds);
	}



	@Override
	public List<Product> findAll(Sort sort) {
		return productRepository.findAll(sort);
	}

	@Override
	public void flush() {
		productRepository.flush();
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public <S extends Product> S saveAndFlush(S entity) {
		return productRepository.saveAndFlush(entity);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public void deleteInBatch(Iterable<Product> entities) {
		productRepository.deleteInBatch(entities);
	}

	@Override
	public Optional<Product> findById(Integer id) {
		return productRepository.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<Product> entities) {
		productRepository.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return productRepository.existsById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		productRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends Product> boolean exists(Example<S> example) {
		return productRepository.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		productRepository.deleteAllInBatch();
	}

	@Override
	public Product getOne(Integer id) {
		return productRepository.getOne(id);
	}

	@Override
	public <S extends Product, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return productRepository.findBy(example, queryFunction);
	}

	@Override
	public long count() {
		return productRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		productRepository.deleteById(id);
	}

	@Override
	public Product getById(Integer id) {
		return productRepository.getById(id);
	}

	@Override
	public void delete(Product entity) {
		productRepository.delete(entity);
	}

	@Override
	public Product getReferenceById(Integer id) {
		return productRepository.getReferenceById(id);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		productRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAll(Iterable<? extends Product> entities) {
		productRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		productRepository.deleteAll();
	}
	

}
