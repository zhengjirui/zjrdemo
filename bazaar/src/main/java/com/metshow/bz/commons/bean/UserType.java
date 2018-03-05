package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2017-7-18.
 */

public class UserType extends POJO {

	String CreateDate;// "\/Date(1490266290000)\/",
	String Name;// "推荐",
	int OrderNum;// 3,
	long UserTypeId;// 1

	public String getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(String createDate) {
		CreateDate = createDate;
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

	public long getUserTypeId() {
		return UserTypeId;
	}

	public void setUserTypeId(long userTypeId) {
		UserTypeId = userTypeId;
	}
}
