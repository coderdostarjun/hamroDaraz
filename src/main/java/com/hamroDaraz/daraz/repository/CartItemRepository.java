package com.hamroDaraz.daraz.repository;

import com.hamroDaraz.daraz.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByCartIdAndShopIdAndProductId(Long id, Long shopId, Long productId);
}


