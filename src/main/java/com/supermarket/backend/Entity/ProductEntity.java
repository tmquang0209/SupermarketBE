package com.supermarket.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "cat_id")
    private CategoryEntity category;

    @Column(name = "unit_price")
    private float unitPrice;

    @Column(name = "unit")
    private String unit;

    @Column(name = "in_stock")
    private int inStock;

    @Column(name = "description")
    private int description;

    @Column(name = "vendor_id")
    private VendorEntity vendor;

    @Column(name = "status")
    private boolean status = true;

    @Column(name = "create_by")
    private EmployeeEntity employee;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
}
