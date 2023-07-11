package com.edu.shop.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("contact")
public class PageContactController {
	
	@RequestMapping("")
	public String contact(Model model) {
		return "site/contact/page-contact";
		
	}
	@PostMapping("")
	public String demo(Model model) {
		model.addAttribute("message","Thôn Tin Đã đc gửi !!");
		
		return "site/contact/page-contact";

		
	}

}
