package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

import java.util.List;

/**
 * Created by Mr.Kwan on 2016-7-5.
 */
public class Channel extends POJO {

    private String ChannelIco;//":"字符串内容",
    private long ChannelId;//":9223372036854775807,
    private String ChannelName;//":"字符串内容",
    private String Description;//":"字符串内容",
    private long OrderNum;//":2147483647,
    private long ParentId;//":9223372036854775807,
    private List<Channel> childChannel;//

    public String getChannelIco() {
        return ChannelIco;
    }

    public void setChannelIco(String channelIco) {
        ChannelIco = channelIco;
    }

    public long getChannelId() {
        return ChannelId;
    }

    public void setChannelId(long channelId) {
        ChannelId = channelId;
    }

    public String getChannelName() {
        return ChannelName;
    }

    public void setChannelName(String channelName) {
        ChannelName = channelName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public long getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(long orderNum) {
        OrderNum = orderNum;
    }

    public long getParentId() {
        return ParentId;
    }

    public void setParentId(long parentId) {
        ParentId = parentId;
    }

    public List<Channel> getChildChannel() {
        return childChannel;
    }

    public void setChildChannel(List<Channel> childChannel) {
        this.childChannel = childChannel;
    }
}
