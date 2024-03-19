package com.ecom.controller.admin;

import com.ecom.dto.CategoryDto;
import com.ecom.entity.Category;
import com.ecom.services.admin.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto){
        log.error("category api called");
        Category category = categoryService.createCategory(categoryDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategory(){
        return  ResponseEntity.ok(categoryService.getAllCategory());
    }


}
