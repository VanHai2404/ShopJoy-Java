package com.edu.shop.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.edu.shop.model.CartItem;
import com.edu.shop.service.ShoppingCartService;
 
@Service
@SessionScope
public class ShoppingCartServiceImpl implements ShoppingCartService {

	private Map<Integer, CartItem> map = new HashMap<>();

	@Override
	public void add(CartItem item) {
		CartItem axCartItem = map.get(item.getProductId());
		if (axCartItem != null) {
			axCartItem.setQuantity(item.getQuantity() + axCartItem.getQuantity());
		} else {
			map.put(item.getProductId(), item);
		}

	}

	@Override
	public void remove(Integer productId) {
		map.remove(productId);

	}

	@Override
	public Collection<CartItem> getCartItems() {
		return map.values();
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public void update(int productId, int quantity) {
		CartItem item = map.get(productId);

		item.setQuantity(quantity);
		if (item.getQuantity() <= 0) {
			map.remove(productId);

		}
	}

	@Override
	public double getAmont() {
		return map.values().stream().mapToDouble(item -> item.getQuantity() * item.getUntiPrice()).sum();
	}

	@Override
	public int getCont() {
		if (map.isEmpty()) {
			return 0;
		}
		return map.values().size();

	}

}
