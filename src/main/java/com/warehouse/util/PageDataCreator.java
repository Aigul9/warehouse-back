package com.warehouse.util;

import com.warehouse.dto.PageData;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageDataCreator {
    public static PageData createPageData(int page, int size, Sort sort) {
        var pageable = createPageable(page, size, sort);
        return new PageData(pageable, sort);
    }

    public static Pageable createPageable(int page, int size, Sort sort) {
        if (page < 0 || size <= 0) {
            return Pageable.unpaged();
        }
        return PageRequest.of(page, size, sort);
    }
}
