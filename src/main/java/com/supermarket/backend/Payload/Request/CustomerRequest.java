package com.supermarket.backend.Payload.Request;

import com.supermarket.backend.Enum.EType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;

@Data
public class CustomerRequest {
    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotNull(message = "Birthday is required")
    private Date birthday;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private int point;

    private EType type;

    private boolean status;
}
