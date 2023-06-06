package com.edu.shop.domain;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "accounts")
public class Account {
	@Id
	@Column(length = 30)
	private String username;
	@Column(length = 100)
	private String fullname;
	@Column(length = 60,nullable = false)
	private String password;
	@Column(length = 100)
	private String email;
	@Column(length = 200)
	private String image;
	@Temporal(TemporalType.DATE)
	private Date birthday;
	@Column(length = 200)
	private String phone;
	@Column(length = 50)
	private String gender;
	
	
	

}
