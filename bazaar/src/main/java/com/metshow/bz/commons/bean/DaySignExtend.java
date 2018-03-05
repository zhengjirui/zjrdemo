package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2017-7-11.
 */

public class DaySignExtend extends POJO {

	private long DaySignExtendId;//9223372036854775807,
	private long DaySignId;//9223372036854775807,
	private long ExtendId;//9223372036854775807,
	private int ExtendType;//32767,
	private String Title;//"

	public long getDaySignExtendId() {
		return DaySignExtendId;
	}

	public long getDaySignId() {
		return DaySignId;
	}

	public long getExtendId() {
		return ExtendId;
	}

	public int getExtendType() {
		return ExtendType;
	}

	public String getTitle() {
		return Title;
	}
}
