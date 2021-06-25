package com.warehouse.controller;

import com.warehouse.dto.PageDto;
import com.warehouse.dto.filter.SupplyModelFilterDto;
import com.warehouse.dto.request.RequestSupplyModelDto;
import com.warehouse.model.SupplyModel;
import com.warehouse.service.SupplyModelService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/supplies")
public class SupplyCrudController {

    private final SupplyModelService supplyModelService;

    public SupplyCrudController(SupplyModelService supplyModelService) {
        this.supplyModelService = supplyModelService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get supplies", notes = "This method is used to get list of supplies made in the store.")
    public List<SupplyModel> getAllSupplies() {
        return supplyModelService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get supply", notes = "This method is used to get specific supply to the store.")
    public SupplyModel get(@PathVariable("id") @ApiParam(value = "Supply id") Long id) {
        return supplyModelService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SUPPLY_PERMISSION')")
    @ApiOperation(value = "Create supply", notes = "This method is used to add a new supply.")
    public ResponseEntity<SupplyModel> post(@RequestBody RequestSupplyModelDto requestSupplyModelDto) {
        return new ResponseEntity<>(supplyModelService.save(
                requestSupplyModelDto.fromRequestSupplyModelDtoToSupplyModel(requestSupplyModelDto)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPPLY_PERMISSION')")
    @ApiOperation(value = "Delete supply", notes = "This method is used to delete the supply.")
    public Long delete(@PathVariable("id") @ApiParam(value = "Supply id") Long id) {
        return supplyModelService.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPPLY_PERMISSION')")
    @ApiOperation(value = "Update supply", notes = "This method is used to update the supply.")
    public SupplyModel update(@RequestBody RequestSupplyModelDto requestSupplyModelDto,
                              @PathVariable("id") @ApiParam(value = "Supply id") long id) {
        return supplyModelService.update(
                requestSupplyModelDto.fromRequestSupplyModelDtoToSupplyModel(requestSupplyModelDto), id);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('READ_ONLY_PERMISSION')")
    @ApiOperation(value = "Get list of supplies with constraints")
    public ResponseEntity<PageDto<SupplyModelFilterDto>> getSupplies(
            @RequestParam(value = "date", required = false) Date date,
            @RequestParam(value = "total", required = false) Double total,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var response = supplyModelService.findAll(
                new SupplyModelFilterDto(
                        date, total
                ),
                page,
                size,
                Sort.by(Sort.Direction.fromString(direction), sortBy)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
