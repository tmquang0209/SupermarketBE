package com.supermarket.backend.Controller;

import com.supermarket.backend.Entity.DepartmentEntity;
import com.supermarket.backend.Entity.EmployeeEntity;
import com.supermarket.backend.Payload.Request.DepartmentRequest;
import com.supermarket.backend.Security.Jwt.JwtUtils;
import com.supermarket.backend.Service.DepartmentService;
import com.supermarket.backend.Service.EmployeeServiceImpl;
import com.supermarket.backend.Util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/create")
    public ApiResponse<?> createDepartment(@RequestHeader(value = "Authorization") String bearerToken, @Valid @RequestBody DepartmentRequest request) {
        try {
            boolean isValidToken = jwtUtils.validateJwtToken(bearerToken);
            if (!isValidToken) throw new Exception("Invalid Token");

            String username = jwtUtils.getUserNameFromJwtToken(bearerToken);
            EmployeeEntity employee = employeeService.getByUsername(username);

            DepartmentEntity newDepartment = new DepartmentEntity(request);
            newDepartment.setCreateAt(new java.sql.Date(System.currentTimeMillis()));

            newDepartment = departmentService.saveDepartment(newDepartment);
            return new ApiResponse<>(true, newDepartment, "Create department successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<?> getAllDepartments() {
        try {
            List<DepartmentEntity> departmentEntities = departmentService.getAllDepartments();
            return new ApiResponse<>(true, departmentEntities, "Get all departments successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/search")
    public ApiResponse<?> searchDepartment(@RequestParam(value = "name") String name) {
        try {
            List<DepartmentEntity> departmentEntities = departmentService.searchByName(name);
            return new ApiResponse<>(true, departmentEntities, "Get all departments successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/{id}/details")
    public ApiResponse<?> getById(@PathVariable(name = "id") Integer id) {
        try {
            DepartmentEntity departmentEntity = departmentService.getById(id);
            return new ApiResponse<>(true, departmentEntity, "Get details successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/{id}/update")
    public ApiResponse<?> update(@PathVariable(name = "id") Integer id, @RequestHeader(value = "Authorization") String bearerToken, @Valid @RequestBody DepartmentRequest request) {
        try {
            DepartmentEntity departmentEntity = departmentService.update(id, request);
            return new ApiResponse<>(true, departmentEntity, "Update department successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/{id}/delete")
    public ApiResponse<?> delete(@PathVariable(name = "id") Integer id, @RequestHeader(value = "Authorization") String bearerToken) {
        try {
            departmentService.delete(id);
            return new ApiResponse<>(true, null, "Delete department successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
