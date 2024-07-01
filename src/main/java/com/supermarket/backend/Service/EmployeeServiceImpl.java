package com.supermarket.backend.Service;

import com.supermarket.backend.Entity.EmployeeEntity;
import com.supermarket.backend.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public List<EmployeeEntity> getAll() {
        return employeeRepository.findAll();
    }

    public EmployeeEntity getById(Integer id){
        return employeeRepository.getById(id);
    }

    public EmployeeEntity getByUsername(String username){
        return employeeRepository.findByUsername(username).orElse(null);
    }

    public EmployeeEntity update(Integer id, EmployeeEntity data){
        Optional<EmployeeEntity> findEmployeeOptional = employeeRepository.findById(id);

        if(findEmployeeOptional.isPresent()){
            EmployeeEntity findEmployee = findEmployeeOptional.get();
            findEmployee.setFullName(data.getFullName());
            findEmployee.setEmail(data.getEmail());
            findEmployee.setPhoneNumber(data.getPhoneNumber());
            findEmployee.setAddress(data.getAddress());
            findEmployee.setBirthday(data.getBirthday());
            findEmployee.setRole(data.getRole());
            findEmployee.setDepartment(data.getDepartment());
            findEmployee.setStatus(data.isStatus());

            return employeeRepository.save(findEmployee);
        }
        return null;
    }

    public EmployeeEntity changePassword(Integer id, String newPassword){
        Optional<EmployeeEntity> findEmployeeOptional = employeeRepository.findById(id);

        if(findEmployeeOptional.isPresent()){
            EmployeeEntity findEmployee = findEmployeeOptional.get();
            findEmployee.setPassword(newPassword);
            return employeeRepository.save(findEmployee);
        }
        return null;
    }
}
