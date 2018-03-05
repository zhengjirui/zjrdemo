package com.metshow.bz.datamodule;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.kwan.base.api.CountingRequestBody;
import com.kwan.base.api.ServerByteSubscriber;
import com.kwan.base.api.ServerSubscriber;
import com.kwan.base.api.ServerUploadSubscriber;
import com.kwan.base.bean.POJO;
import com.kwan.base.bean.ServerMsg;
import com.kwan.base.model.BaseModel;
import com.kwan.base.model.IBasePresenter;
import com.metshow.bz.commons.bean.Adforrec;
import com.metshow.bz.commons.bean.AdminUser;
import com.metshow.bz.commons.bean.Brand;
import com.metshow.bz.commons.bean.BzActivity;
import com.metshow.bz.commons.bean.Category;
import com.metshow.bz.commons.bean.Channel;
import com.metshow.bz.commons.bean.DaySign;
import com.metshow.bz.commons.bean.FilterBean;
import com.metshow.bz.commons.bean.FollowItem;
import com.metshow.bz.commons.bean.ImgUploadResult;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.PointDetail;
import com.metshow.bz.commons.bean.PointProduct;
import com.metshow.bz.commons.bean.PointRecord;
import com.metshow.bz.commons.bean.PointResult;
import com.metshow.bz.commons.bean.Product;
import com.metshow.bz.commons.bean.RecommendUser;
import com.metshow.bz.commons.bean.ServerConfig;
import com.metshow.bz.commons.bean.ServerConfigNew;
import com.metshow.bz.commons.bean.Special;
import com.metshow.bz.commons.bean.Splash;
import com.metshow.bz.commons.bean.StickerType;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.commons.bean.TopicSubject;
import com.metshow.bz.commons.bean.TriProduct;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.commons.bean.UserType;
import com.metshow.bz.commons.bean.VideoUploadResult;
import com.metshow.bz.commons.bean.article.Article;
import com.metshow.bz.commons.bean.article.Banner;
import com.metshow.bz.commons.bean.article.FavArticle;
import com.metshow.bz.commons.bean.message.MsgChatDetail;
import com.metshow.bz.commons.bean.message.MsgChatItem;
import com.metshow.bz.commons.bean.topic.Comment;
import com.metshow.bz.commons.bean.topic.FindTag;
import com.metshow.bz.commons.bean.topic.StickerBean;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.net.api.BazzarAPIUtil;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.metshow.bz.net.api.ServerAPI.TOPICFOLLOW_DELETE_VOCATIONAL_ID;

/**
 * 此类是获取数据的类 包括从服务器 和 数据库
 * Created by Mr.Kwan on 2017-4-11.
 */

public class BZModule extends BaseModel {

	private BazzarAPIUtil mApiUtil;

	public BZModule(IBasePresenter iBasePresenter) {
		super(iBasePresenter);
		mApiUtil = new BazzarAPIUtil();
	}

