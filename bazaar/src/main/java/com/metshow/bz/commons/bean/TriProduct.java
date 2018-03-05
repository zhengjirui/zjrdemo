package com.metshow.bz.commons.bean;


import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2017-7-6.
 */

public class TriProduct extends POJO {

	private String CreateDate;//"\/Date(928120800000+0800)\/",
	private String Name;//"字符串内容",
	private String Pic;//"字符串内容",
	private int State;//32767,
	private long TrialProductId;//9223372036854775807,
	private String Url;//"字符串内容"

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

	public long getTrialProductId() {
		return TrialProductId;
	}

	public void setTrialProductId(long trialProductId) {
		TrialProductId = trialProductId;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
}
