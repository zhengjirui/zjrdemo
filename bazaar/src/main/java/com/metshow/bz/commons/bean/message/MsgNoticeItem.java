package com.metshow.bz.commons.bean.message;


import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-7-22.
 */
public class MsgNoticeItem extends POJO {

    private String Avatar;// "http://7xlovw.com1.z0.glb.clouddn.com/cosmoweb/201603/1458637760687_584.jpg",
    private String CreateDate;// "/Date(1469014433000)/",
    private String NickName;// "编辑小QQ",
    private long NoticeId;// 4092,
    private long RefId;// 6035,
    private int RefType;// 0,
    private String Title;// "赞了你2016-07-19发布的图片",
    private long ToUserId;// 30041,
    private int Type;// 3,
    private String Url;// "http://bznewpicqiniu.createapp.cn/@/attached/0/20160719/AEE495FECFF3D08389673528DEE5F67E_md5_201607191435394031-336_377.jpg",
    private long UserId;// 30041,
    private int IsVip;// 1

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public long getNoticeId() {
        return NoticeId;
    }

    public void setNoticeId(long noticeId) {
        NoticeId = noticeId;
    }

    public long getRefId() {
        return RefId;
    }

    public void setRefId(long refId) {
        RefId = refId;
    }

    public int getRefType() {
        return RefType;
    }

    public void setRefType(int refType) {
        RefType = refType;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public long getToUserId() {
        return ToUserId;
    }

    public void setToUserId(long toUserId) {
        ToUserId = toUserId;
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

    public int getIsVip() {
        return IsVip;
    }

    public void setIsVip(int isVip) {
        IsVip = isVip;
    }
}
