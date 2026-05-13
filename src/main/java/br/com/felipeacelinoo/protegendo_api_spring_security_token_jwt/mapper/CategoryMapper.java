package br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.mapper;

import org.mapstruct.Mapper;

import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.dto.request.CategoryRequest;
import br.com.felipeacelinoo.protegendo_api_spring_security_token_jwt.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest dto);
}