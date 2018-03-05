package com.metshow.bz.net.api;

import android.util.Log;

import com.kwan.base.api.BaseAPIUtil;
import com.kwan.base.api.CountingRequestBody;
import com.kwan.base.api.download.DownloadProgressInterceptor;
import com.kwan.base.util.ImageUtil;
import com.kwan.base.util.PathUtil;
import com.metshow.bz.util.BZSPUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 *
 * Created by Mr.Kwan on 2017-4-10.
 */

public class BazzarAPIUtil extends BaseAPIUtil{
	//public static String HTTP_BASE = "http://bztest.metshow.com/";
	public static String HTTP_BASE = "http://bz.metshow.com/";
	//public static String HTTP_BASE = "http://192.168.11.12/";
	private static String HTTP_API = "api/";
	private static String HTTP_SERVER = HTTP_BASE + HTTP_API;
	public static String HTTP_SERVER_UPLOAD = "http://bznewitem2.metshow.com/";

	private ServerAPI mServerAPI;
	private ServerAPI mServerByteAPI;
	private ServerAPI mWithToken;
	private ServerAPI mUploadAPI;

	public BazzarAPIUtil(){
		mServerAPI = serverAPI().create(ServerAPI.class);
		mWithToken = withToken().create(ServerAPI.class);
		mUploadAPI = upLoadAPI().create(ServerAPI.class);

		mServerByteAPI = serverByteAPI().create(ServerAPI.class);


	}

	@Override
	protected String getBaseUrl() {
		return HTTP_SERVER;
	}

	@Override
	protected String getBaseTokenUrl() {
		return HTTP_SERVER;
	}

	@Override
	protected String getBaseUpLoadUrl() {
		return HTTP_SERVER_UPLOAD;
	}

	@Override
	protected String getToken() {
		Log.d("Token::",BZSPUtil.getUser().getToken());
		return BZSPUtil.getUser().getToken();
	}

	/**
	 * 001002 获取地址信息
	 */

	public Observable<String> getServerConfigNew(){
		return mServerAPI.serverconfig_new();
	}

	/**
	 * 001002 获取启动信息
	 *
	 * @param date {"date":"0"} date默认传0，其他是否传返回的UsingDate的时间戳
	 * @return 启动信息
	 */
	public Observable<String> getSplashList(String date) {
		return mServerAPI.splash_list(date);
	}
	
	/**
	 * 1.2
	 */
	public Observable<String> getConfig() {
		return mServerAPI.config_detail();
	}


	/**
	 * 1.3邮件发送验证码接口
	 *
	 * @param email {"email":"mr.kwan@qq.com"}
	 * @return 返回的验证码
	 */
	public Observable<String> getEmailCode(String email) {
		return mServerAPI.user_emailcode(email);
	}

	/**
	 * 1.4找回密码检测帐号状态接口
	 *
	 * @param account { account:""}
	 * @return 返回绑定手机号，如果为空，则没有绑定
	 */
	public Observable<String> userCheckInfo(String account) {
		return mServerAPI.user_checkinfo(account);
	}

	/**
	 * 1.5获取手机验证码
	 *
	 * @param phonenum {phonenum:""}
	 * @return 返回绑定手机号，如果为空，则没有绑定
	 */
	public Observable<String> userPhoneCode(String phonenum) {
		return mServerAPI.user_phonecode(phonenum);
	}

	/**
	 * 1.5获取手机验证码
	 *
	 * @param phonenum {phonenum:""}
	 * @return 返回绑定手机号，如果为空，则没有绑定
	 */
	public Observable<byte[]> userImgaeVcode(String PHONENUM) {
		return mServerByteAPI.user_validatecode_img(PHONENUM);
	}

	

	/**
	 * 2.1用户反馈添加接口
	 *
	 * @param feedback {"feedback":{"Images":"images1","Content":"758"}}
	 * @return 返回绑定手机号，如果为空，则没有绑定
	 */
	public Observable<String> addFeedBack(String feedback) {
		return mServerAPI.feedback_add(feedback);
	}

	/**
	 * 2.2用户反馈列表接口
	 *
	 * @param arg {"pagesize":10,"createdate":"0"}
	 * @return 返回绑定手机号，如果为空，则没有绑定
	 */
	public Observable<String> getFeedBackList(String arg) {
		return mServerAPI.feedback_list(arg);
	}

	/**
	 * 2.3用户反馈点击支持接口
	 *
	 * @param feedbackid {"feedbackid":1}
	 * @return isSuc:true/false
	 */
	public Observable<String> addFeedBackAction(String feedbackid) {
		return mServerAPI.feedback_action_add(feedbackid);
	}

	/**
	 * 2.4用户反馈内容信息接口
	 *
	 * @return isSuc:true/false
	 */
	public Observable<String> getFeedBackDetail() {
		return mServerAPI.feedback_detail();
	}

