package com.warehouse.specifications;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.warehouse.model.QCategoryModel;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryModelSpecification {

    private static final QCategoryModel Q_CATEGORY_MODEL = QCategoryModel.categoryModel;

    public static BooleanExpression createPredicate(String name) {
        List<BooleanExpression> booleanExpressions = new ArrayList<>();

        if(!StringUtils.isBlank(name)) {
            booleanExpressions.add(Q_CATEGORY_MODEL.name.likeIgnoreCase("%" + name.trim() + "%"));
        }

        var resultBooleanExpression = Expressions.asBoolean(true).isTrue();
        for (var booleanExpression: booleanExpressions) {
            resultBooleanExpression = resultBooleanExpression.and(booleanExpression);
        }

        return resultBooleanExpression;
    }
}
