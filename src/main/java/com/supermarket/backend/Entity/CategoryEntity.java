package com.supermarket.backend.Entity;

import com.supermarket.backend.Payload.Request.CategoryRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "create_by", referencedColumnName = "id")
    private EmployeeEntity createBy;

    @Column(name = "create_at")
    private Date createAt;

    public CategoryEntity(CategoryRequest request, EmployeeEntity createBy){
        this.name = request.getName();
        this.description = request.getDescription();
        this.status = request.isStatus();
        this.createBy = createBy;
    }
}
