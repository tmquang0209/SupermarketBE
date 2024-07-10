package com.supermarket.backend.Repository;

import com.supermarket.backend.Entity.DepartmentEntity;
import com.supermarket.backend.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
    boolean existsById(Integer id);
    boolean existsByName(String name);
    Optional<DepartmentEntity> findById(Integer id);

    @Query("SELECT D FROM DepartmentEntity D WHERE name LIKE %:name%")
    List<DepartmentEntity> searchByName(@Param(value = "name") String name);
}
