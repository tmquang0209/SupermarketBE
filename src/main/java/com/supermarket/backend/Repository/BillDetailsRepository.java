package com.supermarket.backend.Repository;

import com.supermarket.backend.Entity.BillDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetailsRepository extends JpaRepository<BillDetailsEntity, Integer> {
}
