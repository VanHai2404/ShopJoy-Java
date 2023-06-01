package com.edu.shop.controller.admin;

import java.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edu.shop.domain.Account;
import com.edu.shop.model.AccountDto;
import com.edu.shop.service.AccountService;

import jakarta.validation.Valid;



@Controller
@RequestMapping("admin/accounts")
public class AccountController {
	
	@Autowired
	AccountService accountService;

	
	
	@GetMapping("add")
	public String form(Model model) {
		model.addAttribute("account", new AccountDto());
		return "admin/accounts/pages-register";
		
	}
	@PostMapping("save")
	public ModelAndView saveorUpdate(ModelMap model,@Valid @ModelAttribute("account") AccountDto accountDto,BindingResult result) {
		if(result.hasErrors()) {
			return  new ModelAndView("admin/accounts/add");
		}
		Account entity = new Account();
		BeanUtils.copyProperties(accountDto, entity); // copy DTO bỏ vô entity 
		accountService.save(entity);
		model.addAttribute("message","Category is Save !");
		return new ModelAndView("forward:/admin/categories", model);
	}

}
