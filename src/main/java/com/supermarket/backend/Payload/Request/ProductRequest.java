package com.supermarket.backend.Payload.Request;

import com.supermarket.backend.Entity.CategoryEntity;
import com.supermarket.backend.Entity.EmployeeEntity;
import com.supermarket.backend.Entity.VendorEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ProductRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Category is required")
    private CategoryEntity category;

    @NotNull(message = "Unit price is required")
    @Min(value = 0, message = "Unit price must be greater than or equal to 0")
    private Float unitPrice; // Float instead of float to use validation annotations

    @NotBlank(message = "Unit is required")
    private String unit;

    private int inStock;

    private String description;

    private VendorEntity vendor;

    private boolean status = true;

    private EmployeeEntity employee;
}
