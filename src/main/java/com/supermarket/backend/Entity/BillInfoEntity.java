package com.supermarket.backend.Entity;

import com.supermarket.backend.Enum.EPayment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Immutable
@Subselect("SELECT DISTINCT B.*, P.barcode, P.id AS product_id, P.name as product_name, P.cat_id, BD.unit_price, P.unit, BD.qty,  P.description, P.vendor_id FROM bills B JOIN bill_details BD ON B.id = BD.bill_id JOIN products P ON BD.product_id = P.id")
public class BillInfoEntity {
    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private EmployeeEntity employee;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;

    @Column(name = "type", length = 10)
    private String type;

    @Column(name = "discount")
    private float discount;

    @Column(name = "promotion_code", length = 10)
    private String promotionCode;

    @Column(name = "point")
    private int point;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private EPayment paymentMethod;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "cat_id")
    private int catId;

    @Column(name = "unit_price")
    private float unitPrice;

    @Column(name = "unit")
    private String unit;

    @Column(name = "qty")
    private int qty;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private VendorEntity vendor;

    @Column(name = "create_at")
    private Date createAt;
}
