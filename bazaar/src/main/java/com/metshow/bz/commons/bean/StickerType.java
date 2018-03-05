package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2017-11-29.
 */

public class StickerType extends POJO {

	private String CreateDate;//":"\/Date(928120800000+0800)\/",
	private String Name;//":"字符串内容",
	private long StickerTypeId;//":9223372036854775807

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

    public long getStickerTypeId() {
        return StickerTypeId;
    }

    public void setStickerTypeId(long stickerTypeId) {
        StickerTypeId = stickerTypeId;
    }

    @Override
    public String toString() {
        return "StickerType{" +
                "CreateDate='" + CreateDate + '\'' +
                ", Name='" + Name + '\'' +
                ", NameStickerTypeId=" + StickerTypeId +
                '}';
    }
}
