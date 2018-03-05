package com.metshow.bz.commons.bean.topic;


import com.kwan.base.bean.POJO;
import com.metshow.bz.commons.bean.RecommendUser;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.commons.bean.article.ArticlePicture;
import com.metshow.bz.commons.bean.article.Banner;

import java.util.List;

/**
 *
 * Created by Mr.Kwan on 2016-7-20.
 */
public class Topic extends POJO {

    private String ActionDate;// null,
    private long ActionId;// 0,
    private long ArticleId;// 6036,
    private List<ArticlePicture> ArticlePictures;
    private int ArticleType;// 7,
    private String Author;// "编辑小QQ",
    private String AuthorIco;// "http://7xlovw.com1.z0.glb.clouddn.com/cosmoweb/201603/1458637760687_584.jpg",
    private int CommentCount;// 2,
    private List<Comment> CommentList;// [],
    private String Content;// "Ouwei",
    private int FavCount;// 2,
    private String Ico;// "http://bznewpicqiniu.createapp.cn/@/attached/0/20160719/79944987F2595E4B0537ED17C2E7B7CD_md5_201607191611160593-960_637.jpg",
    private int IsFav;// 1,
    private int IsFollow;// 0,
    private int IsUp;// 0,
    private int IsVip;// 1,
    private String PublishDate;// "/Date(1468944677000)/",
    private String Rank;// "时尚达人",
    private long RefId;// 0,
    private int RefType;// 0,
    private int State;// 3,
    private String Tags;// "时装",
    private String Title;// null,
    private int UpCount;// 0,
    private List<User> UpUserList;// [],
    private String Url;// null,
    private long UserId;// 30041,
    private int UserType;// 0,
    private String VideoUrl;// null

	//--------------------------------------------------------------

	private List<RecommendUser> recommendUsers;

	private List<Banner> banners;

	public List<Banner> getBanners() {
		return banners;
	}

	public void setBanners(List<Banner> banners) {
		this.banners = banners;
	}

	public List<RecommendUser> getRecommendUsers() {
		return recommendUsers;
	}


	private List<Tag> topictags;

	public List<Tag> getTopictags() {
		return topictags;
	}

	public void setTopictags(List<Tag> topictags) {
		this.topictags = topictags;
	}

	public void setRecommendUsers(List<RecommendUser> recommendUsers) {
		this.recommendUsers = recommendUsers;
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

    public long getArticleId() {
        return ArticleId;
    }

    public void setArticleId(long articleId) {
        ArticleId = articleId;
    }

    public List<ArticlePicture> getArticlePictures() {
        return ArticlePictures;
    }

    public void setArticlePictures(List<ArticlePicture> articlePictures) {
        ArticlePictures = articlePictures;
    }

    public int getArticleType() {
        return ArticleType;
    }

    public void setArticleType(int articleType) {
        ArticleType = articleType;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getAuthorIco() {
        return AuthorIco;
    }

    public void setAuthorIco(String authorIco) {
        AuthorIco = authorIco;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int commentCount) {
        CommentCount = commentCount;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getFavCount() {
        return FavCount;
    }

    public void setFavCount(int favCount) {
        FavCount = favCount;
    }

    public String getIco() {
        return Ico;
    }

    public void setIco(String ico) {
        Ico = ico;
    }

    public int getIsFav() {
        return IsFav;
    }

    public void setIsFav(int isFav) {
        IsFav = isFav;
    }

    public int getIsFollow() {
        return IsFollow;
    }

    public void setIsFollow(int isFollow) {
        IsFollow = isFollow;
    }

    public int getIsUp() {
        return IsUp;
    }

    public void setIsUp(int isUp) {
        IsUp = isUp;
    }

    public int getIsVip() {
        return IsVip;
    }

    public void setIsVip(int isVip) {
        IsVip = isVip;
    }

    public String getPublishDate() {
        return PublishDate;
    }

    public void setPublishDate(String publishDate) {
        PublishDate = publishDate;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public long getRefId() {
        return RefId;
    }

    public void setRefId(long refId) {
        RefId = refId;
    }

    public int getRefType() {
        return RefType;
    }

    public void setRefType(int refType) {
        RefType = refType;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getUpCount() {
        return UpCount;
    }

    public void setUpCount(int upCount) {
        UpCount = upCount;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public List<User> getUpUserList() {
        return UpUserList;
    }

    public void setUpUserList(List<User> upUserList) {
        UpUserList = upUserList;
    }

    public List<Comment> getCommentList() {
        return CommentList;
    }

    public void setCommentList(List<Comment> commentList) {
        CommentList = commentList;
    }
}
