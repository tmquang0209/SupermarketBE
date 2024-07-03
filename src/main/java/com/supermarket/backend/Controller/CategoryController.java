package com.supermarket.backend.Controller;

import com.supermarket.backend.Entity.CategoryEntity;
import com.supermarket.backend.Entity.EmployeeEntity;
import com.supermarket.backend.Payload.Request.CategoryRequest;
import com.supermarket.backend.Security.Jwt.JwtUtils;
import com.supermarket.backend.Service.CategoryService;
import com.supermarket.backend.Service.EmployeeServiceImpl;
import com.supermarket.backend.Util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ApiResponse<?> createCategory(@RequestHeader(value = "Authorization") String bearerToken, @Valid @RequestBody CategoryRequest request) {
        try {
            System.out.println(bearerToken);
            boolean isValidToken = jwtUtils.validateJwtToken(bearerToken);
            if (!isValidToken) throw new Exception("Invalid Token");

            String username = jwtUtils.getUserNameFromJwtToken(bearerToken);
            EmployeeEntity employee = employeeService.getByUsername(username);

            CategoryEntity newCategory = new CategoryEntity(request, employee);
            newCategory = categoryService.saveCategory(newCategory);
            return new ApiResponse<>(true, newCategory, "Create category successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<?> getAllCategories() {
        try {
            List<CategoryEntity> categoryEntities = categoryService.getAllCategories();
            return new ApiResponse<>(true, categoryEntities, "Get all categories successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/search")
    public ApiResponse<?> searchByName(@RequestParam(value = "name") String name) {
        try {
            List<CategoryEntity> categoryEntities = categoryService.searchByName(name);
            return new ApiResponse<>(true, categoryEntities, "Search successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/{id}/details")
    public ApiResponse<?> getById(@PathVariable(name = "id") Integer id) {
        try {
            CategoryEntity categoryEntity = categoryService.getById(id);
            return new ApiResponse<>(true, categoryEntity, "Get details successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/{id}/update")
    public ApiResponse<?> update(@PathVariable(name = "id") Integer id, @RequestHeader(value = "Authorization") String bearerToken, @Valid @RequestBody CategoryRequest request) {
        try {
            CategoryEntity categoryEntity = categoryService.update(id, request);
            return new ApiResponse<>(true, categoryEntity, "Update category successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/{id}/delete")
    public ApiResponse<?> delete(@PathVariable(name = "id") Integer id, @RequestHeader(value = "Authorization") String bearerToken) {
        try {
            categoryService.delete(id);
            return new ApiResponse<>(true, null, "Delete category successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
