package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.request.ProductRequest;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.response.ProductResponse;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.mapper.ProductMapper;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.model.Product;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.service.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> search(@RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId) {
        List<Product> products = productService.getProductByNameAndCategory(name, categoryId);
        return ResponseEntity.ok(products.stream().map(mapper::toResponse).toList());
    }

    @GetMapping("/by-name-and-category")
    public List<ProductResponse> getProductByNameAndCategory(@RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId) {
        if (name == null && categoryId == null) {
            return productService.findAll().stream().map(mapper::toResponse).toList();
        }
        return productService.getProductByNameAndCategory(name, categoryId).stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(productService.findById(id)));
    }

    @GetMapping("/category/{id}")
    public boolean existsByCategoryId(@PathVariable Long id) {
        return productService.existsByCategoryId(id);
    }

    @GetMapping("/category/{id}/count")
    public Long countByCategoryId(@PathVariable Long id) {
        return productService.countByCategoryId(id);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody ProductRequest request) {
        Product product = productService.save(request);
        return ResponseEntity.status(201).body(mapper.toResponse(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        Product product = productService.update(id, request);
        return ResponseEntity.ok(mapper.toResponse(product));
    }

    @GetMapping("/low-stock")
    public List<ProductResponse> getLowStockProducts() {
        return productService.getLowStockProducts().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/high-stock")
    public List<ProductResponse> getHighStockProducts() {
        return productService.getHighStockProducts().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/alert/low-stock")
    public List<ProductResponse> getTopLowStockProducts(@RequestParam(required = false) Integer quantity) {
        return productService.getTopLowStockProducts(quantity).stream().map(mapper::toResponse).toList();
    }
}
