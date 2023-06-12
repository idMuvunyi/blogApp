package com.spring.blog.service.impl;

import com.spring.blog.exception.ResourceNotFoundException;
import com.spring.blog.model.Category;
import com.spring.blog.payload.CategoryDto;
import com.spring.blog.repository.CategoryRepository;
import com.spring.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        // map Dto to Model
        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        // map Model to Dto
        CategoryDto categoryResponse = mapper.map(savedCategory, CategoryDto.class);

        return categoryResponse;
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
        CategoryDto categoryResponse = mapper.map(category, CategoryDto.class);
        return categoryResponse;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
         List<Category> categories = categoryRepository.findAll();
         List<CategoryDto> listOfCategories = categories.stream().map(category -> mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return listOfCategories;
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setId(id);

        Category updatedCategory = categoryRepository.save(category);

        return mapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(category);
    }
}
