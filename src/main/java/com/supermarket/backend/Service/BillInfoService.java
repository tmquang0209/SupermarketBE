package com.supermarket.backend.Service;

import com.supermarket.backend.Entity.BillInfoEntity;
import com.supermarket.backend.Payload.Dto.BillDTO;
import com.supermarket.backend.Payload.Dto.CartDTO;
import com.supermarket.backend.Repository.BillInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillInfoService {

    @Autowired
    private BillInfoRepository billInfoRepository;

    public BillDTO getBillInfoById(int id) {
        List<BillInfoEntity> result = billInfoRepository.getAll(id);
        BillInfoEntity billInfoEntity = result.getFirst();

        BillDTO billDetails = new BillDTO(
                billInfoEntity.getId(),
                billInfoEntity.getEmployee(),
                billInfoEntity.getCustomer(),
                billInfoEntity.getType(),
                billInfoEntity.getDiscount(),
                billInfoEntity.getPromotionCode(),
                billInfoEntity.getPoint(),
                billInfoEntity.getPaymentMethod()
        );
        List<CartDTO> cart = new ArrayList<>();

        float total = - billInfoEntity.getPoint() * 100;

        for (BillInfoEntity bill : result) {
            CartDTO item = new CartDTO(
                    bill.getProductId(),
                    bill.getBarcode(),
                    bill.getProductName(),
                    bill.getUnitPrice(),
                    bill.getQty()
            );

            total += bill.getUnitPrice() * bill.getQty();

            cart.add(item);
        }

        billDetails.setCart(cart);
        billDetails.setTotal(total);

        return billDetails;
    }
}
