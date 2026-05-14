package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.request.CategoryRequest;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.response.CategoryResponse;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryRequest dto);

    CategoryResponse toResponse(Category entity);

    CategoryRequest toRequest(Category entity);
}