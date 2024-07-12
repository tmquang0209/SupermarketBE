package com.supermarket.backend.Repository;

import com.supermarket.backend.Entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionEntity, Integer> {
    boolean existsByCode(String code);

    Optional<PromotionEntity> findByCode(String code);

    @Query("SELECT P FROM PromotionEntity P WHERE P.customer.id = :cusId")
    List<PromotionEntity> findByCusId(@Param(value = "cusId") Integer cusId);
}
