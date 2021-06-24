package com.warehouse.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@AllArgsConstructor
public class PageData {

    private final Pageable pageable;
    private final Sort sort;
}
