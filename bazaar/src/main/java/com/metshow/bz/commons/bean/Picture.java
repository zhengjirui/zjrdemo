package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-8-18.
 */
public class Picture extends POJO {

    private long Id;//": 2418,

    private String PicPath;//":

    private String Remark;//": ""

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getPicPath() {
        return PicPath;
    }

    public void setPicPath(String picPath) {
        PicPath = picPath;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