	/**
	 * 3.1第三方社交账号登录信息接口
	 *
	 * @param socialuser {"socialuser":{"Platform":"1/2/3/4/5",
	 *                   "OpenId":"OpenId","Token":"Access_Token",
	 *                   "Avatar":"Avatar","AppId":"AppId",
	 *                   "NickName":"NickName"}}
	 *                   <p/>
	 *                   Platform: 社交账号平台/1/2/3/4 【必须】
	 *                   <p/>
	 *                   sinaweibo 1
	 *                   tencentWeibo 2
	 *                   QZone 3
	 *                   wechat 4
	 *                   wechatmoments 5
	 *                   wechatFavorite 6
	 *                   QQ 7
	 *                   <p/>
	 *                   OpenId: 社交账号OpenId或者UID 【必须】
	 *                   Token: 社交账号登陆后的Token 【必须】-注意非本系统Token
	 *                   Avatar:头像 【必须】
	 *                   AppId: 应用ID,第三方社交账号申请的APPID
	 *                   NickName:昵称 【必须】
	 * @return 注册成功默认是登录状态返回token, 登录成功后每次访问在Http头部添加 token
	 */
	public Observable<String> loginSocial(String socialuser) {
		return mServerAPI.user_social_login(socialuser);
	}

	/**
	 * 3.2上传头像并保存接口
	 * <p/>
	 * 登录成功后每次访问在Http头部添加 token
	 *
	 * @param path     上传头像文件路径
	 * @param listener 上传进度监听
	 * @return {error:0,url:imageurl}
	 * error：1  成功返回 0
	 */

	public Observable<String> upLoadAvatar(String path, CountingRequestBody.Listener listener) {
		File file = new File(path);
		RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

		CountingRequestBody prequestBody = new CountingRequestBody(requestBody, listener);
		MultipartBody.Part body = MultipartBody.Part.createFormData("imgFile", file.getName(), prequestBody);
		return mUploadAPI.pic_uploadavatar(body);
	}

	/**
	 * 3.3用户上传图片接口
	 * <p/>
	 * 登录成功后每次访问在Http头部添加 token
	 *
	 * @param path     上传图片文件路径
	 * @param listener 上传进度监听
	 */

	public Observable<String> upLoadImage(String path, CountingRequestBody.Listener listener) {

		File file;
		String targetPath = PathUtil.getApplicationTempPath() + System.currentTimeMillis()+"_compressPic.jpg";
		//调用压缩图片的方法，返回压缩后的图片path
		final String compressImage = ImageUtil.compressImage(path, targetPath, 30);
		final File compressedPic = new File(compressImage);

		if (compressedPic.exists()) {
			Log.d("upload", "图片压缩上传");
			file = compressedPic;
		} else {//直接上传
			file = new File(path);
		}


		RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

		CountingRequestBody prequestBody = new CountingRequestBody(requestBody, listener);
		MultipartBody.Part body = MultipartBody.Part.createFormData("imgFile", file.getName(), prequestBody);

		return mUploadAPI.pic_uploadimage(body);
	}

	public Observable<String> upLoadImage2(List<String> paths) {

		List<MultipartBody.Part> parts = new ArrayList<>(paths.size());
		for (String imgStr : paths) {
			File file = new File(imgStr);
			RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
			MultipartBody.Part part = MultipartBody.Part.createFormData("imgFile", file.getName(), requestBody);
			parts.add(part);
		}


		return mUploadAPI.pic_uploadimage2(parts);
	}


	/**
	 * 3.4用户反馈点击支持接口
	 * <p/>
	 * <p/>
	 * 1.请求改接口，需要登陆,发送http协议header中需增加token
	 * 2.修改成功返回token,登录成功后每次访问在Http头部添加 token
	 *
	 * @param userInfo {"userInfo":{
	 *                 "PhoneNum":"手机号","Sex":1, "NickName":"昵称",
	 *                 "TrueName":"真实姓名", "Description":"介绍",
	 *                 "Address":"地址", "Password":"123456", "Birthday":"2014-04-02",
	 *                 "OldPassword":"aaaaa"}}
	 *                 <p/>
	 *                 Sex 1男 2女
	 *                 修改某项，只需要传某项，其他项为空。
	 * @return isSuc:true/false
	 */
	public Observable<String> updateUser(String userInfo) {
		return mWithToken.user_update(userInfo);
	}

	/**
	 * 3.5 获取某人的部分基本信息
	 *
	 * @param USERID 用户 id
	 */
	public Observable<String> getUserInfo(String USERID) {
		return mServerAPI.user_userinfo(USERID);
	}

	/**
	 * 3.6手机注册接口
	 * <p/>
	 * 注册成功默认是登录状态返回token,登录成功后每次访问在Http头部添加 token
	 *
	 * @param arg {"phonenum":"13980565638","password":"123456"}
	 */
	public Observable<String> regUser(String arg) {
		return mServerAPI.user_reg(arg);
	}

