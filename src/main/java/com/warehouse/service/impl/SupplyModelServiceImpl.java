package com.warehouse.service.impl;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.SupplyModelFilterDto;
import com.warehouse.mapper.SupplyModelMapper;
import com.warehouse.model.SupplyModel;
import com.warehouse.repository.SupplyRepository;
import com.warehouse.service.SupplyModelService;
import com.warehouse.specifications.SupplyModelSpecification;
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
public class SupplyModelServiceImpl implements SupplyModelService {

    private final SupplyRepository supplyRepository;
    private final SupplyModelMapper supplyModelMapper;

    public SupplyModelServiceImpl(
            SupplyRepository supplyRepository, SupplyModelMapper supplyModelMapper) {
        this.supplyRepository = supplyRepository;
        this.supplyModelMapper = supplyModelMapper;
    }

    @Override
    public SupplyModel findById(Long id) {
        try {
            return supplyRepository.findById(id).get();
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, supplyId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Supply is not found.");
        }
    }

    @Override
    public Long deleteById(Long id) {
        try {
            supplyRepository.deleteById(id);
            return id;
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, supplyId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Supply is not found.");
        }
    }

    @Override
    public SupplyModel save(SupplyModel supplyModel) {
        return supplyRepository.save(supplyModel);
    }

    @Override
    public SupplyModel update(SupplyModel supplyModel, long id) {
        try {
            Optional<SupplyModel> supplyModelById = supplyRepository.findById(id);
            supplyModelById.get().setTotal(supplyModel.getTotal());
            return supplyRepository.save(supplyModelById.get());
        } catch(NoSuchElementException e) {
            log.error(String.format("%s, supplyId - %s", e.getMessage(), id));
            throw new NoSuchElementException("Supply is not found.");
        }
    }


    @Override
    public boolean checkTotal(double total) {
        return total > 0;
    }

    @Override
    public PageDto<SupplyModelFilterDto> findAll(
            SupplyModelFilterDto supplyModelFilterDto, int page, int size, Sort sort) {
        var pageData = PageDataCreator.createPageData(page, size, sort);
        var predicate = SupplyModelSpecification.createPredicate(
                supplyModelFilterDto.getDate(),
                supplyModelFilterDto.getTotal()
        );

        var supplyModelPage = supplyRepository.findAll(predicate, pageData);

        return PageDtoCreator.createReadQueryResult(
                supplyModelPage, supplyModelMapper::toDto
        );
    }
}
