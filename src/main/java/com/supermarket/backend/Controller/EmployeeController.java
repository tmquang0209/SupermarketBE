package com.supermarket.backend.Controller;

import com.supermarket.backend.Entity.EmployeeEntity;
import com.supermarket.backend.Enum.ERole;
import com.supermarket.backend.Payload.Request.ChangePasswordRequest;
import com.supermarket.backend.Payload.Request.LoginRequest;
import com.supermarket.backend.Payload.Request.SignupRequest;
import com.supermarket.backend.Payload.Request.UpdateInfoRequest;
import com.supermarket.backend.Repository.EmployeeRepository;
import com.supermarket.backend.Security.Jwt.JwtUtils;
import com.supermarket.backend.Service.EmployeeServiceImpl;
import com.supermarket.backend.Service.UserPrincipal;
import com.supermarket.backend.Util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

            if (create != null)
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
        try {


            log.info(passwordEncoder.encode(loginRequest.getPassword()));

            System.out.println(loginRequest);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

            EmployeeEntity employee = employeeService.getById(userDetails.getId());
            assert employee != null;
            if (!employee.isStatus()) throw new Exception("The account is disable.");

            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", jwt);
            return new ApiResponse<>(true, tokenMap, "Success");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(true, null, e.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<?> getAll(@RequestHeader(value = "Authorization") String bearerToken) {
        String accessToken = bearerToken.replace("Bearer ", "");
        try {
            boolean isValidToken = jwtUtils.validateJwtToken(accessToken);
            if (!isValidToken) throw new Exception("Invalid Token");

            String username = jwtUtils.getUserNameFromJwtToken(accessToken);
            EmployeeEntity employee = employeeRepository.findByUsername(username).orElse(null);
            if (employee == null) throw new Exception("Can not find username: " + username);

            if (employee.getRole() != ERole.MANAGER) throw new Exception("You do not have this permission.");

            List<EmployeeEntity> employeeList = employeeService.getAll();
            return new ApiResponse<>(true, employeeList, "Get employee list successful.");
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/info")
    public ApiResponse<?> personalInformation(@RequestHeader(value = "Authorization") String bearerToken) {
        String accessToken = bearerToken.replace("Bearer ", "").trim();
        boolean isValidToken = jwtUtils.validateJwtToken(accessToken);
        try {
            if (!isValidToken) throw new Exception("Invalid token");

            String username = jwtUtils.getUserNameFromJwtToken(accessToken);
            EmployeeEntity employee = employeeRepository.findByUsername(username).orElse(null);
            if (employee == null) throw new UsernameNotFoundException("Can not find username: " + username);
            return new ApiResponse<>(true, employee, "Get personal info successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ApiResponse<?> updateInfo(@PathVariable Integer id, @RequestHeader(value = "Authorization") String bearerToken, @RequestBody UpdateInfoRequest data) {
        String accessToken = bearerToken.replace("Bearer ", "").trim();
        boolean isValidToken = jwtUtils.validateJwtToken(accessToken);
        try {
            if (!isValidToken) throw new Exception("Invalid token");

            String username = jwtUtils.getUserNameFromJwtToken(accessToken);
            EmployeeEntity employee = employeeRepository.findByUsername(username).orElse(null);
            if (employee == null) throw new UsernameNotFoundException("Can not find username: " + username);

            EmployeeEntity employeeEntity = new EmployeeEntity(data);

            EmployeeEntity update = employeeService.update(id, employeeEntity);
            return new ApiResponse<>(true, update, "Update successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/changePassword")
    public ApiResponse<?> changePassword(@RequestHeader(value = "Authorization") String bearerToken, @RequestBody ChangePasswordRequest data) {
        String accessToken = bearerToken.replace("Bearer ", "").trim();
        boolean isValidToken = jwtUtils.validateJwtToken(accessToken);
        try {
            if (!isValidToken) throw new Exception("Invalid token");

            String username = jwtUtils.getUserNameFromJwtToken(accessToken);
            EmployeeEntity employee = employeeRepository.findByUsername(username).orElse(null);
            if (employee == null) throw new UsernameNotFoundException("Can not find username: " + username);

            if (!passwordEncoder.matches(data.getOldPassword(), employee.getPassword())) throw new Exception("Old password is incorrect.");

            if (!data.getNewPassword().equals(data.getConfirmPassword())) throw new Exception("New password and confirm password are not same.");

            String hashPassword = passwordEncoder.encode(data.getNewPassword());
            EmployeeEntity update = employeeService.changePassword(employee.getId(), hashPassword);

            return new ApiResponse<>(true, update, "Change password successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
