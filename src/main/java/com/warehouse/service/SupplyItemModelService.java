package com.warehouse.service;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.SupplyItemModelFilterDto;
import com.warehouse.model.SupplyItemModel;
import com.warehouse.model.SupplyItemPK;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public interface SupplyItemModelService {
    SupplyItemModel findById(SupplyItemPK id);

    SupplyItemPK deleteById(SupplyItemPK id);

    SupplyItemModel save(SupplyItemModel model);

    SupplyItemModel update(SupplyItemModel model, SupplyItemPK id);

    PageDto<SupplyItemModelFilterDto> findAll(
            SupplyItemModelFilterDto supplyItemModelFilterDto, int page, int size, Sort sort);
}
