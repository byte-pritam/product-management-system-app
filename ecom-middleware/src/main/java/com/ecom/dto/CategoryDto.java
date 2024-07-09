package com.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategoryDto {

    private Long id;
    private String name;
    private String description;
}