	/**
	 * 获取服务器配置地址
	 */
	public void getServerConfigAddress() {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ServerConfigNew> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.SERVERCONFIG_NEW_VOCATIONAL_ID;

		Observable<String> observable = mApiUtil.getServerConfigNew();
		java.lang.reflect.Type type = new TypeToken<ServerMsg<ServerConfigNew>>() {
		}.getType();

		requests.put(ServerAPI.SERVERCONFIG_NEW_VOCATIONAL_ID, observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ServerConfigNew>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber));

	}

	/**
	 * 获取启动信息
	 *
	 * @param date 日期
	 */
	public void getSplashList(String date) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<Splash> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.SPLASH_LIST_VOCATIONAL_ID;

		//参数
		HashMap<String, Object> args = new HashMap<>();
		args.put("date", date);
		String arg = getJsonStrArg(args);

		Observable<String> observable = mApiUtil.getSplashList(arg);
		java.lang.reflect.Type type = new TypeToken<ServerMsg<Splash>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<Splash>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	/**
	 * in分类列表
	 */

	public void getInChannelList() {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<List<Channel>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.CHANNEL_LIST_VOCATIONAL_ID;

		Observable<String> observable = mApiUtil.getChannelList();
		java.lang.reflect.Type type = new TypeToken<ServerMsg<List<Channel>>>() {
		}.getType();

		requests.put(ServerAPI.CHANNEL_LIST_VOCATIONAL_ID, observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<List<Channel>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber));

	}

	public void getInRecommend() {

		if (checkNetWorkAvailable())
			return;
		ServerSubscriber<Adforrec> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ADFORREC_LIST_VOCATIONAL_ID;
		Observable<String> observable = mApiUtil.getAdforrecList();
		java.lang.reflect.Type type = new TypeToken<ServerMsg<Adforrec>>() {
		}.getType();
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<Adforrec>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void getRecList(int pageindex, int pagesize) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<Article>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ARTICLE_RECLIST_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pageindex", pageindex);
		args.put("pagesize", pagesize);
		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.getArticleRecList(arg);
		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Article>>>() {
		}.getType();

		requests.put(ServerAPI.ARTICLE_RECLIST_VOCATIONAL_ID, observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Article>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber));

	}

	public void getArticleList(long channelid, int pagesize, String maxdate) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<Article>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ARTICLE_LIST_VOCATIONAL_ID;

		//参数
		HashMap<String, Object> args = new HashMap<>();
		args.put("channelid", channelid);
		args.put("pagesize", pagesize);
		args.put("maxdate", maxdate);
		String arg = getJsonStrArg(args);

		Observable<String> observable = mApiUtil.getArticleList(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Article>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Article>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void getArticleBanner(long channelid) {

		ServerSubscriber<List<Banner>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.RECOMMEND_LIST_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("channelType", channelid);
		String arg = getJsonStrArg(args);

		Observable<String> observable = mApiUtil.getRecommendList(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<List<Banner>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<List<Banner>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void addFollow(long FollowUserId, int position) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.FOLLOW_ADD_VOCATIONAL_ID;
		subscriber.exData = new HashMap<>();
		subscriber.exData.put("position", position);

		HashMap<String, Object> args = new HashMap<>();
		HashMap<String, Object> follow = new HashMap<>();
		args.put("FollowUserId", FollowUserId);
		follow.put("follow", args);
		String arg = getJsonStrArg(follow);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<String>>() {
		}.getType();

		Observable<String> observable = mApiUtil.addFollow(arg);
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void deleteFollow(long followUserId, int position) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.FOLLOW_DELETE_VOCATIONAL_ID;
		subscriber.exData = new HashMap<>();
		subscriber.exData.put("position", position);

		HashMap<String, Object> args = new HashMap<>();
		args.put("followUserId", followUserId);
		String arg = getJsonStrArg(args);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<String>>() {
		}.getType();

		Observable<String> observable = mApiUtil.deleteFollow(arg);
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	//粉丝接口
	public void getBeFollowList(int pageindex, int pagesize) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<FollowItem>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.FOLLOW_FOLLOWEDLIST_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pagesize", pagesize);
		args.put("pageindex", pageindex);
		String arg = getJsonStrArg(args);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<FollowItem>>>() {
		}.getType();

		Observable<String> observable = mApiUtil.getFollowedList(arg);
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<FollowItem>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getFollowList(int pageindex, int pagesize) {
		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<FollowItem>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.FOLLOW_LIST_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pagesize", pagesize);
		args.put("pageindex", pageindex);
		String arg = getJsonStrArg(args);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<FollowItem>>>() {
		}.getType();

		Observable<String> observable = mApiUtil.getFollowList(arg);
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<FollowItem>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	/**
	 * 获取 发现 标签 列表
	 */
	public void getFindTags() {
		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<List<FindTag>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.TAG_FINDLIST_VOCATIONAL_ID;


		Observable<String> observable = mApiUtil.getTagFindList();
		java.lang.reflect.Type type = new TypeToken<ServerMsg<List<FindTag>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<List<FindTag>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getFindTopics(int pageindex, int pagesize, String tagname) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<Topic>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.COMMUNITY_LISTBYTAG_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pageindex", pageindex);
		args.put("pagesize", pagesize);
		args.put("tagname", tagname);

		Observable<String> observable = mApiUtil.getCommunityByTag(getJsonStrArg(args));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Topic>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Topic>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	//取消收藏
	public void deleteFav(long refid, int position) {

		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ACTION_DELETEFAV_VOCATIONAL_ID;

		HashMap<String, Object> exdata = new HashMap<>();
		exdata.put("position", position);
		subscriber.exData = exdata;

		HashMap<String, Object> args = new HashMap<>();
		args.put("type", 1);
		args.put("refid", refid);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.deleteFav(arg);

		java.lang.reflect.Type ttype = new TypeToken<ServerMsg<String>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(ttype))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	//取消收藏 finditemfragment 中应用
	public void deleteFav(int type, long refid, int position, int fragmentId) {

		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ACTION_DELETEFAV_VOCATIONAL_ID;

		HashMap<String, Object> exdata = new HashMap<>();
		exdata.put("type", type);
		exdata.put("position", position);
		exdata.put("fragmentId", fragmentId);

		subscriber.exData = exdata;

		HashMap<String, Object> args = new HashMap<>();
		args.put("type", type);
		args.put("refid", refid);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.deleteFav(arg);

		java.lang.reflect.Type ttype = new TypeToken<ServerMsg<String>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(ttype))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	//取消收藏
	public void deleteFav(int type, long refid, int position) {

		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ACTION_DELETEFAV_VOCATIONAL_ID;

		HashMap<String, Object> exdata = new HashMap<>();
		exdata.put("type", type);
		exdata.put("position", position);

		subscriber.exData = exdata;

		HashMap<String, Object> args = new HashMap<>();
		args.put("type", type);
		args.put("refid", refid);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.deleteFav(arg);

		java.lang.reflect.Type ttype = new TypeToken<ServerMsg<String>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(ttype))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	//点赞 和 收藏
	public void addAcition(int acttype, long refid, int position) {

		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ACTION_ADD_VOCATIONAL_ID;

		HashMap<String, Object> exdata = new HashMap<>();
		exdata.put("acttype", acttype);
		exdata.put("position", position);
		subscriber.exData = exdata;

		HashMap<String, Object> args = new HashMap<>();
		args.put("type", 3);
		args.put("acttype", acttype);
		args.put("refid", refid);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.addAction(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<String>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	//点赞 和 收藏
	public void addAction(int acttype, long refid, int position) {

		ServerSubscriber<Integer> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ACTION_ADD_VOCATIONAL_ID;

		HashMap<String, Object> exdata = new HashMap<>();
		exdata.put("acttype", acttype);
		exdata.put("position", position);
		subscriber.exData = exdata;

		HashMap<String, Object> args = new HashMap<>();
		args.put("type", 2);
		args.put("acttype", acttype);
		args.put("refid", refid);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.addAction(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<Integer>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<Integer>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void reportComment(long CommentId) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.COMMENTACTION_POLICE_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		HashMap<String, Object> comment = new HashMap<>();
		args.put("CommentId", CommentId);
		comment.put("action", args);

		String arg = getJsonStrArg(comment);

		java.lang.reflect.Type typex = new TypeToken<ServerMsg<String>>() {
		}.getType();

		Observable<String> observable = mApiUtil.policeCommentAction(arg);
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(typex))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void addComment(String content, String image, long refId, int type, long parentId) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.COMMENT_ADD_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		HashMap<String, Object> comment = new HashMap<>();
		args.put("Content", content);
		args.put("Image", image);
		args.put("RefId", refId);
		args.put("Type", type);
		args.put("ParentId", parentId);
		comment.put("comment", args);

		String arg = getJsonStrArg(comment);

		java.lang.reflect.Type typex = new TypeToken<ServerMsg<String>>() {
		}.getType();

		Observable<String> observable = mApiUtil.addComment(arg);
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(typex))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void uploadImag(final String path, final String content, final long refId, final int type, final long parentId) {
		if (checkNetWorkAvailable())
			return;

		ServerUploadSubscriber<ServerMsg<String>> subscriber = new ServerUploadSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.PIC_UPLOADIMAGE_VOCATIONAL_ID;

		Observable<String> observable = mApiUtil.upLoadImage(path, new CountingRequestBody.Listener() {
			@Override
			public void onRequestProgress(long bytesWritten, long contentLength) {

			}
		});
		java.lang.reflect.Type typex = new TypeToken<ImgUploadResult>() {
		}.getType();
		java.lang.reflect.Type typex2 = new TypeToken<ServerMsg<String>>() {
		}.getType();


		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ImgUploadResult>().getTransFormer(typex))
				.subscribeOn(Schedulers.io())
				.flatMap(new Func1<ImgUploadResult, Observable<String>>() {
					@Override
					public Observable<String> call(ImgUploadResult imgUploadResult) {

						HashMap<String, Object> args = new HashMap<>();
						HashMap<String, Object> comment = new HashMap<>();
						args.put("Content", content);
						args.put("Image", imgUploadResult.getUrl());
						args.put("RefId", refId);
						args.put("Type", type);
						args.put("ParentId", parentId);
						comment.put("comment", args);
						String arg = getJsonStrArg(comment);
						return mApiUtil.addComment(arg);
					}
				})
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(typex2))
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getCommentList(int type, long refid, String maxdate) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<Comment>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.COMMENT_LIST_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("type", type);
		args.put("refid", refid);
		args.put("pagesize", 20);
		args.put("maxdate", maxdate);

		String arg = getJsonStrArg(args);

		java.lang.reflect.Type typex = new TypeToken<ServerMsg<ListData<Comment>>>() {
		}.getType();

		Observable<String> observable = mApiUtil.getCommentList(arg);
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Comment>>>().getTransFormer(typex))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}


	public void getMyComment(String maxdate) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<Comment>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.COMMENT_MYLIST_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();

		args.put("pagesize", 20);
		args.put("maxdate", maxdate);

		String arg = getJsonStrArg(args);

		java.lang.reflect.Type typex = new TypeToken<ServerMsg<ListData<Comment>>>() {
		}.getType();

		Observable<String> observable = mApiUtil.getMyCommentList(arg);
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Comment>>>().getTransFormer(typex))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}


	public void getArticleDetail(long articleid) {
		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<Article> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ARTICLE_DETAIL_VOCATIONAL_ID;
		HashMap<String, Object> args = new HashMap<>();
		args.put("articleid", articleid);

		Observable<String> observable = mApiUtil.getArticleDetail(getJsonStrArg(args));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<Article>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<Article>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getHotTag() {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ServerConfig> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.CONFIG_DETAIL_VOCATIONAL_ID;

		Observable<String> observable = mApiUtil.getConfig();
		java.lang.reflect.Type type = new TypeToken<ServerMsg<ServerConfig>>() {
		}.getType();


		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ServerConfig>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void searchArticle(String key, int pagesize, String maxdate) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<Article>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ARTICLE_SEARCH_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("key", key);
		args.put("pagesize", pagesize);
		args.put("maxdate", maxdate);

		Observable<String> observable = mApiUtil.getArticleSearch(getJsonStrArg(args));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Article>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Article>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void topicFollowAdd(long topicid) {
		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.TOPICFOLLOW_ADD_VOCATIONAL_ID;
		HashMap<String, Object> args = new HashMap<>();
		args.put("topicid", topicid);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<String>>() {
		}.getType();

		Observable<String> observable = mApiUtil.addTopicFollow(getJsonStrArg(args));
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void topicFollowDelete(long topicid) {
		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = TOPICFOLLOW_DELETE_VOCATIONAL_ID;
		HashMap<String, Object> args = new HashMap<>();
		args.put("topicid", topicid);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<String>>() {
		}.getType();

		Observable<String> observable = mApiUtil.deleteTopicFollow(getJsonStrArg(args));
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getBannerTopicDetail(long topicid) {
		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<TopicSubject> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.TOPIC_DETAIL_VOCATIONAL_ID;
		HashMap<String, Object> args = new HashMap<>();
		args.put("topicid", topicid);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<TopicSubject>>() {
		}.getType();

		Observable<String> observable = mApiUtil.getTopicDetail(getJsonStrArg(args));
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<TopicSubject>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getNewTopics(String maxdate, int pagesize, long topicid) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<Topic>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.TOPIC_LIST4BANNER_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();

		args.put("maxdate", maxdate);
		args.put("pagesize", pagesize);
		args.put("topicid", topicid);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Topic>>>() {
		}.getType();

		Observable<String> observable = mApiUtil.getCommunityTopicList(getJsonStrArg(args));
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Topic>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void getHotTopics(int pageindex, int pagesize, long topicid) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<Topic>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.COMMUNITY_LIST4TOPICBYUPCOUNT_VOCATIONAL_ID;
		HashMap<String, Object> args = new HashMap<>();

		args.put("pageindex", pageindex);
		args.put("pagesize", pagesize);
		args.put("topicid", topicid);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Topic>>>() {
		}.getType();

		Observable<String> observable = mApiUtil.getCommunityUpcountTopicList(getJsonStrArg(args));
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Topic>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void getMsgChatDialog(int pagesize, String maxdate, long touserid) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<MsgChatDetail>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.MESSAGE_DIALOG_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pagesize", pagesize);
		args.put("maxdate", maxdate);
		args.put("touserid", touserid);
		Observable<String> observable = mApiUtil.getMessageDialog(getJsonStrArg(args));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<MsgChatDetail>>>() {
		}.getType();


		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<MsgChatDetail>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void searchUsers(int pageindex, int pagesize, String key) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<RecommendUser>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.USER_SEARCHVIPLIST_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pageindex", pageindex);
		args.put("pagesize", pagesize);
		args.put("key", key);

		Observable<String> observable = mApiUtil.searchVipList(getJsonStrArg(args));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<RecommendUser>>>() {
		}.getType();


		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<RecommendUser>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getUserList(int pageindex, int pagesize) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<RecommendUser>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.USER_VIPLIST_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pageindex", pageindex);
		args.put("pagesize", pagesize);

		Observable<String> observable = mApiUtil.getVipList(getJsonStrArg(args));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<RecommendUser>>>() {
		}.getType();


		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<RecommendUser>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void upLoadVideo(String path,
							String tags,
							String content,
							String refid,
							String reftype,
							String topicId) {

		if (checkNetWorkAvailable())
			return;

		ServerUploadSubscriber<POJO> subscriber = new ServerUploadSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.PIC_UPLOADMOV_VOCATIONAL_ID;
		java.lang.reflect.Type type = new TypeToken<VideoUploadResult>() {
		}.getType();

		Log.e("kwan", "upLoadVideo");
		Observable<String> observable = mApiUtil.upLoadMov(path, tags, content, refid, reftype, topicId, new CountingRequestBody.Listener() {
			@Override
			public void onRequestProgress(long bytesWritten, long contentLength) {


				Log.e("kwan", "up load" + bytesWritten + "contentLength::" + contentLength);
			}
		});
		Log.e("kwan", "upLoadVideo1");
		observable

				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<VideoUploadResult>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

		Log.e("kwan", "upLoadVideo2");
	}

	public void addTopic(String title, String ico, String content, String tagname, String images, long refId, int refType, long topicId) {
		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ARTICLE_TOPICNEW_ADD_VOCATIONAL_ID;
		java.lang.reflect.Type type = new TypeToken<ServerMsg<String>>() {
		}.getType();


		HashMap<String, Object> args = new HashMap<>();
		args.put("title", title);
		args.put("ico", ico);
		args.put("content", content);
		args.put("tagname", tagname);
		args.put("images", images);
		args.put("refId", refId);
		args.put("refType", refType);
		args.put("topicId", topicId);

		Observable<String> observable = mApiUtil.addNewTopic(getJsonStrArg(args));

		observable

				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}


	public static List<String> backImageUrl;
	public static boolean isFaild = false;
	public static int imageSize;

	public void upLoadImages(final String paths) {
		if (checkNetWorkAvailable())
			return;

		ServerUploadSubscriber<POJO> subscriber = new ServerUploadSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.PIC_UPLOADIMAGE_VOCATIONAL_ID;
		java.lang.reflect.Type type = new TypeToken<ImgUploadResult>() {
		}.getType();

		mApiUtil.upLoadImage(paths, new CountingRequestBody.Listener() {
			@Override
			public void onRequestProgress(long bytesWritten, long contentLength) {
				//Log.d("BZModule", "上传第:" + index + "  bytesWritten::" + bytesWritten + " contentLength::" + contentLength);
			}
		})
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ImgUploadResult>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getTags() {


		ServerSubscriber<List<Tag>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.TAG_LIST_VOCATIONAL_ID;
		Observable<String> observable = mApiUtil.getTagList();
		java.lang.reflect.Type ttype = new TypeToken<ServerMsg<List<Tag>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<List<Tag>>>().getTransFormer(ttype))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void searchSubject(String key) {
		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<TopicSubject>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.TOPIC_SEARCH_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("key", key);

		Observable<String> observable = mApiUtil.searchTopic(getJsonStrArg(args));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<TopicSubject>>>() {
		}.getType();


		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<TopicSubject>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getHotSubject() {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<TopicSubject>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.TOPIC_HOTLIST_VOCATIONAL_ID;

		Observable<String> observable = mApiUtil.getHotTopicList();
		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<TopicSubject>>>() {
		}.getType();


		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<TopicSubject>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void sLogin(String Platform, String OpenId, String Token, String Avatar, String AppId, String NickName) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<User> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.USER_SOCIAL_LOGIN_VOCATIONAL_ID;
		HashMap<String, Object> arg = new HashMap<>();

		HashMap<String, Object> args = new HashMap<>();
		args.put("Platform", Platform);
		args.put("OpenId", OpenId);
		args.put("Token", Token);
		args.put("Avatar", Avatar);
		args.put("AppId", AppId);
		args.put("NickName", NickName);
		arg.put("socialuser", args);

		Observable<String> observable = mApiUtil.loginSocial(getJsonStrArg(arg));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<User>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<User>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void doLogin(String phone, String pwd) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<User> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.USER_LOGIN_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("phonenum", phone);
		args.put("password", pwd);

		Observable<String> observable = mApiUtil.login(getJsonStrArg(args));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<User>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<User>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void doFind(String phonenum, String password) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<User> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.USER_FINDPASS_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("phonenum", phonenum);
		args.put("password", password);
		Observable<String> observable = mApiUtil.findPass(getJsonStrArg(args));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<User>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<User>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void doReg(String phonenum, String password) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<User> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.USER_REG_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("phonenum", phonenum);
		args.put("password", password);
		Observable<String> observable = mApiUtil.regUser(getJsonStrArg(args));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<User>>() {
		}.getType();


		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<User>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void getPhoneCode(String validatecode,String phonenum) {
		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.USER_PHONECODE_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("phonenum", phonenum);
		args.put("validatecode", validatecode);


		Observable<String> observable = mApiUtil.userPhoneCode(getJsonStrArg(args));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<String>>() {

		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getValidatecode(String phonenum) {
		if (checkNetWorkAvailable())
			return;

		ServerByteSubscriber subscriber = new ServerByteSubscriber(this);
		subscriber.vocational_id = ServerAPI.USER_validatecode_VOCATIONAL_ID;

//		HashMap<String, Object> args = new HashMap<>();
//		args.put("phonenum", phonenum);

		Observable<byte[]> observable = mApiUtil.userImgaeVcode(phonenum);
		java.lang.reflect.Type type = new TypeToken<ServerMsg<Byte[]>>() {

		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				//.flatMap(new TransFormJson<ServerMsg<Byte[]>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void uploadAvatar(String path) {

		if (checkNetWorkAvailable())
			return;

		ServerUploadSubscriber<ImgUploadResult> subscriber = new ServerUploadSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.PIC_UPLOADAVATAR_VOCATIONAL_ID;

		//参数
		Observable<String> observable = mApiUtil.upLoadAvatar(path, new CountingRequestBody.Listener() {
			@Override
			public void onRequestProgress(long bytesWrittlong, long contentLength) {
				//  Log.d("upload:",bytesWrittlong*100/contentLength+"");
			}
		});
		java.lang.reflect.Type type = new TypeToken<ImgUploadResult>() {
		}.getType();


		observable.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ImgUploadResult>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void updataUser(String PhoneNum, int Sex, String NickName, String TrueName,
						   String Description, String Address, String Password,
						   String Birthday, String OldPassword) {
		//"userInfo":
		// {"PhoneNum":"手机号","Sex":1,"NickName":"昵称",
		// "TrueName":"真实姓名", "Description":"介绍", "Address":"地址",
		// "Password":"123456", "Birthday":"2014-04-02", "OldPassword":"aaaaa"}}

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.USER_UPDATE_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		HashMap<String, Object> info = new HashMap<>();

		args.put("userInfo", info);

		info.put("PhoneNum", PhoneNum);
		info.put("Sex", Sex);
		info.put("NickName", NickName);
		info.put("TrueName", TrueName);
		info.put("Description", Description);
		info.put("Address", Address);
		info.put("Password", Password);
		info.put("Birthday", Birthday);
		info.put("OldPassword", OldPassword);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<MsgChatDetail>>>() {
		}.getType();

		Observable<String> observable = mApiUtil.updateUser(getJsonStrArg(args));

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getAdminInfo(long id) {

		if (checkNetWorkAvailable())
			return;


		ServerSubscriber<AdminUser> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.USER_ADMININFO_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("adminid", id);

		Observable<String> observable = mApiUtil.getAdminInfo(getJsonStrArg(args));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<AdminUser>>() {
		}.getType();


		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<AdminUser>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void uploadImag(String path) {
		if (checkNetWorkAvailable())
			return;

		ServerUploadSubscriber<ImgUploadResult> subscriber = new ServerUploadSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.PIC_UPLOADIMAGE_VOCATIONAL_ID;

		Observable<String> observable = mApiUtil.upLoadImage(path, new CountingRequestBody.Listener() {
			@Override
			public void onRequestProgress(long bytesWritten, long contentLength) {

			}
		});
		java.lang.reflect.Type type = new TypeToken<ImgUploadResult>() {
		}.getType();


		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ImgUploadResult>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void addMsg(long ToUserId, String Content, int Type, String Url) {
		//   {message:{ ToUserId:174,Content: \"私信内容\",Type:0,Url: \"\"}}
		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<MsgChatDetail> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.MESSAGE_ADD_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		HashMap<String, Object> arg = new HashMap<>();

		args.put("ToUserId", ToUserId);
		args.put("Content", Content);
		args.put("Type", Type);
		args.put("Url", Url);
		arg.put("message", args);

		Observable<String> observable = mApiUtil.addMessage(getJsonStrArg(arg));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<MsgChatDetail>>() {
		}.getType();
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<MsgChatDetail>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void getFindBanner() {

		ServerSubscriber<List<Banner>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.RECOMMEND_LIST_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("channelType", -1);
		String arg = getJsonStrArg(args);

		Observable<String> observable = mApiUtil.getRecommendList(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<List<Banner>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<List<Banner>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void getFindRecommend() {

		ServerSubscriber<List<RecommendUser>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.USER_ADMINLIST_VOCATIONAL_ID;

		Observable<String> observable = mApiUtil.getAdminList();

		java.lang.reflect.Type type = new TypeToken<ServerMsg<List<RecommendUser>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<List<RecommendUser>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getCircleTopics(String maxdate, int pagesize) {

		ServerSubscriber<ListData<Topic>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.COMMUNITY_CIRCLEARTICLE2_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("publishdate", maxdate);
		args.put("pagesize", pagesize);

		Observable<String> observable = mApiUtil.getCommunityCircleArticle2(getJsonStrArg(args));

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Topic>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Topic>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void getTopicRec(int pageindex, int pagesize) {

		ServerSubscriber<ListData<Topic>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.COMMUNITY_REC_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pageindex", pageindex);
		args.put("pagesize", pagesize);

		Observable<String> observable = mApiUtil.getCommunityRec(getJsonStrArg(args));

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Topic>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Topic>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void getShaiActivity(int pageindex, int pagesize) {
		ServerSubscriber<ListData<BzActivity>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.TOPIC_LIST_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pageindex", pageindex);
		args.put("pagesize", pagesize);

		Observable<String> observable = mApiUtil.getShaiActivtys(getJsonStrArg(args));

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<BzActivity>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<BzActivity>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getTriProducts() {

		ServerSubscriber<List<TriProduct>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.TRIPRODUCT_LIST_VOCATIONAL_ID;
		Observable<String> observable = mApiUtil.getTriProducts();
		java.lang.reflect.Type type = new TypeToken<ServerMsg<List<TriProduct>>>() {
		}.getType();
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<List<TriProduct>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getCategoryList() {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<List<Category>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.CATEGORY_LIST_VOCATIONAL_ID;
		Observable<String> observable = mApiUtil.getCategoryList();
		java.lang.reflect.Type type = new TypeToken<ServerMsg<List<Category>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<List<Category>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void getBrand() {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<Brand>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.BRAND_LIST_VOCATIONAL_ID;

		Observable<String> observable = mApiUtil.getBrandList();


		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Brand>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Brand>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}


	public void getProducts(long categoryid, int pagesize, String maxdate, boolean isLogin) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<Product>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.PRODUCT_LISTBYCATEGORY_VOCATIONAL_ID;

		//参数
		HashMap<String, Object> args = new HashMap<>();
		args.put("categoryid", categoryid);
		args.put("pagesize", pagesize);
		args.put("maxdate", maxdate);
		String arg = getJsonStrArg(args);
		Observable<String> observable;
		if (isLogin)
			observable = mApiUtil.getProductsByCategoryIdWithToken(arg);
		else
			observable = mApiUtil.getProductsByCategoryId(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Product>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Product>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getProducts(String productname, int pagesize, String maxdate, boolean isLogin) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<Product>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.PRODUCT_SEARCH_VOCATIONAL_ID;

		//参数
		HashMap<String, Object> args = new HashMap<>();
		args.put("productname", productname);
		args.put("pagesize", pagesize);
		args.put("maxdate", maxdate);
		String arg = getJsonStrArg(args);
		Observable<String> observable;
		if (isLogin)
			observable = mApiUtil.searchProductWithToken(arg);
		else
			observable = mApiUtil.searchProduct(arg);


		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Product>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Product>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getSpecials(int pageSize, String maxdate) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<Special>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.PRODUCTSPECIAL_LIST_VOCATIONAL_ID;

		//参数
		HashMap<String, Object> args = new HashMap<>();
		args.put("pageSize", pageSize);
		args.put("maxdate", maxdate);
		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.getSpecials(arg);
		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Special>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Special>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getSpecial(long productSpecialId) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<Special> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.PRODUCTSPECIAL_DETAIL_VOCATIONAL_ID;

		//参数
		HashMap<String, Object> args = new HashMap<>();
		args.put("productSpecialId", productSpecialId);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.getSpecialDetail(arg);
		java.lang.reflect.Type type = new TypeToken<ServerMsg<Special>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<Special>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getFavArticle(int pagesize, String createdate) {

		ServerSubscriber<ListData<FavArticle>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ACTION_MYARTICLEFAV_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pagesize", pagesize);
		args.put("createdate", createdate);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.getMyArticleFav(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<FavArticle>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<FavArticle>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getFavTopic(int pagesize, String createdate) {

		ServerSubscriber<ListData<Topic>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ACTION_MYCOMMUNITYFAV_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pagesize", pagesize);
		args.put("createdate", createdate);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.getMyCommunityFav(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Topic>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Topic>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void getFavProduct(int pagesize, String createdate) {

		ServerSubscriber<ListData<Product>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ACTION_MYPRODUCTFAV_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pagesize", pagesize);
		args.put("createdate", createdate);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.getMyProductFav(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Product>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Product>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void getDaySign() {

		ServerSubscriber<DaySign> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.DAYSIGN_LIST_VOCATIONAL_ID;

		Observable<String> observable = mApiUtil.getDaysignList();

		java.lang.reflect.Type type = new TypeToken<ServerMsg<DaySign>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<DaySign>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	//屏蔽
	public void disview(long refid, int position) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<Integer> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ACTION_ADD_VOCATIONAL_ID;
		subscriber.exData = new HashMap<>();
		subscriber.exData.put("position", position);
		subscriber.exData.put("acttype", 4);

		HashMap<String, Object> args = new HashMap<>();
		args.put("refid", refid);
		args.put("type", 3);
		args.put("acttype", 4);

		String arg = getJsonStrArg(args);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<Integer>>() {
		}.getType();

		Observable<String> observable = mApiUtil.addAction(arg);
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<Integer>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	//举报
	public void report(long refid, int position) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<Integer> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ACTION_ADD_VOCATIONAL_ID;
		subscriber.exData = new HashMap<>();
		subscriber.exData.put("position", position);
		subscriber.exData.put("acttype", 3);

		HashMap<String, Object> args = new HashMap<>();
		args.put("refid", refid);
		args.put("type", 3);
		args.put("acttype", 3);

		String arg = getJsonStrArg(args);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<Integer>>() {
		}.getType();

		Observable<String> observable = mApiUtil.addAction(arg);
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<Integer>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	//删除帖子
	public void deleteTopic(long articleid, int position) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<Boolean> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ARTICLE_DELETEMYTOPIC_VOCATIONAL_ID;

		subscriber.exData = new HashMap<>();
		subscriber.exData.put("position", position);

		HashMap<String, Object> args = new HashMap<>();
		args.put("articleid", articleid);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<Boolean>>() {
		}.getType();

		Observable<String> observable = mApiUtil.deleteMyTopic(getJsonStrArg(args));
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<Boolean>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getUserTopics(int pagesize, String maxdate, long userid) {
		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<ListData<Topic>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ARTICLE_TOPICBYUSERID_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pagesize", pagesize);
		args.put("maxdate", maxdate);
		args.put("userid", userid);
		String arg = getJsonStrArg(args);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Topic>>>() {
		}.getType();

		Observable<String> observable = mApiUtil.getTopicListById(arg);
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Topic>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getTagTopics(int pagesize, int pageindex, String tagname) {

		ServerSubscriber<ListData<Topic>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.COMMUNITY_LISTBYTAG_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pagesize", pagesize);
		args.put("pageindex", pageindex);
		args.put("tagname", tagname);
		String arg = getJsonStrArg(args);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Topic>>>() {
		}.getType();

		Observable<String> observable = mApiUtil.getCommunityByTag(arg);
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Topic>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}


	public void getTopicDetail(long articleid, boolean islogin) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<Topic> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.COMMUNITY_DETAIL_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("articleid", articleid);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<Topic>>() {
		}.getType();
		Observable<String> observable;

		observable = mApiUtil.getCommunityDetail(getJsonStrArg(args));
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<Topic>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	//点赞 和 收藏
	public void addFav(long refid, int position) {

		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ACTION_ADD_VOCATIONAL_ID;

		HashMap<String, Object> exdata = new HashMap<>();
		exdata.put("acttype", 1);
		exdata.put("position", position);
		subscriber.exData = exdata;

		HashMap<String, Object> args = new HashMap<>();
		args.put("type", 3);
		args.put("acttype", 1);
		args.put("refid", refid);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.addAction(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<String>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getProduct(long productId, boolean isLogin) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<Product> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.PRODUCT_DETAIL_VOCATIONAL_ID;

		HashMap<String, Object> arg = new HashMap<>();
		arg.put("productId", productId);

		Observable<String> observable;

		if (isLogin)
			observable = mApiUtil.getProductDetailWithLogin(getJsonStrArg(arg));
		else
			observable = mApiUtil.getProductDetail(getJsonStrArg(arg));


		java.lang.reflect.Type type = new TypeToken<ServerMsg<Product>>() {
		}.getType();


		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<Product>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}


	public void getTopicFollowList(int pageindex, int pagesize) {

		ServerSubscriber<ListData<BzActivity>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.TOPICFOLLOW_LIST_VOCATIONAL_ID;

		//{\"pageindex\":1, \"pagesize\":10}

		HashMap<String, Object> args = new HashMap<>();

		args.put("pageindex", pageindex);
		args.put("pagesize", pagesize);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.getTopicFollowList(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<BzActivity>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<BzActivity>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}


	public void searchTag(int pageindex, int pagesize, String key) {
//{\"pageindex\":1, \"pagesize\":10, \"key\":\"\"}

		ServerSubscriber<ListData<Tag>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.COMMUNITY_SEARCHTAG_VOCATIONAL_ID;


		HashMap<String, Object> args = new HashMap<>();

		args.put("pageindex", pageindex);
		args.put("pagesize", pagesize);
		args.put("key", key);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.searchCommunityTag(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Tag>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Tag>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void searchUser(int pageindex, int pagesize, String key) {
//{\"pageindex\":1, \"pagesize\":10, \"key\":\"\"}

		ServerSubscriber<ListData<User>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.COMMUNITY_SEARCH_VOCATIONAL_ID;


		HashMap<String, Object> args = new HashMap<>();

		args.put("pageindex", pageindex);
		args.put("pagesize", pagesize);
		args.put("key", key);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.searchCommunity(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<User>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<User>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void searchTopic(int pageindex, int pagesize, String key) {
//{\"pageindex\":1, \"pagesize\":10, \"key\":\"\"}

		ServerSubscriber<ListData<Topic>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.COMMUNITY_SEARCHTOPIC_VOCATIONAL_ID;


		HashMap<String, Object> args = new HashMap<>();

		args.put("pageindex", pageindex);
		args.put("pagesize", pagesize);
		args.put("key", key);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.searchCommunityTopic(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Topic>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Topic>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getUserInfo(String USERID) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<User> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.USER_USERINFO_VOCATIONAL_ID;

		java.lang.reflect.Type type = new TypeToken<ServerMsg<User>>() {
		}.getType();

		Observable<String> observable = mApiUtil.getUserInfo(USERID);
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<User>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void getUserTags(int pageindex, int pagesize, long userid) {

		ServerSubscriber<ListData<Tag>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.TAG_TAGLISTBYUSERID_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pageindex", pageindex);
		args.put("pagesize", pagesize);
		args.put("userid", userid);
		String arg = getJsonStrArg(args);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Tag>>>() {
		}.getType();

		Observable<String> observable = mApiUtil.getTaglistByUserid(arg);
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Tag>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}


	public void getBZActivtyDetail(long topicid) {
		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<BzActivity> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.TOPIC_DETAIL_VOCATIONAL_ID;
		HashMap<String, Object> args = new HashMap<>();
		args.put("topicid", topicid);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<BzActivity>>() {
		}.getType();

		Observable<String> observable = mApiUtil.getTopicDetail(getJsonStrArg(args));
		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<BzActivity>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}


	//获取我的帖子
	public void getMyTopic(int pagesize, String maxdate) {

		ServerSubscriber<ListData<Topic>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.ARTICLE_MYTOPIC_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pagesize", pagesize);
		args.put("maxdate", maxdate);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.getMyTopic(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Topic>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Topic>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void getCommentsToMe(int pagesize, String maxdate) {

		ServerSubscriber<ListData<Comment>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.COMMENT_REPLYLIST_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pagesize", pagesize);
		args.put("maxdate", maxdate);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.getCommentReplyList(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Comment>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Comment>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);


	}

	//获取我的消息 聊天
	public void getMsgChatList(String PAGESIZE, String MAXDATE) {

		ServerSubscriber<ListData<MsgChatItem>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.MESSAGE_LIST_VOCATIONAL_ID;
		Observable<String> observable = mApiUtil.getMessageList(PAGESIZE, MAXDATE);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<MsgChatItem>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<MsgChatItem>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getBeFaved(int pagesize, String createdate) {

		ServerSubscriber<ListData<FavArticle>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.MYCOMMUNITY_FAVED_VOCATIONAL_ID;
		HashMap<String, Object> args = new HashMap<>();
		args.put("pagesize", pagesize);
		args.put("createdate", createdate);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.getMyCommunityFaved(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<FavArticle>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<FavArticle>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getUserType() {

		ServerSubscriber<List<UserType>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.USERTYPE_LIST_VOCATIONAL_ID;
		Observable<String> observable = mApiUtil.getUserType();

		java.lang.reflect.Type type = new TypeToken<ServerMsg<List<UserType>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<List<UserType>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getUserTypeUser(int pageSize, long userTypeId, int pageIndex) {

		ServerSubscriber<ListData<RecommendUser>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.USERTYPE_USERLIST_VOCATIONAL_ID;
		HashMap<String, Object> args = new HashMap<>();

		args.put("pageSize", pageSize);
		args.put("userTypeId", userTypeId);
		args.put("pageIndex", pageIndex);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.getUserTypeUser(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<RecommendUser>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<RecommendUser>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void setState(int state) {

		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.USER_SETMESSAGESTATE_VOCATIONAL_ID;
		HashMap<String, Object> args = new HashMap<>();
		args.put("state", state);

		Observable<String> observable = mApiUtil.setMessageState(getJsonStrArg(args));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<String>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void addFeedBack(String Images, String Content) {

		if (checkNetWorkAvailable())
			return;

		ServerSubscriber<String> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.FEEDBACK_ADD_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		HashMap<String, Object> arg = new HashMap<>();

		args.put("Images", Images);
		args.put("Content", Content);
		arg.put("feedback", args);

		Observable<String> observable = mApiUtil.addFeedBack(getJsonStrArg(arg));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<String>>() {
		}.getType();


		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<String>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}


	public void getCommunityListByTagAndUserid(int pageindex, int pagesize, String tagname, long userid) {

		ServerSubscriber<ListData<Topic>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.COMMUNITY_LISTBYTAGBYUSERID_VOCATIONAL_ID;
		HashMap<String, Object> args = new HashMap<>();
		args.put("tagname", tagname);
		args.put("userid", userid);
		args.put("pageindex", pageindex);
		args.put("pagesize", pagesize);

		String arg = getJsonStrArg(args);
		Observable<String> observable = mApiUtil.getCommunityListByTagAndUserid(arg);

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<Topic>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<Topic>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}

	public void gettag_hotlist4photo() {

		ServerSubscriber<List<Tag>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.TAG_HOTLIST4PHOTO_VOCATIONAL_ID;
		Observable<String> observable = mApiUtil.tag_hotlist4photo();

		java.lang.reflect.Type type = new TypeToken<ServerMsg<List<Tag>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<List<Tag>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}


	public void getStickerType() {

		ServerSubscriber<ListData<StickerType>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.STICKERTYPE_LIST_VOCATIONAL_ID;
		Observable<String> observable = mApiUtil.stickertype_list();

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<StickerType>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<StickerType>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);


	}

	public void getSticker(long id) {

		ServerSubscriber<ListData<StickerBean>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.STICKER_LIST_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pageSize", 20);
		args.put("maxDate", "0");
		args.put("stickerTypeId", id);

		HashMap<String, Object> arg = new HashMap<>();
		arg.put("query", args);

		Observable<String> observable = mApiUtil.stickerList(getJsonStrArg(arg));
		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<StickerBean>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<StickerBean>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

		//mModel.getStickerType();
	}

	public void getFilter() {

		ServerSubscriber<ListData<FilterBean>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.FILTER_LIST_VOCATIONAL_ID;
		Observable<String> observable = mApiUtil.filter_list();

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<FilterBean>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<FilterBean>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getPointProductList(String maxDate, int pageSize) {

		ServerSubscriber<ListData<PointProduct>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.POINTSHOP_LIST_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pageSize", pageSize);
		args.put("maxDate", maxDate);

		HashMap<String, Object> arg = new HashMap<>();
		arg.put("query", args);

		Observable<String> observable = mApiUtil.pointproduct_list(getJsonStrArg(arg));

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<PointProduct>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<PointProduct>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getPointDetail(int pageIndex, int pageSize) {

		ServerSubscriber<ListData<PointDetail>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.USERPOINTSLOG_LIST_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pageSize", pageSize);
		args.put("pageIndex", pageIndex);
		HashMap<String, Object> arg = new HashMap<>();
		arg.put("query", args);
		Observable<String> observable = mApiUtil.userpointslog_list(getJsonStrArg(arg));

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<PointDetail>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<PointDetail>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getPointRecord(int pageIndex, int pageSize) {

		ServerSubscriber<ListData<PointRecord>> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.POINTSHOP_EXCHANGELIST_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("pageSize", pageSize);
		args.put("pageIndex", pageIndex);
		HashMap<String, Object> arg = new HashMap<>();
		arg.put("query", args);
		Observable<String> observable = mApiUtil.pointshop_exchangelist(getJsonStrArg(arg));

		java.lang.reflect.Type type = new TypeToken<ServerMsg<ListData<PointRecord>>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<ListData<PointRecord>>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);
	}

	public void getPointResult(long pointShopId, String trueName, String phoneNum, String address) {

		ServerSubscriber<PointResult> subscriber = new ServerSubscriber<>(this);
		subscriber.vocational_id = ServerAPI.POINTSHOP_ADD_VOCATIONAL_ID;

		HashMap<String, Object> args = new HashMap<>();
		args.put("PointShopId", pointShopId);

		if (trueName != null && phoneNum != null && address != null) {
			args.put("TrueName", trueName);
			args.put("PhoneNum", phoneNum);
			args.put("Address", address);
		}


		HashMap<String, Object> arg = new HashMap<>();
		arg.put("userlog", args);
		Observable<String> observable = mApiUtil.pointshop_add(getJsonStrArg(arg));

		java.lang.reflect.Type type = new TypeToken<ServerMsg<PointResult>>() {
		}.getType();

		observable
				.subscribeOn(Schedulers.io())
				.flatMap(new TransFormJson<ServerMsg<PointResult>>().getTransFormer(type))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribe(subscriber);

	}
}
