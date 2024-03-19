package com.ecom.services.admin.adminproduct;

import com.ecom.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService  {

    public ProductDto addProduct(ProductDto productDto) throws IOException;

    public List<ProductDto> getAllProduct();

    public List<ProductDto> findAllByNameContaining(String name);
}
