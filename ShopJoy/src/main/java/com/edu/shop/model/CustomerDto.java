package com.edu.shop.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
   private int customerId;
    @NotEmpty(message = "Vui lòng nhập username")
	private String username;
	@NotEmpty(message = "Vui lòng nhập Họ Tên")
	private String fullname;
	@NotEmpty
	@Email(message = "Vui lòng nhập email")
	private String email;
    
	private String gender;
	
	@NotEmpty(message = "Vui lòng nhập mật khẩu")
	private String password;
	

	private String phone;

   private MultipartFile ImageFile;
	private String image;

   @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	

	private Date registeredDate;

	private short status;
	
	

}
