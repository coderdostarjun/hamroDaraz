package com.hamroDaraz.daraz.service;

import com.hamroDaraz.daraz.dto.CartItemDto;
import com.hamroDaraz.daraz.entity.CartItem;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CartItemService {
    //create or add products on cart from specific shop
    ResponseEntity<?> addProduct(Long userId,CartItemDto cartItemDto);

    //remove products from cart
    ResponseEntity<?> removeProduct(Long userId,CartItemDto cartItemDto);
}
