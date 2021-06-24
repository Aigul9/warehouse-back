package com.warehouse.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Represents a page.")
public class PageDto<Dto> {

    private int totalPages;
    List<Dto> dtoList;

    public PageDto(
            int totalPages,
            List<Dto> dtoList
    ) {
        this.totalPages = totalPages;
        this.dtoList = dtoList;
    }
}
