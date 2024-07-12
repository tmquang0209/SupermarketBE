package com.supermarket.backend.Payload.Response;

import com.supermarket.backend.Payload.Dto.CartDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillResponse {
    private int id;
    private int employeeId;
    private int customerId;
    private String type;
    private float discount;
    private String promotionCode;
    private int point;
    private String paymentMethod;
    private List<CartDTO> cart;
    private Date createAt;
}
