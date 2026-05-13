package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCategoryNameContainingIgnoreCase(String categoryName);
}
