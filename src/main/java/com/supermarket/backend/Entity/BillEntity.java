package com.supermarket.backend.Entity;

import com.supermarket.backend.Enum.EPayment;
import com.supermarket.backend.Payload.Request.BillRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bills")
public class BillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "create_at")
    private Date createAt = new Date();

    public BillEntity(BillRequest request){
        this.employee = request.getEmployee();
        this.customer = request.getCustomer();
        this.type = request.getType();
        this.discount = request.getDiscount();
        this.promotionCode = request.getPromotionCode();
        this.point = request.getPoint();
        this.paymentMethod = request.getPaymentMethod();
    }
}
