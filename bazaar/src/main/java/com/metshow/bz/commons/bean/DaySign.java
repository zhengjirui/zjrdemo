package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

import java.util.List;

/**
 * Created by Mr.Kwan on 2016-9-21.
 */
public class DaySign extends POJO {

    private String AdminId;//;// 30040,
    private String DaySignId;// 116,
    private String IsEveryShow;// 0,
    private String Link;// null,
    private String Pic;// "http://bznewpicqiniu.createapp.cn/@/attached/0/20160808/E006F90C0FD37E9B77549C330D3E7209_md5_201608082256017007-720_1314.jpg",
    private String ProductId;// 0,
    private String PublishDate;// "/Date(1472169600000)/",
    private String RefId;// 0,
    private String RefType;// 0,
    private String Remark;// "舒淇",
    private String VoteType;// 0,
    private String IsVote;// 0,
    private String VoteList;// null
	private List<DaySignExtend> ExtendList;

	public List<DaySignExtend> getExtendList() {
		return ExtendList;
	}

	public void setExtendList(List<DaySignExtend> extendList) {
		ExtendList = extendList;
	}

	public String getAdminId() {
        return AdminId;
    }

    public void setAdminId(String adminId) {
        AdminId = adminId;
    }

    public String getDaySignId() {
        return DaySignId;
    }

    public void setDaySignId(String daySignId) {
        DaySignId = daySignId;
    }

    public String getIsEveryShow() {
        return IsEveryShow;
    }

    public void setIsEveryShow(String isEveryShow) {
        IsEveryShow = isEveryShow;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getPublishDate() {
        return PublishDate;
    }

    public void setPublishDate(String publishDate) {
        PublishDate = publishDate;
    }

    public String getRefId() {
        return RefId;
    }

    public void setRefId(String refId) {
        RefId = refId;
    }

    public String getRefType() {
        return RefType;
    }

    public void setRefType(String refType) {
        RefType = refType;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getVoteType() {
        return VoteType;
    }

    public void setVoteType(String voteType) {
        VoteType = voteType;
    }

    public String getIsVote() {
        return IsVote;
    }

    public void setIsVote(String isVote) {
        IsVote = isVote;
    }

    public String getVoteList() {
        return VoteList;
    }

    public void setVoteList(String voteList) {
        VoteList = voteList;
    }
}
