package com.warehouse.controller;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.InventoryItemModelFilterDto;
import com.warehouse.dto.request.RequestInventoryItemModelDto;
import com.warehouse.model.ItemModel;
import com.warehouse.model.InventoryItemModel;
import com.warehouse.model.InventoryItemPK;
import com.warehouse.model.InventoryModel;
import com.warehouse.service.ItemModelService;
import com.warehouse.service.InventoryItemModelService;
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

@RestController
@RequestMapping("/api/v1/inventory_item")
public class InventoryItemCrudController {

    private final InventoryItemModelService inventoryItemModelService;
    private final InventoryModelService inventoryModelService;
    private final ItemModelService itemModelService;

    public InventoryItemCrudController(InventoryItemModelService inventoryItemModelService,
                                      InventoryModelService inventoryModelService,
                                      ItemModelService itemModelService) {
        this.inventoryItemModelService = inventoryItemModelService;
        this.inventoryModelService = inventoryModelService;
        this.itemModelService = itemModelService;
    }


    @GetMapping("/{inventory_id}/{item_id}")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get inventory row", notes = "This method is used to get list of all items in an inventory.")
    public InventoryItemModel get(@PathVariable("inventory_id") @ApiParam(value = "Inventory id") Long inventoryId,
                                 @PathVariable("item_id") @ApiParam(value = "Item id") Long itemId) {
        InventoryItemPK inventoryItemPK = new InventoryItemPK();

        InventoryModel inventoryModel = inventoryModelService.findById(inventoryId);
        ItemModel itemModel = itemModelService.findById(itemId);

        inventoryItemPK.setInventory(inventoryModel);
        inventoryItemPK.setItem(itemModel);
        return inventoryItemModelService.findById(inventoryItemPK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INVENTORY_PERMISSION')")
    @ApiOperation(value = "Create inventory row", notes = "This method is used to add a new inventory row.")
    public ResponseEntity<InventoryItemModel> post(@RequestBody RequestInventoryItemModelDto requestInventoryItemModelDto) {
        InventoryItemModel inventoryItemModel = requestInventoryItemModelDto
                .fromRequestInventoryItemModelDtoToInventoryItemModel(requestInventoryItemModelDto,
                        inventoryModelService, itemModelService);
        return new ResponseEntity<>(inventoryItemModelService.save(inventoryItemModel), HttpStatus.OK);
    }

    @DeleteMapping("/{inventory_id}/{item_id}")
    @PreAuthorize("hasAuthority('INVENTORY_PERMISSION')")
    @ApiOperation(value = "Delete inventory row", notes = "This method is used to delete the inventory row.")
    public InventoryItemPK delete(@PathVariable("inventory_id") @ApiParam(value = "Inventory id") Long inventoryId,
                                 @PathVariable("item_id") @ApiParam(value = "Item id") Long itemId) {
        InventoryItemPK inventoryItemPK = new InventoryItemPK();

        InventoryModel inventoryModel = inventoryModelService.findById(inventoryId);
        ItemModel itemModel = itemModelService.findById(itemId);

        inventoryItemPK.setInventory(inventoryModel);
        inventoryItemPK.setItem(itemModel);
        return inventoryItemModelService.deleteById(inventoryItemPK);
    }

    @PutMapping("/{inventory_id}/{item_id}")
    @PreAuthorize("hasAuthority('INVENTORY_PERMISSION')")
    @ApiOperation(value = "Update inventory row", notes = "This method is used to update the inventory row.")
    public InventoryItemModel update(@RequestBody RequestInventoryItemModelDto requestInventoryItemModelDto,
                                    @PathVariable("inventory_id") @ApiParam(value = "Inventory id") Long inventoryId,
                                    @PathVariable("item_id") @ApiParam(value = "Item id") Long itemId) {
        InventoryItemModel inventoryItemModel = requestInventoryItemModelDto
                .fromRequestInventoryItemModelDtoToInventoryItemModel(requestInventoryItemModelDto,
                        inventoryModelService, itemModelService);
        InventoryItemPK inventoryItemPK = new InventoryItemPK();

        InventoryModel inventoryModel = inventoryModelService.findById(inventoryId);
        ItemModel itemModel = itemModelService.findById(itemId);

        inventoryItemPK.setInventory(inventoryModel);
        inventoryItemPK.setItem(itemModel);
        return inventoryItemModelService.update(inventoryItemModel, inventoryItemPK);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get list of inventory items with constraints")
    public ResponseEntity<PageDto<InventoryItemModelFilterDto>> getInventoryItems(
            @RequestParam(value = "inventoryId", required = false) Long inventoryId,
            @RequestParam(value = "itemId", required = false) Long itemId,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var response = inventoryItemModelService.findAll(
                new InventoryItemModelFilterDto(
                        inventoryId,
                        itemId,
                        quantity,
                        price
                ),
                page,
                size,
                Sort.by(Sort.Direction.fromString(direction), sortBy)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
