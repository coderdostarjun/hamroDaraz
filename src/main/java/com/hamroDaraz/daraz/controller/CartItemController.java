package com.hamroDaraz.daraz.controller;

import com.hamroDaraz.daraz.dto.CartItemDto;
import com.hamroDaraz.daraz.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cartItem")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    //add product
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody CartItemDto cartItemDto)
    {
      return cartItemService.addProduct(cartItemDto);
    }
}
