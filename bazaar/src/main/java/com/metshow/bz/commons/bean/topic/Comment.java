package com.metshow.bz.commons.bean.topic;


import com.kwan.base.bean.POJO;

import java.util.List;

/**
 * Created by Mr.Kwan on 2016-8-9.
 */
public class Comment extends POJO {

    private String Avatar;// "http://wx.qlogo.cn/mmopen/N4HWkmwbSVT7RZUW6kpIa66esicicDNtReKmSe1PicXoNceGMpfib3bic22Q8qzZ6gmWFEvSiawWMgGm3XZzrlfIAWGvlubuk13ujw/0",
    private long CommentId;// 162,
    private String Content;// "经常接触几次开发可可粉",
    private String CreateDate;// "/Date(1461189287000)/",
    private String Image;// null,
    private String NickName;// "mci",
    private long ParentId;// 0,
    private long RefId;// 3234,
    private int RefType;// null,
    private String Remark;// null,
    private String SourceDate;// "2016-04-01",
    private String SourceImage;// "http://bznewpicqiniu.createapp.cn/@/attached/30041/20160401/EDD4E11343B5AAA8068C251BBABFA450_md5_201604012140478396-960_640.jpg",
    private String SourceTitle;// null,
    private String ToNickName;// "编辑小Q",
    private long ToUserId;// 30041,
    private int Type;// 3,
    private long UserId;// 64870,
    private int ActionCount;// 0,
    private List<Comment> ChildCommentList;// [],
    private int IsUp;// 0,
    private int IsVip;// 0

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public long getCommentId() {
        return CommentId;
    }

    public void setCommentId(long commentId) {
        CommentId = commentId;
    }

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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public long getParentId() {
        return ParentId;
    }

    public void setParentId(long parentId) {
        ParentId = parentId;
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

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getSourceDate() {
        return SourceDate;
    }

    public void setSourceDate(String sourceDate) {
        SourceDate = sourceDate;
    }

    public String getSourceImage() {
        return SourceImage;
    }

    public void setSourceImage(String sourceImage) {
        SourceImage = sourceImage;
    }

    public String getSourceTitle() {
        return SourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        SourceTitle = sourceTitle;
    }

    public String getToNickName() {
        return ToNickName;
    }

    public void setToNickName(String toNickName) {
        ToNickName = toNickName;
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

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public int getActionCount() {
        return ActionCount;
    }

    public void setActionCount(int actionCount) {
        ActionCount = actionCount;
    }

    public List<Comment> getChildCommentList() {
        return ChildCommentList;
    }

    public void setChildCommentList(List<Comment> childCommentList) {
        ChildCommentList = childCommentList;
    }

    public int getIsUp() {
        return IsUp;
    }

    public void setIsUp(int isUp) {
        IsUp = isUp;
    }

    public int getIsVip() {
        return IsVip;
    }

    public void setIsVip(int isVip) {
        IsVip = isVip;
    }
}
