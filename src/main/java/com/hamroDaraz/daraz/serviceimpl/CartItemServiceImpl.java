package com.hamroDaraz.daraz.serviceimpl;

import com.hamroDaraz.daraz.dto.CartItemDto;
import com.hamroDaraz.daraz.entity.CartItem;
import com.hamroDaraz.daraz.entity.Product;
import com.hamroDaraz.daraz.entity.Shop;
import com.hamroDaraz.daraz.entity.User;
import com.hamroDaraz.daraz.exception.ResourceNotFoundException;
import com.hamroDaraz.daraz.repository.CartItemRepository;
import com.hamroDaraz.daraz.repository.ProductRepository;
import com.hamroDaraz.daraz.repository.ShopRepository;
import com.hamroDaraz.daraz.repository.UserRepository;
import com.hamroDaraz.daraz.service.CartItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> addProduct(CartItemDto cartItemDto) {
        CartItem cartItem = this.modelMapper.map(cartItemDto, CartItem.class);
        User user = userRepository.findById(cartItemDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        Shop shop = shopRepository.findById(cartItemDto.getShopId()).orElseThrow(() -> new ResourceNotFoundException("shop not found"));
        Product product = productRepository.findById(cartItemDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("product not found"));
//         Check if the product belongs to the correct shop
        if (!shop.equals(product.getShop()))  //to check whether the product is  available or not in a shop
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The product is not available in this shop.");
        }
        cartItem.setPrice(product.getPrice());
        cartItem.setCreatedAt(LocalDateTime.now());
        cartItem.setCart(user.getCart());
        CartItemDto cartItemDto1 = this.modelMapper.map(cartItemRepository.save(cartItem), CartItemDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(cartItemDto1);
    }
}
