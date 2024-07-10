package com.supermarket.backend.Entity;

import com.supermarket.backend.Payload.Request.ProductRequest;
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
    private int inStock = 0;

    @Column(name = "description")
    private String description;

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

    public ProductEntity(ProductRequest request){
        this.name = request.getName();
        this.category = request.getCategory();
        this.unit = request.getUnit();
        this.unitPrice = request.getUnitPrice();
        this.inStock = request.getInStock();
        this.description = request.getDescription();
        this.vendor = request.getVendor();
        this.status = request.isStatus();
        this.employee = request.getEmployee();
    }
}
