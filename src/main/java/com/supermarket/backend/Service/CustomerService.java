package com.supermarket.backend.Service;

import com.supermarket.backend.Entity.CustomerEntity;
import com.supermarket.backend.Payload.Request.CustomerRequest;
import com.supermarket.backend.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<CustomerEntity> search(String keyword) {
        return customerRepository.searchByKeyword(keyword);
    }

    public List<CustomerEntity> searchByName(String name) {
        return customerRepository.searchByName(name);
    }

    public CustomerEntity getById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    public CustomerEntity saveCustomer(CustomerEntity customerEntity) {
        boolean isExists = customerRepository.existsByPhoneNumber(customerEntity.getPhoneNumber());
        if (isExists) throw new RuntimeException(customerEntity.getPhoneNumber() + " already exists.");
        return customerRepository.save(customerEntity);
    }

    public CustomerEntity update(Integer id, CustomerRequest customerRequest) {
        Optional<CustomerEntity> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isEmpty()) throw new RuntimeException("Customer does not exist.");

        CustomerEntity customer = optionalCustomer.get();
        customer.setFullName(customerRequest.getFullName());
        customer.setBirthday(customerRequest.getBirthday());
        customer.setAddress(customerRequest.getAddress());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setPoint(customerRequest.getPoint());
        customer.setType(customerRequest.getType());
        customer.setStatus(customerRequest.isStatus());

        return customerRepository.save(customer);
    }

    public CustomerEntity update(Integer id, CustomerEntity data) {
        Optional<CustomerEntity> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isEmpty()) throw new RuntimeException("Customer does not exist.");

        CustomerEntity customer = optionalCustomer.get();
        customer.setFullName(data.getFullName());
        customer.setBirthday(data.getBirthday());
        customer.setAddress(data.getAddress());
        customer.setPhoneNumber(data.getPhoneNumber());
        customer.setPoint(data.getPoint());
        customer.setType(data.getType());
        customer.setStatus(data.isStatus());

        return customerRepository.save(customer);
    }

    public void delete(Integer id) {
        Optional<CustomerEntity> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isEmpty()) throw new RuntimeException("Customer does not exist.");

        customerRepository.deleteById(id);
    }

    public void minusPoint(Integer id, int point){
        CustomerEntity customer = getById(id);
        customer.setPoint(customer.getPoint() - point);
        customerRepository.save(customer);
    }

    public boolean checkPoint(Integer id, int point){
        return getById(id).getPoint() > point;
    }
}
