package com.hamroDaraz.daraz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopDto {
    private Long id;
    private String name;
    private String description;
//    private Long rating;
//    private String review;
    private String contact_info;
    private String logo;

}
