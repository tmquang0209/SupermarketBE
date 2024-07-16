package com.supermarket.backend.Payload.Request;

import com.supermarket.backend.Entity.CustomerEntity;
import com.supermarket.backend.Entity.EmployeeEntity;
import com.supermarket.backend.Enum.EPayment;
import com.supermarket.backend.Payload.Dto.CartDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BillRequest {
    private EmployeeEntity employee;

//    @NotNull(message = "Customer id is required")
    private CustomerEntity customer = new CustomerEntity(-1);

    private String type;

    private float discount;

    private String promotionCode;

    private int point = 0;

    private EPayment paymentMethod = EPayment.CARD;

    @NotNull(message = "Please add least 1 item to cart.")
    private List<CartDTO> cart;
}
