package com.spring.blog.service.impl;

import com.spring.blog.model.Category;
import com.spring.blog.payload.CategoryDto;
import com.spring.blog.repository.CategoryRepository;
import com.spring.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        return null;
    }
}
