package com.edu.shop.domain;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shippings")
public class Shipping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int shippingId;
//	@Column(nullable = false)
//	private int orderId;
	@Column(columnDefinition = "nvarchar(200)")
	private  String fullname;
	
	@Column(columnDefinition = "nvarchar(200) not null")
	private  String address;
	
	@Column(columnDefinition = "nvarchar(200) not null")
	private String City;
	
	@Column(nullable = false)
	private short status;
	
	@Column(columnDefinition = "nvarchar(200) not null")
	private String Country;
	
	private String phone;
	
	@Column(columnDefinition = "nvarchar(200)")
	private  String Shipping_Modes;
	
	@OneToOne
	@JoinColumn(name = "orderId")
	private Order order;
	

}
