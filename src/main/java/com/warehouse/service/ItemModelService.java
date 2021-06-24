package com.warehouse.service;

import com.warehouse.dto.filter.ItemModelFilterDto;
import com.warehouse.dto.PageDto;
import com.warehouse.model.ItemModel;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public interface ItemModelService {
    ItemModel findById(Long id);

    Long deleteById(Long id);

    ItemModel save(ItemModel model);

    ItemModel update(ItemModel model, Long id);

    boolean checkCount(Long id, Integer quantity);

    PageDto<ItemModelFilterDto> findAll(
            ItemModelFilterDto itemModelFilterDto, int page, int size, Sort sort);
}
