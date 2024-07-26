package com.supermarket.backend.Controller;

import com.supermarket.backend.Entity.ProductEntity;
import com.supermarket.backend.Entity.EmployeeEntity;
import com.supermarket.backend.Payload.Request.ProductRequest;
import com.supermarket.backend.Security.Jwt.JwtUtils;
import com.supermarket.backend.Service.ProductService;
import com.supermarket.backend.Service.EmployeeServiceImpl;
import com.supermarket.backend.Util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ApiResponse<?> createProduct(@RequestHeader(value = "Authorization") String bearerToken, @Valid @RequestBody ProductRequest request) {
        try {
            boolean isValidToken = jwtUtils.validateJwtToken(bearerToken);
            if (!isValidToken) throw new Exception("Invalid Token");

            String username = jwtUtils.getUserNameFromJwtToken(bearerToken);
            EmployeeEntity employee = employeeService.getByUsername(username);

            request.setEmployee(employee);

            ProductEntity newProduct = new ProductEntity(request);

            newProduct = productService.saveProduct(newProduct);
            return new ApiResponse<>(true, newProduct, "Create product successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<?> getAllProducts() {
        try {
            List<ProductEntity> productEntities = productService.getAllProducts();
            return new ApiResponse<>(true, productEntities, "Get all products successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/search")
    public ApiResponse<?> searchByName(@RequestParam(value = "name") String name) {
        try {
            List<ProductEntity> productEntities = productService.searchByName(name);
            return new ApiResponse<>(true, productEntities, "Search successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/barcode")
    public ApiResponse<?> getByBarcode(@RequestParam(value = "barcode") String barcode) {
        try {
            ProductEntity productEntity = productService.getByBarcode(barcode);
            return new ApiResponse<>(true, productEntity, "Get successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/{id}/details")
    public ApiResponse<?> getById(@PathVariable(name = "id") Integer id) {
        try {
            ProductEntity productEntity = productService.getById(id);
            return new ApiResponse<>(true, productEntity, "Get details successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/{id}/update")
    public ApiResponse<?> update(@PathVariable(name = "id") Integer id, @RequestHeader(value = "Authorization") String bearerToken, @Valid @RequestBody ProductRequest request) {
        try {
            ProductEntity productEntity = productService.update(id, request);
            return new ApiResponse<>(true, productEntity, "Update product successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/{id}/delete")
    public ApiResponse<?> delete(@PathVariable(name = "id") Integer id, @RequestHeader(value = "Authorization") String bearerToken) {
        try {
            productService.delete(id);
            return new ApiResponse<>(true, null, "Delete product successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
