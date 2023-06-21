package com.edu.shop.service;

import java.util.Collection;

import com.edu.shop.model.CartItem;

public interface ShoppingCartService {

	int getCont();

	double getAmont();

	void update(int productId, int quantity);

	void clear();

	Collection<CartItem> getCartItems();

	void remove(Integer productId);

	void add(CartItem item);

}
