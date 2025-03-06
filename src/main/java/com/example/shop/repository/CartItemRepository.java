package com.example.shop.repository;

import com.example.shop.entity.Cart;
import com.example.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    public CartItem findByCartIdAndItemId(Long cartId, Long itemId);




}
