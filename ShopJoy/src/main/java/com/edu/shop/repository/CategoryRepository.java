package com.edu.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.shop.domain.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
   List<Category> findByNameContaining(String Name);
   Page<Category> findByNameContaining(String Name,Pageable pageable);
}
