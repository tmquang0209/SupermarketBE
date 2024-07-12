package com.supermarket.backend.Repository;

import com.supermarket.backend.Entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<BillEntity, Integer> {

}
