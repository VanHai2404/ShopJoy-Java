package com.edu.shop.controller.site;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.shop.domain.Product;
import com.edu.shop.service.ProductService;
import com.edu.shop.service.StorageService;

@Controller
@RequestMapping("home")
public class PageHomeController {
	@Autowired
	ProductService productService;
	
	@Autowired
	StorageService storageService;
	
	
	
	@GetMapping("/images/{filename:.+}")
	
	@ResponseBody
	public ResponseEntity<Resource> saveFile(@PathVariable String filename){
		Resource file =storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +file.getFilename()+"\"").body(file);
	}
	
	@RequestMapping("")
	private String home(Model model) {
		List<Product> products= productService.findAll();
		
		model.addAttribute("products",products);
		return "site/index";
		
	}
	
	@GetMapping("detail/{productId}")
	public String detail(Model model,@PathVariable("productId") Integer productId) {
		Optional<Product> optional = productService.findById(productId);
		model.addAttribute("product", optional.orElse(null));

		
		
		return "site/products/product-details";
		
	}
	
	@GetMapping("type")
	public String searchByCategory(Model model, @RequestParam("param") String param) {
		   List<Long> ids = Arrays.stream(param.split(","))
                   .map(Long::parseLong)
                   .collect(Collectors.toList());
		System.out.println("ID" +ids);
	    try {
	        List<Product> productList = productService.findByCategory_CategoryIdIn(ids);
	        model.addAttribute("products", productList);
	    } catch (Exception e) {
	    	
	    }
	    	if (ids.contains(2L)) {
	    	    model.addAttribute("message2", "ÁO THUN & POLO");
	    	}

	    	if (ids.contains(3L)) {
	    	    model.addAttribute("message2", "SHORTS & PANTS");
	    	}

	    	if (ids.contains(5L)) {
	    	    model.addAttribute("message2", "SWEATER & HOODIE");
	    	}

	    	if (ids.contains(6L)) {
	    	    model.addAttribute("message2", "PHỤ KIỆN");
	    	}

	   
	    return "site/products/product-Bycategory";
	}

	

}
