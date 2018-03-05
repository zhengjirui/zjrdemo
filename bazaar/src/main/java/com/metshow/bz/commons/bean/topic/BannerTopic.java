package com.metshow.bz.commons.bean.topic;


import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-11-8.
 */

public class BannerTopic extends POJO {


    private String Avatar;// "http://bznewpicqiniu.createapp.cn/@/attached/0/20161103/D885986840D0BC6408CB290C2D8D8819_md5_201611031852430842-750_750.jpg",
    private String CreateDate;// "/Date(1478199182000)/",
    private String Description;// "芭莎福利社｜分享你的鸡汤语录，就有机会赢卸妆膏！",
    private int IsHot;// 1,
    private String IsRecommend;// null,
    private String Name;// "暖身鸡汤",
    private int OrderNum;// 125,
    private String Pic;// "http://bznewpicqiniu.createapp.cn/@/attached/0/20161103/37F18308634395E8764185DB859BA7A5_md5_201611031852506623-1080_547.gif",
    private int State;// 1,
    private long TopicId;// 21,
    private int IsFollow;// 0,
    private int JoinUserCount;// 21

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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getIsHot() {
        return IsHot;
    }

    public void setIsHot(int isHot) {
        IsHot = isHot;
    }

    public String getIsRecommend() {
        return IsRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        IsRecommend = isRecommend;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(int orderNum) {
        OrderNum = orderNum;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public long getTopicId() {
        return TopicId;
    }

    public void setTopicId(long topicId) {
        TopicId = topicId;
    }

    public int getIsFollow() {
        return IsFollow;
    }

    public void setIsFollow(int isFollow) {
        IsFollow = isFollow;
    }

    public int getJoinUserCount() {
        return JoinUserCount;
    }

    public void setJoinUserCount(int joinUserCount) {
        JoinUserCount = joinUserCount;
    }
}
