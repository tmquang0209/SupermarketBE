package com.supermarket.backend.Repository;

import com.supermarket.backend.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    boolean existsById(Integer id);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<CustomerEntity> findById(Integer id);
    Optional<CustomerEntity> findByPhoneNumber(String phone);

    @Query("SELECT c FROM CustomerEntity c WHERE c.fullName LIKE %:name%")
    List<CustomerEntity> searchByName(@Param("name") String name);

    @Query("SELECT c FROM CustomerEntity c WHERE c.fullName LIKE %:keyword% OR c.phoneNumber LIKE %:keyword%")
    List<CustomerEntity> searchByKeyword(@Param("keyword") String keyword);
}
