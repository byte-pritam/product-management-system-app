package com.ecom.controller.admin;

import com.ecom.dto.ProductDto;
import com.ecom.services.admin.adminproduct.AdminProductService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminProductController {

    @Autowired
    private AdminProductService adminProductService;
    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto productDto) throws IOException {

        ProductDto productDto1 = adminProductService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto1);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProduct(){
        List<ProductDto> productDtos = adminProductService.getAllProduct();

        return ResponseEntity.status(HttpStatus.OK).body(productDtos);
    }

    @GetMapping(value = "/search/{name}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = APPlicat)
    @JsonIgnore()
    public ResponseEntity<List<ProductDto>> findAllByNameContaining(@PathVariable String name){
        List<ProductDto> productDtos = adminProductService.findAllByNameContaining(name);
        return ResponseEntity.status(HttpStatus.OK).body(productDtos);
    }
}
