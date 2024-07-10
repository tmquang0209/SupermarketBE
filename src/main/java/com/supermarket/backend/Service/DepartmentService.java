package com.supermarket.backend.Service;

import com.supermarket.backend.Entity.DepartmentEntity;
import com.supermarket.backend.Payload.Request.DepartmentRequest;
import com.supermarket.backend.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<DepartmentEntity> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<DepartmentEntity> searchByName(String name){
        return departmentRepository.searchByName(name);
    }

    public DepartmentEntity getById(Integer id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public DepartmentEntity saveDepartment(DepartmentEntity departmentEntity) {
        boolean isExists = departmentRepository.existsByName(departmentEntity.getName());
        if (isExists) throw new RuntimeException(departmentEntity.getName() + " already exists.");
        return departmentRepository.save(departmentEntity);
    }

    public DepartmentEntity update(Integer id, DepartmentRequest departmentRequest) {
        Optional<DepartmentEntity> optionalDepartment = departmentRepository.findById(id);

        if (optionalDepartment.isEmpty()) throw new RuntimeException("Department does not exist.");

        DepartmentEntity department = optionalDepartment.get();
        department.setName(departmentRequest.getName());
        department.setDescription(departmentRequest.getDescription());

        return departmentRepository.save(department);
    }

    public void delete(Integer id) {
        Optional<DepartmentEntity> optionalDepartment = departmentRepository.findById(id);

        if (optionalDepartment.isEmpty()) throw new RuntimeException("Department does not exist.");

        departmentRepository.deleteById(id);
    }
}
