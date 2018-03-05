package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-8-21.
 */
public class Category extends POJO {


    private String CategoryEngName;// "New",
    private String CategoryIco;// "http://bznewpicqiniu.createapp.cn/@/attached/0/20160706/E772CB4B65E56D02A482F58EDB56D8DA_md5_201607061532570675-1042_833.jpg",
    private long CategoryId;// 11,
    private String CategoryName;// "芭姐最爱",
    private String Description;// "",
    private int OrderNum;// 1,
    private long ParentId;// 0

    public String getCategoryEngName() {
        return CategoryEngName;
    }

    public void setCategoryEngName(String categoryEngName) {
        CategoryEngName = categoryEngName;
    }

    public String getCategoryIco() {
        return CategoryIco;
    }

    public void setCategoryIco(String categoryIco) {
        CategoryIco = categoryIco;
    }

    public long getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(long categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(int orderNum) {
        OrderNum = orderNum;
    }

    public long getParentId() {
        return ParentId;
    }

    public void setParentId(long parentId) {
        ParentId = parentId;
    }
}
