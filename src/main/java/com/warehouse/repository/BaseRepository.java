package com.warehouse.repository;

import com.querydsl.core.types.Predicate;
import com.warehouse.dto.PageData;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<Entity, Key> extends JpaRepository<Entity, Key>, QuerydslPredicateExecutor<Entity> {
    default Page<Entity> findAll(Predicate predicate, PageData pageData) {
        if (pageData.getPageable().isUnpaged()) {
            return new PageImpl<>(IterableUtils.toList(findAll(predicate, pageData.getSort())));
        }
        return findAll(predicate, pageData.getPageable());
    }
}