	/**
	 * 3.7手机登录接口
	 * <p/>
	 * 成功默认是登录状态返回token,登录成功后每次访问在Http头部添加 token
	 *
	 * @param arg {"phonenum":"13980565638","password":"123456"}
	 */
	public Observable<String> login(String arg) {
		return mServerAPI.user_login(arg);
	}

	/**
	 * 3.8找回密码接口
	 * <p/>
	 * 成功默认是登录状态返回token,登录成功后每次访问在Http头部添加 token
	 *
	 * @param arg {"phonenum":"13980565638","password":"123456"}
	 */
	public Observable<String> findPass(String arg) {
		return mServerAPI.user_findpass(arg);
	}

	/**
	 * 3.9获取编辑的基本信息
	 *
	 * @param adminid {"adminid":11}
	 */
	public Observable<String> getAdminInfo(String adminid) {
		return mWithToken.user_admininfo(adminid);
	}

	/**
	 * 3.10推荐vip列表
	 */
	public Observable<String> getAdminList() {
		return mWithToken.user_adminlist();
	}

	/**
	 * 3.11所有vip用户列表
	 *
	 * @param arg {pageindex:1,pagesize:10}
	 */
	public Observable<String> getVipList(String arg) {
		return mServerAPI.user_viplist(arg);
	}

	/**
	 * 3.12搜索vip用户列表
	 *
	 * @param arg {pageindex:1,pagesize:10,key:""}
	 *            key:昵称关键字
	 */
	public Observable<String> searchVipList(String arg) {
		return mServerAPI.user_searchviplist(arg);
	}

	/**
	 * 3.13设定用户是否接收好友动态
	 *
	 * @param state {state:1}
	 *              state:1为拒绝
	 */
	public Observable<String> setMessageState(String state) {
		return mWithToken.user_setmessagestate(state);
	}

	/**
	 * 4.1banner广告接口
	 *
	 * @param channelType {"channelType":20}
	 */
	public Observable<String> getRecommendList(String channelType) {
		return mServerAPI.recommend_list(channelType);
	}

	/**
	 * 4.3首页推荐位列表接口
	 */
	public Observable<String> getAdforrecList() {
		return mWithToken.adforrec_list();
	}

	/**
	 * 5.2日签新接口 2016-6-3改
	 */
	public Observable<String> getDaysignList() {
		return mServerAPI.daysign_list();
	}

	/**
	 * 5.3日签提交投票或者调查表接口
	 *
	 * @param arg {"daysignId":1, "votelist":[
	 *            {"VoteId"：1, "VoteDetailId":1},
	 *            {"VoteId"：1, "VoteDetailId":2}
	 *            ]}
	 */
	public Observable<String> addDaysign(String arg) {
		return mServerAPI.daysign_add(arg);
	}

	/**
	 * 6.1发现栏目 标签分类列表接口
	 * Header中传token
	 */
	public Observable<String> getTagFindList() {
		return mWithToken.tag_findlist();
	}

	/**
	 * 6.2发贴时推荐标签列表接口
	 * Header中传token
	 */
	public Observable<String> getTagList() {
		return mWithToken.tag_list();
	}

	/**
	 * 6.3用户关注标签接口
	 * Header中传token
	 *
	 * @param tagids {"tagids":[{ "TagId":1},{ "TagId":2}]}
	 */
	public Observable<String> addTag(String tagids) {
		return mWithToken.tag_add(tagids);
	}

	/**
	 * 7.1获取分类列表
	 */
	public Observable<String> getChannelList() {
		return mServerAPI.channel_list();
	}

	/**
	 * 8.1热榜接口
	 */
	public Observable<String> getDailyList() {
		return mServerAPI.daily_list();
	}

	/**
	 * 9.1通过频道获取列表接口
	 *
	 * @param arg {"channelid":15,"pagesize":10,"maxdate":"0"}
	 *            Channeled:频道编号
	 *            Pagesize:分页数
	 *            Maxdate:最大的时间戳，用于获取更多，传0获取最新的
	 * @return isSuc:true/false
	 */
	public Observable<String> getArticleList(String arg) {
		return mServerAPI.article_list(arg);
	}

	/**
	 * 9.2文章详情接口
	 *
	 * @param articleid {"articleid":1}
	 *                  <p/>
	 *                  Articleid:文字编号
	 * @return isSuc:true/false
	 */
	public Observable<String> getArticleDetail(String articleid) {
			return mWithToken.article_detail(articleid);
	}

	/**
	 * 9.3文章搜索接口
	 *
	 * @param arg {"key":"美女","pagesize":7,"maxdate":"0"}
	 *            <p/>
	 *            Articleid:文字编号
	 * @return isSuc:true/false
	 */
	public Observable<String> getArticleSearch(String arg) {
		return mServerAPI.article_search(arg);
	}

