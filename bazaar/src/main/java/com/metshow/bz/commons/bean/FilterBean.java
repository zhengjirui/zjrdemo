package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2017-12-1.
 */

public class FilterBean extends POJO {

	private String CreateDate;//":"\/Date(928120800000+0800)\/",
	private long FilterId;//":9223372036854775807,
	private String Name;//":"字符串内容",
	private String Path;//":"字符串内容",
	private String Pic;//":"字符串内容",
	private String Remark;//":"字符串内容",
	private int State;//":32767

	public String getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}

	public long getFilterId() {
		return FilterId;
	}

	public void setFilterId(long filterId) {
		FilterId = filterId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPath() {
		return Path;
	}

	public void setPath(String path) {
		Path = path;
	}

	public String getPic() {
		return Pic;
	}

	public void setPic(String pic) {
		Pic = pic;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public int getState() {
		return State;
	}

	public void setState(int state) {
		State = state;
	}
}
