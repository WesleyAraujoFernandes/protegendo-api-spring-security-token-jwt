package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.request.CategoryRequest;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.exception.ResourceNotFoundException;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.mapper.CategoryMapper;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.model.Category;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category save(CategoryRequest request) {
        return categoryRepository.save(categoryMapper.toEntity(request));
    }

    public Category update(Long id, CategoryRequest request) {
        Category existing = findById(id);
        if (existing == null || existing.getId() == null) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }

        Category updated = categoryMapper.toEntity(request);
        updated.setId(existing.getId());
        return categoryRepository.save(updated);
    }

    public void delete(Long id) {
        Category category = findById(id);
        if (category != null && category.getId() != null) {
            try {
                categoryRepository.delete(category);
                categoryRepository.flush();
            } catch (DataIntegrityViolationException e) {
                throw new ResourceNotFoundException("Exists products with category id: " + id);
            }
        } else {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
    }

    public List<Category> findByCategoryNameContainingIgnoreCase(String categoryName) {
        if (categoryName == null) {
            return categoryRepository.findAll();
        }
        return categoryRepository.findByNameContainingIgnoreCase(categoryName);
    }
}
