package com.warehouse.specifications;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.warehouse.model.QSupplyItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SupplyItemModelSpecification {

    private static final QSupplyItemModel Q_SUPPLY_ITEM_MODEL = QSupplyItemModel.supplyItemModel;

    public static BooleanExpression createPredicate(
            Long supplyId,
            Long itemId,
            Integer quantity,
            Double price
    ) {
        List<BooleanExpression> booleanExpressions = new ArrayList<>();

        if(!Objects.equals(supplyId, null)) {
            booleanExpressions.add(Q_SUPPLY_ITEM_MODEL.id.supply.id.eq(supplyId));
        }

        if(!Objects.equals(itemId, null)) {
            booleanExpressions.add(Q_SUPPLY_ITEM_MODEL.id.item.id.eq(itemId));
        }

        if(!Objects.equals(quantity, null)) {
            booleanExpressions.add(Q_SUPPLY_ITEM_MODEL.quantity.eq(quantity));
        }

        if(!Objects.equals(price, null)) {
            booleanExpressions.add(Q_SUPPLY_ITEM_MODEL.price.eq(price));
        }

        var resultBooleanExpression = Expressions.asBoolean(true).isTrue();
        for (var booleanExpression: booleanExpressions) {
            resultBooleanExpression = resultBooleanExpression.and(booleanExpression);
        }

        return resultBooleanExpression;
    }
}
