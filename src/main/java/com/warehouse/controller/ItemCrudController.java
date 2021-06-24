package com.warehouse.controller;

import com.warehouse.dto.filter.ItemModelFilterDto;
import com.warehouse.dto.PageDto;
import com.warehouse.dto.request.RequestItemModelDto;
import com.warehouse.model.ItemModel;
import com.warehouse.service.CategoryModelService;
import com.warehouse.service.ItemModelService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class ItemCrudController {

    private final ItemModelService itemModelService;
    private final CategoryModelService categoryModelService;

    public ItemCrudController(ItemModelService itemModelService, CategoryModelService categoryModelService) {
        this.itemModelService = itemModelService;
        this.categoryModelService = categoryModelService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get all items", notes = "This method is used to get list of items.")
    public List<ItemModel> getAllItems() {
        return itemModelService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get item", notes = "This method is used to get specific item.")
    public ItemModel get(@PathVariable("id") @ApiParam(value = "Item id") Long id) {
        return itemModelService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SUPPLY_PERMISSION')")
    @ApiOperation(value = "Create item", notes = "This method is used to add a new item.")
    public ResponseEntity<ItemModel> post(@RequestBody RequestItemModelDto requestItemModelDto) {
        ItemModel itemModel = requestItemModelDto.fromRequestItemModelDtoToItemModel(
                requestItemModelDto, categoryModelService);
        return new ResponseEntity<>(itemModelService.save(itemModel), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ORDER_PERMISSION')")
    @ApiOperation(value = "Delete item", notes = "This method is used to delete the item.")
    public Long delete(@PathVariable("id") @ApiParam(value = "Item id") Long id) {
        return itemModelService.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORDER_PERMISSION')")
    @ApiOperation(value = "Update item", notes = "This method is used to update the item.")
    public ItemModel update(@RequestBody RequestItemModelDto requestItemModelDto,
                            @PathVariable("id") @ApiParam(value = "Item id") long id) {
        ItemModel itemModel = requestItemModelDto.fromRequestItemModelDtoToItemModel(
                requestItemModelDto, categoryModelService);
        return itemModelService.update(itemModel, id);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get list of items with constraints")
    public ResponseEntity<PageDto<ItemModelFilterDto>> getItems(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var response = itemModelService.findAll(
                new ItemModelFilterDto(
                        name,
                        quantity,
                        price,
                        categoryId
                ),
                page,
                size,
                Sort.by(Sort.Direction.fromString(direction), sortBy)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
