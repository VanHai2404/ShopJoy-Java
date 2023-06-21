package com.edu.shop.controller.site;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.edu.shop.domain.Customer;
import com.edu.shop.domain.Order;
import com.edu.shop.domain.OrderDetail;
import com.edu.shop.domain.Product;
import com.edu.shop.model.CartItem;
import com.edu.shop.service.CustomerService;
import com.edu.shop.service.OrderService;
import com.edu.shop.service.OrderdetailService;
import com.edu.shop.service.ProductService;
import com.edu.shop.service.SessionService;
import com.edu.shop.service.ShoppingCartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("shopping")
public class ShoppingCartControler {
	public static final short PENDING = 0;
	public static final short CONFIRMED = 1;
	public static final short COMPLETED = 2;
	public static Map<Integer, OrderDetail> ORDER_DETAILS= new HashMap<>();
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderdetailService orderdetailService;
	@Autowired 
	OrderService orderService;

	@Autowired
	SessionService service;
	
	@Autowired 
	CustomerService customerService;

	@Autowired
	ShoppingCartService cartService;

	@GetMapping("cart")
	public String list(Model model) {
		Collection<CartItem> cartItems = cartService.getCartItems();
		model.addAttribute("count", cartService.getCont());
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("tolat", cartService.getAmont());
		
		model.addAttribute("No0fItems", cartService.getCont());

		System.out.println("Cart---------------" + cartItems);
		return "site/shopping/shopping-cart";

	}

	@GetMapping("add")
	public ModelAndView add(ModelMap model,@RequestParam("productId") Integer productId, @RequestParam("size") String size,
			@RequestParam("quantity") Integer quantity) {
		Optional<Product> product = productService.findById(productId);
		System.out.println("Sản Phẩm : " + product);
		if (product != null) {
			CartItem item = new CartItem();
			try {
				BeanUtils.copyProperties(product.get(), item);
			} catch (Exception e) {
				System.out.println("Lỗi khi sao chép thuộc tính: " + e.getMessage());
				e.printStackTrace();
			}
			if(quantity <= product.get().getQuantity()) {
				item.setQuantity(quantity);
			}else {
				model.addAttribute("error","SỐ LƯỢNG KO HỢP LỆ");
				return new ModelAndView("forward:/home",model);

			}
			item.setSize(size);
			Collection<CartItem> cartItems = cartService.getCartItems();
			System.out.println("Cart---------------" + cartItems);
			cartService.add(item);
			   
			service.set("cartItems", cartItems);
			service.set("tolat", cartService.getAmont());
			service.set("No0fItems", cartService.getCont());
			//
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProduct(product.get());
			orderDetail.setQuantity(quantity);
			orderDetail.setUnitPrice(product.get().getUntiPrice());
			ORDER_DETAILS.put(product.get().getProductId(), orderDetail);
			
			
		   

		}
		return new ModelAndView("redirect:/home");

	}

	@PostMapping("update")
	public String update(HttpServletRequest request, @RequestParam("productId") Integer productId,
			@RequestParam("quantity") Integer quantity) {

		cartService.update(productId, quantity);
		
		service.set("tolat", cartService.getAmont());
		String referer = request.getHeader("Referer");
		if (referer != null) {
			return "redirect:" + referer;
		}
		// Hóa Đơn Chi tiết
		OrderDetail item=ORDER_DETAILS.get(productId);
		item.setQuantity(quantity);
		if (item.getQuantity() <= 0) {
			ORDER_DETAILS.remove(productId);

		}
		return "redirect:/shoppingCart/cart";

	}

	@GetMapping("remove/{productId}")
	public String remove(HttpServletRequest request, @PathVariable("productId") Integer productId) {
		cartService.remove(productId);
		ORDER_DETAILS.remove(productId);

		service.set("tolat", cartService.getAmont());
		service.set("No0fItems", cartService.getCont());
		String referer = request.getHeader("Referer");
		if (referer != null) {
			return "redirect:" + referer;
		}
		return "redirect:/shoppingCart/cart";

	}
	
	
	
	@GetMapping("discount")
	public String codeDiscount(Model model, @RequestParam("code") String code) {
		Order order = new Order();
		
		//Optional<Code> discount= 
		
		
		return "site/shopping/shopping-cart";
	}
	
	
	@PostMapping("createInvoice")
	public ModelAndView createInvoice(ModelMap model) {
		System.out.println("Dang Thanh Toán ---------------------------");
		Order order = new Order();
		String username = service.get("name");
	    Optional<Customer> optional =customerService.findByUsername(username);
	    if (optional.isPresent()) {
	        Customer customer = optional.get();
	        order.setCustomer(customer);
	    } else {
	    	model.addAttribute("error","BẠN CHƯA ĐĂNG NHẬP!");
			return new ModelAndView("redirect: /ulogin");

	    }
		Date date = new Date();
		order.setOrderDate(date);
		
		order.setAmount(cartService.getAmont()+30000); // demo tạm phi giao hàng
		order.setStatus(PENDING);
	    // Lưu hóa đơn vào cơ sở dữ liệu
		Order savedInvoice =orderService.save(order); 
		System.out.println("Luu Thành Công Hoa Don ---------------------------");
	    // Gán hóa đơn tương ứng cho mỗi chi tiết hóa đơn
		for (Map.Entry<Integer, OrderDetail> entry : ORDER_DETAILS.entrySet()) {
		    Integer itemId = entry.getKey();
		    OrderDetail detail = entry.getValue();

		    // Gán Order cho OrderDetail
		    detail.setOrder(order);
		    
		    // Lưu OrderDetail
			 System.out.println("Lưu Thanh Công Hoa Don Chi Tiết ---------------------------");
		     orderdetailService.save(detail);
		     // 
		     Optional<Product> product =productService.findById(detail.getProduct().getProductId());
		     product.get().setQuantity(product.get().getQuantity()-detail.getQuantity());
		     productService.save(product.get());
		   
		}
		model.addAttribute("order",order);
		model.addAttribute("message","THÀNH TOÀN THÀNH CÔNG");
		return new ModelAndView("site/shopping/successful-payment",model);
		   
		
	}
	@GetMapping("user")
	public String i( Model model) {
		//   
		    model.addAttribute("message","BẠN ĐÃ ĐẶT HÀNG THÀNH CÔNG");
		return "site/shopping/successful-payment";
		
	}
	
	@GetMapping("user/purchase")
	public String confirmation() {
		
		
		return "site/shopping/Wait-for-confirmation";
	}

	
	

	@GetMapping("pay")
	public String demo(Model model) {
		Collection<CartItem> cartItems = cartService.getCartItems();
		service.set("payment", cartService.getAmont()+30000);
		
		if(cartItems==null) {
			model.addAttribute("error","Giỏ Hàng bạn trống, Vui long chọn sản phẩm rồi thành toàn");
			return "site/shopping/shopping-cart";
		}

		return "site/shopping/shopping-pay";

	}
	

}
