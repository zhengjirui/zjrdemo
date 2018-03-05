package com.metshow.bz.commons.bean.article;


/**
 *
 * Created by Mr.Kwan on 2016-8-15.
 */
public class FavArticle extends Article {

	private String ActName;//"like",
	private int ActType;//1,
	private long ActUserId;//30041,

	// private String Ico;//"http://bznewpicqiniu.createapp.cn/@/attached/1/20160326/D88EB1882D5FC30195924ACB73943771_md5_201603262030284051-640_512.jpg",
	private long Id;//717,
	private String Ip;//"118.26.181.33",
	private String Remark;//"“露肉”季前迅速get√ 好身材，吃喝不误才是王道！",
	private String Tag;//"",

    public String getActName() {
        return ActName;
    }

    public void setActName(String actName) {
        ActName = actName;
    }

    public int getActType() {
        return ActType;
    }

    public void setActType(int actType) {
        ActType = actType;
    }

    public long getActUserId() {
        return ActUserId;
    }

    public void setActUserId(long actUserId) {
        ActUserId = actUserId;
    }

//    public String getCreateDate() {
//        return CreateDate;
//    }
//
//    public void setCreateDate(String createDate) {
//        CreateDate = createDate;
//    }

//    public String getIco() {
//        return Ico;
//    }
//
//    public void setIco(String ico) {
//        Ico = ico;
//    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

//    public long getRefId() {
//        return RefId;
//    }
//
//    public void setRefId(long refId) {
//        RefId = refId;
//    }
//
//    public int getRefType() {
//        return RefType;
//    }
//
//    public void setRefType(int refType) {
//        RefType = refType;
//    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    @Override
    public String getTag() {
        return Tag;
    }

    @Override
    public void setTag(String tag) {
        Tag = tag;
    }

//    public int getType() {
//        return Type;
//    }
//
//    public void setType(int type) {
//        Type = type;
//    }
//
//    public String getSummary() {
//        return Summary;
//    }
//
//    public void setSummary(String summary) {
//        Summary = summary;
//    }

   // public String getUrl() {
     //   return retrofit2.http.Url;
   // }

   // public void setUrl(String url) {
     //   retrofit2.http.Url = url;
   // }
}
