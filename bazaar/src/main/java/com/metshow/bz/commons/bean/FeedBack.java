package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * 反馈信息
 * Created by Mr.Kwan on 2016-7-6.
 */
public class FeedBack extends POJO {

    private String Content;//字符串内容,
    private String CreateDate;//\/Date(928120800000+0800)\/,
    private String Email;//字符串内容,
    private long Id;//:9223372036854775807,
    private String Images;//字符串内容,
    private String NickName;//字符串内容,
    private int State;//:32767,
    private String Tags;//字符串内容,
    private String UserId;//字符串内容,
    private String Avatar;//字符串内容,
    private int UpCount;//:9223372036854775807

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public int getUpCount() {
        return UpCount;
    }

    public void setUpCount(int upCount) {
        UpCount = upCount;
    }
}
