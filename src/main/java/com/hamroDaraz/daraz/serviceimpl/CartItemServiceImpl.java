package com.hamroDaraz.daraz.serviceimpl;

import com.hamroDaraz.daraz.dto.CartItemDto;
import com.hamroDaraz.daraz.dto.DeleteResponseDto;
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
    public ResponseEntity<?> addProduct(Long userId,CartItemDto cartItemDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        Shop shop = shopRepository.findById(cartItemDto.getShopId()).orElseThrow(() -> new ResourceNotFoundException("shop not found"));
        Product product = productRepository.findById(cartItemDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("product not found"));
//         Check if the product belongs to the correct shop
        if (!shop.getId().equals(product.getShop().getId())) {  //imp concept very usefull
            //yadi shop ko id ra product bata get gareko shop ra tyo get gareko shop ko id same vayena vane
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The product is not available in this shop.");
        }
        // Check if the same product, shop, and user combination already exists in the cart
        CartItem existingCartItem = cartItemRepository.findByCartIdAndShopIdAndProductId(
                user.getCart().getId(),
                cartItemDto.getShopId(),
                cartItemDto.getProductId()
        );

        if (existingCartItem != null) {
            // Update the existing cart item
            int updatedQuantity = existingCartItem.getQuantity() + cartItemDto.getQuantity();
            //yeta cart ma add huda pasa ma stock ghatxa
            product.setStock(product.getStock()-cartItemDto.getQuantity());
            existingCartItem.setQuantity(updatedQuantity);
            existingCartItem.setPrice(product.getPrice() * updatedQuantity); // Update total price
            existingCartItem.setUpdatedAt(LocalDateTime.now());
            productRepository.save(product);

            CartItem updatedCartItem = cartItemRepository.save(existingCartItem);

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Product quantity updated successfully: " + updatedCartItem.getQuantity());
        } else {
            // Create a new cart item if it doesn't exist
            CartItem newCartItem = new CartItem();
            newCartItem.setShopId(cartItemDto.getShopId());
            newCartItem.setProductId(cartItemDto.getProductId());
            newCartItem.setQuantity(cartItemDto.getQuantity());
            newCartItem.setPrice(product.getPrice() * cartItemDto.getQuantity()); // Total price
            newCartItem.setCreatedAt(LocalDateTime.now());
            newCartItem.setUpdatedAt(LocalDateTime.now());
            newCartItem.setCart(user.getCart()); // Set the user's cart manually

            CartItem savedCartItem = cartItemRepository.save(newCartItem);
            product.setStock(product.getStock()-savedCartItem.getQuantity());
            productRepository.save(product);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Product added to cart successfully: " + savedCartItem.getQuantity());
        }

    }

    @Override
    public ResponseEntity<?> removeProduct(Long userId, CartItemDto cartItemDto) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        Shop shop = shopRepository.findById(cartItemDto.getShopId()).orElseThrow(() -> new ResourceNotFoundException("shop not found"));
        Product product = productRepository.findById(cartItemDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("product not found"));
//         Check if the product belongs to the correct shop
        if (!shop.getId().equals(product.getShop().getId())) {  //imp concept very usefull
            //yadi shop ko id ra product bata get gareko shop ra tyo get gareko shop ko id same vayena vane
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The product is not available in this shop.");
        }
        // Check if the same product, shop, and user combination already exists in the cart
        CartItem existingCartItem = cartItemRepository.findByCartIdAndShopIdAndProductId(
                user.getCart().getId(),
                cartItemDto.getShopId(),
                cartItemDto.getProductId()
        );
//product lai remove garna ko lagi pahilai product vako huna parxa exist otherwise navako product ko kasari
        //remove garna quantity so we check product exist or not
        if (existingCartItem != null) {
            int updatedQuantity = existingCartItem.getQuantity()  - cartItemDto.getQuantity();
            //yeta cart ma remove huda pasal ma stock badxa
            product.setStock(product.getStock()+cartItemDto.getQuantity());
            existingCartItem.setQuantity(updatedQuantity);
            existingCartItem.setPrice(product.getPrice() * updatedQuantity); // Update total price
            existingCartItem.setUpdatedAt(LocalDateTime.now());
            productRepository.save(product);

            CartItem updatedCartItem = cartItemRepository.save(existingCartItem);
            DeleteResponseDto deleteResponseDto=new DeleteResponseDto();
            deleteResponseDto.setSuccess(true);
            deleteResponseDto.setMessage("Product quantity removed successfully: " + updatedCartItem.getQuantity());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(deleteResponseDto);
        } else {
            // Create a new cart item if it doesn't exist
//            CartItem newCartItem = new CartItem();
//            newCartItem.setShopId(cartItemDto.getShopId());
//            newCartItem.setProductId(cartItemDto.getProductId());
//            newCartItem.setQuantity(cartItemDto.getQuantity());
//            newCartItem.setPrice(product.getPrice() * cartItemDto.getQuantity()); // Total price
//            newCartItem.setCreatedAt(LocalDateTime.now());
//            newCartItem.setUpdatedAt(LocalDateTime.now());
//            newCartItem.setCart(user.getCart()); // Set the user's cart manually
//
//            CartItem savedCartItem = cartItemRepository.save(newCartItem);
//            product.setStock(product.getStock()-savedCartItem.getQuantity());
//            productRepository.save(product);
            DeleteResponseDto deleteResponseDto=new DeleteResponseDto();
            deleteResponseDto.setSuccess(true);
            deleteResponseDto.setMessage("there is no product in your cart");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(deleteResponseDto );
        }

    }
}

















//        // Manual mapping for specific fields
//        CartItem cartItem = new CartItem();
//        if(cartItem.getShopId()!=null )
//        cartItem.setShopId(cartItemDto.getShopId());
//        cartItem.setProductId(cartItemDto.getProductId());
//        cartItem.setQuantity(cartItemDto.getQuantity());
//        cartItem.setPrice(product.getPrice());
//        cartItem.setCreatedAt(LocalDateTime.now());
//        cartItem.setCart(user.getCart()); // Set the user's cart manually

//        // Save the cart item and convert back to DTO
//        CartItem savedCartItem = cartItemRepository.save(cartItem);
//        CartItemDto savedCartItemDto = new CartItemDto(
//                savedCartItem.getShopId(),
//                savedCartItem.getProductId(),
//                savedCartItem.getQuantity()
//        );
//
//        return ResponseEntity.status(HttpStatus.OK).body(savedCartItemDto);

//using modelmapper
//        CartItem cartItem = this.modelMapper.map(cartItemDto, CartItem.class);
//        cartItem.setPrice(product.getPrice());
//        cartItem.setCreatedAt(LocalDateTime.now());
//        cartItem.setCart(user.getCart());
//        CartItemDto cartItemDto1 = this.modelMapper.map(cartItemRepository.save(cartItem), CartItemDto.class);
//        return ResponseEntity.status(HttpStatus.OK).body(cartItemDto1);