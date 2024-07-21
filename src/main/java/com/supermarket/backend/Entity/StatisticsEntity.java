package com.supermarket.backend.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Immutable
@Subselect("SELECT \n" +
        "    (SELECT 1) AS id," +
        "    (SELECT COUNT(B.id) FROM bills B) AS total_bill,\n" +
        "    (SELECT COUNT(B.id) FROM bills B WHERE DATE(create_at) = CURDATE()) AS total_bill_today,\n" +
        "    (SELECT SUM(BD.qty * BD.unit_price) AS total_amount FROM bills B JOIN bill_details BD ON B.id = BD.bill_id WHERE B.create_at BETWEEN DATE_FORMAT(CURDATE() ,'%Y-%m-01')  AND LAST_DAY(CURDATE())) AS monthly_revenue,\n" +
        "    (SELECT IFNULL(SUM(BD.qty * BD.unit_price) > 0, 0) AS total_amount FROM bills B JOIN bill_details BD ON B.id = BD.bill_id WHERE DATE(B.create_at) = CURDATE()) AS today_revenue,\n" +
        "    (SELECT COUNT(C.id) FROM customers C WHERE create_at BETWEEN DATE_FORMAT(CURDATE() ,'%Y-%m-01')  AND LAST_DAY(CURDATE())) AS new_customer,\n" +
        "    (SELECT COUNT(C.id) FROM customers C) AS total_customer")
public class StatisticsEntity {
    @Id
    private int id;

    @Column(name = "total_bill")
    private Long totalBill;

    @Column(name = "total_bill_today")
    private Long totalBillToday;

    @Column(name = "monthly_revenue")
    private Double monthlyRevenue;

    @Column(name = "today_revenue")
    private Double todayRevenue;

    @Column(name = "new_customer")
    private Long newCustomer;

    @Column(name = "total_customer")
    private Long totalCustomer;
}
