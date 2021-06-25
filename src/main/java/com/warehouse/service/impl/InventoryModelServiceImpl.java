package com.warehouse.service.impl;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.InventoryModelFilterDto;
import com.warehouse.mapper.InventoryModelMapper;
import com.warehouse.model.InventoryModel;
import com.warehouse.model.InventoryStatus;
import com.warehouse.repository.InventoryRepository;
import com.warehouse.service.InventoryModelService;
import com.warehouse.specifications.InventoryModelSpecification;
import com.warehouse.util.PageDataCreator;
import com.warehouse.util.PageDtoCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class InventoryModelServiceImpl implements InventoryModelService {
    
    private final InventoryRepository inventoryRepository;
    private final InventoryModelMapper inventoryModelMapper;

    public InventoryModelServiceImpl(
            InventoryRepository inventoryRepository, InventoryModelMapper inventoryModelMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryModelMapper = inventoryModelMapper;
    }

    @Override
    public InventoryModel findById(Long id) {
        try {
            return inventoryRepository.findById(id).get();
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, InventoryId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Inventory is not found.");
        }
    }

    @Override
    public List<InventoryModel> findAll() {
        return inventoryRepository.findAll();
    }

    @Override
    public Long deleteById(Long id) {
        try {
            inventoryRepository.deleteById(id);
            return id;
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, InventoryId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Inventory is not found.");
        }
    }

    @Override
    public InventoryModel save(InventoryModel inventoryModel) {
        return inventoryRepository.save(inventoryModel);
    }

    @Override
    public InventoryModel update(InventoryModel inventoryModel, long id) {
        try {
            Optional<InventoryModel> inventoryModelById = inventoryRepository.findById(id);
            InventoryModel oldInventoryModel = inventoryModelById.get();
            oldInventoryModel.setDate(inventoryModel.getDate());
            oldInventoryModel.setTotal(inventoryModel.getTotal());
            oldInventoryModel.setInventoryStatus(inventoryModel.getInventoryStatus());
            return inventoryRepository.save(inventoryModelById.get());
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, InventoryId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Inventory is not found.");
        }
    }

    @Override
    public boolean checkTotal(double total) {
        return total > 0;
    }

    @Override
    public PageDto<InventoryModelFilterDto> findAll(InventoryModelFilterDto inventoryModelFilterDto, int page, int size, Sort sort) {
        var pageData = PageDataCreator.createPageData(page, size, sort);
        var predicate = InventoryModelSpecification.createPredicate(
                inventoryModelFilterDto.getDate(),
                inventoryModelFilterDto.getTotal(),
                inventoryModelFilterDto.getInventoryStatus()
        );

        var inventoryModelPage = inventoryRepository.findAll(predicate, pageData);

        return PageDtoCreator.createReadQueryResult(
                inventoryModelPage, inventoryModelMapper::toDto
        );
    }
}
