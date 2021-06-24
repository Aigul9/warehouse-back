package com.warehouse.controller;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.PurchaseItemModelFilterDto;
import com.warehouse.dto.request.RequestPurchaseItemModelDto;
import com.warehouse.model.ItemModel;
import com.warehouse.model.PurchaseItemModel;
import com.warehouse.model.PurchaseItemPK;
import com.warehouse.model.PurchaseModel;
import com.warehouse.service.ItemModelService;
import com.warehouse.service.PurchaseItemModelService;
import com.warehouse.service.PurchaseModelService;
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
@RequestMapping("/api/v1/purchase_item")
public class PurchaseItemCrudController {

    private final PurchaseItemModelService purchaseItemModelService;
    private final PurchaseModelService purchaseModelService;
    private final ItemModelService itemModelService;

    public PurchaseItemCrudController(PurchaseItemModelService purchaseItemModelService,
                                      PurchaseModelService purchaseModelService,
                                      ItemModelService itemModelService) {
        this.purchaseItemModelService = purchaseItemModelService;
        this.purchaseModelService = purchaseModelService;
        this.itemModelService = itemModelService;
    }


    @GetMapping("/{purchase_id}/{item_id}")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get purchase row", notes = "This method is used to get list of all items in a purchase.")
    public PurchaseItemModel get(@PathVariable("purchase_id") @ApiParam(value = "Supply id") Long purchaseId,
                                 @PathVariable("item_id") @ApiParam(value = "Item id") Long itemId) {
        PurchaseItemPK purchaseItemPK = new PurchaseItemPK();

        PurchaseModel purchaseModel = purchaseModelService.findById(purchaseId);
        ItemModel itemModel = itemModelService.findById(itemId);

        purchaseItemPK.setPurchase(purchaseModel);
        purchaseItemPK.setItem(itemModel);
        return purchaseItemModelService.findById(purchaseItemPK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORDER_PERMISSION')")
    @ApiOperation(value = "Create purchase row", notes = "This method is used to add a new purchase row.")
    public ResponseEntity<PurchaseItemModel> post(@RequestBody RequestPurchaseItemModelDto requestPurchaseItemModelDto) {
        PurchaseItemModel purchaseItemModel = requestPurchaseItemModelDto
                .fromRequestPurchaseItemModelDtoToPurchaseItemModel(requestPurchaseItemModelDto,
                        purchaseModelService, itemModelService);
        return new ResponseEntity<>(purchaseItemModelService.save(purchaseItemModel), HttpStatus.OK);
    }

    @DeleteMapping("/{purchase_id}/{item_id}")
    @PreAuthorize("hasAuthority('ORDER_PERMISSION')")
    @ApiOperation(value = "Delete purchase row", notes = "This method is used to delete the purchase row.")
    public PurchaseItemPK delete(@PathVariable("purchase_id") @ApiParam(value = "Supply id") Long purchaseId,
                                 @PathVariable("item_id") @ApiParam(value = "Item id") Long itemId) {
        PurchaseItemPK purchaseItemPK = new PurchaseItemPK();

        PurchaseModel purchaseModel = purchaseModelService.findById(purchaseId);
        ItemModel itemModel = itemModelService.findById(itemId);

        purchaseItemPK.setPurchase(purchaseModel);
        purchaseItemPK.setItem(itemModel);
        return purchaseItemModelService.deleteById(purchaseItemPK);
    }

    @PutMapping("/{purchase_id}/{item_id}")
    @PreAuthorize("hasAuthority('ORDER_PERMISSION')")
    @ApiOperation(value = "Update purchase row", notes = "This method is used to update the purchase row.")
    public PurchaseItemModel update(@RequestBody RequestPurchaseItemModelDto requestPurchaseItemModelDto,
                                    @PathVariable("purchase_id") @ApiParam(value = "Supply id") Long purchaseId,
                                    @PathVariable("item_id") @ApiParam(value = "Item id") Long itemId) {
        PurchaseItemModel purchaseItemModel = requestPurchaseItemModelDto
                .fromRequestPurchaseItemModelDtoToPurchaseItemModel(requestPurchaseItemModelDto,
                        purchaseModelService, itemModelService);
        PurchaseItemPK purchaseItemPK = new PurchaseItemPK();

        PurchaseModel purchaseModel = purchaseModelService.findById(purchaseId);
        ItemModel itemModel = itemModelService.findById(itemId);

        purchaseItemPK.setPurchase(purchaseModel);
        purchaseItemPK.setItem(itemModel);
        return purchaseItemModelService.update(purchaseItemModel, purchaseItemPK);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get list of purchase items with constraints")
    public ResponseEntity<PageDto<PurchaseItemModelFilterDto>> getPurchaseItems(
            @RequestParam(value = "purchaseId", required = false) Long purchaseId,
            @RequestParam(value = "itemId", required = false) Long itemId,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var response = purchaseItemModelService.findAll(
                new PurchaseItemModelFilterDto(
                        purchaseId,
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
