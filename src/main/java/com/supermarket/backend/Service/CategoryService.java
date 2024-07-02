package com.supermarket.backend.Service;

import com.supermarket.backend.Entity.CategoryEntity;
import com.supermarket.backend.Payload.Request.CategoryRequest;
import com.supermarket.backend.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<CategoryEntity> searchByName(String name) {
        return categoryRepository.searchByName(name);
    }

    public CategoryEntity getById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public CategoryEntity saveCategory(CategoryEntity categoryEntity) {
        boolean isExists = categoryRepository.existsByName(categoryEntity.getName());
        if (isExists) throw new RuntimeException(categoryEntity.getName() + " is exists.");
        return categoryRepository.save(categoryEntity);
    }

    public CategoryEntity update(Integer id, CategoryRequest categoryEntity) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()) throw new RuntimeException("Category is not exists.");

        CategoryEntity category = optionalCategory.get();
        category.setName(categoryEntity.getName());
        category.setDescription(categoryEntity.getDescription());
        category.setStatus(categoryEntity.isStatus());

        return categoryRepository.save(category);
    }

    public void delete(Integer id){
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()) throw new RuntimeException("Category is not exists.");

        categoryRepository.deleteById(id);
    }
}
