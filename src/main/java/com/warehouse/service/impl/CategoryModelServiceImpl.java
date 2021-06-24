package com.warehouse.service.impl;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.CategoryModelFilterDto;
import com.warehouse.mapper.CategoryModelMapper;
import com.warehouse.model.CategoryModel;
import com.warehouse.repository.CategoryRepository;
import com.warehouse.service.CategoryModelService;
import com.warehouse.specifications.CategoryModelSpecification;
import com.warehouse.util.PageDataCreator;
import com.warehouse.util.PageDtoCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CategoryModelServiceImpl implements CategoryModelService {

    private final CategoryRepository categoryRepository;
    private final CategoryModelMapper categoryModelMapper;

    public CategoryModelServiceImpl(
            CategoryRepository categoryRepository, CategoryModelMapper categoryModelMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryModelMapper = categoryModelMapper;
    }

    @Override
    public CategoryModel findById(Long id) {
        try {
            return categoryRepository.findById(id).get();
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, categoryId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Category is not found.");
        }
    }

    @Override
    public Long deleteById(Long id) {
        try {
            categoryRepository.deleteById(id);
            return id;
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, categoryId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Category is not found.");
        }
    }

    @Override
    public CategoryModel save(CategoryModel categoryModel) {
        return categoryRepository.save(categoryModel);
    }

    @Override
    public CategoryModel update(CategoryModel categoryModel, Long id) {
        try {
            Optional<CategoryModel> categoryModelById = categoryRepository.findById(id);
            categoryModelById.get().setName(categoryModel.getName());
            return categoryRepository.save(categoryModelById.get());
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, categoryId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Category is not found.");
        }
    }

    @Override
    public PageDto<CategoryModelFilterDto> findAll(
            CategoryModelFilterDto categoryModelFilterDto, int page, int size, Sort sort) {

        var pageData = PageDataCreator.createPageData(page, size, sort);
        var predicate = CategoryModelSpecification.createPredicate(
                categoryModelFilterDto.getName()
        );

        var categoryModelPage = categoryRepository.findAll(predicate, pageData);

        return PageDtoCreator.createReadQueryResult(
                categoryModelPage, categoryModelMapper::toDto
        );
    }
}
