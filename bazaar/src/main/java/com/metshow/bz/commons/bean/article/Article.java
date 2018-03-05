package com.metshow.bz.commons.bean.article;


import com.kwan.base.bean.POJO;
import com.metshow.bz.commons.bean.Adforrec;
import com.metshow.bz.commons.bean.topic.Comment;

import java.util.List;

/**
 *
 * Created by Mr.Kwan on 2016-7-6.
 */
public class Article extends POJO {
    private long AdminId;// 30040,
    private long ArticleId;// 3207,
    private int ArticleType;// 1,
    private String Author;// "时尚芭莎",
    private String AuthorIco;// "http://bznewpicqiniu.createapp.cn/@/attached/30040/20160725/F17C67FA5DD66000C15633B2251BE2CF_md5_201607251635535346-400_400.jpg",
    private long ChannelId;// 3,
    private long ChannelParentId;// 0,
    private String Content;
    private String CreateDate;// "/Date(1459020258000)
    private String Flags;// "d",
    private String GroupPic;// null,
    private String Ico;// "http://bznewpicqiniu.createapp.cn/@/attached/1/20160326/D88EB1882D5FC30195924ACB73943771_md5_201603262030284051-640_512.jpg",
    private int IcoHeight;// 400,
    private int IcoWidth;// 300,
    private int IsOtherTag;// 0,
    private int IsRecommend;// 0,
    private int IsTop;// 0,
    private String PublishDate;// "/Date(1459020298000)
    private long RefId;// 1,
    private int RefType;// 0,
    private String Source;// null,
    private int State;// 3,
    private String Summary;// "为了让你能在“露肉”季大胆秀出好身材，芭姐今天就要给你一个从运动方式、高颜值装备到如何吃出好身材的全方位瘦身方案！",
    private String Tags;// "",
    private String Title;// "“露肉”季前迅速get√好身材，吃喝不误才是王道！",
    private String TopEndDate;// null,
    private String TopStartDate;// null,
    private long TopicId;// null,
    private String Url;// null,
    private long UserId;// 0,
    private String VideoUrl;// null,
    private String ViewCount;// 12401,
    private ArticleAct Activity;// {"ActivityId;//0,"EndDate;//null,"JoinCount;//null,"StartDate;//null,"ActivityState;//0,"IsJoin;//0},
    private List<ArticlePicture> ArticlePictures;// [],
    private String ChannelName;// null,
    private int CommentCount;// 0,
    private List<String> ContentPics;// [
    private int FavCount;// 8,
    private List<Comment> HotCommentList;// [],
    private int IsFav;// 1,
    private int IsJoin;// 0,
    private int IsUp;// 1,
    private int IsVip;// 1,
    private List<Comment> NewCommentList;// [],
    private int OrderNum;// 0,
    private long PublishNum;// null,
    private String Rank;// "时尚达人",
    private List<Article> RelatedArticles;// [
    private int UpCount;// 3,
    private List<ArticleVote> Vote;// []

	//=-----------------------------------------------

	private Adforrec adforrec;

	private List<Banner> banners;



	public List<Banner> getBanners() {
		return banners;
	}

	public void setBanners(List<Banner> banners) {
		this.banners = banners;
	}

	public Adforrec getAdforrecs() {
		return adforrec;
	}

	public void setAdforrecs(Adforrec adforrec) {
		this.adforrec = adforrec;
	}

	public long getAdminId() {
        return AdminId;
    }

    public void setAdminId(long adminId) {
        AdminId = adminId;
    }

    public long getArticleId() {
        return ArticleId;
    }

