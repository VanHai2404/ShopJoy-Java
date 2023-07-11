package com.edu.shop.controller.site;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.shop.domain.Product;
import com.edu.shop.service.ProductService;
import com.edu.shop.service.StorageService;

@Controller
@RequestMapping("product")
public class PageProductController {
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
	public String list(ModelMap map,@RequestParam(name = "search" ,required = false) String search,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
	    int currentPage = page.orElse(0).intValue();
	    int pageSize = size.orElse(4).intValue();
	
	    Pageable pageable = PageRequest.of(currentPage, pageSize,Sort.by("productId").descending());
	    Page<Product> resultPage = null;
	    if(StringUtils.hasText(search)) {
	    	resultPage =productService.findByNameContaining(search, pageable);
	    	map.addAttribute("search",search);
	    }else {
	    	resultPage = productService.findAll(pageable);
	    }
	    if (resultPage != null) {
	        int totalPages = resultPage.getTotalPages();
	        
	        if (totalPages > 0) {
	            int start = Math.max(1, currentPage - 2);
	            int end = Math.min(currentPage + 2, totalPages);

	            if (totalPages > 5) {
	                if (end == totalPages)
	                    start = end - 5;
	                else if (start == 1)
	                    end = start + 5;
	            }
       
	            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
	                    .boxed()
	                    .collect(Collectors.toList());
	            map.addAttribute("pageNumbers", pageNumbers);
	        }
	    } else {
	        // Xử lý khi rePage là null, ví dụ: hiển thị thông báo lỗi
	        map.addAttribute("message", "Không tìm thấy danh mục.");
	    }
	    map.addAttribute("productPage", resultPage);
	   	return "site/products/product-list";
	}

}
