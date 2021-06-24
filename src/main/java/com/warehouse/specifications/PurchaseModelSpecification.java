package com.warehouse.specifications;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.warehouse.model.QPurchaseModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PurchaseModelSpecification {

    private static final QPurchaseModel Q_PURCHASE_MODEL = QPurchaseModel.purchaseModel;

    public static BooleanExpression createPredicate(
            Date date,
            Double total
    ) {
        List<BooleanExpression> booleanExpressions = new ArrayList<>();

        if(!Objects.equals(date, null)) {
            booleanExpressions.add(Q_PURCHASE_MODEL.date.eq(date));
        }

        if(!Objects.equals(total, null)) {
            booleanExpressions.add(Q_PURCHASE_MODEL.total.eq(total));
        }

        var resultBooleanExpression = Expressions.asBoolean(true).isTrue();
        for (var booleanExpression: booleanExpressions) {
            resultBooleanExpression = resultBooleanExpression.and(booleanExpression);
        }

        return resultBooleanExpression;
    }
}
