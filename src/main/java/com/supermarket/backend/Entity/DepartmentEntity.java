package com.supermarket.backend.Entity;

import com.supermarket.backend.Payload.Request.DepartmentRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "departments")
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "create_at")
    private Date createAt;

    public DepartmentEntity(DepartmentRequest request){
        this.name = request.getName();
        this.description = request.getDescription();
    }
}
