package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;
import com.metshow.bz.commons.bean.article.Article;

import java.util.List;

/**
 * Created by Mr.Kwan on 2016-8-23.
 */
public class RecommendUser extends POJO {

	private long AdminId;// 0,
	private String Avatar;// "http://bznewpicqiniu.createapp.cn/@/attached/64859/20160411/5F9795F12176EEF46139F4B50636C485_md5_201604111256401053-400_400.jpg",
	private int BeFollowedCount;// 207,
	private String Description;// "这个人很懒...什么都没有写 (我不懒)",
	private int FollowCount;// 0,
	private int IsFollow;// 0,
	private int IsRefuseMessage;// 0,
	private int IsVip;// 1,
	private String NickName;// "曹GAYE",
	private String Rank;// null,
	private String Tags;// null,
	private long UserId;// 64859
	private List<Article> ArticleList;
	private int FollowedBadgeCount;//":9223372036854775807,
	private int MessageBadgeCount;//":9223372036854775807,
	private int ReplyBadgeCount;//":9223372036854775807,


	public List<Article> getArticleList() {
		return ArticleList;
	}

	public void setArticleList(List<Article> articleList) {
		ArticleList = articleList;
	}

	public int getFollowedBadgeCount() {
		return FollowedBadgeCount;
	}

	public void setFollowedBadgeCount(int followedBadgeCount) {
		FollowedBadgeCount = followedBadgeCount;
	}

	public int getMessageBadgeCount() {
		return MessageBadgeCount;
	}

	public void setMessageBadgeCount(int messageBadgeCount) {
		MessageBadgeCount = messageBadgeCount;
	}

	public int getReplyBadgeCount() {
		return ReplyBadgeCount;
	}

	public void setReplyBadgeCount(int replyBadgeCount) {
		ReplyBadgeCount = replyBadgeCount;
	}

	public long getAdminId() {
		return AdminId;
	}

	public void setAdminId(long adminId) {
		AdminId = adminId;
	}

	public String getAvatar() {
		return Avatar;
	}

	public void setAvatar(String avatar) {
		Avatar = avatar;
	}

	public int getBeFollowedCount() {
		return BeFollowedCount;
	}

	public void setBeFollowedCount(int beFollowedCount) {
		BeFollowedCount = beFollowedCount;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getFollowCount() {
		return FollowCount;
	}

	public void setFollowCount(int followCount) {
		FollowCount = followCount;
	}

	public int getIsFollow() {
		return IsFollow;
	}

	public void setIsFollow(int isFollow) {
		IsFollow = isFollow;
	}

	public int getIsRefuseMessage() {
		return IsRefuseMessage;
	}

	public void setIsRefuseMessage(int isRefuseMessage) {
		IsRefuseMessage = isRefuseMessage;
	}

	public int getIsVip() {
		return IsVip;
	}

	public void setIsVip(int isVip) {
		IsVip = isVip;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public String getRank() {
		return Rank;
	}

	public void setRank(String rank) {
		Rank = rank;
	}

	public String getTags() {
		return Tags;
	}

	public void setTags(String tags) {
		Tags = tags;
	}

	public long getUserId() {
		return UserId;
	}

	public void setUserId(long userId) {
		UserId = userId;
	}
}

