package com.warehouse.controller;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.SupplyItemModelFilterDto;
import com.warehouse.dto.request.RequestSupplyItemModelDto;
import com.warehouse.model.ItemModel;
import com.warehouse.model.SupplyItemModel;
import com.warehouse.model.SupplyItemPK;
import com.warehouse.model.SupplyModel;
import com.warehouse.service.ItemModelService;
import com.warehouse.service.SupplyItemModelService;
import com.warehouse.service.SupplyModelService;
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
@RequestMapping("/api/v1/supply_item")
public class SupplyItemCrudController {
    
    private final SupplyItemModelService supplyItemModelService;
    private final SupplyModelService supplyModelService;
    private final ItemModelService itemModelService;

    public SupplyItemCrudController(SupplyItemModelService supplyItemModelService,
                                    SupplyModelService supplyModelService,
                                    ItemModelService itemModelService) {
        this.supplyItemModelService = supplyItemModelService;
        this.supplyModelService = supplyModelService;
        this.itemModelService = itemModelService;
    }

    @GetMapping("/{supply_id}/{item_id}")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get supply row", notes = "This method is used to get list of all items in a supply.")
    public SupplyItemModel get(@PathVariable("supply_id") @ApiParam(value = "Supply id") Long supplyId,
                               @PathVariable("item_id") @ApiParam(value = "Item id") Long itemId) {
        SupplyItemPK supplyItemPK = new SupplyItemPK();

        SupplyModel supplyModel = supplyModelService.findById(supplyId);
        ItemModel itemModel = itemModelService.findById(itemId);

        supplyItemPK.setSupply(supplyModel);
        supplyItemPK.setItem(itemModel);
        return supplyItemModelService.findById(supplyItemPK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SUPPLY_PERMISSION')")
    @ApiOperation(value = "Create supply row", notes = "This method is used to add a new supply row.")
    public ResponseEntity<SupplyItemModel> post(@RequestBody RequestSupplyItemModelDto requestSupplyItemModelDto) {
        SupplyItemModel supplyItemModel = requestSupplyItemModelDto
                .fromRequestSupplyItemModelDtoToSupplyItemModel(requestSupplyItemModelDto,
                        supplyModelService, itemModelService);
        return new ResponseEntity<>(supplyItemModelService.save(supplyItemModel), HttpStatus.OK);
    }

    @DeleteMapping("/{supply_id}/{item_id}")
    @PreAuthorize("hasAuthority('SUPPLY_PERMISSION')")
    @ApiOperation(value = "Delete supply row", notes = "This method is used to delete the supply row.")
    public SupplyItemPK delete(@PathVariable("supply_id") @ApiParam(value = "Supply id") Long supplyId,
                               @PathVariable("item_id") @ApiParam(value = "Item id") Long itemId) {
        SupplyItemPK supplyItemPK = new SupplyItemPK();

        SupplyModel supplyModel = supplyModelService.findById(supplyId);
        ItemModel itemModel = itemModelService.findById(itemId);

        supplyItemPK.setSupply(supplyModel);
        supplyItemPK.setItem(itemModel);
        return supplyItemModelService.deleteById(supplyItemPK);
    }

    @PutMapping("/{supply_id}/{item_id}")
    @PreAuthorize("hasAuthority('SUPPLY_PERMISSION')")
    @ApiOperation(value = "Update supply row", notes = "This method is used to update the supply row.")
    public SupplyItemModel update(@RequestBody RequestSupplyItemModelDto requestSupplyItemModelDto,
                                  @PathVariable("supply_id") @ApiParam(value = "Supply id") Long supplyId,
                                  @PathVariable("item_id") @ApiParam(value = "Item id") Long itemId) {
        SupplyItemModel supplyItemModel = requestSupplyItemModelDto
                .fromRequestSupplyItemModelDtoToSupplyItemModel(requestSupplyItemModelDto,
                        supplyModelService, itemModelService);
        SupplyItemPK supplyItemPK = new SupplyItemPK();

        SupplyModel supplyModel = supplyModelService.findById(supplyId);
        ItemModel itemModel = itemModelService.findById(itemId);

        supplyItemPK.setSupply(supplyModel);
        supplyItemPK.setItem(itemModel);
        return supplyItemModelService.update(supplyItemModel, supplyItemPK);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get list of supply items with constraints")
    public ResponseEntity<PageDto<SupplyItemModelFilterDto>> getSupplyItems(
            @RequestParam(value = "supplyId", required = false) Long supplyId,
            @RequestParam(value = "itemId", required = false) Long itemId,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var response = supplyItemModelService.findAll(
                new SupplyItemModelFilterDto(
                        supplyId,
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
