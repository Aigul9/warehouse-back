package com.warehouse.service.impl;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.InventoryItemModelFilterDto;
import com.warehouse.mapper.InventoryItemModelMapper;
import com.warehouse.model.InventoryItemModel;
import com.warehouse.model.InventoryItemPK;
import com.warehouse.repository.InventoryItemRepository;
import com.warehouse.repository.ItemRepository;
import com.warehouse.service.InventoryItemModelService;
import com.warehouse.specifications.InventoryItemModelSpecification;
import com.warehouse.util.PageDataCreator;
import com.warehouse.util.PageDtoCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class InventoryItemModelServiceImpl implements InventoryItemModelService {

    private final InventoryItemRepository inventoryItemRepository;
    private final ItemRepository itemRepository;
    private final InventoryItemModelMapper inventoryItemModelMapper;

    public InventoryItemModelServiceImpl(
            InventoryItemRepository inventoryItemRepository,
            ItemRepository itemRepository,
            InventoryItemModelMapper inventoryItemModelMapper) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.itemRepository = itemRepository;
        this.inventoryItemModelMapper = inventoryItemModelMapper;
    }

    @Override
    public InventoryItemModel findById(InventoryItemPK id) {
        try {
            return inventoryItemRepository.findById(id).get();
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, InventoryItemId - %s", e.getMessage(), id));
            throw new NoSuchElementException("InventoryItem is not found.");
        }
    }

    @Override
    public InventoryItemPK deleteById(InventoryItemPK id) {
        try {
            inventoryItemRepository.deleteById(id);
            return id;
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, InventoryItemId - %s", e.getMessage(), id));
            throw new NoSuchElementException("InventoryItem is not found.");
        }
    }

    @Override
    public InventoryItemModel save(InventoryItemModel inventoryItemModel) {
        return inventoryItemRepository.save(inventoryItemModel);
    }

    @Override
    public InventoryItemModel update(InventoryItemModel inventoryItemModel, InventoryItemPK id) {
        try {
            Optional<InventoryItemModel> inventoryItemModelById = inventoryItemRepository.findById(id);

            InventoryItemModel oldInventoryItemModel = inventoryItemModelById.get();
            oldInventoryItemModel.setId(inventoryItemModel.getId());
            oldInventoryItemModel.setQuantity(inventoryItemModel.getQuantity());
            oldInventoryItemModel.setPrice(inventoryItemModel.getPrice());
            return inventoryItemRepository.save(inventoryItemModelById.get());
        } catch (NoSuchElementException e) {
            log.error(String.format("%s, InventoryItemId - %s", e.getMessage(), id));
            throw new NoSuchElementException("InventoryItem is not found.");
        }
    }

    @Override
    public PageDto<InventoryItemModelFilterDto> findAll(
            InventoryItemModelFilterDto inventoryItemModelFilterDto, int page, int size, Sort sort) {
        var pageData = PageDataCreator.createPageData(page, size, sort);
        var predicate = InventoryItemModelSpecification.createPredicate(
                inventoryItemModelFilterDto.getInventoryId(),
                inventoryItemModelFilterDto.getItemId(),
                inventoryItemModelFilterDto.getQuantity(),
                inventoryItemModelFilterDto.getPrice()
        );

        var inventoryItemModelPage = inventoryItemRepository.findAll(predicate, pageData);

        return PageDtoCreator.createReadQueryResult(
                inventoryItemModelPage, inventoryItemModelMapper::toDto
        );
    }
}
