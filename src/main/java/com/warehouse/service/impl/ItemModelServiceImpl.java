package com.warehouse.service.impl;

import com.warehouse.dto.filter.ItemModelFilterDto;
import com.warehouse.dto.PageDto;
import com.warehouse.mapper.ItemModelMapper;
import com.warehouse.model.ItemModel;
import com.warehouse.repository.ItemRepository;
import com.warehouse.service.ItemModelService;
import com.warehouse.specifications.ItemModelSpecification;
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
public class ItemModelServiceImpl implements ItemModelService {

    private final ItemRepository itemRepository;
    private final ItemModelMapper itemModelMapper;

    public ItemModelServiceImpl(ItemRepository itemRepository, ItemModelMapper itemModelMapper) {
        this.itemRepository = itemRepository;
        this.itemModelMapper = itemModelMapper;
    }

    @Override
    public ItemModel findById(Long id) {
        try {
            return itemRepository.findById(id).get();
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, itemId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Item is not found.");
        }
    }

    @Override
    public Long deleteById(Long id) {
        try {
            itemRepository.deleteById(id);
            return id;
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, itemId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Item is not found.");
        }
    }

    @Override
    public ItemModel save(ItemModel itemModel) {
        return itemRepository.save(itemModel);
    }

    @Override
    public ItemModel update(ItemModel itemModel, Long id) {
        try {
            Optional<ItemModel> itemModelById = itemRepository.findById(id);
            ItemModel oldItemModel = itemModelById.get();

            oldItemModel.setName(itemModel.getName());
            oldItemModel.setPrice(itemModel.getPrice());
            oldItemModel.setQuantity(itemModel.getQuantity());
            oldItemModel.setGroup(itemModel.getGroup());

            return itemRepository.save(oldItemModel);
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, itemId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Item is not found.");
        }
    }

    @Override
    public boolean checkCount(Long id, Integer quantity) {
        try {
            if (quantity <= 0) {
                log.error(String.format("Negative quantity, itemId - %s, quantity - %s", id, quantity));
                throw new ArithmeticException("Negative quantity.");
            } else return itemRepository.findById(id).get().getQuantity() - quantity >= 0;
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, itemId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Item is not found.");
        }
    }

    @Override
    public PageDto<ItemModelFilterDto> findAll(
            ItemModelFilterDto itemModelFilterDto, int page, int size, Sort sort) {

        var pageData = PageDataCreator.createPageData(page, size, sort);
        var predicate = ItemModelSpecification.createPredicate(
                itemModelFilterDto.getName(),
                itemModelFilterDto.getQuantity(),
                itemModelFilterDto.getPrice(),
                itemModelFilterDto.getCategoryId()
        );

        var itemModelPage = itemRepository.findAll(predicate, pageData);

        return PageDtoCreator.createReadQueryResult(
                itemModelPage, itemModelMapper::toDto
        );
    }
}
