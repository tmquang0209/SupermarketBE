package com.supermarket.backend.Repository;

import com.supermarket.backend.Entity.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Integer> {
    boolean existsById(Integer id);
    Optional<VendorEntity> findById(Integer id);

    @Query("SELECT V FROM VendorEntity V WHERE name LIKE %:name%")
    List<VendorEntity> findByName(@Param("name") String name);
}
