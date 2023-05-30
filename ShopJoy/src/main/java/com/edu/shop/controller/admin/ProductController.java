package com.edu.shop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/product")
public class ProductController {
	
	@GetMapping("")
	public String add() {
		return "admin/products/pages-product";
        
	}
	
	@GetMapping("list")
	public String listProduct() {
		return "admin/products/pages-product-list";
	}


    
}
