package com.ecom.services.admin.category;

import com.ecom.dto.CategoryDto;
import com.ecom.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    public Category createCategory(CategoryDto categoryDto);

    public List<Category> getAllCategory();
}
