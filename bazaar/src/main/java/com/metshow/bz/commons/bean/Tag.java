package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

import java.util.List;

/**
 * Created by Mr.Kwan on 2016-8-6.
 */
public class Tag extends POJO {

    private String Ico;// "",
    private long Id;// 442,
    private int IsRecommend;// 0,
    private String Name;// "3ce",
    private int OrderNum;// null,
    private int Type;// 3,
    private List<String> Images;// [
//            "http://bznewpicqiniu.createapp.cn/@/attached/0/20160623/3519FBFF3324ACF845D8D9C3068C5C83_md5_201606231157583011-800_800.jpg"
//            ]
    private int CountNum;//": 0,
    private int IsFollow;//": 0
	private int CommunityArticleCount;

	public int getCommunityArticleCount() {
		return CommunityArticleCount;
	}

	public void setCommunityArticleCount(int communityArticleCount) {
		CommunityArticleCount = communityArticleCount;
	}

	public String getIco() {
        return Ico;
    }

    public void setIco(String ico) {
        Ico = ico;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public int getIsRecommend() {
        return IsRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        IsRecommend = isRecommend;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(int orderNum) {
        OrderNum = orderNum;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public List<String> getImages() {
        return Images;
    }

    public void setImages(List<String> images) {
        Images = images;
    }

    public int getCountNum() {
        return CountNum;
    }

    public void setCountNum(int countNum) {
        CountNum = countNum;
    }

    public int getIsFollow() {
        return IsFollow;
    }

    public void setIsFollow(int isFollow) {
        IsFollow = isFollow;
    }
}
