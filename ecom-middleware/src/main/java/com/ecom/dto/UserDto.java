package com.ecom.dto;

import com.ecom.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private UserRole userRole;
}
