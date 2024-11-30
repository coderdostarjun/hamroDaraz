package com.hamroDaraz.daraz.serviceimpl;

import com.hamroDaraz.daraz.dto.CategoryDto;
import com.hamroDaraz.daraz.entity.Category;
import com.hamroDaraz.daraz.exception.ResourceNotFoundException;
import com.hamroDaraz.daraz.repository.CategoryRepository;
import com.hamroDaraz.daraz.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
       Category category= this.modelMapper.map(categoryDto,Category.class);
      return this.modelMapper.map(categoryRepository.save(category),CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
       Category category= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found"));
       category.setName(categoryDto.getName());
       Category newCategory=categoryRepository.save(category);
        System.out.println(newCategory.getName());
        return this.modelMapper.map(newCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found"));
        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
      List<Category> category=categoryRepository.findAll();
        return category.stream().map((category1) ->  //stream le euta euta pathuaxa nai vitra lambda fn le jastai kama garxa
                  this.modelMapper.map(category1,CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found"));
        categoryRepository.delete(category);

    }
}
