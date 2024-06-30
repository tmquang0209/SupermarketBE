package com.supermarket.backend.Service;

import com.supermarket.backend.Entity.EmployeeEntity;
import com.supermarket.backend.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements UserDetailsService {
    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeEntity saveEmployee(EmployeeEntity employee) {
        EmployeeEntity employeeEntity = employeeRepository.findByUsername(employee.getUsername()).orElse(null);
        System.out.println(employeeEntity);

        if (employeeEntity == null)
            return employeeRepository.save(employee);
        else
            return null;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmployeeEntity employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return EmployeeImpl.build(employee);
    }
}
