package com.edu.shop.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "products")
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	@Column(columnDefinition = "nvarchar(100) not null")
	private String name;
	@Column(nullable = false)
	private int quantity;
	@Column(nullable = false)
	private double untiPrice;
	@Column(length = 200)
	private String image;
	@Column(columnDefinition = "nvarchar(1000) not null")
	private String description;
	@Column(columnDefinition = "nvarchar(100)")
	private String size;
	@Column(nullable = true)
	private double discount;
	@Temporal(TemporalType.DATE)
	private Date enterdDate;
	@Column(nullable = true)
	private short status;
	
	
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;
	
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
	private Set<OrderDetail> orderDetails;
	
	

}
