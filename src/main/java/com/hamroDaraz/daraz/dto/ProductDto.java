package com.hamroDaraz.daraz.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Long price;
    private Long stock;
}
