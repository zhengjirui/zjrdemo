package com.metshow.bz.commons.bean.article;


import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-7-6.
 */
public class ArticleAct extends POJO {

    private long ActivityId;//9223372036854775807,
    private String EndDate;// \/Date(928120800000+0800)\/ ,
    private int JoinCount;//9223372036854775807,
    private String StartDate;// \/Date(928120800000+0800)\/ ,
    private int ActivityState;//2147483647,
    private int IsJoin;//2147483647

    public long getActivityId() {
        return ActivityId;
    }

    public void setActivityId(long activityId) {
        ActivityId = activityId;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public int getJoinCount() {
        return JoinCount;
    }

    public void setJoinCount(int joinCount) {
        JoinCount = joinCount;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public int getActivityState() {
        return ActivityState;
    }

    public void setActivityState(int activityState) {
        ActivityState = activityState;
    }

    public int getIsJoin() {
        return IsJoin;
    }

    public void setIsJoin(int isJoin) {
        IsJoin = isJoin;
    }
}
