package com.supermarket.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

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

    @ManyToOne
    @JoinColumn(name = "cat_id", referencedColumnName = "id")
    private CategoryEntity category;

    @Column(name = "unit_price")
    private float unitPrice;

    @Column(name = "unit")
    private String unit;

    @Column(name = "in_stock")
    private int inStock;

    @Column(name = "description")
    private int description;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private VendorEntity vendor;

    @Column(name = "status")
    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "create_by", referencedColumnName = "id")
    private EmployeeEntity employee;

    @Column(name = "create_at")
    private Date createAt;
}
