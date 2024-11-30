package com.hamroDaraz.daraz.serviceimpl;

import com.hamroDaraz.daraz.dto.ProductDto;
import com.hamroDaraz.daraz.entity.Category;
import com.hamroDaraz.daraz.entity.Product;
import com.hamroDaraz.daraz.entity.Shop;
import com.hamroDaraz.daraz.exception.ResourceNotFoundException;
import com.hamroDaraz.daraz.repository.CategoryRepository;
import com.hamroDaraz.daraz.repository.ProductRepository;
import com.hamroDaraz.daraz.repository.ShopRepository;
import com.hamroDaraz.daraz.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ProductServicImpl implements ProductService {
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto addProduct(Long shopId, Long categoryId, ProductDto productDto) {
        Shop shop=shopRepository.findById(shopId).orElseThrow(()->new ResourceNotFoundException("shop not found"));
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category not found"));

       Product product=this.modelMapper.map(productDto, Product.class);
       product.setCategory(category);
       product.setShop(shop);
       product.setCreatedAt(LocalDateTime.now());
      return this.modelMapper.map(productRepository.save(product),ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        return null;
    }

    @Override
    public ProductDto getProduct(Long productId) {
        return null;
    }

    @Override
    public List<ProductDto> getAllProduct() {
        return List.of();
    }

    @Override
    public void deleteProduct(Long productId) {

    }
}
