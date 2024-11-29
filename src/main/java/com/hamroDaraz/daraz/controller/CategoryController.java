package com.hamroDaraz.daraz.controller;

import com.hamroDaraz.daraz.dto.CategoryDto;
import com.hamroDaraz.daraz.dto.DeleteResponseDto;
import com.hamroDaraz.daraz.service.CategoryService;
import com.hamroDaraz.daraz.serviceimpl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //create
    @PostMapping("/create")
    public ResponseEntity<?> addCategory(@RequestBody  CategoryDto categoryDto)
    {
        CategoryDto categoryDto1=categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDto1);
    }
    //update
    @PutMapping("/update")
    public ResponseEntity<?>  updateCategory(@RequestParam Long categoryId,@RequestBody CategoryDto categoryDto)
    {
        CategoryDto categoryDto1=categoryService.updateCategory(categoryId,categoryDto);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDto1);
    }
    //delete
    @DeleteMapping("/delete")
    public ResponseEntity<?>  deleteCategory(@RequestParam Long categoryId)
    {
     categoryService.deleteCategory(categoryId);
        DeleteResponseDto deleteResponseDto=new DeleteResponseDto("category delted",true);
        return ResponseEntity.status(HttpStatus.OK).body(deleteResponseDto);
    }
    //get
    @GetMapping("/get")
    public ResponseEntity<?> getCategory(@RequestParam Long categoryId)
    {
      CategoryDto categoryDto=categoryService.getCategory(categoryId);
      return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }

    //getAll
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCategory()
    {
        List<CategoryDto> categoryDto=categoryService.getAllCategory();
        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }
}
