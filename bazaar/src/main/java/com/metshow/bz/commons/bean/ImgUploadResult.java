package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-7-29.
 */
public class ImgUploadResult extends POJO {

    private String url;//"http://bznewpicqiniu.createapp.cn/@/attached/30041/20160729/9F842B54DBFE514CDD5A1FF8EE25BD3B_md5_201607291223316078-1080_1920.png",
    private String message;//null,
    private int error;//0,
    private int width;//0,
    private int height;//0,
    private String md5value;//"9F842B54DBFE514CDD5A1FF8EE25BD3B_md5"}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMd5value() {
        return md5value;
    }

    public void setMd5value(String md5value) {
        this.md5value = md5value;
    }
}
