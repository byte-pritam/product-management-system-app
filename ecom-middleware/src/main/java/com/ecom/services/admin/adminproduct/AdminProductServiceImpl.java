package com.ecom.services.admin.adminproduct;

import com.ecom.dto.ProductDto;
import com.ecom.entity.Category;
import com.ecom.entity.Product;
import com.ecom.repository.CategoryRepository;
import com.ecom.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminProductServiceImpl implements AdminProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;


    public ProductDto addProduct(ProductDto productDto) throws IOException {

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImg(productDto.getImg().getBytes());

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();

        product.setCategory(category);
        return productRepository.save(product).getDto();

    }

    @Transactional
    public List<ProductDto> getAllProduct(){
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(Product ::getDto).collect(Collectors.toList());
    }

    @Transactional
    public List<ProductDto> findAllByNameContaining(String name){
        List<Product> productList = productRepository.findAllByNameIgnoreCaseContaining(name);
        return productList.stream().map(Product ::getDto).collect(Collectors.toList());
    }
}
