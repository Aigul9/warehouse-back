package com.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PurchaseItemPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private PurchaseModel purchase;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemModel item;
}