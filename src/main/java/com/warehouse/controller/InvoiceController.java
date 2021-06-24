package com.warehouse.controller;

import com.warehouse.dto.request.RequestInvoiceDto;
import com.warehouse.model.CategoryModel;
import com.warehouse.model.ItemModel;
import com.warehouse.model.SupplyItemModel;
import com.warehouse.model.SupplyItemPK;
import com.warehouse.model.SupplyModel;
import com.warehouse.service.CategoryModelService;
import com.warehouse.service.ItemModelService;
import com.warehouse.service.SupplyItemModelService;
import com.warehouse.service.SupplyModelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/create_invoice")
public class InvoiceController {
    
    private final SupplyModelService supplyModelService;
    private final SupplyItemModelService supplyItemModelService;
    private final ItemModelService itemModelService;
    private final CategoryModelService categoryModelService;

    public InvoiceController(SupplyModelService supplyModelService,
                             SupplyItemModelService supplyItemModelService,
                             ItemModelService itemModelService,
                             CategoryModelService categoryModelService) {
        this.supplyModelService = supplyModelService;
        this.supplyItemModelService = supplyItemModelService;
        this.itemModelService = itemModelService;
        this.categoryModelService = categoryModelService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SUPPLY_PERMISSION')")
    @ApiOperation(value = "Create invoice", notes = "This method is used to make a new invoice.")
    public ResponseEntity<List<SupplyItemModel>> post(@RequestBody List<RequestInvoiceDto> requestInvoiceDtoList) {

        SupplyModel supplyModel = new SupplyModel();
        double supplyTotal = 0;

        for (RequestInvoiceDto requestInvoiceDto: requestInvoiceDtoList) {
            SupplyItemModel supplyItemModel = requestInvoiceDto.fromRequestInvoiceDtoToSupplyItemModel(requestInvoiceDto);

            if (requestInvoiceDto.getQuantity() > 0) {
                supplyTotal += supplyItemModel.getQuantity() * supplyItemModel.getPrice();
            }
        }

        if (!supplyModelService.checkTotal(supplyTotal)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        supplyModel.setDate(new Date());
        supplyModel.setTotal(supplyTotal);
        supplyModel = supplyModelService.save(supplyModel);

        List<SupplyItemModel> supplyItemModels = new ArrayList<>();

        for (RequestInvoiceDto requestInvoiceDto : requestInvoiceDtoList) {

            CategoryModel categoryModel;
            ItemModel itemModel;

            try {
                long categoryId = Integer.parseInt(requestInvoiceDto.getCategoryProperty());
                categoryModel = categoryModelService.findById(categoryId);
            } catch (NumberFormatException e) {
                categoryModel = new CategoryModel();
                categoryModel.setName(requestInvoiceDto.getCategoryProperty());
                categoryModel = categoryModelService.save(categoryModel);
            }

            try {
                long itemId = Integer.parseInt(requestInvoiceDto.getItemProperty());
                itemModel = itemModelService.findById(itemId);
            } catch (NumberFormatException e) {
                itemModel = new ItemModel();
                itemModel.setName(requestInvoiceDto.getItemProperty());
                itemModel.setQuantity(0);
                itemModel.setGroup(categoryModel);
            }

            itemModel.setPrice(requestInvoiceDto.getPrice());

            SupplyItemPK supplyItemPK = new SupplyItemPK();
            supplyItemPK.setSupply(supplyModel);
            supplyItemPK.setItem(itemModel);

            SupplyItemModel supplyItemModel = requestInvoiceDto.fromRequestInvoiceDtoToSupplyItemModel(requestInvoiceDto);
            supplyItemModel.setId(supplyItemPK);
            supplyItemModels.add(supplyItemModelService.save(supplyItemModel));

            itemModel.setQuantity(itemModel.getQuantity() + requestInvoiceDto.getQuantity());
            itemModelService.update(itemModel, itemModel.getId());
        }

        return new ResponseEntity<>(supplyItemModels, HttpStatus.OK);
    }
}
