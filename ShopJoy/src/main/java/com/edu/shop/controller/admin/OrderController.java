package com.edu.shop.controller.admin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.edu.shop.domain.Order;
import com.edu.shop.domain.Product;
import com.edu.shop.model.ProductDto;
import com.edu.shop.service.OrderService;

@Controller
@RequestMapping("admin/orders")
public class OrderController {
	public static final short PENDING = 0;
	public static final short CONFIRMED = 1;
	public static final short COMPLETED = 2;
	
	@Autowired
	OrderService orderService;
	
	
	public List<Order> findOrdersByStatus(short status) {
		List<Order> orders = orderService.findAll();
	//	System.out.println("Order Đâu---------"+orders.toString());
	    List<Order> filteredOrders = new ArrayList<>();
	    
	    for (Order order : orders) {
	        if (order.getStatus() == status) {
	            filteredOrders.add(order);
	        }
	    }
	    return filteredOrders;
	}
	
	
	@PostMapping("save")
	public ModelAndView edit(ModelMap model,@RequestParam("orderId") Integer orderId,@RequestParam("inputState") Short selectedStatus) {
		System.out.println("inputState: "+selectedStatus);
		System.out.println("orderId: "+orderId);
		Optional<Order> opt = orderService.findById(orderId);
		
		if(opt.isPresent()) {		
			opt.get().setStatus(selectedStatus);
			orderService.save(opt.get());
			return new ModelAndView("redirect:/admin/orders/confirmation");		
		}
		return new ModelAndView("forward:/admin/orders/confirmation",model);	
	}
	@PostMapping("save2")
	public ModelAndView save(ModelMap model,@RequestParam("orderId") Integer orderId,@RequestParam("inputState") Short selectedStatus) {
		System.out.println("inputState: "+selectedStatus);
		System.out.println("orderId: "+orderId);
		Optional<Order> opt = orderService.findById(orderId);
		
		if(opt.isPresent()) {		
			opt.get().setStatus(selectedStatus);
			orderService.save(opt.get());
			return new ModelAndView("redirect:/admin/orders/shipping");		
		}
		return new ModelAndView("forward:/admin/orders/shipping",model);	
	}
	
	
	
	@GetMapping("confirmation")
	public String bill_confirmation(Model model){
		List<Order> orders = this.findOrdersByStatus(PENDING);
	    orders.sort(Comparator.comparing(Order::getOrderId).reversed());
		model.addAttribute("orders", orders);
		return "admin/order/bill-confirmation";
		
	}
	@GetMapping("shipping")
	public String shipping(Model model){
		List<Order> orders = this.findOrdersByStatus(CONFIRMED);
	    orders.sort(Comparator.comparing(Order::getOrderId).reversed());
		model.addAttribute("orders", orders);
		return "admin/order/bill-shipping";
		
	}
	
	@GetMapping("page")
	public String bill_confirmation(ModelMap map,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
	    int currentPage = page.orElse(0).intValue();
	    int pageSize = size.orElse(5).intValue();
	
	    Pageable pageable = PageRequest.of(currentPage, pageSize);
	    Page<Order> resultPage = orderService.findAll(pageable);
	    
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
	            map.addAttribute("pageNumbers", pageNumbers);
	        }
	    } else {
	        // Xử lý khi rePage là null, ví dụ: hiển thị thông báo lỗi
	        map.addAttribute("message", "Không tìm thấy danh mục.");
	    }
	    map.addAttribute("orderPage", resultPage);
		   return "admin/order/bill-All";

	}
	

}
