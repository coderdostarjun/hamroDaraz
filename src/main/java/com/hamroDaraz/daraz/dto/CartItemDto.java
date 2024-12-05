package com.hamroDaraz.daraz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hamroDaraz.daraz.entity.Cart;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemDto {
//    private Long userId;
    private Long shopId;//kuna pasal bata
    private Long productId;//kuna product kinew
    private int quantity;

}
