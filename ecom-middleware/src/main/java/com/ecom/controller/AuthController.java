package com.ecom.controller;

import com.ecom.dto.AuthenticationRequest;
import com.ecom.dto.SignupRequest;
import com.ecom.dto.UserDto;
import com.ecom.entity.User;
import com.ecom.repository.UserRepository;
import com.ecom.services.auth.AuthService;
import com.ecom.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

   // public static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);


@Autowired
    private AuthService authService;

    public static final String HEADER_STRING = "Bearer ";
    public static final String TOKEN_PREFIX = "Authorization ";

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) throws IOException, JSONException {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),
                    authenticationRequest.getPassword()));
            log.error("try executed authenticate api");

        }catch (BadCredentialsException ex){
            log.error("Bad credential not authorize {}",ex);
            throw new BadCredentialsException("Incorrect Username or password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername((authenticationRequest.getUserName()));

        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        response.addHeader("Access-Control-Expose-Headers", "authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
            response.addHeader( TOKEN_PREFIX ,HEADER_STRING+ jwt);
        if(optionalUser.isPresent()){
            try (PrintWriter writer = response.getWriter()) {
                writer.write(new JSONObject()
                        .put("userId", optionalUser.get().getId())
                        .put("role", optionalUser.get().getRole())
                        .toString());
            }catch(Exception ex){
                System.out.println("exception"+ ex.getMessage());
            }

        }

    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        log.error("try executed signup api");
        if(authService.hasUserWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("User already exist", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto userDto = authService.createUser(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
