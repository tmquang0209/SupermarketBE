package com.supermarket.backend.Repository;

import com.supermarket.backend.Entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
    boolean existsById(Integer id);
    Optional<EmployeeEntity> findById(Integer id);
    Optional<EmployeeEntity> findByUsername(String username);
}
