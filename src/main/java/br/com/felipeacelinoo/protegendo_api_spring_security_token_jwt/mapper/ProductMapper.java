package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.request.ProductRequest;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.response.ProductResponse;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // Request → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductRequest dto);

    // Update com MappingTarget
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateEntityFromRequest(
            ProductRequest dto,
            @org.mapstruct.MappingTarget Product product);

    // Entity → Response
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toResponse(Product product);

}