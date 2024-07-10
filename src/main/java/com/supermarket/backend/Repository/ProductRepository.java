package com.supermarket.backend.Repository;

import com.supermarket.backend.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    boolean existsById(Integer id);
    boolean existsByName(String name);
    Optional<ProductEntity> findById(Integer id);

    @Query("SELECT P FROM ProductEntity P WHERE name LIKE %:name%")
    List<ProductEntity> searchByName(@Param(value = "name") String name);
}
