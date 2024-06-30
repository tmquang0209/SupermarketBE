package com.supermarket.backend.Payload.Request;

import com.supermarket.backend.Entity.DepartmentEntity;
import com.supermarket.backend.Enum.ERole;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Date;

@Data
public class SignupRequest {
    @NotBlank
    String username;

    @NotBlank
    String password;

    String email;

    String fullName;

    Date birthDay;

    ERole role;

    DepartmentEntity department;
    boolean status;
}
