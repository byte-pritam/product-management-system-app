package com.ecom.services.category;

import com.ecom.entity.Category;
import com.ecom.repository.CategoryRepository;
import com.ecom.services.admin.category.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        List<Category> list = new ArrayList<>();
        list.add(new Category(2L, "oil", "Hair oil dabur"));
        when(categoryRepository.findAll()).thenReturn(list);
    }

    @Test
    public void getAllCategory(){

        List<Category> categoryList= categoryService.getAllCategory();

        List<Category> expectedValue = new ArrayList<>();
        expectedValue.add(new Category(2L, "oil", "Hair oil"));
        System.out.println(categoryList.get(0).getDescription());
        assertNotNull(categoryList);
        assertEquals("oil", categoryList.get(0).getName());



    }


}
