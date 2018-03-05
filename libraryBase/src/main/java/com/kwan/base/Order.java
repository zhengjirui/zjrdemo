package com.kwan.base;

import com.kwan.base.bean.POJO;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Mr.Kwan on 2016-12-30.
 */
@Entity
public class Order extends POJO {

    private long orderId;
    private int orderNum;

    @Generated(hash = 1727044028)
    public Order(long orderId, int orderNum) {
        this.orderId = orderId;
        this.orderNum = orderNum;
    }

    @Generated(hash = 1105174599)
    public Order() {
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
}
