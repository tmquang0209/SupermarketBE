package com.supermarket.backend.Payload.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank(message = "Please fill in the category name.")
    private String name;

    private String description;

    private boolean status = true;
}
