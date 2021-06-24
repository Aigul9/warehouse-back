package com.warehouse.specifications;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.warehouse.model.QItemModel;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemModelSpecification {

    private static final QItemModel Q_ITEM_MODEL = QItemModel.itemModel;

    public static BooleanExpression createPredicate(
            String name,
            Integer quantity,
            Double price,
            Long categoryId
    ) {
        List<BooleanExpression> booleanExpressions = new ArrayList<>();

        if(!StringUtils.isBlank(name)) {
            booleanExpressions.add(Q_ITEM_MODEL.name.likeIgnoreCase("%" + name.trim() + "%"));
        }

        if(!Objects.equals(quantity, null)) {
            booleanExpressions.add(Q_ITEM_MODEL.quantity.eq(quantity));
        }

        if(!Objects.equals(price, null)) {
            booleanExpressions.add(Q_ITEM_MODEL.price.eq(price));
        }

        if(!Objects.equals(categoryId, null)) {
            booleanExpressions.add(Q_ITEM_MODEL.group.id.eq(categoryId));
        }

        var resultBooleanExpression = Expressions.asBoolean(true).isTrue();
        for (var booleanExpression: booleanExpressions) {
            resultBooleanExpression = resultBooleanExpression.and(booleanExpression);
        }

        return resultBooleanExpression;
    }
}
