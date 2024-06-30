package com.supermarket.backend.Entity;

import com.supermarket.backend.Enum.ERole;
import com.supermarket.backend.Payload.Request.SignupRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "employees")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "role")
    private ERole role;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private DepartmentEntity department;

    @Column(name = "status")
    private boolean status;

    public EmployeeEntity(String username, String password, String email, String fullName, Date birthday, ERole role, DepartmentEntity department, boolean status) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.birthday = birthday;
        this.role = role;
        this.department = department;
        this.status = status;
    }

    public EmployeeEntity(SignupRequest signupRequest){
        this.username = signupRequest.getUsername();
        this.password = signupRequest.getPassword();
        this.email = signupRequest.getEmail();
        this.fullName = signupRequest.getFullName();
        this.birthday = signupRequest.getBirthDay();
        this.role = signupRequest.getRole();
        this.department = signupRequest.getDepartment();
        this.status = true;
    }
}
