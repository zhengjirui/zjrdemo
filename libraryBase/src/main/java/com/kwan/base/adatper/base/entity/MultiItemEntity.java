package com.kwan.base.adatper.base.entity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public abstract class MultiItemEntity {

    protected int itemType = 0;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
