package com.warehouse.service;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.CategoryModelFilterDto;
import com.warehouse.model.CategoryModel;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryModelService {
    CategoryModel findById(Long id);

    List<CategoryModel> findAll();

    Long deleteById(Long id);

    CategoryModel save(CategoryModel model);

    CategoryModel update(CategoryModel model, Long id);

    PageDto<CategoryModelFilterDto> findAll(
            CategoryModelFilterDto categoryModelFilterDto, int page, int size, Sort sort);
}
