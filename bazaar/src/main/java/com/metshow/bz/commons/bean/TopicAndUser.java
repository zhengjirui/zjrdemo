package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;
import com.metshow.bz.commons.bean.topic.Topic;

import java.util.List;

/**
 * Created by Mr.Kwan on 2016-8-30.
 */
public class TopicAndUser extends POJO {

    private List<Topic> ArticleList;

    private List<User> UserList;

    public List<User> getUserList() {
        return UserList;
    }

    public void setUserList(List<User> userList) {
        UserList = userList;
    }

    public List<Topic> getArticleList() {
        return ArticleList;
    }

    public void setArticleList(List<Topic> articleList) {
        ArticleList = articleList;
    }
}
