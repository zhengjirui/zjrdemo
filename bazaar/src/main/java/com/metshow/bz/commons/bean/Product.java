package com.metshow.bz.commons.bean;


import com.kwan.base.bean.POJO;
import com.metshow.bz.commons.bean.article.Article;

import java.util.List;

/**
 *
 * Created by Mr.Kwan on 2016-8-15.
 */
public class Product extends POJO {

    private long AdminId;// 30040,
    private long BrandId;// 39,
    private long CategoryId;// 2,
    private String Color;// "#000000,#666666,#330066",
    private String Content;// null,
    private String CreateDate;// "/Date(1457459630000)/",
    private String EditorNote;// "短款修身的造型，帅气迷人，立领、铆钉扣装饰，再配上帅气的金属拉链点缀，无不体现着机车夹克的经典，细腻柔软的羊皮面料做了硬朗的做旧植鞣工艺，更酷的街头风格，做旧后的羊皮同样表现莹润的光泽质感，凸显皮革独有的张扬气场。",
    private String Flags;// null,
    private String Keywords;// "BALENCIAGA， 皮革，机车夹克",
    private String Link;// "https://www.net-a-porter.com/cn/zh/product/618809/Balenciaga/-",
    private String ModifyDate;// null,
    private String Picture;// "http://bznewpic.qiniudn.com/@/attached/1/20160308/A485FB9E9948EFA38473E458635F468A_md5_201603081753485291-510_600.jpg",
    private long ProductId;// 52,
    private String ProductName;// "机车夹克",
    private String PublishDate;// "/Date(1457459474000)/",
    private String SKU;// null,
    private int State;// 1,
    private String SubTitle;// "2016春",
    private String UnitPrice;// 18000,
    private String UnitSize;// "皮革机车夹克",
    private String ActionDate;// "/Date(1459161887000)/",
    private long ActionId;// 355,
    private int FavCount;// 0
    private int IsComment;//": 0,
    private int IsFav;//": 0,
    private String BrandName;//": "卡地亚",
    private int CommentCount;//": 1,

    private List<ProductPicture> ProductPictures;//":
    private List<Article> RelativeArticle;//":[],
    private List<Product> RelativeList;//":[]


    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int commentCount) {
        CommentCount = commentCount;
    }

    public long getAdminId() {
        return AdminId;
    }

    public void setAdminId(long adminId) {
        AdminId = adminId;
    }

    public long getBrandId() {
        return BrandId;
    }

    public void setBrandId(long brandId) {
        BrandId = brandId;
    }

    public long getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(long categoryId) {
        CategoryId = categoryId;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

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

    public String getEditorNote() {
        return EditorNote;
    }

    public void setEditorNote(String editorNote) {
        EditorNote = editorNote;
    }

    public String getFlags() {
        return Flags;
    }

    public void setFlags(String flags) {
        Flags = flags;
    }

    public String getKeywords() {
        return Keywords;
    }

    public void setKeywords(String keywords) {
        Keywords = keywords;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getModifyDate() {
        return ModifyDate;
    }

    public void setModifyDate(String modifyDate) {
        ModifyDate = modifyDate;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public long getProductId() {
        return ProductId;
    }

    public void setProductId(long productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getPublishDate() {
        return PublishDate;
    }

    public void setPublishDate(String publishDate) {
        PublishDate = publishDate;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getSubTitle() {
        return SubTitle;
    }

    public void setSubTitle(String subTitle) {
        SubTitle = subTitle;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getUnitSize() {
        return UnitSize;
    }

    public void setUnitSize(String unitSize) {
        UnitSize = unitSize;
    }

    public String getActionDate() {
        return ActionDate;
    }

    public void setActionDate(String actionDate) {
        ActionDate = actionDate;
    }

    public long getActionId() {
        return ActionId;
    }

    public void setActionId(long actionId) {
        ActionId = actionId;
    }

    public int getFavCount() {
        return FavCount;
    }

    public void setFavCount(int favCount) {
        FavCount = favCount;
    }

    public int getIsComment() {
        return IsComment;
    }

    public void setIsComment(int isComment) {
        IsComment = isComment;
    }

    public int getIsFav() {
        return IsFav;
    }

    public void setIsFav(int isFav) {
        IsFav = isFav;
    }

    public List<ProductPicture> getProductPictures() {
        return ProductPictures;
    }

    public void setProductPictures(List<ProductPicture> productPictures) {
        ProductPictures = productPictures;
    }

    public List<Article> getRelativeArticle() {
        return RelativeArticle;
    }

    public void setRelativeArticle(List<Article> relativeArticle) {
        RelativeArticle = relativeArticle;
    }

    public List<Product> getRelativeList() {
        return RelativeList;
    }

    public void setRelativeList(List<Product> relativeList) {
        RelativeList = relativeList;
    }
}
