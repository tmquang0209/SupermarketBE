package com.supermarket.backend.Payload.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private int productId;
    private String barcode;
    private String productName;
    private float unitPrice;
    private int qty;
}
