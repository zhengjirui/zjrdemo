package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-10-31.
 */

public class AdminUser extends POJO {

    private long AdminId;// 30040,
    private String Avatar;// "http://bznewpicqiniu.createapp.cn/@/attached/30040/20161025/09179DBAA65052B148E1B13ACB1E26B3_md5_201610251210213512-400_400.jpg",
    private int BeFollowedCount;// 2928,
    private String Description;// "我就是人见人爱的芭姐，我在这里等你噢！",
    private int FollowCount;// 0,
    private int IsFollow;// 0,
    private int IsRefuseMessage;// 0,
    private int IsVip;// 1,
    private String NickName;// "时尚芭莎",
    private String Rank;// "芭姐大管家",
    private String Tags;// "高端大气上档次，低调奢华有内涵",
    private long UserId;// 30040

    public long getAdminId() {
        return AdminId;
    }

    public void setAdminId(long adminId) {
        AdminId = adminId;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public int getBeFollowedCount() {
        return BeFollowedCount;
    }

    public void setBeFollowedCount(int beFollowedCount) {
        BeFollowedCount = beFollowedCount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getFollowCount() {
        return FollowCount;
    }

    public void setFollowCount(int followCount) {
        FollowCount = followCount;
    }

    public int getIsFollow() {
        return IsFollow;
    }

    public void setIsFollow(int isFollow) {
        IsFollow = isFollow;
    }

    public int getIsRefuseMessage() {
        return IsRefuseMessage;
    }

    public void setIsRefuseMessage(int isRefuseMessage) {
        IsRefuseMessage = isRefuseMessage;
    }

    public int getIsVip() {
        return IsVip;
    }

    public void setIsVip(int isVip) {
        IsVip = isVip;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }
}
