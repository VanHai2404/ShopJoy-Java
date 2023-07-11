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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.edu.shop.domain.Account;
import com.edu.shop.model.AccountDto;
import com.edu.shop.service.AccountService;
import com.edu.shop.service.StorageService;

import jakarta.validation.Valid;



@Controller
@RequestMapping("admin/accounts")
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	StorageService storageService;

	
	
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("account", new AccountDto());
		return "admin/accounts/pages-register";
		
	}
	

	
	@GetMapping("/images/{filename:.+}")
	
	@ResponseBody
	public ResponseEntity<Resource> saveFile(@PathVariable String filename){
		Resource file =storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +file.getFilename()+"\"").body(file);
	}
	@GetMapping("form")
	public String formEdit(Model model) {
		model.addAttribute("account", new AccountDto());
		return "admin/accounts/pages-account";
		
	}
	
	@PostMapping("saveOrUpadate")
	public ModelAndView saveorUpdate(ModelMap model,@Valid @ModelAttribute("account") AccountDto accountDto,BindingResult result) {
		if(result.hasErrors()) {
		    model.addAttribute("errors:", result.getAllErrors());
		    System.out.println("lỗi : "+result.getAllErrors());
			return  new ModelAndView("admin/accounts/pages-account");
		}
		Account entity = new Account();
		BeanUtils.copyProperties(accountDto, entity); // copy DTO bỏ vô entity 
		
		if (!accountDto.getImageFile().isEmpty()) {
			UUID uuid= UUID.randomUUID();
			String uu =uuid.toString();
			entity.setImage(storageService.getStoreFilename(accountDto.getImageFile(), uu));
			storageService.store(accountDto.getImageFile(), entity.getImage());
			
			
		}
		accountService.save(entity);
		model.addAttribute("message","Accont is Save !");
		return new ModelAndView("admin/accounts/pages-account", model);
	}
	@GetMapping("delete/{username}")
	public ModelAndView delete(ModelMap model,@PathVariable("username") String username) throws IOException {
		
		Optional<Account> Opt =accountService.findById(username);
		
		if(Opt.isPresent()) {
			// xóa ảnh trong thự mục đã lưu
			if(!StringUtils.isEmpty(Opt.get().getImage())) {
				storageService.delete(Opt.get().getImage());
			}
			
			accountService.delete(Opt.get());
			model.addAttribute("message","Account delete successful !");
		}else {
			model.addAttribute("error","Account deletion failed");
			
		}
		return new ModelAndView("forward:/admin/accounts/page");

	}
	@GetMapping("edit/{username}")
	public ModelAndView edit(ModelMap model,@PathVariable("username") String Username) {
		Optional<Account> account=accountService.findById(Username); // Tìm account theo user
		System.out.println("Account đc Tìm :" +account);
		
		AccountDto dto = new AccountDto();
		
		
	 if(account.isPresent()) {
		 Account entity =account.get();
		 BeanUtils.copyProperties(entity, dto); // copy entity bỏ vô bto
		 model.addAttribute("account",dto);
		 System.out.println("edit ----:"+dto);
		 return new ModelAndView("admin/accounts/pages-account");
	 }
	 System.out.println("Lỗi : Ko Tim Thấy ");
	return new ModelAndView("forward:/admin/accounts/form",model);		
	}
	@GetMapping("page")
	public String list(ModelMap model,@RequestParam(name = "search" ,required = false) String search,
			@PathVariable("page") Optional<Integer> page,
			@PathVariable("size") Optional<Integer> size ) {
		
		// nếu ko chọn thi mắc định
		int currentPage =page.orElse(0).intValue();
		int pageSize=size.orElse(5).intValue(); // mắc định page đâu tiên và 5 item 
		
		Pageable pageable =PageRequest.of(currentPage, pageSize, Sort.by("username")); // tạo page
		
		Page<Account> resultPage=null;
		if(StringUtils.hasText(search)) {
			resultPage= accountService.findByUsernameContaining(search, pageable);
		}else {
			resultPage=accountService.findAll(pageable);
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
		
	
		
		
			 model.addAttribute("accountPage", resultPage);
			   	return "admin/accounts/pages-account-list";
	}
	
	@GetMapping("profile")
	public String profile(ModelMap map) {
		return "admin/accounts/users-profile";
	}
	
	
	
	}
