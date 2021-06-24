package com.warehouse.specifications;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.warehouse.model.InventoryStatus;
import com.warehouse.model.QInventoryModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class InventoryModelSpecification {

    private static final QInventoryModel Q_INVENTORY_MODEL = QInventoryModel.inventoryModel;

    public static BooleanExpression createPredicate(
            Date date,
            Double total,
            InventoryStatus inventoryStatus
    ) {
        List<BooleanExpression> booleanExpressions = new ArrayList<>();

        if(!Objects.equals(date, null)) {
            booleanExpressions.add(Q_INVENTORY_MODEL.date.eq(date));
        }

        if(!Objects.equals(total, null)) {
            booleanExpressions.add(Q_INVENTORY_MODEL.total.eq(total));
        }

        if(!Objects.equals(inventoryStatus, null)) {
            booleanExpressions.add(Q_INVENTORY_MODEL.inventoryStatus.eq(inventoryStatus));
        }

        var resultBooleanExpression = Expressions.asBoolean(true).isTrue();
        for (var booleanExpression: booleanExpressions) {
            resultBooleanExpression = resultBooleanExpression.and(booleanExpression);
        }

        return resultBooleanExpression;
    }
}
