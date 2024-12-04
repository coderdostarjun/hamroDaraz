package com.hamroDaraz.daraz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long shopId;//kuna pasal bata
    private Long productId;//kuna product kinew
    private int quantity;
    private Long price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "cart_id")  //kuna userko cart ho tesaiko cart ma add garna kinaki hareka user ko unique cart xa
    //so cart ma add garnu vaneko tyo user ni unique hunxa tesai ma addd gareko vanew bujenxa
    private Cart cart;
}
