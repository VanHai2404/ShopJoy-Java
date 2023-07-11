package com.edu.shop.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.shop.domain.Customer;
import com.edu.shop.domain.Order;
import com.edu.shop.domain.Product;
import com.edu.shop.service.CustomerService;
import com.edu.shop.service.OrderService;
import com.edu.shop.service.ProductService;

@Controller
@RequestMapping("admin/dashboard")
public class DashboardHomeController {
	public static final short COMPLETE = 3;
	@Autowired
	ProductService productService;
	@Autowired
	CustomerService customerService;
	@Autowired
	OrderService orderService;
	
	
	@GetMapping("")
	public String Home(Model model) {
		List<Product> list = productService.findAll();
		Integer count=list.size();
		System.out.println("Số Lượng :"+count);
		List<Customer> list2 =customerService.findAll();
		Integer count2=list2.size();
		System.out.println("Số Lượng :"+count2);
		
		List<Order> list3 = orderService.findAll();
		
		
		double totalAmount = 0.0;
	    List<Order> filteredOrders = new ArrayList<>();

		for (Order order : list3) {
			
			 if (order.getStatus() == COMPLETE) {
		            filteredOrders.add(order);
		        }
		}
		for (Order order : filteredOrders) {
			  double amount = order.getAmount();
			    totalAmount += amount;
		}

		System.out.println("Tổng số tiền của tất cả các đơn hàng: " + totalAmount);
		
       model.addAttribute("product",count);
       model.addAttribute("customer",count2);
       model.addAttribute("totalAmount",totalAmount);
		
		return "admin/index";
		
	}

}
