package com.supermarket.backend.Payload.Request;

import com.supermarket.backend.Entity.CustomerEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;

@Data
public class PromotionRequest {
    @NotBlank(message = "Code is required")
    private String code;

    @NotNull(message = "Discount value is required")
    private float discount;

    @NotNull(message = "Customer is not null")
    private CustomerEntity customer;

    private String description;

    @NotNull(message = "Start date is require")
    private Date startDate;

    @NotNull(message = "End date is require")
    private Date endDate;
}
