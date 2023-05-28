package com.edu.shop.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayMent {
	private int paymentId;
	private int orderId;
	private float amount;
	private Date payDate;

}
