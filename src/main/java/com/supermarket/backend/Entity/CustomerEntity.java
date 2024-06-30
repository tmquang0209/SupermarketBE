package com.supermarket.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "full_name", columnDefinition = "MEDIUMTEXT")
    private String fullName;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "address", columnDefinition = "MEDIUMTEXT")
    private String address;

    @Column(name = "phone_number", length = 255)
    private String phoneNumber;

    @Column(name = "point")
    private int point;

    @Column(name = "type", length = 10)
    private String type;

    @Column(name = "status")
    private boolean status;

    @Column(name = "create_at")
    private Date createAt;
}
