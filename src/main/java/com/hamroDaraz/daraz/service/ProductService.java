package com.hamroDaraz.daraz.service;

import com.hamroDaraz.daraz.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductService {
    //create
    ProductDto addProduct(Long shopId, Long categoryId,ProductDto productDto);

    //update
    ProductDto updateProduct(Long productId,ProductDto productDto);

    //get
    ProductDto getProduct(Long productId);
    //getAll
    List<ProductDto> getAllProduct();
    //delete
    void deleteProduct(Long productId);

}
