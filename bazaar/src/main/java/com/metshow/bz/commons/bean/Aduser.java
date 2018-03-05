package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-7-6.
 */
public class Aduser extends POJO {

    private long AdId;//":9223372036854775807,
    private long AdUserId;//":9223372036854775807,
    private String Avatar;//":"字符串内容",
    private String NickName;//":"字符串内容",
    private long UserId;//":9223372036854775807
    private int IsFollow;

    public int getIsFollow() {
        return IsFollow;
    }

    public void setIsFollow(int isFollow) {
        this.IsFollow = isFollow;
    }

    public long getAdId() {
        return AdId;
    }

    public void setAdId(long adId) {
        AdId = adId;
    }

    public long getAdUserId() {
        return AdUserId;
    }

    public void setAdUserId(long adUserId) {
        AdUserId = adUserId;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }
}
