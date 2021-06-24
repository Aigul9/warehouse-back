package com.warehouse.controller;

import com.warehouse.dto.request.RequestInventoryAllDto;
import com.warehouse.dto.request.RequestInventoryApproveDto;
import com.warehouse.dto.request.RequestInventoryPartialDto;
import com.warehouse.model.InventoryItemModel;
import com.warehouse.model.InventoryItemPK;
import com.warehouse.model.InventoryModel;
import com.warehouse.model.ItemModel;
import com.warehouse.model.InventoryStatus;
import com.warehouse.repository.InventoryItemRepository;
import com.warehouse.service.InventoryItemModelService;
import com.warehouse.service.InventoryModelService;
import com.warehouse.service.ItemModelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryController {

    private final InventoryModelService inventoryModelService;
    private final InventoryItemModelService inventoryItemModelService;
    private final ItemModelService itemModelService;
    private final InventoryItemRepository inventoryItemRepository;

    public InventoryController(InventoryModelService inventoryModelService,
                               InventoryItemModelService inventoryItemModelService,
                               ItemModelService itemModelService,
                               InventoryItemRepository inventoryItemRepository) {
        this.inventoryModelService = inventoryModelService;
        this.inventoryItemModelService = inventoryItemModelService;
        this.itemModelService = itemModelService;
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INVENTORY_PERMISSION')")
    @RequestMapping(method = RequestMethod.POST, value = "/create_inventory")
    @ApiOperation(value = "Create inventory", notes = "This method is used to make a new list of inventories.")
    public ResponseEntity<List<InventoryItemModel>> post (@RequestBody RequestInventoryAllDto requestInventoryAllDto) {
        List<ItemModel> itemModels = requestInventoryAllDto.fromRequestInventoryAllDtoToItemModelList(
                requestInventoryAllDto, itemModelService
        );

        double inventoryTotal = 0.0;

        for (ItemModel itemModel: itemModels) {
            inventoryTotal += itemModel.getQuantity() * itemModel.getPrice();
        }

        InventoryModel inventoryModel = new InventoryModel();
        inventoryModel.setDate(new Date());
        inventoryModel.setTotal(inventoryTotal);
        inventoryModel.setInventoryStatus(InventoryStatus.PENDING);
        inventoryModel = inventoryModelService.save(inventoryModel);

        List<InventoryItemModel> inventoryItemModels = new ArrayList<>();

        for (ItemModel itemModel: itemModels) {
            InventoryItemPK inventoryItemPK = new InventoryItemPK();
            inventoryItemPK.setItem(itemModel);
            inventoryItemPK.setInventory(inventoryModel);

            InventoryItemModel inventoryItemModel = new InventoryItemModel();

            inventoryItemModel.setId(inventoryItemPK);
            inventoryItemModel.setQuantity(itemModel.getQuantity());
            inventoryItemModel.setPrice(itemModel.getPrice());
            inventoryItemModels.add(inventoryItemModelService.save(inventoryItemModel));

            itemModel.setQuantity(0);
            itemModelService.save(itemModel);
        }

        return new ResponseEntity<>(inventoryItemModels, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('APPROVE_INVENTORY_PERMISSION')")
    @RequestMapping(method = RequestMethod.POST, value = "/approve_inventory")
    @ApiOperation(value = "Approve inventory", notes = "This method is used to approve a list of inventories.")
    public ResponseEntity<InventoryModel> post (@RequestBody RequestInventoryApproveDto requestInventoryApproveDto) {
        InventoryModel inventoryModel = requestInventoryApproveDto.fromRequestInventoryApproveDtoToInventoryModel(
                requestInventoryApproveDto, inventoryModelService
        );

        if (requestInventoryApproveDto.isApproved()) {
            inventoryModel.setInventoryStatus(InventoryStatus.APPROVED);
        } else {
            inventoryModel.setInventoryStatus(InventoryStatus.REJECTED);
            List<Long> rejectedItemIds = inventoryItemRepository.getItemModelsByInventoryId(inventoryModel.getId());

            for (long id: rejectedItemIds) {
                ItemModel itemModel = itemModelService.findById(id);
                InventoryItemPK inventoryItemPK = new InventoryItemPK();
                inventoryItemPK.setInventory(inventoryModel);
                inventoryItemPK.setItem(itemModel);
                InventoryItemModel inventoryItemModel = inventoryItemModelService.findById(inventoryItemPK);
                itemModel.setQuantity(itemModel.getQuantity() + inventoryItemModel.getQuantity());
                itemModelService.save(itemModel);
            }
        }

        return new ResponseEntity<>(inventoryModel, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INVENTORY_PERMISSION')")
    @RequestMapping(method = RequestMethod.POST, value = "/create_inventory_with_quantity")
    @ApiOperation(value = "Create inventory with quantity",
            notes = "This method is used to make a new list of inventories with quantity.")
    public ResponseEntity<List<InventoryItemModel>> post(@RequestBody List<RequestInventoryPartialDto> requestInventoryPartialDtoList) {
        InventoryModel inventoryModel = new InventoryModel();
        double inventoryTotal = 0.0;
        Iterator<RequestInventoryPartialDto> iter = requestInventoryPartialDtoList.iterator();

        while(iter.hasNext()) {
            RequestInventoryPartialDto requestInventoryPartialDto = iter.next();
            InventoryItemModel inventoryItemModel = requestInventoryPartialDto.fromRequestInventoryPartialDtoToItemModel(
                    requestInventoryPartialDto, itemModelService
            );

            if (itemModelService.checkCount(requestInventoryPartialDto.getId(), requestInventoryPartialDto.getQuantity())) {
                inventoryTotal += inventoryItemModel.getQuantity() * inventoryItemModel.getPrice();
            } else {
                iter.remove();
            }
        }

        if (!inventoryModelService.checkTotal(inventoryTotal)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        inventoryModel.setDate(new Date());
        inventoryModel.setTotal(inventoryTotal);
        inventoryModel.setInventoryStatus(InventoryStatus.PENDING);
        inventoryModel = inventoryModelService.save(inventoryModel);

        List<InventoryItemModel> inventoryItemModels = new ArrayList<>();

        for (RequestInventoryPartialDto requestInventoryPartialDto : requestInventoryPartialDtoList) {
            InventoryItemPK inventoryItemPK = new InventoryItemPK();
            ItemModel itemModel = itemModelService.findById(requestInventoryPartialDto.getId());
            inventoryItemPK.setInventory(inventoryModel);
            inventoryItemPK.setItem(itemModel);

            InventoryItemModel inventoryItemModel = requestInventoryPartialDto.fromRequestInventoryPartialDtoToItemModel(
                    requestInventoryPartialDto,
                    itemModelService);
            inventoryItemModel.setId(inventoryItemPK);
            inventoryItemModels.add(inventoryItemModelService.save(inventoryItemModel));

            itemModel.setQuantity(itemModel.getQuantity() - requestInventoryPartialDto.getQuantity());
            itemModelService.save(itemModel);
        }

        return new ResponseEntity<>(inventoryItemModels, HttpStatus.OK);
    }
}
