package com.warehouse.specifications;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.warehouse.model.QPurchaseItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PurchaseItemModelSpecification {

    private static final QPurchaseItemModel Q_PURCHASE_ITEM_MODEL = QPurchaseItemModel.purchaseItemModel;

    public static BooleanExpression createPredicate(
            Long purchaseId,
            Long itemId,
            Integer quantity,
            Double price
    ) {
        List<BooleanExpression> booleanExpressions = new ArrayList<>();

        if(!Objects.equals(purchaseId, null)) {
            booleanExpressions.add(Q_PURCHASE_ITEM_MODEL.id.purchase.id.eq(purchaseId));
        }

        if(!Objects.equals(itemId, null)) {
            booleanExpressions.add(Q_PURCHASE_ITEM_MODEL.id.item.id.eq(itemId));
        }

        if(!Objects.equals(quantity, null)) {
            booleanExpressions.add(Q_PURCHASE_ITEM_MODEL.quantity.eq(quantity));
        }

        if(!Objects.equals(price, null)) {
            booleanExpressions.add(Q_PURCHASE_ITEM_MODEL.price.eq(price));
        }

        var resultBooleanExpression = Expressions.asBoolean(true).isTrue();
        for (var booleanExpression: booleanExpressions) {
            resultBooleanExpression = resultBooleanExpression.and(booleanExpression);
        }

        return resultBooleanExpression;
    }
}
