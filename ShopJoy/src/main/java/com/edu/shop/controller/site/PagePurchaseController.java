package com.edu.shop.controller.site;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.shop.domain.Customer;
import com.edu.shop.domain.Order;
import com.edu.shop.repository.OrderRepostory;
import com.edu.shop.service.CustomerService;
import com.edu.shop.service.OrderService;
import com.edu.shop.service.SessionService;
@Controller
@RequestMapping("user")
public class PagePurchaseController {
	public static final short PENDING = 0;
	public static final short CONFIRMED = 1;
	public static final short COMPLETED = 2;
	public static final short COMPLETE = 3;
	@Autowired
	CustomerService customerService;
	@Autowired
	SessionService service;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderRepostory orderRepostory;

	public List<Order> findOrdersByUsernameAndStatus(String username, short status) {
	//	List<Order> orders = customerService.findAllOrdersByUsername(username);
		Optional<Customer> optional = customerService.findByUsername(username);
		List<Order> orders = orderRepostory.findByCustomer(optional.get());
	//	System.out.println("Order Đâu---------"+orders.toString());
	    List<Order> filteredOrders = new ArrayList<>();
	    for (Order order : orders) {
	        if (order.getStatus() == status) {
	            filteredOrders.add(order);
	        }
	    }
	    return filteredOrders;
	}
	@GetMapping("/purchase/{status}")
	public String showCustomerOrdersByStatus(@PathVariable short status, Model model) {
		String username = service.get("name");
		List<Order> orders = this.findOrdersByUsernameAndStatus(username, status);
	//    System.out.println("orders---------------"+orders.toString());
		
             System.out.println("status----------"+status);
	        switch (status) {
	            case 0:
	            	  model.addAttribute("isitem",true);
	                  model.addAttribute("messages2","Đơn hàng đăng chờ xác nhận");
	                  model.addAttribute("messages","CHỜ XÁC NHẬN");
	                break;
	            case 1:
	            	  model.addAttribute("isitem",false);
	                  model.addAttribute("messages2","Đơn hàng Đã được xác nhận");
	                  model.addAttribute("messages","ĐANG GIAO HÀNG");
	                  break;
	            case 2:
	            	  model.addAttribute("isitem",true);
	                  model.addAttribute("messages2","các đơn hàng bạn đã hủy");
	                  model.addAttribute("messages","ĐƠN HÀNG HỦY");
	                break;
	            case 3:
	            	  model.addAttribute("isitem",false);
	            	  model.addAttribute("isitem2",3);
	                  model.addAttribute("messages2","cáC đơn hàng hoàn thành");
	                  model.addAttribute("messages","ĐƠN HÀNG HOÀN THÀNH");
	                break;    
	            default:
	                System.out.println("Chạy vào khối default");
	                break;
	        }
	    
	    model.addAttribute("orders", orders);
		return "site/shopping/Wait-for-confirmation";
	}

	
	
	@GetMapping("purchase/update/{orderId}")
	public String confirmation(Model model,@PathVariable("orderId") Integer orderId) {
		Optional<Order> optional = orderService.findById(orderId);
		if(optional.isPresent()) {
			optional.get().setStatus(COMPLETE);
			orderService.save(optional.get());
		}   
		return "redirect:/user/purchase/3";
	}

	
}
