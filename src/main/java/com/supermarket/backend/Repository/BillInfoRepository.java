package com.supermarket.backend.Repository;

import com.supermarket.backend.Entity.BillInfoEntity;
import com.supermarket.backend.Payload.Dto.BillDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillInfoRepository extends JpaRepository<BillInfoEntity, Integer> {
//    @Query("SELECT new com.supermarket.backend.Payload.Dto.BillDTO(BI.id, BI.employee, BI.customer, BI.type, BI.discount, BI.promotionCode, BI.point, BI.paymentMethod) FROM BillInfoEntity BI WHERE BI.id = :id")
    @Query("SELECT BI FROM BillInfoEntity BI WHERE BI.id = :id")
    List<BillInfoEntity> getAll(@Param(value = "id") int id);
}
