package com.metshow.bz.commons.bean.message;


import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-7-26.
 */
public class MsgChatDetail extends POJO {

    private String Content;// "[咒骂]",
    private String CreateDate;// "/Date(1469438508000)/",
    private long DialogId;// 425,
    private int IsRead;// 0,
    private long MessageId;// 2181,
    private String ToUserAvatar;// "http://bznewpicqiniu.createapp.cn/@/attached/64868/20160413/1123085D18E65126F8C5A19026626F05_md5_201604131711052908-400_400.jpg",
    private long ToUserId;// 64868,
    private String ToUserNickName;// "恐怖游轮",
    private int Type;// 0,
    private String Url;// null,
    private String UserAvatar;// "http://7xlovw.com1.z0.glb.clouddn.com/cosmoweb/201603/1458637760687_584.jpg",
    private long UserId;// 30041,
    private String UserNickName;// "编辑小QQ"

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

    public long getDialogId() {
        return DialogId;
    }

    public void setDialogId(long dialogId) {
        DialogId = dialogId;
    }

    public int getIsRead() {
        return IsRead;
    }

    public void setIsRead(int isRead) {
        IsRead = isRead;
    }

    public long getMessageId() {
        return MessageId;
    }

    public void setMessageId(long messageId) {
        MessageId = messageId;
    }

    public String getToUserAvatar() {
        return ToUserAvatar;
    }

    public void setToUserAvatar(String toUserAvatar) {
        ToUserAvatar = toUserAvatar;
    }

    public long getToUserId() {
        return ToUserId;
    }

    public void setToUserId(long toUserId) {
        ToUserId = toUserId;
    }

    public String getToUserNickName() {
        return ToUserNickName;
    }

    public void setToUserNickName(String toUserNickName) {
        ToUserNickName = toUserNickName;
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

    public String getUserAvatar() {
        return UserAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        UserAvatar = userAvatar;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public String getUserNickName() {
        return UserNickName;
    }

    public void setUserNickName(String userNickName) {
        UserNickName = userNickName;
    }

    @Override
    public String toString() {
        return "MsgChatDetail{" +
                "Content='" + Content + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", DialogId=" + DialogId +
                ", IsRead=" + IsRead +
                ", MessageId=" + MessageId +
                ", ToUserAvatar='" + ToUserAvatar + '\'' +
                ", ToUserId=" + ToUserId +
                ", ToUserNickName='" + ToUserNickName + '\'' +
                ", Type=" + Type +
                ", Url='" + Url + '\'' +
                ", UserAvatar='" + UserAvatar + '\'' +
                ", UserId=" + UserId +
                ", UserNickName='" + UserNickName + '\'' +
                '}';
    }
}
