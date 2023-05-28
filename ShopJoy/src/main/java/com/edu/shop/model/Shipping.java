package com.edu.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipping {
	private int shippingId;
	private int orderId;
	private  String address;
	private String City;
	private short status;
	private String Country;

}
