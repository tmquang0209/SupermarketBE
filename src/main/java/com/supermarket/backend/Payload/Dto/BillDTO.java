package com.supermarket.backend.Payload.Dto;

import com.supermarket.backend.Entity.CustomerEntity;
import com.supermarket.backend.Entity.EmployeeEntity;
import com.supermarket.backend.Enum.EPayment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    private Integer id;
    private EmployeeEntity employee;
    private CustomerEntity customer;
    private String type;
    private float discount;
    private String promotionCode;
    private int point; // point use to purchase
    private EPayment paymentMethod = EPayment.CARD;
    private List<CartDTO> cart;
    private float total;

    public BillDTO(Integer id, EmployeeEntity employee, CustomerEntity customer, String type, float discount, String promotionCode, int point, EPayment paymentMethod) {
        this.id = id;
        this.employee = employee;
        this.customer = customer;
        this.type = type;
        this.discount = discount;
        this.promotionCode = promotionCode;
        this.point = point;
        this.paymentMethod = paymentMethod;
    }
}
