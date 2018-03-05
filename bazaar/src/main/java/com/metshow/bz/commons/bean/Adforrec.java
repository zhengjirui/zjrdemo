package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

import java.util.List;

/**
 * Created by Mr.Kwan on 2016-7-6.
 */
public class Adforrec extends POJO {

    private long AdId;//":9223372036854775807,
    private String CreateDate;//":"\/Date(928120800000+0800)\/",
    private String EndDate;//":"\/Date(928120800000+0800)\/",
    private int IndexPosition;//":9223372036854775807,
    private String Name;//":"字符串内容",
    private String Pic;//":"字符串内容",
    private long RefId;//":9223372036854775807,
    private String StartDate;//":"\/Date(928120800000+0800)\/",
    private String StatClickUrl;//":"字符串内容",
    private String StatPvUrl;//":"字符串内容",
    private int State;//":32767,
    private int Type;//":32767,
    private int Width;//":32767,
    private int Height;//":32767,
    private String Url;//":"字符串内容",

    private List<Aduser> aduser;//

    public long getAdId() {
        return AdId;
    }

    public void setAdId(long adId) {
        AdId = adId;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public int getIndexPosition() {
        return IndexPosition;
    }

    public void setIndexPosition(int indexPosition) {
        IndexPosition = indexPosition;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public long getRefId() {
        return RefId;
    }

    public void setRefId(long refId) {
        RefId = refId;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getStatClickUrl() {
        return StatClickUrl;
    }

    public void setStatClickUrl(String statClickUrl) {
        StatClickUrl = statClickUrl;
    }

    public String getStatPvUrl() {
        return StatPvUrl;
    }

    public void setStatPvUrl(String statPvUrl) {
        StatPvUrl = statPvUrl;
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

    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public List<Aduser> getAduser() {
        return aduser;
    }

    public void setAduser(List<Aduser> aduser) {
        this.aduser = aduser;
    }
}
