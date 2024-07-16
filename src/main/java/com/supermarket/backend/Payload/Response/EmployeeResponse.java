package com.supermarket.backend.Payload.Response;

import com.supermarket.backend.Entity.DepartmentEntity;
import com.supermarket.backend.Entity.EmployeeEntity;
import com.supermarket.backend.Enum.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private Integer id;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private String fullName;
    private Date birthday;
    private ERole role;
    private DepartmentEntity department;
    private boolean status;

    public EmployeeResponse(EmployeeEntity employee) {
        this.id = employee.getId();
        this.username = employee.getUsername();
        this.email = employee.getEmail();
        this.phoneNumber = employee.getPhoneNumber();
        this.address = employee.getAddress();
        this.fullName = employee.getFullName();
        this.birthday = employee.getBirthday();
        this.role = employee.getRole();
        this.department = employee.getDepartment();
        this.status = employee.isStatus();
    }
}
