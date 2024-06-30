package com.supermarket.backend.Service;

import com.supermarket.backend.Entity.EmployeeEntity;
import com.supermarket.backend.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public CustomUserDetailsService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check if the user exists in the Employee table
        Optional<EmployeeEntity> employee = employeeRepository.findByUsername(username);
        if (employee.isPresent()) {
            EmployeeEntity emp = employee.get();
            return new UserPrincipal(EmployeeImpl.build(emp), emp.getId(), emp.getEmail());
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
