package com.supermarket.backend.Entity;

import com.supermarket.backend.Enum.ERole;
import com.supermarket.backend.Payload.Request.SignupRequest;
import com.supermarket.backend.Payload.Request.UpdateInfoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
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
        this.address = signupRequest.getAddress();
        this.phoneNumber = signupRequest.getPhoneNumber();
        this.role = signupRequest.getRole();
        this.department = signupRequest.getDepartment();
        this.status = true;
    }

    public EmployeeEntity(UpdateInfoRequest updateInfoRequest){
        this.username = updateInfoRequest.getUsername();
        this.email = updateInfoRequest.getEmail();
        this.fullName = updateInfoRequest.getFullName();
        this.birthday = updateInfoRequest.getBirthDay();
        this.address = updateInfoRequest.getAddress();
        this.phoneNumber = updateInfoRequest.getPhoneNumber();
        this.role = updateInfoRequest.getRole();
        this.department = updateInfoRequest.getDepartment();
        this.status = updateInfoRequest.isStatus();
    }

    @Override
    public String toString() {
        return "EmployeeEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", fullName='" + fullName + '\'' +
                ", birthday=" + birthday +
                ", role=" + role +
                ", department=" + (department != null ? department.getName() : null) +
                ", status=" + status +
                '}';
    }

}
