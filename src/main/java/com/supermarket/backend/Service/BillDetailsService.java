package com.supermarket.backend.Service;

import com.supermarket.backend.Entity.BillDetailsEntity;
import com.supermarket.backend.Repository.BillDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillDetailsService {
    @Autowired
    private BillDetailsRepository billDetailsRepository;

    public List<BillDetailsEntity> saveAll(List<BillDetailsEntity> billDetailsEntities){
        return  billDetailsRepository.saveAll(billDetailsEntities);
    }

    public BillDetailsEntity save(BillDetailsEntity billDetailsEntity){
        return  billDetailsRepository.save(billDetailsEntity);
    }
}
