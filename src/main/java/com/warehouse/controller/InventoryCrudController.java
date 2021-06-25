package com.warehouse.controller;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.InventoryModelFilterDto;
import com.warehouse.dto.request.RequestInventoryModelDto;
import com.warehouse.model.InventoryModel;
import com.warehouse.model.InventoryStatus;
import com.warehouse.service.InventoryModelService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryCrudController {

    private final InventoryModelService inventoryModelService;

    public InventoryCrudController(InventoryModelService inventoryModelService) {
        this.inventoryModelService = inventoryModelService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get inventories", notes = "This method is used to get list of supplies made in the store.")
    public List<InventoryModel> getAllInventories() {
        return inventoryModelService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get inventory", notes = "This method is used to get specific inventory from the store.")
    public InventoryModel get(@PathVariable("id") @ApiParam(value = "Inventory id") Long id) {
        return inventoryModelService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INVENTORY_PERMISSION')")
    @ApiOperation(value = "Create inventory", notes = "This method is used to add a new inventory.")
    public ResponseEntity<InventoryModel> post(@RequestBody RequestInventoryModelDto requestInventoryModelDto) {
        return new ResponseEntity<>(inventoryModelService.save(
                requestInventoryModelDto.fromRequestInventoryModelDtoToInventoryModel(requestInventoryModelDto)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('INVENTORY_PERMISSION')")
    @ApiOperation(value = "Delete inventory", notes = "This method is used to delete the inventory.")
    public Long delete(@PathVariable("id") @ApiParam(value = "Inventory id") Long id) {
        return inventoryModelService.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('INVENTORY_PERMISSION')")
    @ApiOperation(value = "Update inventory", notes = "This method is used to update the inventory.")
    public InventoryModel update(@RequestBody RequestInventoryModelDto requestInventoryModelDto,
                                @PathVariable("id") @ApiParam(value = "Inventory id") long id) {
        return inventoryModelService.update(
                requestInventoryModelDto.fromRequestInventoryModelDtoToInventoryModel(requestInventoryModelDto), id);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get list of inventories with constraints")
    public ResponseEntity<PageDto<InventoryModelFilterDto>> getInventories(
            @RequestParam(value = "date", required = false) Date date,
            @RequestParam(value = "total", required = false) Double total,
            @RequestParam(value = "inventoryStatus", required = false) InventoryStatus inventoryStatus,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var response = inventoryModelService.findAll(
                new InventoryModelFilterDto(
                        date,
                        total,
                        inventoryStatus
                ),
                page,
                size,
                Sort.by(Sort.Direction.fromString(direction), sortBy)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
