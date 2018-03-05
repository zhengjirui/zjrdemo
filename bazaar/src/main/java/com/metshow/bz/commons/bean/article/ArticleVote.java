package com.metshow.bz.commons.bean.article;


import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-7-6.
 */
public class ArticleVote extends POJO {
   private long Id;//9223372036854775807,
   private int OrderNum;//2147483647,
   private String Title;// 字符串内容 ,
   private int VoteCount;//9223372036854775807,
   private long VoteId;//9223372036854775807,
   private String EndDate;// \/Date(928120800000+0800)\/ ,
   private int  IsJoin;//2147483647,
   private String StartDate;// \/Date(928120800000+0800)\/ ,
   private int VoteState;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public int getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(int orderNum) {
        OrderNum = orderNum;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getVoteCount() {
        return VoteCount;
    }

    public void setVoteCount(int voteCount) {
        VoteCount = voteCount;
    }

    public long getVoteId() {
        return VoteId;
    }

    public void setVoteId(long voteId) {
        VoteId = voteId;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public int getIsJoin() {
        return IsJoin;
    }

    public void setIsJoin(int isJoin) {
        IsJoin = isJoin;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public int getVoteState() {
        return VoteState;
    }

    public void setVoteState(int voteState) {
        VoteState = voteState;
    }
}
