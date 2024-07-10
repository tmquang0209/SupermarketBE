package com.supermarket.backend.Controller;

import com.supermarket.backend.Entity.CustomerEntity;
import com.supermarket.backend.Entity.EmployeeEntity;
import com.supermarket.backend.Payload.Request.CustomerRequest;
import com.supermarket.backend.Security.Jwt.JwtUtils;
import com.supermarket.backend.Service.CustomerService;
import com.supermarket.backend.Service.EmployeeServiceImpl;
import com.supermarket.backend.Util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public ApiResponse<?> createCustomer(@RequestHeader(value = "Authorization") String bearerToken, @Valid @RequestBody CustomerRequest request) {
        try {
            boolean isValidToken = jwtUtils.validateJwtToken(bearerToken);
            if (!isValidToken) throw new Exception("Invalid Token");

            String username = jwtUtils.getUserNameFromJwtToken(bearerToken);
            EmployeeEntity employee = employeeService.getByUsername(username);

            CustomerEntity newCustomer = new CustomerEntity(request);
            newCustomer.setCreateAt(new java.sql.Date(System.currentTimeMillis()));

            newCustomer = customerService.saveCustomer(newCustomer);
            return new ApiResponse<>(true, newCustomer, "Create customer successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<?> getAllCustomers() {
        try {
            List<CustomerEntity> customerEntities = customerService.getAllCustomers();
            return new ApiResponse<>(true, customerEntities, "Get all customers successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/search")
    public ApiResponse<?> searchByKeyword(@RequestParam(value = "keyword") String keyword) {
        try {
            List<CustomerEntity> customerEntities = customerService.search(keyword);
            return new ApiResponse<>(true, customerEntities, "Search customers with keyword: " + keyword + " successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/{id}/details")
    public ApiResponse<?> getById(@PathVariable(name = "id") Integer id) {
        try {
            CustomerEntity customerEntity = customerService.getById(id);
            return new ApiResponse<>(true, customerEntity, "Get details successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/{id}/update")
    public ApiResponse<?> update(@PathVariable(name = "id") Integer id, @RequestHeader(value = "Authorization") String bearerToken, @Valid @RequestBody CustomerRequest request) {
        try {
            CustomerEntity customerEntity = customerService.update(id, request);
            return new ApiResponse<>(true, customerEntity, "Update customer successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/{id}/delete")
    public ApiResponse<?> delete(@PathVariable(name = "id") Integer id, @RequestHeader(value = "Authorization") String bearerToken) {
        try {
            customerService.delete(id);
            return new ApiResponse<>(true, null, "Delete customer successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
