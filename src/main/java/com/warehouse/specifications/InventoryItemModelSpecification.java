package com.warehouse.specifications;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.warehouse.model.QInventoryItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryItemModelSpecification {

    private static final QInventoryItemModel Q_INVENTORY_ITEM_MODEL = QInventoryItemModel.inventoryItemModel;

    public static BooleanExpression createPredicate(
            Long inventoryId,
            Long itemId,
            Integer quantity,
            Double price
    ) {
        List<BooleanExpression> booleanExpressions = new ArrayList<>();

        if(!Objects.equals(inventoryId, null)) {
            booleanExpressions.add(Q_INVENTORY_ITEM_MODEL.id.inventory.id.eq(inventoryId));
        }

        if(!Objects.equals(itemId, null)) {
            booleanExpressions.add(Q_INVENTORY_ITEM_MODEL.id.item.id.eq(itemId));
        }

        if(!Objects.equals(quantity, null)) {
            booleanExpressions.add(Q_INVENTORY_ITEM_MODEL.quantity.eq(quantity));
        }

        if(!Objects.equals(price, null)) {
            booleanExpressions.add(Q_INVENTORY_ITEM_MODEL.price.eq(price));
        }

        var resultBooleanExpression = Expressions.asBoolean(true).isTrue();
        for (var booleanExpression: booleanExpressions) {
            resultBooleanExpression = resultBooleanExpression.and(booleanExpression);
        }

        return resultBooleanExpression;
    }
}