	/**
	 * 10.1评论添加
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"comment":{"Content":"content","Image":"image1,image2",
	 *            "RefId":442,"Type":1,"ParentId":0}}
	 *            <p/>
	 *            Type:1文章 2为商品 3为社区 4为专题
	 *            Image:多图，已逗号分隔
	 *            ParentId:一级为0 二级为对应的父级commentid
	 * @return isSuc:true/false
	 */
	public Observable<String> addComment(String arg) {
		return mWithToken.comment_add(arg);
	}

	/**
	 * 10.2评论列表
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"type":1,"refid":442,"pagesize":10,"maxdate":"0"}
	 *            <p/>
	 *         Type:1为文章 2为商品 3为社区 4为专题 5为活动（话题）6为商品专辑<p/>
	 *            Refid：对应的id号<p/>
	 * @return isSuc:true/false
	 */
	public Observable<String> getCommentList(String arg) {
		return mWithToken.comment_list(arg);
	}

	/**
	 * 10.3我的评论列表
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pagesize":10,"maxdate":"0"}
	 * @return isSuc:true/false
	 */
	public Observable<String> getMyCommentList(String arg) {
		return mWithToken.comment_mylist(arg);
	}

	/**
	 * 10.4回复我的评论列表
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pagesize":10,"maxdate":"0"}
	 * @return isSuc:true/false
	 */
	public Observable<String> getCommentReplyList(String arg) {
		return mWithToken.comment_replylist(arg);
	}

	/**
	 * 10.5评论点赞
	 * <p/>
	 * Header中带上token
	 *
	 * @param action {"action":{"CommentId":1}}
	 *               <p/>
	 *               CommentId:评论编号
	 * @return isSuc:true/false
	 */
	public Observable<String> addCommentAction(String action) {
		return mWithToken.commentaction_add(action);
	}

	/**
	 * 10.6评论举报
	 * <p/>
	 * Header中带上token
	 *
	 * @param action {"action":{"CommentId":1}}
	 *               <p/>
	 *               CommentId:评论编号
	 * @return isSuc:true/false
	 */
	public Observable<String> policeCommentAction(String action) {
		return mWithToken.commentaction_police(action);
	}

	/**
	 * 10.7评论删除
	 * <p/>
	 * Header中带上token
	 *
	 * @param action {"action":{"CommentId":1}}
	 *               <p/>
	 *               CommentId:评论编号
	 * @return isSuc:true/false
	 */
	public Observable<String> deleteComment(String action) {
		return mWithToken.commentaction_delete(action);
	}

	/**
	 * 11.1文章列表投票接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param action {"voteId":1,"voteDetailId":1}
	 * @return isSuc:true/false
	 */
	public Observable<String> addVote(String action) {
		return mWithToken.vote_add(action);
	}

	/**
	 * 12.1收藏/点赞/举报/屏蔽接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"type":1,"acttype":1,"refid":758}
	 *            <p/>
	 *            type:1文章 2为商品 3为社区圈子<p/>
	 *            acttype:1为收藏 2为点赞 3为举报 4为屏蔽<p/>
	 *            refid：关联的id<p/>
	 * @return isSuc:true/false
	 */
	public Observable<String> addAction(String arg) {
		return mWithToken.action_add(arg);
	}

	/**
	 * 12.2取消收藏接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {type:1,refid:2}
	 *            <p/>
	 *            type:1为文章 2为商品 3为社区圈子
	 *            refid：关联的id
	 * @return isSuc:true/false
	 */
	public Observable<String> deleteFav(String arg) {
		return mWithToken.action_deletefav(arg);
	}

	/**
	 * 12.3取消点赞接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {type:1,refid:2}
	 *            <p/>
	 *            type:1为文章 2为商品 3为社区圈子
	 *            refid：关联的id
	 * @return isSuc:true/false
	 */
	public Observable<String> deleteUp(String arg) {
		return mWithToken.action_deleteup(arg);
	}

	/**
	 * 12.4我的资讯收藏列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pagesize":10,"createdate":"0"}
	 * @return isSuc:true/false
	 */
	public Observable<String> getMyArticleFav(String arg) {
		return mWithToken.action_myarticlefav(arg);
	}

	/**
	 * 12.5我的欲望清单列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pagesize":10,"createdate":"0"}
	 * @return isSuc:true/false
	 */
	public Observable<String> getMyProductFav(String arg) {
		return mWithToken.action_myproductfav(arg);
	}

	/**
	 * 12.6我的收藏社区列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pagesize":10,"createdate":"0"}
	 * @return isSuc:true/false
	 */
	public Observable<String> getMyCommunityFav(String arg) {
		return mWithToken.action_mycommunityfav(arg);
	}

