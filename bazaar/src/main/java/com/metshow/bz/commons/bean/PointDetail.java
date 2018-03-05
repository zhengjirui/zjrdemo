package com.metshow.bz.commons.bean;


import com.kwan.base.bean.POJO;

/**
 * Created by Kwan on 2018/2/7/007.
 */

public class PointDetail extends POJO {
	
	private String CreateDate;//"\/Date(928120800000+0800)\/",
	private String EngName;//"字符串内容",
	private long Id;//9223372036854775807,
	private String Name;//"字符串内容",
	private int Points;//2147483647,
	private int Type;//2147483647,
	private long UserId;//9223372036854775807

	public String getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}

	public String getEngName() {
		return EngName;
	}

	public void setEngName(String engName) {
		EngName = engName;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getPoints() {
		return Points;
	}

	public void setPoints(int points) {
		Points = points;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public long getUserId() {
		return UserId;
	}

	public void setUserId(long userId) {
		UserId = userId;
	}
}
