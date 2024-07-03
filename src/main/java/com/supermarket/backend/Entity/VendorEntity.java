package com.supermarket.backend.Entity;

import com.supermarket.backend.Payload.Request.VendorRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vendors")
public class VendorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private boolean status = true;

    public VendorEntity(VendorRequest request){
        this.name = request.getName();
        this.phoneNumber = request.getPhoneNumber();
        this.email = request.getEmail();
        this.address = request.getAddress();
        this.description = request.getDescription();
        this.status = request.isStatus();
    }
}
