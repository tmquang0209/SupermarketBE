package com.supermarket.backend.Payload.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VendorRequest {
    @NotBlank
    String name;

    @NotBlank
    String phoneNumber;

    @NotBlank
    String email;

    @NotBlank
    String address;

    String description;

    boolean status;
}
