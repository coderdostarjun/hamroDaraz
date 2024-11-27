package com.hamroDaraz.daraz.controller;

import com.hamroDaraz.daraz.dto.DeleteResponseDto;
import com.hamroDaraz.daraz.dto.ShopDto;
import com.hamroDaraz.daraz.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;
    //create
    @PostMapping("/create")

    public ResponseEntity<ShopDto> createShop(@RequestParam Long userId,@RequestBody ShopDto shopDto)
    {
      ShopDto createShop=shopService.createShop(userId,shopDto);
      return ResponseEntity.status(HttpStatus.OK).body(createShop);
    }
    //update
    @PutMapping("/update")
    public ResponseEntity<ShopDto> updateShop(@RequestParam Long shopId,@RequestBody ShopDto shopDto)
    {
        ShopDto createShop=shopService.updateShop(shopId,shopDto);
        return ResponseEntity.status(HttpStatus.OK).body(createShop);
    }

    //delete
    @DeleteMapping("/delete")
    public ResponseEntity<DeleteResponseDto> deleteShop(@RequestParam Long shopId)
    {
        shopService.deleteShop(shopId);
        DeleteResponseDto deleteResponseDto=new DeleteResponseDto();
        deleteResponseDto.setMessage("Shop delete hogaya");
        deleteResponseDto.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(deleteResponseDto);
    }

    //get
   @GetMapping("/get")
   public ResponseEntity<?> getShopById(@RequestParam Long shopId)
   {
       ShopDto getShop=shopService.getShop(shopId);
       return ResponseEntity.status(HttpStatus.OK).body(getShop);
   }

    //getAll
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllShop()
    {
        List<ShopDto> getAllShop=shopService.getAllShop();
        return ResponseEntity.status(HttpStatus.OK).body(getAllShop);
    }
}
