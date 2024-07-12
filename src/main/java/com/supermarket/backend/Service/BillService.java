package com.supermarket.backend.Service;

import com.supermarket.backend.Entity.BillEntity;
import com.supermarket.backend.Repository.BillDetailsRepository;
import com.supermarket.backend.Repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BillDetailsRepository billDetailsRepository;

    public List<BillEntity> getDetails(Integer id) {
        return billRepository.findAll();
    }

    public BillEntity save(BillEntity bill) {
        customerService.minusPoint(bill.getCustomer().getId(), bill.getPoint());
        return billRepository.save(bill);
    }
}
