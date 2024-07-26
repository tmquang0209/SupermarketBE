package com.supermarket.backend.Payload.Request;

import com.supermarket.backend.Entity.DepartmentEntity;
import com.supermarket.backend.Enum.ERole;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateInfoRequest {
    @NotBlank
    String username;

    String email;

    String phoneNumber;

    String address;

    String fullName;

    Date birthDay;

    ERole role;

    DepartmentEntity department;

    boolean status;

    @Override
    public String toString() {
        return "UpdateInfoRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", fullName='" + fullName + '\'' +
                ", birthday=" + birthDay +
                ", role=" + role +
                ", department=" + (department != null ? department.getName() : null) +
                ", status=" + status +
                '}';
    }
}
