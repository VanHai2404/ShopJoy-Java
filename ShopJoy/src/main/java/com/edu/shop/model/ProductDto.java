package com.edu.shop.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	private int productId;
	private String name;
	private int quantity;
	private double untiPrice;
	private String image;
	private MultipartFile ImageFile;
	private String description;
	private double discount;
	private Date enterdDate;
	private short status;
	private Long categoryId;
	

}
