package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

import java.util.List;

/**
 * Created by Mr.Kwan on 2016-7-6.
 */
public class ListData<T> extends POJO {

    private int CurrentPage;//":9223372036854775807,
    private List<T> Items;//":[{}],
    private int ItemsPerPage;//":9223372036854775807,
    private int TotalItems;//":9223372036854775807,
    private int TotalPage;//；s:9223372036854775807

    public int getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        CurrentPage = currentPage;
    }

    public List<T> getItems() {
        return Items;
    }

    public void setItems(List<T> items) {
        Items = items;
    }

    public int getItemsPerPage() {
        return ItemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        ItemsPerPage = itemsPerPage;
    }

    public int getTotalItems() {
        return TotalItems;
    }

    public void setTotalItems(int totalItems) {
        TotalItems = totalItems;
    }

    public int getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(int totalPage) {
        TotalPage = totalPage;
    }

    @Override
    public String toString() {
        return "ListData{" +
                "CurrentPage=" + CurrentPage +
                ", ItemsPerPage=" + ItemsPerPage +
                ", TotalItems=" + TotalItems +
                ", TotalPage=" + TotalPage +
                '}';
    }
}
