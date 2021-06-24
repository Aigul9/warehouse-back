package com.warehouse.service.impl;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.PurchaseModelFilterDto;
import com.warehouse.mapper.PurchaseModelMapper;
import com.warehouse.model.PurchaseModel;
import com.warehouse.repository.PurchaseRepository;
import com.warehouse.service.PurchaseModelService;
import com.warehouse.specifications.PurchaseModelSpecification;
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
public class PurchaseModelServiceImpl implements PurchaseModelService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseModelMapper purchaseModelMapper;

    public PurchaseModelServiceImpl(
            PurchaseRepository purchaseRepository, PurchaseModelMapper purchaseModelMapper) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseModelMapper = purchaseModelMapper;
    }

    @Override
    public PurchaseModel findById(Long id) {
        try {
            return purchaseRepository.findById(id).get();
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, purchaseId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Purchase is not found.");
        }
    }

    @Override
    public Long deleteById(Long id) {
        try {
            purchaseRepository.deleteById(id);
            return id;
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, purchaseId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Purchase is not found.");
        }
    }

    @Override
    public PurchaseModel save(PurchaseModel purchaseModel) {
        return purchaseRepository.save(purchaseModel);
    }

    @Override
    public PurchaseModel update(PurchaseModel purchaseModel, long id) {
        try {
            Optional<PurchaseModel> purchaseModelById = purchaseRepository.findById(id);
            PurchaseModel oldPurchaseModel = purchaseModelById.get();
            oldPurchaseModel.setDate(purchaseModel.getDate());
            oldPurchaseModel.setTotal(purchaseModel.getTotal());
            return purchaseRepository.save(purchaseModelById.get());
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, purchaseId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Purchase is not found.");
        }
    }

    @Override
    public boolean checkTotal(double total) {
        return total > 0;
    }

    @Override
    public PageDto<PurchaseModelFilterDto> findAll(
            PurchaseModelFilterDto purchaseModelFilterDto, int page, int size, Sort sort) {
        var pageData = PageDataCreator.createPageData(page, size, sort);
        var predicate = PurchaseModelSpecification.createPredicate(
                purchaseModelFilterDto.getDate(),
                purchaseModelFilterDto.getTotal()
        );

        var purchaseModelPage = purchaseRepository.findAll(predicate, pageData);

        return PageDtoCreator.createReadQueryResult(
                purchaseModelPage, purchaseModelMapper::toDto
        );
    }
}
