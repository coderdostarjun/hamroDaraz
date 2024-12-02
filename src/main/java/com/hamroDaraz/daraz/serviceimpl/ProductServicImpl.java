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
import java.util.stream.Collectors;

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
        Product product=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product not found"));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setImage(productDto.getImage());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setImage(productDto.getImage());
        product.setUpdatedAt(LocalDateTime.now());
       return this.modelMapper.map(productRepository.save(product),ProductDto.class);
    }

    @Override
    public ProductDto getProduct(Long productId) {
        Product product=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product not found"));
            return this.modelMapper.map(product, ProductDto.class);

    }

    @Override
    public List<ProductDto> getAllProduct() {
      List<Product> product= productRepository.findAll();
       List<ProductDto> productDtosList=product.stream().map((productDto )->
               this.modelMapper.map(productDto, ProductDto.class)).collect(Collectors.toList());
       return productDtosList;
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product not found"));
         productRepository.delete(product);

    }
}
