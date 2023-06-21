package com.edu.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edu.shop.domain.Customer;
import com.edu.shop.model.AdminLoginDto;
import com.edu.shop.model.UserLoginDto;
import com.edu.shop.service.CustomerService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserLoginController {
	@Autowired
	CustomerService customerService;
	@Autowired
	private HttpSession session;
	
	@GetMapping("ulogin")
	public String login(ModelMap model) {
		model.addAttribute("USER",new UserLoginDto());
		
		
		return "site/user/page-login";
		
	}
	@GetMapping("out")
	public String Out() {
		session.removeAttribute("name");
		session.removeAttribute("fullname");
	return "redirect:/ulogin";
		
	}
	
	@PostMapping("ulogin")
	public ModelAndView login(ModelMap model,@Valid @ModelAttribute("USER") UserLoginDto loginDto,BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("site/user/page-login",model);
		}
		model.addAttribute("USER",new UserLoginDto());
		
		
		Customer customer = customerService.login(loginDto.getUsername(), loginDto.getPassword());
		if(customer==null) {
			model.addAttribute("error","INvalid username or password");
			return new ModelAndView("site/user/page-login",model);
			
		}
		session.setAttribute("name", customer.getUsername());
		session.setAttribute("fullname", customer.getFullname());
		
		
//		Object rurl =session.getAttribute("redirect-uri");
//		if(rurl!=null) {
//			session.removeAttribute("redirect");
//			return new ModelAndView("redirect:"+rurl);	
//		}
		model.addAttribute("message","Login Thành cong");
		/// login thanh công 
		return new ModelAndView("redirect:/home");
	
		
	}
	
	}


