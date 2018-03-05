package com.kwan.base.bean;

import com.kwan.base.adatper.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by Mr.Kwan on 2016-6-28.
 */
public class POJO<T> extends MultiItemEntity implements Serializable{

    private String tag;

	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
