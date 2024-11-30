package com.hamroDaraz.daraz.controller;

import com.hamroDaraz.daraz.dto.ProductDto;
import com.hamroDaraz.daraz.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    //create
    @PostMapping("/create")
    public ResponseEntity<ProductDto> addProduct(@RequestParam Long shopId, @RequestParam Long categoryId, @RequestBody ProductDto productDto)
    {
       ProductDto createdProduct= productService.addProduct(shopId,categoryId,productDto);
       return ResponseEntity.status(HttpStatus.OK).body(createdProduct);
    }
}
