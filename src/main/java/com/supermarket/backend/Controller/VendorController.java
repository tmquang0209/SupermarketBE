package com.supermarket.backend.Controller;

import com.supermarket.backend.Entity.EmployeeEntity;
import com.supermarket.backend.Entity.VendorEntity;
import com.supermarket.backend.Enum.ERole;
import com.supermarket.backend.Payload.Request.VendorRequest;
import com.supermarket.backend.Security.Jwt.JwtUtils;
import com.supermarket.backend.Service.EmployeeServiceImpl;
import com.supermarket.backend.Service.VendorService;
import com.supermarket.backend.Util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private VendorService vendorService;

    @PostMapping("/create")
    public ApiResponse<?> createVendor(@RequestHeader(value = "Authorization") String bearerToken, @Valid @RequestBody VendorRequest request) {
        try {
            boolean isValidToken = jwtUtils.validateJwtToken(bearerToken);
            if (!isValidToken) throw new Exception("Invalid Token");

            String username = jwtUtils.getUserNameFromJwtToken(bearerToken);
            EmployeeEntity employee = employeeService.getByUsername(username);

            if(employee.getRole().name().equals(ERole.MANAGER.toString())) throw new Exception("You do not have this permission");

            VendorEntity newVendor = new VendorEntity(request);
            newVendor = vendorService.saveVendor(newVendor);
            return new ApiResponse<>(true, newVendor, "Create vendor successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<?> getAllVendors() {
        try {
            List<VendorEntity> vendorEntities = vendorService.getAllVendors();
            return new ApiResponse<>(true, vendorEntities, "Get all vendors successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/search")
    public ApiResponse<?> searchByName(@RequestParam(value = "name") String name) {
        try {
            List<VendorEntity> vendorEntities = vendorService.searchByName(name);
            return new ApiResponse<>(true, vendorEntities, "Search successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/{id}/details")
    public ApiResponse<?> getById(@PathVariable(name = "id") Integer id) {
        try {
            VendorEntity vendorEntity = vendorService.getById(id);
            return new ApiResponse<>(true, vendorEntity, "Get details successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/{id}/update")
    public ApiResponse<?> update(@PathVariable(name = "id") Integer id, @RequestHeader(value = "Authorization") String bearerToken, @Valid @RequestBody VendorRequest request) {
        try {
            VendorEntity vendorEntity = vendorService.update(id, request);
            return new ApiResponse<>(true, vendorEntity, "Update category successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/{id}/delete")
    public ApiResponse<?> delete(@PathVariable(name = "id") Integer id, @RequestHeader(value = "Authorization") String bearerToken) {
        try {
            vendorService.delete(id);
            return new ApiResponse<>(true, null, "Delete category successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
