package com.warehouse.service.impl;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.PurchaseItemModelFilterDto;
import com.warehouse.mapper.PurchaseItemModelMapper;
import com.warehouse.model.PurchaseItemModel;
import com.warehouse.model.PurchaseItemPK;
import com.warehouse.repository.PurchaseItemRepository;
import com.warehouse.service.PurchaseItemModelService;
import com.warehouse.specifications.PurchaseItemModelSpecification;
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
public class PurchaseItemModelServiceImpl implements PurchaseItemModelService {

    private final PurchaseItemRepository purchaseItemRepository;
    private final PurchaseItemModelMapper purchaseItemModelMapper;

    public PurchaseItemModelServiceImpl(
            PurchaseItemRepository purchaseItemRepository, PurchaseItemModelMapper purchaseItemModelMapper) {
        this.purchaseItemRepository = purchaseItemRepository;
        this.purchaseItemModelMapper = purchaseItemModelMapper;
    }

    @Override
    public PurchaseItemModel findById(PurchaseItemPK id) {
        try {
            return purchaseItemRepository.findById(id).get();
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, purchaseItemId - %s", e.getMessage(), id));
            throw new NoSuchElementException("PurchaseItem is not found.");
        }
    }

    @Override
    public PurchaseItemPK deleteById(PurchaseItemPK id) {
        try {
            purchaseItemRepository.deleteById(id);
            return id;
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, purchaseItemId - %s", e.getMessage(), id));
            throw new NoSuchElementException("PurchaseItem is not found.");
        }
    }

    @Override
    public PurchaseItemModel save(PurchaseItemModel purchaseItemModel) {
        return purchaseItemRepository.save(purchaseItemModel);
    }

    @Override
    public PurchaseItemModel update(PurchaseItemModel purchaseItemModel, PurchaseItemPK id) {
        try {
            Optional<PurchaseItemModel> purchaseItemModelById = purchaseItemRepository.findById(id);

            PurchaseItemModel oldPurchaseItemModel = purchaseItemModelById.get();
            oldPurchaseItemModel.setId(purchaseItemModel.getId());
            oldPurchaseItemModel.setQuantity(purchaseItemModel.getQuantity());
            oldPurchaseItemModel.setPrice(purchaseItemModel.getPrice());
            return purchaseItemRepository.save(purchaseItemModelById.get());
        } catch (NoSuchElementException e) {
            log.error(String.format("%s, purchaseItemId - %s", e.getMessage(), id));
            throw new NoSuchElementException("PurchaseItem is not found.");
        }
    }

    @Override
    public PageDto<PurchaseItemModelFilterDto> findAll(
            PurchaseItemModelFilterDto purchaseItemModelFilterDto, int page, int size, Sort sort) {
        var pageData = PageDataCreator.createPageData(page, size, sort);
        var predicate = PurchaseItemModelSpecification.createPredicate(
                purchaseItemModelFilterDto.getPurchaseId(),
                purchaseItemModelFilterDto.getItemId(),
                purchaseItemModelFilterDto.getQuantity(),
                purchaseItemModelFilterDto.getPrice()
        );

        var purchaseItemModelPage = purchaseItemRepository.findAll(predicate, pageData);

        return PageDtoCreator.createReadQueryResult(
                purchaseItemModelPage, purchaseItemModelMapper::toDto
        );
    }
}
