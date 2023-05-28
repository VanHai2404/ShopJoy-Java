package com.edu.shop.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderDetails")
public class OrderDetail implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderDetailId;
//	@Column(nullable = false)
//	private int orderId;
	@Column(nullable = false)
	private int quantity;
	@Column(nullable = false)
	private double unitPrice;
	
	
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "orderId")
	private Order order;

}
