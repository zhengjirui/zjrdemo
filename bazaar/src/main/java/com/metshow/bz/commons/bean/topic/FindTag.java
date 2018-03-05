package com.metshow.bz.commons.bean.topic;


import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-8-24.
 */
public class FindTag extends POJO {


	private int CommunityArticleCount;//": null,
    private String Ico;// "",
    private long Id;// 78,
    private int IsRecommend;// 1,
    private String Name;// "美妆",
    private int OrderNum;// 4,
    private int Type;// 1,
    private int CountNum;// 0,
    private int IsFollow;// 0

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
