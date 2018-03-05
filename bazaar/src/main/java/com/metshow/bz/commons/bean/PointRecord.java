package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Kwan on 2018/2/8/008.
 */

public class PointRecord extends POJO {

	/*
	ExpressName：快递名称
    ExpressNum：快递号
    ExpressType：快递类型
    State：0为待发货，1为已发货  -1为拒绝
    OrderNum:订单号，用于显示及后台查询订单状态
    Type: 1为实物 2为电子券
	 */
	
	private String Address;//;//"字符串内容",
	private String CouponNum;//"字符串内容",
	private String CouponPassword;//"字符串内容",
	private String CreateDate;//"\/Date(928120800000+0800)\/",
	private long ElecCouponsId;//9223372036854775807,
	private String ExpressName;//"字符串内容",
	private String ExpressNum;//"字符串内容",
	private int ExpressType;//2147483647,
	private String OrderNum;//"字符串内容",
	private String PhoneNum;//"字符串内容",
	private long PointShopId;//9223372036854775807,
	private long PointShopUserLogId;//9223372036854775807,
	private int Points;//9223372036854775807,
	private int State;//32767,
	private String TrueName;//"字符串内容",
	private int Type;//32767,
	private String UserId;//9223372036854775807
	private String CouponEndDate ;//          券有效期
	private String PointShopName    ;//       商品名称
	private String PointShopIco	       ;//     商品图片

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCouponNum() {
		return CouponNum;
	}

	public void setCouponNum(String couponNum) {
		CouponNum = couponNum;
	}

	public String getCouponPassword() {
		return CouponPassword;
	}

	public void setCouponPassword(String couponPassword) {
		CouponPassword = couponPassword;
	}

	public String getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}

	public long getElecCouponsId() {
		return ElecCouponsId;
	}

	public void setElecCouponsId(long elecCouponsId) {
		ElecCouponsId = elecCouponsId;
	}

	public String getExpressName() {
		return ExpressName;
	}

	public void setExpressName(String expressName) {
		ExpressName = expressName;
	}

	public String getExpressNum() {
		return ExpressNum;
	}

	public void setExpressNum(String expressNum) {
		ExpressNum = expressNum;
	}

	public int getExpressType() {
		return ExpressType;
	}

	public void setExpressType(int expressType) {
		ExpressType = expressType;
	}

	public String getOrderNum() {
		return OrderNum;
	}

	public void setOrderNum(String orderNum) {
		OrderNum = orderNum;
	}

	public String getPhoneNum() {
		return PhoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		PhoneNum = phoneNum;
	}

	public long getPointShopId() {
		return PointShopId;
	}

	public void setPointShopId(long pointShopId) {
		PointShopId = pointShopId;
	}

	public long getPointShopUserLogId() {
		return PointShopUserLogId;
	}

	public void setPointShopUserLogId(long pointShopUserLogId) {
		PointShopUserLogId = pointShopUserLogId;
	}

	public int getPoints() {
		return Points;
	}

	public void setPoints(int points) {
		Points = points;
	}

	public int getState() {
		return State;
	}

	public void setState(int state) {
		State = state;
	}

	public String getTrueName() {
		return TrueName;
	}

	public void setTrueName(String trueName) {
		TrueName = trueName;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getCouponEndDate() {
		return CouponEndDate;
	}

	public void setCouponEndDate(String couponEndDate) {
		CouponEndDate = couponEndDate;
	}

	public String getPointShopName() {
		return PointShopName;
	}

	public void setPointShopName(String pointShopName) {
		PointShopName = pointShopName;
	}

	public String getPointShopIco() {
		return PointShopIco;
	}

	public void setPointShopIco(String pointShopIco) {
		PointShopIco = pointShopIco;
	}
}
