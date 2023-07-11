package com.edu.shop.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	private int productId;
	@NotEmpty
	private String name;
	
	@NotNull
	@DecimalMin("1")
	private int quantity;
	@NotNull
	@DecimalMin("1000")
	private double untiPrice;
	private String image;
	private MultipartFile ImageFile;
	private String description;
	@NotEmpty
	private String size;
	private double discount;
	private Date enterdDate;
	private short status;
    @NotNull
	private Long categoryId;
	

}
