package com.warehouse.service;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.PurchaseModelFilterDto;
import com.warehouse.model.PurchaseModel;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public interface PurchaseModelService {
    PurchaseModel findById(Long id);

    Long deleteById(Long id);

    PurchaseModel save(PurchaseModel model);

    PurchaseModel update(PurchaseModel model, long id);

    boolean checkTotal(double total);

    PageDto<PurchaseModelFilterDto> findAll(
            PurchaseModelFilterDto purchaseModelFilterDto, int page, int size, Sort sort);
}
