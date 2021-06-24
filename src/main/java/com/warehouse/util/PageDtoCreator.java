package com.warehouse.util;

import com.warehouse.dto.PageDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageDtoCreator {

    public static <Entity, Dto> PageDto<Dto> createReadQueryResult(
            Page<Entity> page, Function<Entity, Dto> convert
    ) {
        List<Dto> dtoList = page
                .stream()
                .map(convert)
                .collect(Collectors.toList());
        return new PageDto<>(page.getTotalPages(), dtoList);
    }

    private PageDtoCreator(){}
}
