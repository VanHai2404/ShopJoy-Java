package com.edu.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.shop.domain.OrderDetail;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{

}
