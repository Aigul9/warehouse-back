package com.warehouse.mapper;

import com.warehouse.dto.filter.CategoryModelFilterDto;
import com.warehouse.model.CategoryModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class CategoryModelMapper {
    public CategoryModelFilterDto toDto(CategoryModel categoryModel) {
        return new CategoryModelFilterDto(categoryModel.getName());
    }
}
