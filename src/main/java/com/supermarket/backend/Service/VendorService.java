package com.supermarket.backend.Service;

import com.supermarket.backend.Entity.VendorEntity;
import com.supermarket.backend.Payload.Request.VendorRequest;
import com.supermarket.backend.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    public List<VendorEntity> getAllVendors() {
        return vendorRepository.findAll();
    }

    public VendorEntity getById(Integer id) {
        return vendorRepository.findById(id).orElse(null);
    }

    public VendorEntity saveVendor(VendorEntity vendorEntity) {
        boolean isExists = vendorRepository.existsById(vendorEntity.getId());
        if (isExists) throw new RuntimeException(vendorEntity.getName() + " already exists.");
        return vendorRepository.save(vendorEntity);
    }

    public List<VendorEntity> searchByName(String name){
        return vendorRepository.findByName(name);
    }

    public VendorEntity update(Integer id, VendorRequest vendorRequest) {
        Optional<VendorEntity> optionalVendor = vendorRepository.findById(id);

        if (optionalVendor.isEmpty()) throw new RuntimeException("Vendor does not exist.");

        VendorEntity vendor = optionalVendor.get();
        vendor.setName(vendorRequest.getName());
        vendor.setDescription(vendorRequest.getDescription());
        vendor.setStatus(vendorRequest.isStatus());

        return vendorRepository.save(vendor);
    }

    public void delete(Integer id) {
        Optional<VendorEntity> optionalVendor = vendorRepository.findById(id);

        if (optionalVendor.isEmpty()) throw new RuntimeException("Vendor does not exist.");

        vendorRepository.deleteById(id);
    }
}
