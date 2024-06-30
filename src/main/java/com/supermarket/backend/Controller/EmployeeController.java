package com.supermarket.backend.Controller;

import com.supermarket.backend.Entity.EmployeeEntity;
import com.supermarket.backend.Payload.Request.LoginRequest;
import com.supermarket.backend.Payload.Request.SignupRequest;
import com.supermarket.backend.Repository.EmployeeRepository;
import com.supermarket.backend.Security.Jwt.JwtUtils;
import com.supermarket.backend.Service.EmployeeServiceImpl;

import com.supermarket.backend.Service.UserPrincipal;
import com.supermarket.backend.Util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/employee")
@RestController
public class EmployeeController {
    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeServiceImpl employeeService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ApiResponse<?> signup(@RequestBody SignupRequest data) {
        try {
            String password = passwordEncoder.encode(data.getPassword());
            data.setPassword(password);

            EmployeeEntity newEmployee = new EmployeeEntity(data);

            EmployeeEntity create = employeeService.saveEmployee(newEmployee);

            if(create != null)
                return new ApiResponse<>(true, create, "Create employee successful.");
            else
                return new ApiResponse<>(true, null, "The employee already exists with the username " + data.getUsername());
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ApiResponse<>(true, null, exception.getMessage());
        }
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequest loginRequest) {
        log.info(passwordEncoder.encode(loginRequest.getPassword()));

        System.out.println(loginRequest.toString());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", jwt);
        return new ApiResponse<>(true, tokenMap, "Success");
    }
}
