package com.hamroDaraz.daraz.service;

import com.hamroDaraz.daraz.dto.CategoryDto;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    //update
    CategoryDto updateCategory(Long categoryId,CategoryDto categoryDto);

    //get
    CategoryDto getCategory(Long categoryId);
    //getAll
    List<CategoryDto> getAllCategory();
    //delete
    void deleteCategory(Long categoryId);

}
