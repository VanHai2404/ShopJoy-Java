package com.edu.shop.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	private int customerId;
	private String name;
	private String password;
	private String phone;
	private Date registeredDate;
	private short status;

}
