package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-8-29.
 */
public class MsgCount extends POJO {

    private int FollowedCount ;//":9223372036854775807,
    private int MessageCount;//":9223372036854775807,
    private int ReplyCount;//":9223372036854775807

    public int getFollowedCount() {
        return FollowedCount;
    }

    public void setFollowedCount(int followedCount) {
        FollowedCount = followedCount;
    }

    public int getMessageCount() {
        return MessageCount;
    }

    public void setMessageCount(int messageCount) {
        MessageCount = messageCount;
    }

    public int getReplyCount() {
        return ReplyCount;
    }

    public void setReplyCount(int replyCount) {
        ReplyCount = replyCount;
    }
}
