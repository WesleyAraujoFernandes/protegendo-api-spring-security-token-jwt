package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.response;

import java.math.BigDecimal;

public record ProductResponse(Long id,
        String name,
        Long categoryId,
        String categoryName,
        String unit,
        BigDecimal purchasePrice,
        BigDecimal salePrice,
        Integer stockQuantity,
        Integer minimumStock) {

}