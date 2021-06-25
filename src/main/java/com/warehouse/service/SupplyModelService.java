package com.warehouse.service;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.SupplyModelFilterDto;
import com.warehouse.model.SupplyModel;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplyModelService {
    SupplyModel findById(Long id);

    List<SupplyModel> findAll();

    Long deleteById(Long id);

    SupplyModel save(SupplyModel model);

    SupplyModel update(SupplyModel model, long id);

    boolean checkTotal(double total);

    PageDto<SupplyModelFilterDto> findAll(
            SupplyModelFilterDto supplyModelFilterDto, int page, int size, Sort sort);
}