	/**
	 * 13.1分享积分接口
	 * <p/>
	 * Header中带上token
	 *
	 * @return isSuc:true/false
	 */
	public Observable<String> addSharePoint(String token) {
		return mWithToken.points_share_add();
	}

	/**
	 * 14.1我的消息接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pagesize":7,"createdate":"0"}
	 * @return isSuc:true/false
	 */
	public Observable<String> getNoticeList(String arg) {
		return mWithToken.notice_list(arg);
	}

	/**
	 * 14.2是否有新的系统消息接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param createdate {"createdate":"0"}
	 *                   <p/>
	 *                   如果本地无消息最后的时间，则传0，其他传本地保存的消息的最大时间
	 * @return isSuc:true/false
	 */
	public Observable<String> getNewSystemNotice(String createdate) {
		return mWithToken.notic_new_system(createdate);
	}

	/**
	 * 14.3是否有新的用户消息接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param createdate {"createdate":"0"}
	 *                   <p/>
	 *                   如果本地无消息最后的时间，则传0，其他传本地保存的消息的最大时间
	 * @return isSuc:true/false
	 */
	public Observable<String> getNewUserNotice(String createdate) {
		return mWithToken.notice_new_user(createdate);
	}

	/**
	 * 14.4消息数量整合新粉丝，未读消息，新评论接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"followeddate":"0","commentdate":"0"}
	 *            <p/>
	 *            followeddate：获取粉丝接口的时候，获取servertime进行处理保存
	 *            commentdate：获取回复我的评论的时候，获取servertime进行处理保存
	 * @return isSuc:true/false
	 */
	public Observable<String> getMessageAllCount(String arg) {
		return mWithToken.message_allcount(arg);
	}

	/**
	 * 14.5删除消息接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param noticeId {"noticeId":7}
	 * @return isSuc:true/false
	 */
	public Observable<String> deleteNotice(String noticeId) {
		return mWithToken.notice_delete(noticeId);
	}

	/**
	 * 16.1写对话接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param message {message:{ ToUserId:174,Content:"私信内容",Type:0,Url:""}}
	 *                <p/>
	 *                Type: 0为文字 1为图片 2为语音
	 *                Url:type为1或者2时，Url为图片/语音地址
	 * @return isSuc:true/false
	 */
	public Observable<String> addMessage(String message) {
		return mWithToken.message_add(message);
	}

	/**
	 * 16.2对话首页列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param PAGESIZE 每次获取数量
	 * @param MAXDATE  最大日期
	 * @return isSuc:true/false
	 */
	public Observable<String> getMessageList(String PAGESIZE, String MAXDATE) {
		return mWithToken.message_list(PAGESIZE, MAXDATE);
	}

	/**
	 * 16.3删除会话列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param id {"id":11}
	 * @return isSuc:true/false
	 */
	public Observable<String> deleteMessage(String id) {
		return mWithToken.message_dellist(id);
	}

	/**
	 * 16.4对话回话详情接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pagesize":"10","maxdate":"0","touserid":178}
	 * @return isSuc:true/false
	 */
	public Observable<String> getMessageDialog(String arg) {
		return mWithToken.message_dialog(arg);
	}

	/**
	 * 16.5获取比当前时间小的消息列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pagesize":"10","maxdate":"0","touserid":178}
	 * @return isSuc:true/false
	 */
	public Observable<String> getMessageDialogOld(String arg) {
		return mWithToken.message_dialogold(arg);
	}

	/**
	 * 16.6是否有未读对话接口
	 * <p/>
	 * Header中带上token
	 *
	 * @return isSuc:true/false
	 */
	public Observable<String> hasNoReadMessage(String token) {
		return mWithToken.message_hasnotread();
	}

	/**
	 * 16.7 未读对话条数
	 * <p/>
	 * Header中带上token
	 *
	 * @return isSuc:true/false
	 */
	public Observable<String> getNotReadMessageCount(String token) {
		return mWithToken.message_notreadcount();
	}


	/**
	 * 17.2发起话题接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"title":"投稿","ico":"ico1","content":"content","tagname":"美女",
	 *            "images":"xx,xx","refId":1, "refType":2,"topicId":1}
	 *            <p/>
	 *            tile:标题<P/>
	 *            ico：预览图</P>
	 *            content：内容</P>
	 *            tagname：标签名称</P>
	 *            images：多图以逗号分隔</P>
	 *            refId:默认传0，如果是RefType为2的话，RefId为商品编号  </P>
	 *            refType:默认为0，商品为2  </P>
	 *            topicId:话题编号</P>
	 * @return isSuc:true/false
	 */
	public Observable<String> addNewTopic(String arg) {
		return mWithToken.article_topicnew_add(arg);
	}

	/**
	 * 17.3上传视频并保存接口[20160530改]
	 * <p/>
	 * 登录成功后每次访问在Http头部添加 token
	 *
	 * @param path     文件路径
	 * @param tags     标签
	 * @param content  输入的文字内容
	 * @param refid    refid
	 * @param reftype  reftype
	 * @param topicId  topicId
	 * @param listener 上传监听
	 */

