package com.edu.shop.controller.admin;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.edu.shop.domain.Account;
import com.edu.shop.domain.Customer;
import com.edu.shop.model.CustomerDto;
import com.edu.shop.service.CustomerService;
import com.edu.shop.service.StorageService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/customers")
public class CustomerController {
	
	@Autowired
    CustomerService customerService;
	@Autowired
	StorageService storageService;
	
	
	
	
	
	
	
	@GetMapping("add")
	public ModelAndView add(ModelMap map) {
	    CustomerDto dto = new CustomerDto();
	    map.addAttribute("customer", dto);
	    map.addAttribute("isNewCustomer", true); // Form được sử dụng để tạo khách hàng mới
	    return new ModelAndView("admin/customers/page-customer-form");
	}
	
	@GetMapping("/images/{filename:.+}")
	
	@ResponseBody
	public ResponseEntity<Resource> saveFile(@PathVariable String filename){
		Resource file =storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +file.getFilename()+"\"").body(file);
	}
	
	@PostMapping("saveOrUpadate")
	public ModelAndView saveOrUpadate(ModelMap model,@Valid @ModelAttribute("customer") CustomerDto dto,BindingResult result) {
		
		if(result.hasErrors()) {
		
			System.out.println("Lỗi : "+result.getAllErrors());
			model.addAttribute("error","Error data cannot be saved !");
			return new ModelAndView("admin/customers/page-customer-form");
		}
		
		Customer entity= new Customer();
		BeanUtils.copyProperties(dto, entity);
		
		if(!dto.getImageFile().isEmpty()) {
			UUID uuid =UUID.randomUUID();
			String uustring =uuid.toString();
			entity.setImage(storageService.getStoreFilename(dto.getImageFile(), uustring));
			storageService.store(dto.getImageFile(), entity.getImage());
		}
		Date currentDate = new Date();
		entity.setRegisteredDate(currentDate);
		customerService.save(entity);
		model.addAttribute("message","Customer is Save Successfully !");
		return new ModelAndView("admin/customers/page-customer-form", model);
	}
	@GetMapping("edit/{customerId}")
	public ModelAndView edit(ModelMap map, @PathVariable("customerId") Integer customerId) {
	    Optional<Customer> opt = customerService.findById(customerId);
	    
	    if (opt.isEmpty()) {
	        map.addAttribute("error", "Error: not found customer!");
	        return new ModelAndView("forward:/admin/customers/add", map);
	    }
	    CustomerDto dto = new CustomerDto();
	    BeanUtils.copyProperties(opt.get(), dto);
	    map.addAttribute("isNewCustomer", false); // Form được sử dụng để chỉnh sửa
	    map.addAttribute("customer", dto);
	    return new ModelAndView("admin/customers/page-customer-form");
	}
	@GetMapping("delete/{customerId}")
	private ModelAndView delete(ModelMap map,@PathVariable("customerId") Integer customerId) throws IOException {
		Optional<Customer> opt = customerService.findById(customerId);
		if(opt.isPresent()) {			
			if(!StringUtils.isEmpty(opt.get().getImage())) {
				storageService.delete(opt.get().getImage());	
			}
			customerService.delete(opt.get());
			map.addAttribute("message","Account delete successful !");
			
			
		}else {
			map.addAttribute("error","Account delete successful !");
		}
		return new ModelAndView("forward:/admin/customers/page");
		
	
		
	}

	
	@GetMapping("page")
	public String list(ModelMap model,@RequestParam(name = "search" ,required = false) String search,
			@PathVariable("page") Optional<Integer> page,
			@PathVariable("size") Optional<Integer> size ) {
		
		// nếu ko chọn thi mắc định
		int currentPage =page.orElse(0).intValue();
		int pageSize=size.orElse(5).intValue(); // mắc định page đâu tiên và 5 item 
		
		Pageable pageable =PageRequest.of(currentPage, pageSize, Sort.by("username")); // tạo page
		
		Page<Customer> resultPage=null;
		if(StringUtils.hasText(search)) {
			resultPage= customerService.findByUsernameContaining(search, pageable);
		}else {
			resultPage=customerService.findAll(pageable);
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
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
	    } else {
	        // Xử lý khi rePage là null, ví dụ: hiển thị thông báo lỗi
	        model.addAttribute("message", "Không tìm thấy danh mục.");
	    }
			 model.addAttribute("customerPage", resultPage);
			   	return "admin/customers/page-customer-list";
	}
	
	
	
	

}
