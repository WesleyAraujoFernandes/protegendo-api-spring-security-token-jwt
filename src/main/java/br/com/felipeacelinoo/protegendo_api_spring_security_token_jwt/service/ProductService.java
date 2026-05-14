package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.request.ProductRequest;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.exception.ResourceNotFoundException;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.mapper.ProductMapper;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.model.Category;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.model.Product;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.repository.CategoryRepository;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(ProductRequest request) {
        if (request.categoryId() == null) {
            throw new IllegalArgumentException("Category id cannot be null.");
        }
        Product product = productMapper.toEntity(request);
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category not found with id: " + request.categoryId()));
        product.setCategory(category);
        return productRepository.save(product);
    }

    public Product update(Long id, ProductRequest request) {
        Product existing = findById(id);
        if (existing == null || existing.getId() == null) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        Product updated = productMapper.toEntity(request);
        updated.setId(existing.getId());
        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Category not found with id: " + request.categoryId()));
            updated.setCategory(category);
        }
        return productRepository.save(updated);
    }

    public void delete(Long id) {
        Product product = findById(id);
        if (product != null && product.getId() != null) {
            productRepository.delete(product);
            productRepository.flush();
        } else {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
    }

    public Long count() {
        return productRepository.count();
    }

    public Long countByCategoryId(Long categoryId) {
        return productRepository.countByCategoryId(categoryId);
    }

    public Boolean existsByCategoryId(Long categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }

    public List<Product> getLowStockProducts() {
        return productRepository.findAll(Sort.by("stockQuantity").ascending());
    }

    public List<Product> getHighStockProducts() {
        return productRepository.findAll(Sort.by("stockQuantity").descending());
    }

    public List<Product> getTopLowStockProducts(Integer quantity) {
        return productRepository.findByStockQuantityLessThanEqualOrderByStockQuantityAsc(BigDecimal.valueOf(quantity));
    }

    public List<Product> getTopHighStockProducts(Integer quantity) {
        return productRepository.findByStockQuantityLessThanEqualOrderByStockQuantityDesc(BigDecimal.valueOf(quantity));
    }

    public List<Product> getCriticalStockProducts() {
        return productRepository.findCriticalStockProducts();
    }

    public List<Product> getProductByNameAndCategory(String name, Long categoryId) {
        boolean hasName = name != null && !name.isEmpty();
        boolean hasCategory = categoryId != null;
        if (hasName && hasCategory) {
            return productRepository.findByNameContainingIgnoreCaseAndCategoryId(name, categoryId);
        }
        if (hasName) {
            return productRepository.findByNameContainingIgnoreCase(name);
        }
        if (hasCategory) {
            return productRepository.findByCategoryId(categoryId);
        }
        return productRepository.findAll();
    }
}
