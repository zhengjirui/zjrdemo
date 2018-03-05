package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * 启动屏 信息
 * Created by Mr.Kwan on 2016-6-28.
 */
public class Splash extends POJO {

    private String CreateDate;//= "\/Date(928120800000+0800)\/",
    private long Id;//:18446744073709551615,
    private String Image1;//"字符串内容",
    private String Image2;//"字符串内容",
    private String Image3;//":"字符串内容",
    private String Image4;//":"字符串内容",
    private String ModifyDate;//":"\/Date(928120800000+0800)\/",
    private String Name;//":"字符串内容",
    private int State;//":32767,
    private int Type;// 1:图片 2:视频 3:html5 4:gif图片
    private String Url;//":"字符串内容",
    private String UserId;//:9223372036854775807,
    private String UsingDate;//":"\/Date(928120800000+0800)\/"
    private long RefId;

    public long getRefId() {
        return RefId;
    }

    public void setRefId(long refId) {
        RefId = refId;
    }

    private String ClickUrl;//": "http://activity.m.yohobuy.com/yohoodsec?from=singlemessage&amp;isappinstalled=0",

    public String getClickUrl() {
        return ClickUrl;
    }

    public void setClickUrl(String clickUrl) {
        ClickUrl = clickUrl;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }

    public String getImage3() {
        return Image3;
    }

    public void setImage3(String image3) {
        Image3 = image3;
    }

    public String getImage4() {
        return Image4;
    }

    public void setImage4(String image4) {
        Image4 = image4;
    }

    public String getModifyDate() {
        return ModifyDate;
    }

    public void setModifyDate(String modifyDate) {
        ModifyDate = modifyDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUsingDate() {
        return UsingDate;
    }

    public void setUsingDate(String usingDate) {
        UsingDate = usingDate;
    }

    @Override
    public String toString() {
        return "Splash{" +
                "CreateDate='" + CreateDate + '\'' +
                ", Id=" + Id +
                ", Image1='" + Image1 + '\'' +
                ", Image2='" + Image2 + '\'' +
                ", Image3='" + Image3 + '\'' +
                ", Image4='" + Image4 + '\'' +
                ", ModifyDate='" + ModifyDate + '\'' +
                ", Name='" + Name + '\'' +
                ", State=" + State +
                ", Type=" + Type +
                ", Url='" + Url + '\'' +
                ", UserId='" + UserId + '\'' +
                ", UsingDate='" + UsingDate + '\'' +
                '}';
    }
}
