package com.hamroDaraz.daraz.service;

import com.hamroDaraz.daraz.dto.ShopDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ShopService {
    //create
    ShopDto createShop(Long userId,ShopDto shopDto);

    //update
    ShopDto updateShop(Long shopId,ShopDto shopDto);

    //get
    ShopDto getShop(Long shopId);
    //getAll
    List<ShopDto> getAllShop();
    //delete
    void deleteShop(Long shopId);

}
