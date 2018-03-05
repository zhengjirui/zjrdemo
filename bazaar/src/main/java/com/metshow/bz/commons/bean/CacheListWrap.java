package com.metshow.bz.commons.bean;

import java.util.List;

/**
 * Created by Mr.Kwan on 2016-7-14.
 */
public class CacheListWrap<T>  {

    private List<T> data;


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
