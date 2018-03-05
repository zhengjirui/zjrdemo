package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 *
 * Created by Mr.Kwan on 2016-8-8.
 */
public class FollowItem extends POJO {

    private String Avatar;// "http://7xlovw.com1.z0.glb.clouddn.com/cosmoweb/201603/1458637760687_584.jpg",
    private String FollowAvatar;// null,
    private String FollowDate;// "/Date(1469631201000)/",
    private String FollowNickName;// null,
    private long FollowUserId;// 0,
    private long Id;// 14452,
    private String NickName;// "编辑小QQ",
    private long UserId;// 30041,
    private String Description;// null,
    private int IsFollow;// 1,
    private int IsVip;// 0,
    private int UserType;// 0

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getFollowAvatar() {
        return FollowAvatar;
    }

    public void setFollowAvatar(String followAvatar) {
        FollowAvatar = followAvatar;
    }

    public String getFollowDate() {
        return FollowDate;
    }

    public void setFollowDate(String followDate) {
        FollowDate = followDate;
    }

    public String getFollowNickName() {
        return FollowNickName;
    }

    public void setFollowNickName(String followNickName) {
        FollowNickName = followNickName;
    }

    public long getFollowUserId() {
        return FollowUserId;
    }

    public void setFollowUserId(long followUserId) {
        FollowUserId = followUserId;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getIsFollow() {
        return IsFollow;
    }

    public void setIsFollow(int isFollow) {
        IsFollow = isFollow;
    }

    public int getIsVip() {
        return IsVip;
    }

    public void setIsVip(int isVip) {
        IsVip = isVip;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }
}
