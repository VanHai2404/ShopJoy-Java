package com.edu.shop.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	@Temporal(TemporalType.DATE)
	private Date orderDate;
//	@Column(nullable = false)
//	private int customerId;
	@Column(nullable = false)
	private Double amount;
	@Column(nullable = false)
	private Short status;
	
	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;
	
	@OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
	private Shipping shipping;
	
	@OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
	private PayMent payMent;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<OrderDetail> orderDetails;

	

}
