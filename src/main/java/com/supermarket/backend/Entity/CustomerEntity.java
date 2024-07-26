package com.supermarket.backend.Entity;

import com.supermarket.backend.Enum.EType;
import com.supermarket.backend.Payload.Request.CustomerRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    @Column(name = "phone_number", length = 11, unique = true)
    private String phoneNumber;

    @Column(name = "point")
    private int point;

    @Column(name = "type", length = 10)
    private EType type = EType.BRONZE;

    @Column(name = "status")
    private boolean status = true;

    @Column(name = "create_at")
    private Date createAt;

    public CustomerEntity(int id) {
        this.id = id;
    }

    public CustomerEntity(CustomerRequest request) {
        this.fullName = request.getFullName();
        this.birthday = request.getBirthday();
        this.address = request.getAddress();
        this.phoneNumber = request.getPhoneNumber();
        this.point = request.getPoint();
        this.type = request.getType();
        this.status = request.isStatus();
    }
}
