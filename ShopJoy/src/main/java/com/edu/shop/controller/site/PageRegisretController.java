package com.edu.shop.controller.site;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edu.shop.domain.Customer;
import com.edu.shop.model.CustomerDto;
import com.edu.shop.service.CustomerService;

import jakarta.validation.Valid;

@Controller
public class PageRegisretController {
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("regisret")
	public String form(ModelMap map) {
		map.addAttribute("customer",new CustomerDto());
		return "site/user/page-Regisret";
		
	}
	@PostMapping("regisret")
	public ModelAndView regisret(ModelMap map,@Valid @ModelAttribute("customer") CustomerDto dto,BindingResult result) {
		
		if(result.hasErrors()) {
			map.addAttribute("error","lỗi dữ liệu: nhập vào");
			return new ModelAndView("site/user/page-Regisret",map);

		}
		Optional<Customer> optional=customerService.findByEmail(dto.getEmail());
		if(optional.isPresent()) {
			map.addAttribute("error","Email đã tồn tại !!!");
			return new ModelAndView("site/user/page-Regisret",map);
		}
		Customer customer = new Customer();
		BeanUtils.copyProperties(dto, customer);
		if(customer !=null) {
			map.addAttribute("message","Email đã tồn tại !!!");
			Date currentDate = new Date();
			customer.setRegisteredDate(currentDate);
			customerService.save(customer);
		}
		return new ModelAndView("site/user/page-Regisret");
		
	}

}
