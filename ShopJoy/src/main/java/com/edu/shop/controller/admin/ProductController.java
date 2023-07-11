package com.edu.shop.controller.admin;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.edu.shop.domain.Category;
import com.edu.shop.domain.Product;
import com.edu.shop.model.CategoryDto;
import com.edu.shop.model.ProductDto;
import com.edu.shop.repository.ProductRepository;
import com.edu.shop.service.CategoryService;
import com.edu.shop.service.ProductService;
import com.edu.shop.service.StorageService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/products")
public class ProductController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	StorageService storageService;
	
	@ModelAttribute("categories")
	List<CategoryDto> getCategoryDtos(){//trả về một danh sách các đối tượng CategoryDto,Đối tượng CategoryDto là một đối tượng dùng để truyền dữ liệu từ lớp dịch vụ (service) đến giao diện người dùng.
		return categoryService.findAll().stream().map(item->{//danh sách các danh mục được chuyển đổi thành một luồng (stream) và áp dụng phương thức map() lên từng phần tử trong danh sách. Phương thức map() này chuyển đổi từng đối tượng danh mục thành một đối tượng CategoryDto.
			CategoryDto dto = new CategoryDto();
			BeanUtils.copyProperties(item,dto);
			return dto;
		}).toList();//danh sách các đối tượng CategoryDto mới được thu thập lại thành một danh sách bằng cách sử dụng phương thức toList() của luồng (stream) và trả về cho giao diện người dùng.
	}
	@GetMapping("/images/{filename:.+}")
	
	@ResponseBody
	public ResponseEntity<Resource> saveFile(@PathVariable String filename){
		Resource file =storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +file.getFilename()+"\"").body(file);
	}

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("product",new ProductDto());
	    model.addAttribute("isNewCustomer", true); // Form được sử dụng để tạo khách hàng mới

		return "admin/products/pages-product";
        
	}

	@GetMapping("edit/{productId}")
	public ModelAndView edit(ModelMap model,@PathVariable("productId") Integer productId) {
		Optional<Product> opt = productService.findById(productId);
		ProductDto dto = new ProductDto();
		if(opt.isPresent()) {
			Product entity=opt.get();
			BeanUtils.copyProperties(entity, dto);
			model.addAttribute("product",dto);
			return new ModelAndView("admin/products/pages-product");
			
		}
	    model.addAttribute("isNewCustomer", false); // Form được sử dụng để tạo khách hàng mới

		return new ModelAndView("forward:/admin/products",model);	
	}
	
	@GetMapping("delete/{productId}")
	public ModelAndView delete(ModelMap model,@PathVariable("productId") Integer productId) throws IOException {
		Optional<Product> opt = productService.findById(productId);
		
		if(opt.isPresent()) {
			if(!StringUtils.isEmpty(opt.get().getImage())) {
				storageService.delete(opt.get().getImage());
				System.out.println("Imge -----------"+opt.get().getImage());
			}
			productService.delete(opt.get());
			model.addAttribute("message","Product is delete !");
		}else {
			model.addAttribute("message","Product is not Found !");
			
		}
		return new ModelAndView("forward:/admin/products/add");
	}
	
	
	@PostMapping("saveOrUpdate")
	public  ModelAndView saveOrUpdate(ModelMap model,@Valid @ModelAttribute("product") ProductDto dto,BindingResult result) {
		// kiếm tra dữ liệu nhập vào 
		if(result.hasErrors()) {
			System.out.println("ko co ảnh ");
			model.addAttribute("error","Lỗi Dữ liệu nhập vào !!");
			return new ModelAndView("admin/products/pages-product");
		}
		Product entity = new Product();
		BeanUtils.copyProperties(dto, entity);// copy dto bỏ vô entity
		Category category = new Category();
		category.setCategoryId(dto.getCategoryId());
		entity.setCategory(category);
//		if(dto.getImageFile().isEmpty()) {
//			model.addAttribute("message","Error images is Null ");
//			return new ModelAndView("admin/products/pages-product");
//		}
		if(!dto.getImageFile().isEmpty()) {
			UUID uuid =UUID.randomUUID();
			String uutring =uuid.toString();
			entity.setImage(storageService.getStoreFilename(dto.getImageFile(), uutring));
			storageService.store(dto.getImageFile(), entity.getImage());
			// Tạo đối tượng Date để lưu trữ ngày hiện tại
			Date currentDate = new Date();

			// Thiết lập giá trị ngày hiện tại cho thuộc tính enterDate của đối tượng entity
			entity.setEnterdDate(currentDate);
	
		}
		
		productService.save(entity);
		model.addAttribute("message","Product is save !");
		
		
		return new ModelAndView("admin/products/pages-product");
	}
	@GetMapping("page")
	public String list(ModelMap map,@RequestParam(name = "search" ,required = false) String search,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
	    int currentPage = page.orElse(0).intValue();
	    int pageSize = size.orElse(5).intValue();
	
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

	            if (totalPages > 5) { // 
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
	   	return "admin/products/pages-product-list";
	}
	
	
	
	@GetMapping("list")
	public String listProduct() {
		return "admin/products/pages-product-list";
	}


    
}
