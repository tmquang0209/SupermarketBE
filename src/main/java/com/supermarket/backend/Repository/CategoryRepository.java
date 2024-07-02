package com.supermarket.backend.Repository;

import com.supermarket.backend.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    boolean existsById(Integer id);

    boolean existsByName(String name);

    @Query("SELECT c FROM CategoryEntity c WHERE c.name LIKE %:name%")
    List<CategoryEntity> searchByName(@Param("name") String name);
}
