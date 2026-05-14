package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.request.CategoryRequest;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.response.CategoryResponse;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.mapper.CategoryMapper;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.model.Category;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        return ResponseEntity.ok(categoryService.findAll().stream().map(categoryMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryMapper.toResponse(categoryService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/find-by-name/{name}")
    public ResponseEntity<List<CategoryResponse>> findByCategoryNameContainingIgnoreCase(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.findByCategoryNameContainingIgnoreCase(name).stream()
                .map(categoryMapper::toResponse).toList());
    }
}
