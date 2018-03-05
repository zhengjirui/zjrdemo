package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-7-7.
 */

public class Special extends POJO {
	
	private String Content;// "",
	private String CreateDate;// "/Date(1499126384000)/",
	private String Ico;// "http://bznewpicqiniu.createapp.cn/@/attached/0/20170707/B9BD3875D0CC3D9A20649C9FC6FC48FF_md5_201707070009152807-1076_850.gif",
	private long ProductSpecialId;// 24,
	private String Remark;// "解放双手的不止是双肩包，还有这造型感十足的腰包。",
	private int State;// 1,
	private String Title;// "一周 IT BAG|宋茜把TA挂腰上，肯小妹把TA挂胸前，这个“卖菜包“魔力真不小~",
	private long UserId;// 30040,
	private int ViewCount;// 296

	private List<Product> RelativeProduct;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}

	public String getIco() {
		return Ico;
	}

	public void setIco(String ico) {
		Ico = ico;
	}

	public long getProductSpecialId() {
		return ProductSpecialId;
	}

	public void setProductSpecialId(long productSpecialId) {
		ProductSpecialId = productSpecialId;
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

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public long getUserId() {
		return UserId;
	}

	public void setUserId(long userId) {
		UserId = userId;
	}

	public int getViewCount() {
		return ViewCount;
	}

	public void setViewCount(int viewCount) {
		ViewCount = viewCount;
	}

	public List<Product> getRelativeProduct() {
		return RelativeProduct;
	}

	public void setRelativeProduct(List<Product> relativeProduct) {
		RelativeProduct = relativeProduct;
	}
}
