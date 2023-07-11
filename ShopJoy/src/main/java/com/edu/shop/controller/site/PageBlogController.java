package com.edu.shop.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("blog")
public class PageBlogController {
	@RequestMapping("")
	public String blog(Model model) {
		
		return "site/blog/pages-blog";
	}

}
