package com.edu.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edu.shop.domain.Account;
import com.edu.shop.model.AdminLoginDto;
import com.edu.shop.service.AccountService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AdminLoginController {
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("alogin")
	public String login(ModelMap model) {
		model.addAttribute("account", new AdminLoginDto());
		
		return "admin/accounts/pages-login";
		
	}
	
	@PostMapping("alogin")
	public ModelAndView login(ModelMap model,
			@Valid @ModelAttribute("account") AdminLoginDto dto,BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("/admin/accounts/pages-login",model);
		}
		
		Account account =accountService.login(dto.getUsername(), dto.getPassword());
		if(account==null) {
			model.addAttribute("message","INvalid username or password");
			return new ModelAndView("admin/accounts/pages-login",model);		
			}
		
		session.setAttribute("username", account.getUsername());
		
		Object rurl =session.getAttribute("redirect-uri");
		if(rurl!=null) {
			session.removeAttribute("redirect");
			return new ModelAndView("redirect:"+rurl);	
		}
		/// login thanh công 
		return new ModelAndView("redirect:/admin/dashboard");
	
		
	}
	@GetMapping("aout")
	public String out(Model model) {
		session.removeAttribute("username");
		session.removeAttribute("redirect-uri");
		model.addAttribute("account", new AdminLoginDto());
		return "redirect:/alogin";
		
	}

	
	
	

}