	public Observable<String> upLoadMov(String path,
										String tags,
										String content,
										String refid,
										String reftype,
										String topicId,
										CountingRequestBody.Listener listener) {

		//文件
		File videoFile = new File(path);
		RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), videoFile);
		CountingRequestBody prequestBody = new CountingRequestBody(requestBody, listener);
		MultipartBody.Part body = MultipartBody.Part.createFormData("videoFile", videoFile.getName(), prequestBody);

		//普通key/value
		RequestBody rTags =
				RequestBody.create(
						MediaType.parse("multipart/form-data"), tags);
		RequestBody rContent =
				RequestBody.create(
						MediaType.parse("multipart/form-data"), content);
		RequestBody rRefid =
				RequestBody.create(
						MediaType.parse("multipart/form-data"), refid);
		RequestBody rReftype =
				RequestBody.create(
						MediaType.parse("multipart/form-data"), reftype);
		RequestBody rTopicId =
				RequestBody.create(
						MediaType.parse("multipart/form-data"), topicId);

		return mUploadAPI.uploadmov(body, rTags, rContent, rRefid, rReftype, rTopicId);
	}

	/**
	 * 17.5我的贴子接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {\"pagesize\":7,\"maxdate\":\"0\"}
	 * @return isSuc:true/false
	 */
	public Observable<String> getMyTopic(String arg) {
		return mWithToken.article_mytopic(arg);
	}

	/**
	 * 17.6某人的贴子列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pagesize":7,"maxdate":"0","userid":15}
	 *            <p/>
	 *            Maxdate:取PublishDate
	 * @return isSuc:true/false
	 */
	public Observable<String> getTopicListById(String arg) {
		return mWithToken.article_topicbyuserid(arg);
	}

	/**
	 * 17.7删除帖子接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param articleid {"articleid":15}
	 * @return isSuc:true/false
	 */
	public Observable<String> deleteMyTopic(String articleid) {
		return mWithToken.article_deletemytopic(articleid);
	}

	/**
	 * 17.8社区贴子详情接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param articleid {"articleid":15}
	 * @return isSuc:true/false
	 */
	public Observable<String> getCommunityDetail(String articleid) {
		return mWithToken.community_detail(articleid);
	}


	/**
	 * 18.1品牌接口
	 *
	 * @return isSuc:true/false
	 */
	public Observable<String> getBrandList() {
		return mServerAPI.brand_list();
	}

	/**
	 * 18.2品牌搜索接口
	 *
	 * @param key {"key":""}
	 * @return isSuc:true/false
	 */
	public Observable<String> searchBrand(String key) {
		return mServerAPI.brand_search(key);
	}

	/**
	 * 18.3品牌详情接口
	 *
	 * @param id 品牌ID
	 * @return isSuc:true/false
	 */
	public Observable<String> getBrandDetail(String id) {
		return mServerAPI.brand_detail(id);
	}

	/**
	 * 18.4专题接口
	 *
	 * @param arg {"pagesize":10,"maxdate":"0"}
	 * @return isSuc:true/false
	 */
	public Observable<String> getProductTopicList(String arg) {
		return mServerAPI.producttopic_list(arg);
	}

	/**
	 * 18.5专题详情接口
	 *
	 * @param id id
	 * @return isSuc:true/false
	 */
	public Observable<String> getProductTopicDetail(String id) {
		return mServerAPI.producttopic_detail(id);
	}

	/**
	 * 18.6新品列表接口
	 *
	 * @param query {“query”:{“pageSize”:10,”maxdate”:”0”,brandId:10}}
	 *              <p/>
	 *              brandId:传0则为新品列表，传品牌编号则是通过品牌编号来获取商品列表
	 * @return isSuc:true/false
	 */
	public Observable<String> getProductList(String query) {
		return mServerAPI.product_list(query);
	}

	/**
	 * 18.7单品搜索列表接口
	 *
	 * @param arg { “pagesize”:10,”maxdate”:”0”, ”productname”:””}}
	 *            <p/>
	 *            brandId:传0则为新品列表，传品牌编号则是通过品牌编号来获取商品列表
	 * @return isSuc:true/false
	 */
	public Observable<String> searchProduct(String arg) {
		return mServerAPI.product_search(arg);
	}

	public Observable<String> searchProductWithToken(String arg) {
		return mWithToken.product_search(arg);
	}

	/**
	 * 18.8新品详情接口
	 *
	 * @param productId {“productId”:222}
	 *                  <p/>
	 *                  productId:新品主键
	 * @return isSuc:true/false
	 */
	public Observable<String> getProductDetail(String productId) {
		return mServerAPI.product_detail(productId);
	}

	public Observable<String> getProductDetailWithLogin(String productId) {
		return mWithToken.product_detail(productId);
	}


	/**
	 * 18.9品类列表接口
	 *
	 * @return isSuc:true/false
	 */
	public Observable<String> getCategoryList() {
		return mServerAPI.category_list();
	}

	/**
	 * 18.10通过品类获取单品列表接口
	 *
	 * @param arg { “pagesize”:10,”maxdate”:”0”, ”categoryid”:1}}
	 * @return isSuc:true/false
	 */
	public Observable<String> getProductsByCategoryId(String arg) {
		return mServerAPI.product_listbycategoryid(arg);
	}

	public Observable<String> getProductsByCategoryIdWithToken(String arg) {
		return mWithToken.product_listbycategoryid(arg);
	}

	/**
	 * 19.1关注接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param follow {"follow":{"FollowUserId":30006}}
	 *               <p/>
	 *               FollowUserId:被关注者UserId
	 * @return isSuc:true/false
	 */
	public Observable<String> addFollow(String follow) {
		return mWithToken.follow_add(follow);
	}

	/**
	 * 19.2关注者列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {\"pageindex\":1, \"pagesize\":20}
	 *            <p/>
	 *            pageindex:页数
	 *            pagesize:分页数
	 * @return isSuc:true/false
	 */
	public Observable<String> getFollowList(String arg) {
		return mWithToken.follow_list(arg);
	}

	/**
	 * 19.3被关注者列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {\"pageindex\":1, \"pagesize\":20}
	 *            <p/>
	 *            pageindex:页数
	 *            pagesize:分页数
	 * @return isSuc:true/false
	 */
	public Observable<String> getFollowedList(String arg) {
		return mWithToken.follow_followedlist(arg);
	}

	/**
	 * 19.4通过手机列表获取用户列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pageindex":1,"pagesize":10,"phonenums":["13980565631","13980565632"]}
	 *            <p/>
	 *            pageindex:页数
	 *            pagesize:分页数
	 * @return isSuc:true/false
	 */
	public Observable<String> getPhoneUserList(String arg) {
		return mWithToken.follow_phoneuserlist(arg);
	}

	/**
	 * 19.5取消关注接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param followUserId {"followUserId":30006}
	 * @return isSuc:true/false
	 */
	public Observable<String> deleteFollow(String followUserId) {
		return mWithToken.follow_delete(followUserId);
	}

	/**
	 * 20.1发现接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"maxdate":”0”, "pagesize":10}
	 * @return isSuc:true/false
	 */
	public Observable<String> getCommunityFind(String arg) {
		return mWithToken.community_find(arg);
	}

	/**
	 * 20.2通过标签查询贴子列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pageindex":1, "pagesize":10, "tagname":"美女"}
	 * @return isSuc:true/false
	 */
	public Observable<String> getCommunityByTag(String arg) {
		return mWithToken.community_listbytag(arg);
	}

	/**
	 * 20.3精选接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pageindex":1, "pagesize":10}
	 *            <p/>
	 *            pageindex进行分页
	 * @return isSuc:true/false
	 */
	public Observable<String> getCommunityRec(String arg) {
		return mServerAPI.community_rec(arg);
	}

	/**
	 * 20.4圈子列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"publishdate":"0", "pagesize":10}
	 *            <p/>
	 *            Publishdate：最新的传 0, 其他时候传返回的 PublishDate
	 * @return isSuc:true/false
	 */
	public Observable<String> getCommunityCircleArticle2(String arg) {
		return mWithToken.community_circlearticle2(arg);
	}

	/**
	 * 20.5用户搜索接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pageindex":1, "pagesize":10, "key":""}
	 *            <p/>
	 *            pageindex进行分页
	 * @return isSuc:true/false
	 */
	public Observable<String> searchCommunity(String arg) {
		return mWithToken.community_search(arg);
	}

	/**
	 * 20.6用户、文章 搜索接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param key {"key":""}
	 * @return isSuc:true/false
	 */
	public Observable<String> search4UserOrArticle(String key) {
		return mWithToken.community_search4userorarticle(key);
	}

	/**
	 * 20.7搜索帖子接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pageindex":1, "pagesize":10, "key":""}
	 *            pageindex进行分页
	 * @return isSuc:true/false
	 */
	public Observable<String> searchCommunityTopic(String arg) {
		return mWithToken.community_searchtopic(arg);
	}

	public Observable<String> searchCommunityTag(String arg) {
		return mWithToken.community_searchtag(arg);
	}


	/**
	 * 21.1热门话题列表接口  用于用户发帖的时候选择热门话题列表
	 * <p/>
	 * Header中带上token
	 *
	 * @return isSuc:true/false
	 */
	public Observable<String> getHotTopicList() {
		return mWithToken.topic_hotlist();
	}

	/**
	 * 21.2搜索话题列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param key {"key":""}
	 * @return isSuc:true/false
	 */
	public Observable<String> searchTopic(String key) {
		return mWithToken.topic_search(key);
	}

	/**
	 * 21.3话题For Banner推荐列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @return isSuc:true/false
	 */
	public Observable<String> getTopicBannerList(String token) {
		return mWithToken.topic_list4banner();
	}

	/**
	 * 21.4话题下的所有帖子接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"maxdate":”0”, "pagesize":10, "topicid":10}
	 * @return isSuc:true/false
	 */
	public Observable<String> getCommunityTopicList(String arg) {
		return mWithToken.community_list4topic(arg);
	}

	/**
	 * 21.5话题下的热门帖子接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pageindex":1, "pagesize":10, "topicid":10}
	 *            通过pageindex进行分页
	 * @return isSuc:true/false
	 */
	public Observable<String> getCommunityUpcountTopicList(String arg) {
		return mWithToken.community_list4topicbyupcount(arg);
	}


	/**
	 * 21.6关注话题接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param topicid {"topicid":10}
	 * @return isSuc:true/false
	 */
	public Observable<String> addTopicFollow(String topicid) {
		return mWithToken.topicfollow_add(topicid);
	}

	/**
	 * 21.7话题取消关注接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param topicid {"topicid":10}
	 * @return isSuc:true/false
	 */
	public Observable<String> deleteTopicFollow(String topicid) {
		return mWithToken.topicfollow_delete(topicid);
	}

	/**
	 * 21.8我的话题列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pageindex":1, "pagesize":10}
	 * @return isSuc:true/false
	 */
	public Observable<String> getTopicFollowList(String arg) {
		return mWithToken.topicfollow_list(arg);
	}


	/**
	 * 21.9话题详情接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"topicid":10}
	 * @return isSuc:true/false
	 */
	public Observable<String> getTopicDetail(String arg) {
		return mWithToken.topic_detail(arg);
	}

	/**
	 * 22.1我的标签接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pageindex": 1,"pagesize": 10,"userid": 10}
	 * @return isSuc:true/false
	 */
	public Observable<String> getTaglistByUserid(String arg) {
		return mWithToken.tag_taglistbyuserid(arg);
	}

	/**
	 * 22.2通过标签查询我的贴子列表接口
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pageindex":1, "pagesize":10, "tagname":"美女","userid": 10}
	 *            <p/>
	 *            pageindex进行分页
	 * @return isSuc:true/false
	 */
	public Observable<String> getCommunityListByTagAndUserid(String arg) {
		return mWithToken.community_listbytagbyuserid(arg);
	}

	public Observable<String> checkVersion() {
		return mServerAPI.appversion();
	}

	public Observable<ResponseBody> downLoadFile(String baseUrl, String apkUrl, DownloadProgressInterceptor.DownloadProgressListener listener) {
		return downloadAPI(baseUrl, listener).create(ServerAPI.class).download(apkUrl);
	}

	//------------------------------------ 2.0版新加接口 --------------------------------


	/**
	 * 9.1通过频道获取列表接口
	 *
	 * @param arg {\"pageindex\":1,\"pagesize\":40}
	 *
	 * @return isSuc:true/false
	 */
	public Observable<String> getArticleRecList(String arg) {
		return mServerAPI.article_reclist(arg);
	}

	public Observable<String> getShaiActivtys(String arg) {
		return mServerAPI.topic_list(arg);
	}

	public Observable<String> getTriProducts() {
		return mWithToken.trialproduct_list();
	}

	public Observable<String> getSpecials(String arg) {
		return mServerAPI.productspecial_list(arg);
	}

	public Observable<String> getSpecialDetail(String arg) {
		return mServerAPI.productspecial_detail(arg);
	}

	public Observable<String> getMyCommunityFaved(String arg) {
		return mWithToken.mycommunity_faved(arg);
	}

	public Observable<String> getUserType() {
		return mWithToken.usertype_list();
	}

	public Observable<String> getUserTypeUser(String arg) {
		return mWithToken.usertype_userlist(arg);
	}

	public Observable<String> tag_hotlist4photo() {
		return mWithToken.tag_hotlist4photo();
	}

	public Observable<String> stickertype_list() {
		return mWithToken.stickertype_list();
	}

	public Observable<String> stickerList(String arg) {
		return mWithToken.sticker_list(arg);
	}

	public Observable<String> filter_list() {
		return mWithToken.filter_list();
	}


	public Observable<String> pointproduct_list(String arg) {
		return mWithToken.pointshop_list(arg);
	}

	public Observable<String> userpointslog_list(String arg) {
		return mWithToken.userpointslog_list(arg);
	}

	public Observable<String> pointshop_exchangelist(String arg) {
		return mWithToken.pointshop_exchangelist(arg);
	}

	public Observable<String> pointshop_add(String arg) {
		return mWithToken.pointshop_add(arg);
	}


}
