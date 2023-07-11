package com.edu.shop.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("about")
public class PageAboutController {
	
	@RequestMapping("")
	public String about(Model model) {
		return "site/about/page-about";
	}

}
