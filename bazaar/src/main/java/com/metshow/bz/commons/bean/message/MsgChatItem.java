package com.metshow.bz.commons.bean.message;


import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-7-22.
 */
public class MsgChatItem extends POJO {

    private String Avatar;// "http://bznewpicqiniu.createapp.cn/@/attached/64868/20160413/1123085D18E65126F8C5A19026626F05_md5_201604131711052908-400_400.jpg",
    private String Content;// null,
    private long DialogId;// 425,
    private String FollowDate;// "/Date(1468938521000)/",
    private long FollowId;// 64868,
    private long Id;// 854,
    private int IsDeleted;// 0,
    private String NickName;// "恐怖游轮",
    private int Type;// 0,
    private String Url;// null,
    private long UserId;// 30041,
    private String IsVip;// 0,
    private int NotReadCount;// 0

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public long getDialogId() {
        return DialogId;
    }

    public void setDialogId(long dialogId) {
        DialogId = dialogId;
    }

    public String getFollowDate() {
        return FollowDate;
    }

    public void setFollowDate(String followDate) {
        FollowDate = followDate;
    }

    public long getFollowId() {
        return FollowId;
    }

    public void setFollowId(long followId) {
        FollowId = followId;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public int getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        IsDeleted = isDeleted;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
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

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public String getIsVip() {
        return IsVip;
    }

    public void setIsVip(String isVip) {
        IsVip = isVip;
    }

    public int getNotReadCount() {
        return NotReadCount;
    }

    public void setNotReadCount(int notReadCount) {
        NotReadCount = notReadCount;
    }
}
