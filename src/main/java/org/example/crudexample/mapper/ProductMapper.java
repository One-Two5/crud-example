package org.example.crudexample.mapper;

import org.example.crudexample.dto.*;
import org.example.crudexample.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductCreateDto createDto);

    ProductResponse toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ProductUpdateDto updateDto, @MappingTarget Product product);

    @Mapping(target = "id", ignore = true)
    List<Product> toEntityList(List<ProductCreateDto> dtoList);

    List<ProductResponse> toResponseList(List<Product> productList);
}