    public void setArticleId(long articleId) {
        ArticleId = articleId;
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

    public long getChannelId() {
        return ChannelId;
    }

    public void setChannelId(long channelId) {
        ChannelId = channelId;
    }

    public long getChannelParentId() {
        return ChannelParentId;
    }

    public void setChannelParentId(long channelParentId) {
        ChannelParentId = channelParentId;
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

    public String getFlags() {
        return Flags;
    }

    public void setFlags(String flags) {
        Flags = flags;
    }

    public String getGroupPic() {
        return GroupPic;
    }

    public void setGroupPic(String groupPic) {
        GroupPic = groupPic;
    }

    public String getIco() {
        return Ico;
    }

    public void setIco(String ico) {
        Ico = ico;
    }

    public int getIcoHeight() {
        return IcoHeight;
    }

    public void setIcoHeight(int icoHeight) {
        IcoHeight = icoHeight;
    }

    public int getIcoWidth() {
        return IcoWidth;
    }

    public void setIcoWidth(int icoWidth) {
        IcoWidth = icoWidth;
    }

    public int getIsOtherTag() {
        return IsOtherTag;
    }

    public void setIsOtherTag(int isOtherTag) {
        IsOtherTag = isOtherTag;
    }

    public int getIsRecommend() {
        return IsRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        IsRecommend = isRecommend;
    }

    public int getIsTop() {
        return IsTop;
    }

    public void setIsTop(int isTop) {
        IsTop = isTop;
    }

    public String getPublishDate() {
        return PublishDate;
    }

    public void setPublishDate(String publishDate) {
        PublishDate = publishDate;
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

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
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

    public String getTopEndDate() {
        return TopEndDate;
    }

    public void setTopEndDate(String topEndDate) {
        TopEndDate = topEndDate;
    }

    public String getTopStartDate() {
        return TopStartDate;
    }

    public void setTopStartDate(String topStartDate) {
        TopStartDate = topStartDate;
    }

    public long getTopicId() {
        return TopicId;
    }

    public void setTopicId(long topicId) {
        TopicId = topicId;
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

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getViewCount() {
        return ViewCount;
    }

    public void setViewCount(String viewCount) {
        ViewCount = viewCount;
    }

    public ArticleAct getActivity() {
        return Activity;
    }

    public void setActivity(ArticleAct activity) {
        Activity = activity;
    }

    public List<ArticlePicture> getArticlePictures() {
        return ArticlePictures;
    }

    public void setArticlePictures(List<ArticlePicture> articlePictures) {
        ArticlePictures = articlePictures;
    }

    public String getChannelName() {
        return ChannelName;
    }

    public void setChannelName(String channelName) {
        ChannelName = channelName;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int commentCount) {
        CommentCount = commentCount;
    }

    public List<String> getContentPics() {
        return ContentPics;
    }

    public void setContentPics(List<String> contentPics) {
        ContentPics = contentPics;
    }

    public int getFavCount() {
        return FavCount;
    }

    public void setFavCount(int favCount) {
        FavCount = favCount;
    }

    public List<Comment> getHotCommentList() {
        return HotCommentList;
    }

    public void setHotCommentList(List<Comment> hotCommentList) {
        HotCommentList = hotCommentList;
    }

    public int getIsFav() {
        return IsFav;
    }

    public void setIsFav(int isFav) {
        IsFav = isFav;
    }

    public int getIsJoin() {
        return IsJoin;
    }

    public void setIsJoin(int isJoin) {
        IsJoin = isJoin;
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

    public List<Comment> getNewCommentList() {
        return NewCommentList;
    }

    public void setNewCommentList(List<Comment> newCommentList) {
        NewCommentList = newCommentList;
    }

    public int getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(int orderNum) {
        OrderNum = orderNum;
    }

    public long getPublishNum() {
        return PublishNum;
    }

    public void setPublishNum(long publishNum) {
        PublishNum = publishNum;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public List<Article> getRelatedArticles() {
        return RelatedArticles;
    }

    public void setRelatedArticles(List<Article> relatedArticles) {
        RelatedArticles = relatedArticles;
    }

    public int getUpCount() {
        return UpCount;
    }

    public void setUpCount(int upCount) {
        UpCount = upCount;
    }

    public List<ArticleVote> getVote() {
        return Vote;
    }

    public void setVote(List<ArticleVote> vote) {
        Vote = vote;
    }
}
