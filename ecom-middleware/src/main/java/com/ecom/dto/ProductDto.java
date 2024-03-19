package com.ecom.dto;

import com.ecom.entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {

    private Long id;

    private String name;

    private String description;

    private Long price;

    private byte[] byteImg;

    private Long categoryId;
    private String categoryName;

   private MultipartFile img;
}
