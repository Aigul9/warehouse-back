package com.warehouse.service;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.PurchaseItemModelFilterDto;
import com.warehouse.model.PurchaseItemModel;
import com.warehouse.model.PurchaseItemPK;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public interface PurchaseItemModelService {
    PurchaseItemModel findById(PurchaseItemPK id);

    PurchaseItemPK deleteById(PurchaseItemPK id);

    PurchaseItemModel save(PurchaseItemModel model);

    PurchaseItemModel update(PurchaseItemModel model, PurchaseItemPK id);

    PageDto<PurchaseItemModelFilterDto> findAll(
            PurchaseItemModelFilterDto purchaseItemModelFilterDto, int page, int size, Sort sort);
}
