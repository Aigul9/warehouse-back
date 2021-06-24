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
public class SupplyItemPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "supply_id")
    private SupplyModel supply;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemModel item;
}
