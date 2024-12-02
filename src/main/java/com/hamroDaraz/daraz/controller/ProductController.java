package com.hamroDaraz.daraz.controller;

import com.hamroDaraz.daraz.dto.DeleteResponseDto;
import com.hamroDaraz.daraz.dto.ProductDto;
import com.hamroDaraz.daraz.dto.ShopDto;
import com.hamroDaraz.daraz.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    //update
    @PutMapping("/update")
    public ResponseEntity<ProductDto> updateProduct(@RequestParam Long productId, @RequestBody ProductDto productDto)
    {
       ProductDto updateProduct= productService.updateProduct(productId,productDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }
    //get
    @GetMapping("/get")
    public ResponseEntity<?> getProductById(@RequestParam Long productId)
    {
        ProductDto product=productService.getProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    //getAll
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProduct()
    {
        List<ProductDto> getAllProducts=productService.getAllProduct();
        return ResponseEntity.status(HttpStatus.OK).body(getAllProducts);
    }
    //delete
    @DeleteMapping("/delete")
    public ResponseEntity<DeleteResponseDto> deleteProduct(@RequestParam Long productId)
    {
       productService.deleteProduct(productId);
        DeleteResponseDto deleteResponseDto=new DeleteResponseDto();
        deleteResponseDto.setMessage("Product delete hogaya");
        deleteResponseDto.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(deleteResponseDto);
    }
}
