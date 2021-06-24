package com.warehouse.service.impl;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.SupplyItemModelFilterDto;
import com.warehouse.mapper.SupplyItemModelMapper;
import com.warehouse.model.SupplyItemModel;
import com.warehouse.model.SupplyItemPK;
import com.warehouse.repository.SupplyItemRepository;
import com.warehouse.service.SupplyItemModelService;
import com.warehouse.specifications.SupplyItemModelSpecification;
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
public class SupplyItemModelServiceImpl implements SupplyItemModelService {

    private final SupplyItemRepository supplyItemRepository;
    private final SupplyItemModelMapper subItemModelMapper;

    public SupplyItemModelServiceImpl(
            SupplyItemRepository supplyItemRepository, SupplyItemModelMapper subItemModelMapper) {
        this.supplyItemRepository = supplyItemRepository;
        this.subItemModelMapper = subItemModelMapper;
    }

    @Override
    public SupplyItemModel findById(SupplyItemPK id) {
        try {
            return supplyItemRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            log.error(String.format("%s, supplyItemId - %s", e.getMessage(), id));
            throw new NoSuchElementException("SupplyItem is not found.");
        }
    }

    @Override
    public SupplyItemPK deleteById(SupplyItemPK id) {
        try {
            supplyItemRepository.deleteById(id);
            return id;
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, supplyItemId - %s", e.getMessage(), id));
            throw new NoSuchElementException("SupplyItem is not found.");
        }
    }

    @Override
    public SupplyItemModel save(SupplyItemModel supplyItemModel) {
        return supplyItemRepository.save(supplyItemModel);
    }

    @Override
    public SupplyItemModel update(SupplyItemModel supplyItemModel, SupplyItemPK id) {
        try {
            Optional<SupplyItemModel> supplyItemModelById = supplyItemRepository.findById(id);
            SupplyItemModel oldSupplyItemModel = supplyItemModelById.get();

            oldSupplyItemModel.setId(supplyItemModel.getId());
            oldSupplyItemModel.setQuantity(supplyItemModel.getQuantity());
            oldSupplyItemModel.setPrice(supplyItemModel.getPrice());
            return supplyItemRepository.save(supplyItemModelById.get());
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, supplyItemId - %s", e.getMessage(), id));
            throw new NoSuchElementException("SupplyItem is not found.");
        }
    }

    @Override
    public PageDto<SupplyItemModelFilterDto> findAll(
            SupplyItemModelFilterDto supplyItemModelFilterDto, int page, int size, Sort sort) {
        var pageData = PageDataCreator.createPageData(page, size, sort);
        var predicate = SupplyItemModelSpecification.createPredicate(
                supplyItemModelFilterDto.getSupplyId(),
                supplyItemModelFilterDto.getItemId(),
                supplyItemModelFilterDto.getQuantity(),
                supplyItemModelFilterDto.getPrice()
        );

        var supplyItemModelPage = supplyItemRepository.findAll(predicate, pageData);

        return PageDtoCreator.createReadQueryResult(
                supplyItemModelPage, subItemModelMapper::toDto
        );
    }
}
