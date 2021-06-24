package com.warehouse.controller;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.PurchaseModelFilterDto;
import com.warehouse.dto.request.RequestPurchaseModelDto;
import com.warehouse.model.PurchaseModel;
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

import java.util.Date;

@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseCrudController {
    
    private final PurchaseModelService purchaseModelService;

    public PurchaseCrudController(PurchaseModelService purchaseModelService) {
        this.purchaseModelService = purchaseModelService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get purchase", notes = "This method is used to get list of purchases made in the store.")
    public PurchaseModel get(@PathVariable("id") @ApiParam(value = "Purchase id") Long id) {
        return purchaseModelService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORDER_PERMISSION')")
    @ApiOperation(value = "Create purchase", notes = "This method is used to add a new purchase.")
    public ResponseEntity<PurchaseModel> post(@RequestBody RequestPurchaseModelDto requestPurchaseModelDto) {
        return new ResponseEntity<>(purchaseModelService.save(
                requestPurchaseModelDto.fromRequestPurchaseModelDtoToPurchaseModel(requestPurchaseModelDto)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ORDER_PERMISSION')")
    @ApiOperation(value = "Delete purchase", notes = "This method is used to delete the purchase.")
    public Long delete(@PathVariable("id") @ApiParam(value = "Purchase id") Long id) {
        return purchaseModelService.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORDER_PERMISSION')")
    @ApiOperation(value = "Update purchase", notes = "This method is used to update the purchase.")
    public PurchaseModel update(@RequestBody RequestPurchaseModelDto requestPurchaseModelDto,
                                @PathVariable("id") @ApiParam(value = "Purchase id") long id) {
        return purchaseModelService.update(
                requestPurchaseModelDto.fromRequestPurchaseModelDtoToPurchaseModel(requestPurchaseModelDto), id);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get list of purchases with constraints")
    public ResponseEntity<PageDto<PurchaseModelFilterDto>> getPurchases(
            @RequestParam(value = "date", required = false) Date date,
            @RequestParam(value = "total", required = false) Double total,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var response = purchaseModelService.findAll(
                new PurchaseModelFilterDto(
                        date,
                        total
                ),
                page,
                size,
                Sort.by(Sort.Direction.fromString(direction), sortBy)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
