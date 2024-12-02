package com.hamroDaraz.daraz.serviceimpl;

import com.hamroDaraz.daraz.dto.ShopDto;
import com.hamroDaraz.daraz.entity.Shop;
import com.hamroDaraz.daraz.entity.User;
import com.hamroDaraz.daraz.exception.ResourceNotFoundException;
import com.hamroDaraz.daraz.repository.ShopRepository;
import com.hamroDaraz.daraz.repository.UserRepository;
import com.hamroDaraz.daraz.service.ShopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ShopDto createShop(Long userId, ShopDto shopDto) {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found"));
        Shop shop= this.modelMapper.map(shopDto, Shop.class);
        shop.setCreatedAt(LocalDateTime.now());
        shop.setUser(user);
        user.setRole("Seller");
        userRepository.save(user);
        Shop newShop=shopRepository.save(shop);
       return this.modelMapper.map(newShop,ShopDto.class);

    }

    @Override
    public ShopDto updateShop(Long shopId, ShopDto shopDto) {
        Shop shop=shopRepository.findById(shopId).orElseThrow(()->new ResourceNotFoundException("Shop not found"));
        shop.setName(shopDto.getName());
        shop.setDescription(shopDto.getDescription());
        shop.setLogo(shopDto.getLogo());
        shop.setContact_info(shopDto.getContact_info());
        shop.setUpdatedAt(LocalDateTime.now());
        Shop newShop=shopRepository.save(shop);
        return this.modelMapper.map(newShop, ShopDto.class);
    }

    @Override
    public ShopDto getShop(Long shopId) {
        Shop shop=shopRepository.findById(shopId).orElseThrow(()->new ResourceNotFoundException("Shop not found"));
        return this.modelMapper.map(shop, ShopDto.class);

    }

    @Override
    public List<ShopDto> getAllShop() {
        List<Shop> shop=shopRepository.findAll();
       List<ShopDto> listOfShop=shop.stream().map((hamroShop)->
               this.modelMapper.map(hamroShop, ShopDto.class)).collect(Collectors.toList());
     return listOfShop;
    }

    @Override
    public void deleteShop(Long shopId) {
        Shop shop=shopRepository.findById(shopId).orElseThrow(()->new ResourceNotFoundException("Shop not found"));
        shopRepository.delete(shop);

    }
}

