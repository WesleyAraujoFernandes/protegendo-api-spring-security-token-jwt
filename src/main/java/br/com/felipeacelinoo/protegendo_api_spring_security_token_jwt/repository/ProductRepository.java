package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByStockQuantityAsc();

    List<Product> findAllByOrderByStockQuantityDesc();

    @NonNull
    List<Product> findAll(@NonNull Sort sort);

    List<Product> findByStockQuantityLessThanEqualOrderByStockQuantityAsc(BigDecimal quantity);

    List<Product> findByStockQuantityLessThanEqualOrderByStockQuantityDesc(BigDecimal quantity);

    @Query("""
            SELECT p
            FROM Product p
            WHERE p.minimumStock IS NOT NULL
            AND p.stockQuantity <= p.minimumStock
            AND p.stockQuantity >= 0
            """)
    List<Product> findCriticalStockProducts();

    List<Product> findByNameContainingIgnoreCaseAndCategoryId(String name, Long categoryId);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByNameContainingIgnoreCaseAndStockQuantityLessThanEqual(String name, BigDecimal quantity);

    Boolean existsByNameAndCategoryId(String name, Long categoryId);

    Long countByCategoryId(Long categoryId);

    Boolean existsByCategoryId(Long categoryId);
}
