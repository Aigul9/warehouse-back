package com.warehouse.controller;

import com.warehouse.dto.request.RequestOrderDto;
import com.warehouse.model.ItemModel;
import com.warehouse.model.PurchaseItemModel;
import com.warehouse.model.PurchaseItemPK;
import com.warehouse.model.PurchaseModel;
import com.warehouse.service.ItemModelService;
import com.warehouse.service.PurchaseItemModelService;
import com.warehouse.service.PurchaseModelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/create_order")
public class OrderController {

    private final PurchaseModelService purchaseModelService;
    private final PurchaseItemModelService purchaseItemModelService;
    private final ItemModelService itemModelService;

    public OrderController(PurchaseModelService purchaseModelService,
                           PurchaseItemModelService purchaseItemModelService,
                           ItemModelService itemModelService) {
        this.purchaseModelService = purchaseModelService;
        this.purchaseItemModelService = purchaseItemModelService;
        this.itemModelService = itemModelService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORDER_PERMISSION')")
    @ApiOperation(value = "Create order", notes = "This method is used to make a new order.")
    public ResponseEntity<List<PurchaseItemModel>> post(@RequestBody List<RequestOrderDto> requestOrderDtoList) {

        PurchaseModel purchaseModel = new PurchaseModel();
        double purchaseTotal = 0;

        Iterator<RequestOrderDto> iter = requestOrderDtoList.iterator();

        while(iter.hasNext()) {
            RequestOrderDto requestOrderDto = iter.next();
            PurchaseItemModel purchaseItemModel = requestOrderDto.fromRequestOrderDtoToPurchaseItemModel(requestOrderDto,
                    itemModelService);

            if (itemModelService.checkCount(requestOrderDto.getItemId(), requestOrderDto.getQuantity())) {
                purchaseTotal += purchaseItemModel.getPrice() * purchaseItemModel.getQuantity();
            } else {
                iter.remove();
            }
        }

        if (!purchaseModelService.checkTotal(purchaseTotal)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        purchaseModel.setDate(new Date());
        purchaseModel.setTotal(purchaseTotal);
        purchaseModel = purchaseModelService.save(purchaseModel);

        List<PurchaseItemModel> purchaseItemModels = new ArrayList<>();

        for (RequestOrderDto requestOrderDto : requestOrderDtoList) {
            PurchaseItemPK purchaseItemPK = new PurchaseItemPK();
            ItemModel itemModel = itemModelService.findById(requestOrderDto.getItemId());
            purchaseItemPK.setPurchase(purchaseModel);
            purchaseItemPK.setItem(itemModel);

            PurchaseItemModel purchaseItemModel = requestOrderDto.fromRequestOrderDtoToPurchaseItemModel(requestOrderDto,
                    itemModelService);
            purchaseItemModel.setId(purchaseItemPK);
            purchaseItemModels.add(purchaseItemModelService.save(purchaseItemModel));

            itemModel.setQuantity(itemModel.getQuantity() - requestOrderDto.getQuantity());
            itemModelService.update(itemModel, itemModel.getId());
        }

        return new ResponseEntity<>(purchaseItemModels, HttpStatus.OK);
    }
}
