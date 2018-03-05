package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-9-26.
 */

public class Brand extends POJO {

    private String BrandDes;// " 年轻的天才设计师Anya Hindmarch 在伦敦东区推出了她的第一间专卖店。品牌多款手提包的设计都是从设计师的时尚偶像撒切尔夫人身上汲取的灵感。",
    private String BrandId;// 153,
    private String BrandLogo;// "http://bznewpicqiniu.createapp.cn/@/attached/0/20160417/19837A1FE9CC75DB9C504C5D4B6D10A2_md5_201604170050214314-1000_1334.jpg",
    private String BrandName;// " Anya Hindmarch",
    private String BrandNamePinYin;// " Anya Hindmarch",
    private String IsRecommend;// null,
    private String SupplierId;// null,
    private String _IsNew;// 1,
    private String ProductCount;// 1

    public String getBrandDes() {
        return BrandDes;
    }

    public void setBrandDes(String brandDes) {
        BrandDes = brandDes;
    }

    public String getBrandId() {
        return BrandId;
    }

    public void setBrandId(String brandId) {
        BrandId = brandId;
    }

    public String getBrandLogo() {
        return BrandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        BrandLogo = brandLogo;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getBrandNamePinYin() {
        return BrandNamePinYin;
    }

    public void setBrandNamePinYin(String brandNamePinYin) {
        BrandNamePinYin = brandNamePinYin;
    }

    public String getIsRecommend() {
        return IsRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        IsRecommend = isRecommend;
    }

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public String get_IsNew() {
        return _IsNew;
    }

    public void set_IsNew(String _IsNew) {
        this._IsNew = _IsNew;
    }

    public String getProductCount() {
        return ProductCount;
    }

    public void setProductCount(String productCount) {
        ProductCount = productCount;
    }
}
