package com.ecom.services.auth;

import com.ecom.dto.SignupRequest;
import com.ecom.dto.UserDto;
import com.ecom.entity.User;
import com.ecom.enums.UserRole;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public UserDto createUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);


}
