package com.metshow.bz.commons.bean.topic;

import com.kwan.base.bean.POJO;

/**
 * Created by mayn on 2017/11/30 0030.
 */

public class StickerBean extends POJO {


    private long AdminId;//":9223372036854775807,
    private String CreateDate;//":"\/Date(928120800000+0800)\/",
    private String Name;//":"字符串内容",
    private String Pic;//":"字符串内容",
    private String Remark;//":"字符串内容",
    private int  State;//":32767,
    private long StickerId;//":9223372036854775807,
    private long StickerTypeId;//":9223372036854775807,
    private long UserId;//":9223372036854775807

    public long getAdminId() {
        return AdminId;
    }

    public void setAdminId(long adminId) {
        AdminId = adminId;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public long getStickerId() {
        return StickerId;
    }

    public void setStickerId(long stickerId) {
        StickerId = stickerId;
    }

    public long getStickerTypeId() {
        return StickerTypeId;
    }

    public void setStickerTypeId(long stickerTypeId) {
        StickerTypeId = stickerTypeId;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }
}
