package com.warehouse.controller;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.CategoryModelFilterDto;
import com.warehouse.dto.request.RequestCategoryModelDto;
import com.warehouse.model.CategoryModel;
import com.warehouse.service.CategoryModelService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryCrudController {

    private final CategoryModelService categoryModelService;

    public CategoryCrudController(CategoryModelService categoryModelService) {
        this.categoryModelService = categoryModelService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get category", notes = "This method is used to get list of categories.")
    public CategoryModel get(@PathVariable("id") @ApiParam(value = "Category id") Long id) {
        return categoryModelService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SUPPLY_PERMISSION')")
    @ApiOperation(value = "Create category", notes = "This method is used to add a new category.")
    public ResponseEntity<CategoryModel> post(@RequestBody RequestCategoryModelDto requestCategoryModelDto) {
        return new ResponseEntity<>(categoryModelService.save(
                requestCategoryModelDto.fromRequestCategoryModelDtoToCategoryModel(requestCategoryModelDto)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ORDER_PERMISSION')")
    @ApiOperation(value = "Remove category", notes = "This method is used to delete the category.")
    public Long delete(@PathVariable("id") @ApiParam(value = "Category id") Long id) {
        return categoryModelService.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPPLY_PERMISSION')")
    @ApiOperation(value = "Update category", notes = "This method is used to update the category.")
    public CategoryModel update(@RequestBody RequestCategoryModelDto requestCategoryModelDto,
                                @PathVariable("id") @ApiParam(value = "Category id") Long id) {
        return categoryModelService.update(
                requestCategoryModelDto.fromRequestCategoryModelDtoToCategoryModel(requestCategoryModelDto), id);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get list of categories with constraints")
    public ResponseEntity<PageDto<CategoryModelFilterDto>> getCategories(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var response = categoryModelService.findAll(
                new CategoryModelFilterDto(name),
                page,
                size,
                Sort.by(Sort.Direction.fromString(direction), sortBy)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
