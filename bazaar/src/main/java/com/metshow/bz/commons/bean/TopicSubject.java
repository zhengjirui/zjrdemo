package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-9-1.
 */
public class TopicSubject extends POJO {

    private String Avatar;// "http://bznewpicqiniu.createapp.cn/@/attached/0/20160803/926C978625E8925F958DADB005F422FA_md5_201608031726236075-750_750.gif",
    private String CreateDate;// "/Date(1470245193000)/",
    private String Description;// "只要搭得好，一支口红就能让你各种美！",
    private int IsHot;// 1,
    private int IsRecommend;// 1,
    private String Name;// "只要搭得好，一支口红就能让你各种美！",
    private int OrderNum;// 5,
    private String Pic;// "http://bznewpicqiniu.createapp.cn/@/attached/0/20160803/C27410C212AC79C588CF0987496E4B8E_md5_201608031724123106-1080_756.gif",
    private int State;// 1,
    private long TopicId;// 9
    private int IsFollow;//": 0,
    private int JoinUserCount;//": 21
    private long ArticleId;
    public long getArticleId() {
        return ArticleId;
    }

    public void setArticleId(long articleId) {
        ArticleId = articleId;
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

    public int getIsRecommend() {
        return IsRecommend;
    }

    public void setIsRecommend(int isRecommend) {
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
}
