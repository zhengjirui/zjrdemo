package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-9-21.
 */
public class ServerConfig extends POJO {

    private String Bg;//":"fff",
    private String ConfigId;//":1,
    private String FavStaticCount;//":2,
    private String HotKey;//":"美女,红唇,明星,宋仲基,宋慧乔,林俊杰,周杰伦,芭莎大咖秀,哈哈,黄晓明,张根硕,古巨基,骑车去拉萨,遇见最美的你,微微一笑很倾城,时尚芭莎",
    private String PointsCount;//":50000,
    private String UpStaticCount;//":1

    public String getBg() {
        return Bg;
    }

    public void setBg(String bg) {
        Bg = bg;
    }

    public String getConfigId() {
        return ConfigId;
    }

    public void setConfigId(String configId) {
        ConfigId = configId;
    }

    public String getFavStaticCount() {
        return FavStaticCount;
    }

    public void setFavStaticCount(String favStaticCount) {
        FavStaticCount = favStaticCount;
    }

    public String getHotKey() {
        return HotKey;
    }

    public void setHotKey(String hotKey) {
        HotKey = hotKey;
    }

    public String getPointsCount() {
        return PointsCount;
    }

    public void setPointsCount(String pointsCount) {
        PointsCount = pointsCount;
    }

    public String getUpStaticCount() {
        return UpStaticCount;
    }

    public void setUpStaticCount(String upStaticCount) {
        UpStaticCount = upStaticCount;
    }
}
