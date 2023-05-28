package com.edu.shop.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "customers")
public class Customer  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int customerId;
	@Column(columnDefinition = "nvarchar(50) not null")
	private String name;
	@Column(columnDefinition = "nvarchar(100) not null")
	private String email;
	@Column( length = 20, nullable = false)
	private String password;
	@Column(length = 20)
	private String phone;
	@Temporal(TemporalType.DATE)
	private Date registeredDate;
	@Column(nullable = false)
	private short status;
	
	
	@OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
	private Set<Order> orders;

}
