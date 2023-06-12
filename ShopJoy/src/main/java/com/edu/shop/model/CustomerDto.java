package com.edu.shop.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
   private int customerId;
   @NotEmpty
	private String username;
	@NotEmpty
	private String fullname;
	@NotEmpty
	private String email;

	private String gender;
	

	private String password;
	

	private String phone;

   private MultipartFile ImageFile;
	private String image;
	    
   @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	

	private Date registeredDate;

	private short status;
	
	

}
