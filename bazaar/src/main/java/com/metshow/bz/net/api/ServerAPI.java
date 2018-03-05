package com.metshow.bz.net.api;


import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 服务器 接口
 * Created by Mr.Kwan on 2016-6-28.
 */
public interface ServerAPI {

//	String HTTP_SERVER = "http://bz.metshow.com/api/";
//	String HTTP_TEST_SERVER = "http://bz.metshow.com:8080/api/";
//	String HTTP_SERVER_UPLOAD = "http://bznewitem2.metshow.com/";

	String HTTP_USER = "user/";
	String HTTP_FEEDBACK = "feedback/";
	String HTTP_PIC = "pic/";
	String HTTP_TAG = "tag/";
	String HTTP_ARTICLE = "article/";
	String HTTP_COMMENT = "comment/";
	String HTTP_COMMENTACTION = "commentaction/";
	String HTTP_VOTE = "vote/";
	String HTTP_ACTION = "action/";
	String HTTP_NOTICE = "notice/";
	String HTTP_MESSAGE = "message/";
	String HTTP_BRAND = "brand/";
	String HTTP_PRODUCTTOPIC = "producttopic/";
	String HTTP_PRODUCT = "product/";
	String HTTP_FOLLOW = "follow/";
	String HTTP_COMMUNITY = "community/";
	String HTTP_TOPIC = "Topic/";
	String HTTP_TOPICFOLLOW = "topicfollow/";

	/**
	 * 001001 获取地址
	 * serverconfig/new
	 */
	@GET(HTTP_SERVERCONFIG_NEW)
	Observable<String> serverconfig_new();

	String HTTP_SERVERCONFIG_NEW = "serverconfig/new";
	int SERVERCONFIG_NEW_VOCATIONAL_ID = 0x001001;


	/**
	 * 001002	启动屏接口
	 *
	 * @param user {"date":"0"}
	 *             date默认传0，其他是否传返回的UsingDate的时间戳
	 */
	@POST(HTTP_SPLASH_LIST)
	Observable<String> splash_list(@Body String user);

	String HTTP_SPLASH_LIST = "splash/list";
	int SPLASH_LIST_VOCATIONAL_ID = 0x001002;

	/**
	 * 1002 背景图片接口
	 *
	 * @return isSuc:true处理  false时不用更新
	 * Bg:背景图片，如果判断Bg跟本地的不相同，则更新
	 */
	@GET(HTTP_CONFIG_DETAIL)
	Observable<String> config_detail();

	String HTTP_CONFIG_DETAIL = "config/detail";
	int CONFIG_DETAIL_VOCATIONAL_ID = 1002;

	/**
	 * 1003 邮件发送验证码接口
	 *
	 * @param email 邮件地址 {email:""}
	 * @return isSuc:true处理  false时不用更新
	 * result：为返回的验证码
	 */
	@POST(HTTP_USER_EMAILCODE)
	Observable<String> user_emailcode(@Body String email);

	String HTTP_USER_EMAILCODE = HTTP_USER + "emailcode";
	int USER_EMAILCODE_VOCATIONAL_ID = 1003;

	/**
	 * 1004 找回密码检测帐号状态接口
	 *
	 * @param account 账号 { account:""}
	 * @return isSuc:true处理  false时，返回帐号不存在
	 * result：为返回的绑定手机号，如果为空，则没有绑定
	 */
	@POST(HTTP_USER_CHECKINFO)
	Observable<String> user_checkinfo(@Body String account);

	String HTTP_USER_CHECKINFO = HTTP_USER + "checkinfo";
	int USER_CHECKINFO_VOCATIONAL_ID = 1004;

	/**
	 * 1005 获取手机验证码
	 *
	 * @param phonenum 手机号码 {“phonenum”:”13980565638”}
	 * @return Result的code为手机验证码
	 */
	@POST(HTTP_USER_PHONECODE)
	Observable<String> user_phonecode(@Body String phonenum);

	String HTTP_USER_PHONECODE = HTTP_USER + "phonecode4and";
	int USER_PHONECODE_VOCATIONAL_ID = 1005;

	/**
	 * 1005 获取手机验证码
	 *
	 * @param phonenum 手机号码 {“phonenum”:”13980565638”}
	 * @return Result的code为手机验证码
	 */
	@POST(HTTP_USER_validatecode)
	Observable<String> user_validatecode(@Body String phonenum);

	String HTTP_USER_validatecode = HTTP_USER + "";
	int USER_validatecode_VOCATIONAL_ID = 1009;


	@GET(HTTP_USER_validatecode_img + "{PHONENUM}")
	Observable<byte[]> user_validatecode_img(@Path("PHONENUM") String PHONENUM);

	String HTTP_USER_validatecode_img = HTTP_USER + "validatecode4img/";
	int USER_validatecode_img_VOCATIONAL_ID = 1008;




	/**
	 * 1006 获取国际手机验证码
	 *
	 * @param phonenum 手机号码 {"phonenum":"8613980565638"}
	 * @return Result的code为手机验证码
	 */
	@POST(HTTP_USER_PHONECODE4INTERNAL)
	Observable<String> user_phonecode4internal(@Body String phonenum);

	String HTTP_USER_PHONECODE4INTERNAL = HTTP_USER + "phonecode4internal";
	int USER_PHONECODE4INTERNAL_VOCATIONAL_ID = 1006;

	// 20xx 用户反馈接口

	/**
	 * 2001 用户反馈添加接口
	 *
	 * @param feedback 反馈信息 {\"feedback\":{\"Images\":\"images1\",\"Content\":\"758\"}}
	 * @return isSuc:true/false
	 */
	@POST(HTTP_FEEDBACK_ADD)
	Observable<String> feedback_add(@Body String feedback);

	String HTTP_FEEDBACK_ADD = HTTP_FEEDBACK + "add";
	int FEEDBACK_ADD_VOCATIONAL_ID = 2001;

	/**
	 * 2002 用户反馈列表接口
	 *
	 * @param args 参数 {\"pagesize\":10,\"createdate\":\"0\"}
	 * @return Result的code为手机验证码
	 */
	@POST(HTTP_FEEDBACK_LIST)
	Observable<String> feedback_list(@Body String args);

	String HTTP_FEEDBACK_LIST = HTTP_FEEDBACK + "list";
	int FEEDBACK_LIST_VOCATIONAL_ID = 2002;

	/**
	 * 2003 用户反馈点击支持接口
	 *
	 * @param feedbackid 参数 {"feedbackid":1}
	 * @return
	 */
	@POST(HTTP_FEEDBACK_ACTION_ADD)
	Observable<String> feedback_action_add(@Body String feedbackid);

	String HTTP_FEEDBACK_ACTION_ADD = HTTP_FEEDBACK + "action/add";
	int FEEDBACK_ACTION_ADD_VOCATIONAL_ID = 2003;

	/**
	 * 2004 用户反馈点击支持接口
	 *
	 * @return
	 */
	@POST(HTTP_FEEDBACK_DETAIL)
	Observable<String> feedback_detail();

	String HTTP_FEEDBACK_DETAIL = HTTP_FEEDBACK + "detail";
	int FEEDBACK_DETAIL_VOCATIONAL_ID = 2004;


	//30xx 用户模块接口

	/**
	 * 3001 三方社交账号登录信息接口
	 *
	 * @param socialuser 参数 {"socialuser":{"Platform":"1/2/3/4/5","OpenId":"OpenId",
	 *                   "Token":"Access_Token","Avatar":"Avatar",
	 *                   "AppId":"AppId","NickName":"NickName"}}
	 *                   <p/>
	 *                   Platform: 社交账号平台/1/2/3/4 【必须】
	 *                   sinaweibo 1
	 *                   tencentWeibo 2
	 *                   QZone 3
	 *                   wechat 4
	 *                   wechatmoments 5
	 *                   wechatFavorite 6
	 *                   QQ 7
	 *                   OpenId: 社交账号OpenId或者UID 【必须】
	 *                   Token: 社交账号登陆后的Token 【必须】-注意非本系统Token
	 *                   Avatar:头像 【必须】
	 *                   AppId: 应用ID,第三方社交账号申请的APPID
	 *                   NickName:昵称 【必须】
	 * @return
	 */
	@POST(HTTP_USER_SOCIAL_LOGIN)
	Observable<String> user_social_login(@Body String socialuser);

	String HTTP_USER_SOCIAL_LOGIN = HTTP_USER + "social/login";
	int USER_SOCIAL_LOGIN_VOCATIONAL_ID = 3001;

	/**
	 * 3002 上传头像并保存接口
	 *
	 * @return {error:0,url:imageurl}
	 * 出错时 error：1  成功返回0
	 */
	@Multipart
	@POST(HTTP_PIC_UPLOADAVATAR)
	Observable<String> pic_uploadavatar(@Part MultipartBody.Part file);

	String HTTP_PIC_UPLOADAVATAR = HTTP_PIC + "uploadavatar.ashx";
	int PIC_UPLOADAVATAR_VOCATIONAL_ID = 3002;

	/**
	 * 3003 用户上传图片接口
	 *
	 * @return {error:0,url:imageurl}
	 * 出错时 error：1  成功返回0
	 */
	@Multipart
	@POST(HTTP_PIC_UPLOADIMAGE)
	Observable<String> pic_uploadimage(@Part MultipartBody.Part file);

	@Multipart
	@POST(HTTP_PIC_UPLOADIMAGE)
	Observable<String> pic_uploadimage2(@Part() List<MultipartBody.Part> parts);

	String HTTP_PIC_UPLOADIMAGE = HTTP_PIC + "uploadimage.ashx";
	int PIC_UPLOADIMAGE_VOCATIONAL_ID = 3003;

	/**
	 * 3004 三方社交账号登录信息接口
	 *
	 * @param userInfo {"userInfo":{"PhoneNum":"手机号","Sex":1, "NickName":"昵称",
	 *                 "TrueName":"真实姓名", "Description":"介绍", "Address":"地址",
	 *                 "Password":"123456", "Birthday":"2014-04-02", "OldPassword":"aaaaa"}}
	 *                 <p/>
	 *                 Sex 1男2女
	 *                 修改某项，只需要传某项，其他项为空。
	 * @return 1.请求改接口，需要登陆,发送http协议header中需增加token
	 * 2.修改成功返回token,登录成功后每次访问在Http头部添加 token
	 */
	@POST(HTTP_USER_UPDATE)
	Observable<String> user_update(@Body String userInfo);

	String HTTP_USER_UPDATE = HTTP_USER + "update";
	int USER_UPDATE_VOCATIONAL_ID = 3004;

	/**
	 * 3005 获取某人的部分基本信息
	 *
	 * @param USERID 用户id
	 * @return 1.请求改接口，需要登陆,发送http协议header中需增加token
	 * 2.修改成功返回token,登录成功后每次访问在Http头部添加 token
	 */
	@GET(HTTP_USER_USERINFO + "{USERID}")
	Observable<String> user_userinfo(@Path("USERID") String USERID);

	String HTTP_USER_USERINFO = HTTP_USER + "userinfo/";
	int USER_USERINFO_VOCATIONAL_ID = 3005;

	/**
	 * 3006 手机注册接口
	 *
	 * @param arg 参数 {"phonenum":"13980565638","password":"123456"}
	 * @return 注册成功默认是登录状态返回token, 登录成功后每次访问在Http头部添加 token
	 */
	@POST(HTTP_USER_REG)
	Observable<String> user_reg(@Body String arg);

	String HTTP_USER_REG = HTTP_USER + "reg";
	int USER_REG_VOCATIONAL_ID = 3006;

	/**
	 * 3007 手机登陆接口
	 *
	 * @param arg 参数 {"phonenum":"13980565638","password":"123456"}
	 * @return 注册成功默认是登录状态返回token, 登录成功后每次访问在Http头部添加 token
	 */
	@POST(HTTP_USER_LOGIN)
	Observable<String> user_login(@Body String arg);

	String HTTP_USER_LOGIN = HTTP_USER + "login";
	int USER_LOGIN_VOCATIONAL_ID = 3007;

	/**
	 * 3008 找回密码接口
	 *
	 * @param arg 参数 {"phonenum":"13980565638","password":"123456"}
	 * @return 注册成功默认是登录状态返回token, 登录成功后每次访问在Http头部添加 token
	 */
	@POST(HTTP_USER_FINDPASS)
	Observable<String> user_findpass(@Body String arg);

	String HTTP_USER_FINDPASS = HTTP_USER + "findpass";
	int USER_FINDPASS_VOCATIONAL_ID = 3008;

	/**
	 * 3009 获取编辑的基本信息
	 *
	 * @param arg 参数 {“adminid”:11}
	 * @return
	 */
	@POST(HTTP_USER_ADMININFO)
	Observable<String> user_admininfo(@Body String arg);

	String HTTP_USER_ADMININFO = HTTP_USER + "admininfo";
	int USER_ADMININFO_VOCATIONAL_ID = 3009;

	/**
	 * 3010 推荐vip列表
	 */
	@POST(HTTP_USER_ADMINLIST)
	Observable<String> user_adminlist();

	String HTTP_USER_ADMINLIST = HTTP_USER + "adminlist";
	int USER_ADMINLIST_VOCATIONAL_ID = 3010;

	/**
	 * 3011 所有vip用户列表
	 *
	 * @param arg 参数 {pageindex:1,pagesize:10}
	 * @return
	 */
	@POST(HTTP_USER_VIPLIST)
	Observable<String> user_viplist(@Body String arg);

	String HTTP_USER_VIPLIST = HTTP_USER + "viplist";
	int USER_VIPLIST_VOCATIONAL_ID = 3011;

	/**
	 * 3012 搜索vip用户列表
	 *
	 * @param arg 参数 {pageindex:1,pagesize:10,key:""}
	 * @return
	 */
	@POST(HTTP_USER_SEARCHVIPLIST)
	Observable<String> user_searchviplist(@Body String arg);

	String HTTP_USER_SEARCHVIPLIST = HTTP_USER + "searchviplist";
	int USER_SEARCHVIPLIST_VOCATIONAL_ID = 3012;

	/**
	 * 3013 设定用户是否接收好友动态
	 *
	 * @param state { state:1}
	 *              state:1为拒绝
	 * @return
	 */
	@POST(HTTP_USER_SETMESSAGESTATE)
	Observable<String> user_setmessagestate(@Body String state);

	String HTTP_USER_SETMESSAGESTATE = HTTP_USER + "setmessagestate";
	int USER_SETMESSAGESTATE_VOCATIONAL_ID = 3013;


	//40xx 广告接口


	/**
	 * 4001 banner 广告接口
	 *
	 * @param arg 参数 {“channelType”:20}
	 * @return
	 */
	@POST(HTTP_RECOMMEND_LIST)
	Observable<String> recommend_list(@Body String arg);

	String HTTP_RECOMMEND_LIST = "recommend/list";
	int RECOMMEND_LIST_VOCATIONAL_ID = 4001;

	/**
	 * 4003 banner 首页推荐位列表接口
	 *
	 * @return
	 */
	@POST(HTTP_ADFORREC_LIST)
	Observable<String> adforrec_list();

	String HTTP_ADFORREC_LIST = "adforrec/list";
	int ADFORREC_LIST_VOCATIONAL_ID = 4003;


	//50xx 日签接口

	/**
	 * 5002 日签新接口2016-6-3改
	 *
	 * @return
	 */
	@GET(HTTP_DAYSIGN_LIST)
	Observable<String> daysign_list();

	String HTTP_DAYSIGN_LIST = "daysign/list";
	int DAYSIGN_LIST_VOCATIONAL_ID = 5002;

	/**
	 * 5003 日签提交投票或者调查表接口
	 *
	 * @param arg 参数{\"daysignId\":1, \"votelist\":[{\"VoteId\"：1, \"VoteDetailId\":1},
	 *            {\"VoteId\"：1, \"VoteDetailId\":2}]}
	 * @return
	 */
	@POST(HTTP_DAYSIGN_ADD)
	Observable<String> daysign_add(@Body String arg);

	String HTTP_DAYSIGN_ADD = "daysign/add";
	int DAYSIGN_ADD_VOCATIONAL_ID = 5003;

	// 60XX 推荐标签接口

	/**
	 * 6001 发现栏目 标签分类列表接口
	 *
	 * @return
	 */
	@POST(HTTP_TAG_FINDLIST)
	Observable<String> tag_findlist();

	String HTTP_TAG_FINDLIST = HTTP_TAG + "findlist";
	int TAG_FINDLIST_VOCATIONAL_ID = 6001;

	/**
	 * 6002 发现栏目 标签分类列表接口
	 *
	 * @return
	 */
	@POST(HTTP_TAG_LIST)
	Observable<String> tag_list();

	String HTTP_TAG_LIST = HTTP_TAG + "list";
	int TAG_LIST_VOCATIONAL_ID = 6002;

	/**
	 * 6003 用户关注标签接口
	 *
	 * @param tagids 参数 {\"tagids\":[{ \"TagId\":1},{ \"TagId\":2}]}
	 *               Header中传token
	 * @return
	 */
	@POST(HTTP_TAG_ADD)
	Observable<String> tag_add(@Body String tagids);

	String HTTP_TAG_ADD = HTTP_TAG + "add";
	int TAG_ADD_VOCATIONAL_ID = 6003;


	// 70xx 分类接口

	/**
	 * 7001 7.1分类列表接口
	 *
	 * @return
	 */
	@POST(HTTP_CHANNEL_LIST)
	Observable<String> channel_list();

	String HTTP_CHANNEL_LIST = "channel/list";
	int CHANNEL_LIST_VOCATIONAL_ID = 7001;

	// 80xx 热榜接口

	/**
	 * 8001 热榜接口
	 *
	 * @return
	 */
	@POST(HTTP_DAILY_LIST)
	Observable<String> daily_list();

	String HTTP_DAILY_LIST = "daily/list";
	int DAILY_LIST_VOCATIONAL_ID = 8001;

	// 90xx 文章分类接口

	/**
	 * 9001 通过频道获取列表接口
	 *
	 * @param arg {"channelid":15,"pagesize":10,"maxdate":"0"}
	 *            Channeled:频道编号
	 *            Pagesize:分页数
	 *            Maxdate:最大的时间戳，用于获取更多，传0获取最新的
	 * @return
	 */
	@POST(HTTP_ARTICLE_LIST)
	Observable<String> article_list(@Body String arg);

	String HTTP_ARTICLE_LIST = HTTP_ARTICLE + "list";
	int ARTICLE_LIST_VOCATIONAL_ID = 9001;

	/**
	 * 9002 文章详情接口
	 *
	 * @param arg {\"articleid\":1}
	 *            Articleid:文字编号
	 * @return
	 */

	@POST(HTTP_ARTICLE_DETAIL)
	Observable<String> article_detail(@Body String arg);

	//  String HTTP_ARTICLE_DETAIL = HTTP_ARTICLE + "detail";
	String HTTP_ARTICLE_DETAIL = HTTP_ARTICLE + "detail4android";
	int ARTICLE_DETAIL_VOCATIONAL_ID = 9002;

	/**
	 * 9003 文章搜索接口
	 *
	 * @param arg{\"key\":\"美女\",\"pagesize\":7,\"maxdate\":\"0\"} Key:关键字
	 * @return
	 */

	@POST(HTTP_ARTICLE_SEARCH)
	Observable<String> article_search(@Body String arg);

	String HTTP_ARTICLE_SEARCH = HTTP_ARTICLE + "search";
	int ARTICLE_SEARCH_VOCATIONAL_ID = 9003;


	//10XXX评论

	/**
	 * 10001 评论添加
	 *
	 * @param comment {"comment":{"Content":"content","Image":"image1,image2",
	 *                "RefId":442,"Type":1,"ParentId":0}}
	 *                <p/>
	 *                Type:1文章 2为商品 3为社区 4为专题
	 *                Image:多图，已逗号分隔
	 *                ParentId:一级为0 二级为对应的父级commentid
	 *                <p/>
	 *                Header中带上token
	 * @return
	 */

	@POST(HTTP_COMMENT_ADD)
	Observable<String> comment_add(@Body String comment);

	String HTTP_COMMENT_ADD = HTTP_COMMENT + "add";
	int COMMENT_ADD_VOCATIONAL_ID = 10001;

	/**
	 * 10002 评论列表
	 *
	 * @param arg {"type":1,"refid":442,"pagesize":10,"maxdate":"0"}
	 *            <p/>
	 *            Type:1为文章 2为商品 3为社区 4为专题 5为活动（话题）6为商品专辑
	 *            Refid：对应的id号
	 *            <p/>
	 *            Header中带上token
	 * @return
	 */

	@POST(HTTP_COMMENT_LIST)
	Observable<String> comment_list(@Body String arg);

	String HTTP_COMMENT_LIST = HTTP_COMMENT + "list";
	int COMMENT_LIST_VOCATIONAL_ID = 10002;

	/**
	 * 10003 我的评论列表
	 * <p/>
	 * Header中带上token
	 *
	 * @param arg {"pagesize":10,"maxdate":"0"}
	 * @return
	 */

	@POST(HTTP_COMMENT_MYLIST)
	Observable<String> comment_mylist(@Body String arg);

	String HTTP_COMMENT_MYLIST = HTTP_COMMENT + "mylist";
	int COMMENT_MYLIST_VOCATIONAL_ID = 10003;

	/**
	 * 10004 回复我的评论列表
	 *
	 * @param arg {"pagesize":10,"maxdate":"0"}
	 *            <p/>
	 *            Header中带上token
	 * @return
	 */

	@POST(HTTP_COMMENT_REPLYLIST)
	Observable<String> comment_replylist(@Body String arg);

	String HTTP_COMMENT_REPLYLIST = HTTP_COMMENT + "replylist";
	int COMMENT_REPLYLIST_VOCATIONAL_ID = 10004;

	/**
	 * 10005 评论点赞
	 *
	 * @param action {"action":{"CommentId":1}}
	 *               <p/>
	 *               Header中带上token
	 * @return
	 */

	@POST(HTTP_COMMENTACTION_ADD)
	Observable<String> commentaction_add(@Body String action);

	String HTTP_COMMENTACTION_ADD = HTTP_COMMENTACTION + "add";
	int COMMENTACTION_ADD_VOCATIONAL_ID = 10005;

	/**
	 * 10006 评论举报
	 *
	 * @param action {"action":{"CommentId":1}}
	 *               <p/>
	 *               Header中带上token
	 * @return
	 */

	@POST(HTTP_COMMENTACTION_POLICE)
	Observable<String> commentaction_police(@Body String action);

	String HTTP_COMMENTACTION_POLICE = HTTP_COMMENTACTION + "police";
	int COMMENTACTION_POLICE_VOCATIONAL_ID = 10006;


	/**
	 * 10007 评论举报
	 *
	 * @param commentId {"commentId":1}}
	 *                  <p/>
	 *                  Header中带上token
	 * @return
	 */

	@POST(HTTP_COMMENT_DELETE)
	Observable<String> commentaction_delete(@Body String commentId);

	String HTTP_COMMENT_DELETE = HTTP_COMMENT + "delete";
	int COMMENT_DELETE_VOCATIONAL_ID = 10007;

	//11xx 投票接口

	/**
	 * 11001 文章列表投票接口
	 *
	 * @param arg {"voteId":1,"voteDetailId":1}
	 *            <p/>
	 *            Header中带上token
	 * @return
	 */

	@POST(HTTP_VOTE_ADD)
	Observable<String> vote_add(@Body String arg);

	String HTTP_VOTE_ADD = HTTP_VOTE + "add";
	int VOTE_ADD_VOCATIONAL_ID = 11001;


	//12XX收藏/点赞/举报/屏蔽

	/**
	 * 12001 收藏/点赞/举报/屏蔽接口
	 *
	 * @param arg {"type":1,"acttype":1,"refid":758}
	 *            <p/>
	 *            type:1文章 2为商品 3为社区圈子
	 *            acttype:1为收藏 2为点赞 3为举报 4为屏蔽
	 *            refid：关联的id
	 *            Header中带上token
	 * @return
	 */

	@POST(HTTP_ACTION_ADD)
	Observable<String> action_add(@Body String arg);

	String HTTP_ACTION_ADD = HTTP_ACTION + "add";
	int ACTION_ADD_VOCATIONAL_ID = 12001;


	/**
	 * 12002 取消收藏接口
	 *
	 * @param arg {type:1,refid:2}
	 *            <p/>
	 *            type:1为文章 2为商品 3为社区圈子
	 *            refid：关联的id
	 *            Header中带上token
	 * @return
	 */

	@POST(HTTP_ACTION_DELETEFAV)
	Observable<String> action_deletefav(@Body String arg);

	String HTTP_ACTION_DELETEFAV = HTTP_ACTION + "deletefav";
	int ACTION_DELETEFAV_VOCATIONAL_ID = 12002;

	/**
	 * 12003 取消点赞接口
	 *
	 * @param arg {"type":1,"acttype":1,"refid":758}
	 *            <p/>
	 *            type:1为文章 2为商品 3为社区圈子
	 *            refid：关联的id
	 *            Header中带上token
	 * @return
	 */

	@POST(HTTP_ACTION_DELETEUP)
	Observable<String> action_deleteup(@Body String arg);

	String HTTP_ACTION_DELETEUP = HTTP_ACTION + "deleteup";
	int ACTION_DELETEUP_VOCATIONAL_ID = 12003;

	/**
	 * 12004 我的资讯收藏列表接口
	 *
	 * @param arg {"pagesize":10,"createdate":"0"}
	 *            <p/>
	 *            type:1文章 2为商品 3为社区圈子
	 *            acttype:1为收藏 2为点赞 3为举报 4为屏蔽
	 *            refid：关联的id
	 *            Header中带上token
	 * @return
	 */

	@POST(HTTP_ACTION_MYARTICLEFAV)
	Observable<String> action_myarticlefav(@Body String arg);

	String HTTP_ACTION_MYARTICLEFAV = HTTP_ACTION + "myarticlefav";
	int ACTION_MYARTICLEFAV_VOCATIONAL_ID = 12004;

	/**
	 * 12005 我的欲望清单列表接口
	 *
	 * @param arg {"pagesize":10,"createdate":"0"}
	 *            createdate：默认为0获取最新的，列表中最小值，
	 *            获取更多 使用返回的ActionDate字段
	 * @return
	 */

	@POST(HTTP_ACTION_MYPRODUCTFAV)
	Observable<String> action_myproductfav(@Body String arg);

	String HTTP_ACTION_MYPRODUCTFAV = HTTP_ACTION + "myproductfav";
	int ACTION_MYPRODUCTFAV_VOCATIONAL_ID = 12005;

	/**
	 * 12006 我的收藏社区列表接口
	 *
	 * @param arg {"pagesize":10,"createdate":"0"}
	 *            createdate：默认为0获取最新的，列表中最小值，获取更多
	 *            <p/>
	 *            Header中带上token
	 * @return
	 */

	@POST(HTTP_ACTION_MYCOMMUNITYFAV)
	Observable<String> action_mycommunityfav(@Body String arg);

	String HTTP_ACTION_MYCOMMUNITYFAV = HTTP_ACTION + "mycommunityfav";
	int ACTION_MYCOMMUNITYFAV_VOCATIONAL_ID = 12006;


	// 13xx 分享积分请求接口

	/**
	 * 13001 分享积分接口
	 * Header中带token
	 *
	 * @return
	 */

	@POST(HTTP_POINTS_SHARE_ADD)
	Observable<String> points_share_add();

	String HTTP_POINTS_SHARE_ADD = "points/share/add";
	int POINTS_SHARE_ADD_VOCATIONAL_ID = 13001;


	// 14xx 消息接口

	/**
	 * 14001 我的消息接口
	 *
	 * @param arg {"pagesize":7,"createdate":"0"}
	 *            <p/>
	 *            Header中带上token
	 * @return
	 */

	@POST(HTTP_NOTICE_LIST)
	Observable<String> notice_list(@Body String arg);

	String HTTP_NOTICE_LIST = HTTP_NOTICE + "list";
	int NOTICE_LIST_VOCATIONAL_ID = 14001;

	/**
	 * 14002 是否有新的系统消息接口
	 *
	 * @param arg {\"createdate\":\"0\"}
	 *            如果本地无消息最后的时间，则传0，其他传本地保存的消息的最大时间
	 *            <p/>
	 *            Header中带上token
	 * @return Result:false为无新消息，true为有新消息
	 */

	@POST(HTTP_NOTICE_NEW_SYSTEM)
	Observable<String> notic_new_system(@Body String arg);

	String HTTP_NOTICE_NEW_SYSTEM = HTTP_NOTICE + "new/system";
	int NOTICE_NEW_SYSTEM_VOCATIONAL_ID = 14002;

	/**
	 * 14003 是否有新的用户消息接口
	 *
	 * @param arg {"createdate":"0"}
	 *            如果本地无消息最后的时间，则传0，其他传本地保存的消息的最大时间
	 *            Header中带上token
	 * @return
	 */

	@POST(HTTP_NOTICE_NEW_USER)
	Observable<String> notice_new_user(@Body String arg);

	String HTTP_NOTICE_NEW_USER = HTTP_NOTICE + "new/user";
	int NOTICE_NEW_USER_VOCATIONAL_ID = 14003;

	/**
	 * 14004 消息数量整合新粉丝，未读消息，新评论接口
	 *
	 * @param arg {"followeddate":"0","commentdate":"0"}
	 *            <p/>
	 *            followeddate：获取粉丝接口的时候，获取servertime进行处理保存
	 *            commentdate：获取回复我的评论的时候，获取servertime进行处理保存
	 *            Header中带上token
	 * @return Result:false/True:
	 */

	@POST(HTTP_MESSAGE_ALLCOUNT)
	Observable<String> message_allcount(@Body String arg);

	String HTTP_MESSAGE_ALLCOUNT = HTTP_MESSAGE + "allcount";
	int MESSAGE_ALLCOUNT_VOCATIONAL_ID = 14004;

	/**
	 * 14005 删除消息
	 *
	 * @param noticeId {"noticeId":7}
	 *                 <p/>
	 *                 Header中带上token
	 * @return Result:false/True:
	 */

	@POST(HTTP_NOTICE_DELETE)
	Observable<String> notice_delete(@Body String noticeId);

	String HTTP_NOTICE_DELETE = HTTP_NOTICE + "delete";
	int NOTICE_DELETE_VOCATIONAL_ID = 14005;


	/**
	 * 15 xx 分享地址接口 </p>
	 * http://bz.metshow.com/api/share.html?articleid=2935
	 */

	String HTTP_SHARE_URL = "http://bz.metshow.com/api/share.html?articleid=";


	//16xx 对话接口

	/**
	 * 16001 写对话接口
	 *
	 * @param message {message:{ ToUserId:174,Content: \"私信内容\",Type:0,Url: \"\"}}
	 *                <p/>
	 *                Type: 0为文字 1为图片 2为语音
	 *                Url:  type 为1 或者 2时，Url为图片/语音地址
	 *                <p/>
	 *                Header中带上token
	 * @return Result:false/True:
	 */

	@POST(HTTP_MESSAGE_ADD)
	Observable<String> message_add(@Body String message);

	String HTTP_MESSAGE_ADD = HTTP_MESSAGE + "add";
	int MESSAGE_ADD_VOCATIONAL_ID = 16001;

	/**
	 * 16002 对话首页列表接口
	 */

	@GET(HTTP_MESSAGE_LIST + "{PAGESIZE}/{MAXDATE}")
	Observable<String> message_list(@Path("PAGESIZE") String PAGESIZE, @Path("MAXDATE") String MAXDATE);

	String HTTP_MESSAGE_LIST = HTTP_MESSAGE + "list/";
	int MESSAGE_LIST_VOCATIONAL_ID = 16002;


	/**
	 * 16003 删除会话列表接口
	 *
	 * @param id id为15.2获取到的Id
	 *           <p/>
	 *           Header中带上token
	 */

	@POST(HTTP_MESSAGE_DELLIST)
	Observable<String> message_dellist(@Body String id);

	String HTTP_MESSAGE_DELLIST = HTTP_MESSAGE + "dellist";
	int MESSAGE_DELLIST_VOCATIONAL_ID = 16003;

	/**
	 * 16004 对话回话详情接口
	 *
	 * @param arg {"pagesize":"10","maxdate":"0","touserid":178}
	 *            <p/>
	 *            Pagesize:分页数
	 *            Maxdate:时间戳，
	 *            Touserid:
	 */

	@POST(HTTP_MESSAGE_DIALOG)
	Observable<String> message_dialog(@Body String arg);

	String HTTP_MESSAGE_DIALOG = HTTP_MESSAGE + "dialog";
	int MESSAGE_DIALOG_VOCATIONAL_ID = 16004;

	/**
	 * 16005 获取比当前时间小的消息列表接口
	 *
	 * @param arg {"pagesize":"10","maxdate":"0","touserid":178}
	 *            <p/>
	 *            Pagesize:分页数
	 *            Maxdate:时间戳，
	 *            Touserid:
	 */

	@POST(HTTP_MESSAGE_DIALOGOLD)
	Observable<String> message_dialogold(@Body String arg);

	String HTTP_MESSAGE_DIALOGOLD = HTTP_MESSAGE + "dialogold";
	int MESSAGE_DIALOGOLD_VOCATIONAL_ID = 16005;

	/**
	 * 16006 是否有未读对话接口
	 */

	@GET(HTTP_MESSAGE_HASNOTREAD)
	Observable<String> message_hasnotread();

	String HTTP_MESSAGE_HASNOTREAD = HTTP_MESSAGE + "hasnotread";
	int MESSAGE_HASNOTREAD_VOCATIONAL_ID = 16006;

	/**
	 * 16007 未读对话条数
	 */

	@GET(HTTP_MESSAGE_NOTREADCOUNT)
	Observable<String> message_notreadcount();

	String HTTP_MESSAGE_NOTREADCOUNT = HTTP_MESSAGE + "notreadcount";
	int MESSAGE_NOTREADCOUNT_VOCATIONAL_ID = 16007;

	//17XX 用户社区发帖接口

	/**
	 * 17001 发起话题接口
	 *
	 * @param arg {"title":"投稿","ico":"ico1","content":"content","tagname":"美女",
	 *            "images":
	 *            "http://www.sd.xinhuanet.com/news/yule/2015-10/08/1116742491_14440348978261n.jpg,
	 *            http://www.sd.xinhuanet.com/news/yule/2015-10/08/1116742491_14440348978261n.jpg"}
	 *            <p/>
	 *            tile:标题
	 *            ico：预览图
	 *            content：内容
	 *            tagname：标签名称
	 *            images：多图以逗号分隔
	 */

	@POST(HTTP_ARTICLE_TOPIC_ADD)
	Observable<String> article_topic_add(@Body String arg);

	String HTTP_ARTICLE_TOPIC_ADD = HTTP_ARTICLE + "Topic/add";
	int ARTICLE_TOPIC_ADD_VOCATIONAL_ID = 17001;

	/**
	 * 17002 发起话题新接口（2016-5-27更新）
	 *
	 * @param arg {"title":"投稿","ico":"ico1","content":"content","tagname":"美女",
	 *            "images":"http://www.sd.xinhuanet.com/news/yule/2015-10/08/1116742491_14440348978261n.jpg,
	 *            http://www.sd.xinhuanet.com/news/yule/2015-10/08/1116742491_14440348978261n.jpg",
	 *            "refId":1, "refType":2,”topicId”:1}
	 *            <p/>
	 *            tile:标题
	 *            ico：预览图
	 *            content：内容
	 *            tagname：标签名称
	 *            images：多图以逗号分隔
	 *            refId:默认传0，如果是RefType为2的话，RefId为商品编号
	 *            refType:默认为0，商品为2
	 *            topicId:话题编号
	 */

	@POST(HTTP_ARTICLE_TOPICNEW_ADD)
	Observable<String> article_topicnew_add(@Body String arg);

	String HTTP_ARTICLE_TOPICNEW_ADD = HTTP_ARTICLE + "topicnew/add";
	int ARTICLE_TOPICNEW_ADD_VOCATIONAL_ID = 17002;

	/**
	 * 17003 上传视频并保存接口[20160530改]
	 * <p/>
	 * videoFile:文件数据
	 * tags:标签
	 * content:输入的文字内容
	 * refid:
	 * reftype:
	 * topicId:
	 *
	 * @return {error:0,url：videourl, previewpic:""， articleId：""}
	 * 出错时 error：1  成功返回0
	 * articleId 为返回的数据库社区文章编号
	 * <p/>
	 * 登录成功后每次访问在Http头部添加 token
	 */


	@Multipart
	@POST(HTTP_PIC_UPLOADMOV)
	Observable<String> uploadmov(@Part MultipartBody.Part videoFile,
								 @Part("tags") RequestBody tags,
								 @Part("content") RequestBody content,
								 @Part("refid") RequestBody refid,
								 @Part("reftype") RequestBody reftype,
								 @Part("topicId") RequestBody topicId);

	String HTTP_PIC_UPLOADMOV = HTTP_PIC + "uploadmov.ashx";
	int PIC_UPLOADMOV_VOCATIONAL_ID = 17003;


	/**
	 * 17005 我的贴子接口
	 *
	 * @param arg videoFile:文件数据
	 *            tags:标签
	 *            content:输入的文字内容
	 *            refid:
	 *            reftype:
	 *            topicId:
	 * @return {error:0, url:videourl,previewpic:"",articleId：""}
	 * <p/>
	 * 出错时 error：1  成功返回 0
	 * articleId 为返回的数据库社区文章编号
	 * <p/>
	 * 登录成功后每次访问在Http头部添加 token
	 */

	@POST(HTTP_ARTICLE_MYTOPIC)
	Observable<String> article_mytopic(@Body String arg);

	String HTTP_ARTICLE_MYTOPIC = HTTP_ARTICLE + "mytopic";
	int ARTICLE_MYTOPIC_VOCATIONAL_ID = 17005;


	/**
	 * 17006 某人的贴子列表接口
	 *
	 * @param arg {\"pagesize\":7,\"maxdate\":\"0\",\"userid\":15}
	 *            <p/>
	 *            Maxdate:取PublishDate
	 *            <p/>
	 *            登录成功后每次访问在Http头部添加 token
	 */

	@POST(HTTP_ARTICLE_TOPICBYUSERID)
	Observable<String> article_topicbyuserid(@Body String arg);

	String HTTP_ARTICLE_TOPICBYUSERID = HTTP_ARTICLE + "topicbyuserid";
	int ARTICLE_TOPICBYUSERID_VOCATIONAL_ID = 17006;


	/**
	 * 17007 某人的贴子列表接口
	 *
	 * @param articleid {\"articleid\":15}
	 *                  <p/>
	 *                  Maxdate:取PublishDate
	 *                  <p/>
	 *                  登录成功后每次访问在Http头部添加 token
	 */

	@POST(HTTP_ARTICLE_DELETEMYTOPIC)
	Observable<String> article_deletemytopic(@Body String articleid);

	String HTTP_ARTICLE_DELETEMYTOPIC = HTTP_ARTICLE + "deletemytopic";
	int ARTICLE_DELETEMYTOPIC_VOCATIONAL_ID = 17007;


	/**
	 * 17008 某人的贴子列表接口
	 *
	 * @param articleid {\"articleid\":15}
	 *                  <p/>
	 *                  登录成功后每次访问在Http头部添加 token
	 */

	@POST(HTTP_COMMUNITY_DETAIL)
	Observable<String> community_detail(@Body String articleid);

	String HTTP_COMMUNITY_DETAIL = "community/detail";
	int COMMUNITY_DETAIL_VOCATIONAL_ID = 17008;


	//18xx 商城管理接口

	/**
	 * 18001 品牌接口
	 */

	@GET(HTTP_BRAND_LIST)
	Observable<String> brand_list();

	String HTTP_BRAND_LIST = HTTP_BRAND + "list";
	int BRAND_LIST_VOCATIONAL_ID = 18001;

	/**
	 * 18002 某人的贴子列表接口
	 *
	 * @param key {"key":""}
	 */

	@POST(HTTP_BRAND_SEARCH)
	Observable<String> brand_search(@Body String key);

	String HTTP_BRAND_SEARCH = HTTP_BRAND + "search";
	int BRAND_SEARCH_VOCATIONAL_ID = 18002;

	/**
	 * 18003 品牌详情接口
	 *
	 * @param id id
	 */

	@GET(HTTP_BRAND_DETAIL + "{id}")
	Observable<String> brand_detail(@Path("id") String id);

	String HTTP_BRAND_DETAIL = HTTP_BRAND + "detail/";
	int BRAND_DETAIL_VOCATIONAL_ID = 18003;


	/**
	 * 18004 专题接口
	 *
	 * @param arg {“pagesize”:10,”maxdate”:”0”}
	 */

	@POST(HTTP_PRODUCTTOPIC_LIST)
	Observable<String> producttopic_list(@Body String arg);

	String HTTP_PRODUCTTOPIC_LIST = HTTP_PRODUCTTOPIC + "list";
	int PRODUCTTOPIC_LIST_VOCATIONAL_ID = 18004;

	/**
	 * 18005 专题详情接口
	 *
	 * @param id id
	 */

	@GET(HTTP_PRODUCTTOPIC_DETAIL + "{id}")
	Observable<String> producttopic_detail(@Path("id") String id);

	String HTTP_PRODUCTTOPIC_DETAIL = HTTP_PRODUCTTOPIC + "detail/";
	int PRODUCTTOPIC_DETAIL_VOCATIONAL_ID = 18005;

	/**
	 * 18006 新品列表接口
	 *
	 * @param query {"query":{"pageSize":10,"maxdate":"0",brandId:10}}
	 *              <p/>
	 *              brandId:传0则为新品列表，传品牌编号则是通过品牌编号来获取商品列表
	 */

	@POST(HTTP_PRODUCT_LIST)
	Observable<String> product_list(@Body String query);

	String HTTP_PRODUCT_LIST = HTTP_PRODUCT + "list";
	int PRODUCT_LIST_VOCATIONAL_ID = 18006;


	/**
	 * 18007 单品搜索列表接口
	 *
	 * @param arg {"query":{"pageSize":10,"maxdate":"0",brandId:10}}
	 *            <p/>
	 *            brandId:传0则为新品列表，传品牌编号则是通过品牌编号来获取商品列表
	 */

	@POST(HTTP_PRODUCT_SEARCH)
	Observable<String> product_search(@Body String arg);

	String HTTP_PRODUCT_SEARCH = HTTP_PRODUCT + "search";
	int PRODUCT_SEARCH_VOCATIONAL_ID = 18007;

	/**
	 * 18008 新品详情接口
	 *
	 * @param productId {“productId”:222}
	 *                  productId:新品主键
	 */

	@POST(HTTP_PRODUCT_DETAIL)
	Observable<String> product_detail(@Body String productId);

	String HTTP_PRODUCT_DETAIL = HTTP_PRODUCT + "detail";
	int PRODUCT_DETAIL_VOCATIONAL_ID = 18008;

	/**
	 * 18009 品类列表接口
	 */

	@POST(HTTP_CATEGORY_LIST)
	Observable<String> category_list();

	String HTTP_CATEGORY_LIST = "category/list";
	int CATEGORY_LIST_VOCATIONAL_ID = 18009;

	/**
	 * 18010 通过品类获取单品列表接口
	 *
	 * @param arg { “pagesize”:10,”maxdate”:”0”, ”categoryid”:1}}
	 */

	@POST(HTTP_PRODUCT_LISTBYCATEGORY)
	Observable<String> product_listbycategoryid(@Body String arg);

	String HTTP_PRODUCT_LISTBYCATEGORY = HTTP_PRODUCT + "listbycategoryid";
	int PRODUCT_LISTBYCATEGORY_VOCATIONAL_ID = 18010;

	//19XX 关注接口

	/**
	 * 19001 通过品类获取单品列表接口
	 *
	 * @param follow {\"follow\":{\"FollowUserId\":30006}}
	 *               FollowUserId:被关注者UserId
	 */

	@POST(HTTP_FOLLOW_ADD)
	Observable<String> follow_add(@Body String follow);

	String HTTP_FOLLOW_ADD = HTTP_FOLLOW + "add";
	int FOLLOW_ADD_VOCATIONAL_ID = 19001;

	/**
	 * 19002 关注者列表接口
	 *
	 * @param arg {"pageindex":1, "pagesize":20}
	 *            pageindex:页数
	 *            pagesize:分页数
	 */

	@POST(HTTP_FOLLOW_LIST)
	Observable<String> follow_list(@Body String arg);

	String HTTP_FOLLOW_LIST = HTTP_FOLLOW + "list";
	int FOLLOW_LIST_VOCATIONAL_ID = 19002;

	/**
	 * 19003 被关注者列表接口
	 *
	 * @param arg {"pageindex":1, "pagesize":20}
	 *            pageindex:页数
	 *            pagesize:分页数
	 */

	@POST(HTTP_FOLLOW_FOLLOWEDLIST)
	Observable<String> follow_followedlist(@Body String arg);

	String HTTP_FOLLOW_FOLLOWEDLIST = HTTP_FOLLOW + "followedlist";
	int FOLLOW_FOLLOWEDLIST_VOCATIONAL_ID = 19003;

	/**
	 * 19004 通过手机列表获取用户列表接口
	 *
	 * @param arg {\"pageindex\":1,\"pagesize\":10,\"phonenums\":[\"13980565631\",\"13980565632\"]}
	 *            pageindex:页数
	 *            pagesize:分页数
	 */

	@POST(HTTP_FOLLOW_PHONEUSERLIST)
	Observable<String> follow_phoneuserlist(@Body String arg);

	String HTTP_FOLLOW_PHONEUSERLIST = HTTP_FOLLOW + "phoneuserlist";
	int FOLLOW_PHONEUSERLIST_VOCATIONAL_ID = 19004;

	/**
	 * 19005 取消关注接口
	 *
	 * @param arg {\"pageindex\":1,\"pagesize\":10,\"phonenums\":[\"13980565631\",\"13980565632\"]}
	 *            pageindex:页数
	 *            pagesize:分页数
	 */

	@POST(HTTP_FOLLOW_DELETE)
	Observable<String> follow_delete(@Body String arg);

	String HTTP_FOLLOW_DELETE = HTTP_FOLLOW + "delete";
	int FOLLOW_DELETE_VOCATIONAL_ID = 19005;


	//200xx 社区接口

	/**
	 * 20001 发现接口
	 *
	 * @param arg {"maxdate":”0”, "pagesize":10}
	 */

	@POST(HTTP_COMMUNITY_FIND)
	Observable<String> community_find(@Body String arg);

	String HTTP_COMMUNITY_FIND = HTTP_COMMUNITY + "find";
	int COMMUNITY_FIND_VOCATIONAL_ID = 20001;


	/**
	 * 20002 通过标签查询贴子列表接口
	 *
	 * @param arg {"pageindex":1, "pagesize":10, "tagname":"美女"}
	 *            pageindex进行分页
	 */

	@POST(HTTP_COMMUNITY_LISTBYTAG)
	Observable<String> community_listbytag(@Body String arg);

	String HTTP_COMMUNITY_LISTBYTAG = HTTP_COMMUNITY + "listbytag";
	int COMMUNITY_LISTBYTAG_VOCATIONAL_ID = 20002;

	/**
	 * 20003 精选接口
	 *
	 * @param arg{\"pageindex\":1, \"pagesize\":10}
	 *                             pageindex进行分页
	 */

	@POST(HTTP_COMMUNITY_REC)
	Observable<String> community_rec(@Body String arg);

	String HTTP_COMMUNITY_REC = HTTP_COMMUNITY + "rec";
	int COMMUNITY_REC_VOCATIONAL_ID = 20003;


	/**
	 * 20004 圈子列表接口
	 *
	 * @param arg{"publishdate":”0”, "pagesize":10}
	 *                               <p>
	 *                               Publishdate：最新的传 0 ，其他时候传返回的 PublishDate
	 */

	@POST(HTTP_COMMUNITY_CIRCLEARTICLE2)
	Observable<String> community_circlearticle2(@Body String arg);

	String HTTP_COMMUNITY_CIRCLEARTICLE2 = HTTP_COMMUNITY + "circlearticle2";
	int COMMUNITY_CIRCLEARTICLE2_VOCATIONAL_ID = 20004;


	/**
	 * 20005 用户搜索接口
	 *
	 * @param arg {"pageindex":1, "pagesize":10, "key":""}
	 *            <p/>
	 *            pageindex进行分页
	 */

	@POST(HTTP_COMMUNITY_SEARCH)
	Observable<String> community_search(@Body String arg);

	String HTTP_COMMUNITY_SEARCH = HTTP_COMMUNITY + "search";
	int COMMUNITY_SEARCH_VOCATIONAL_ID = 20005;

	/**
	 * 20006 用户、帖子搜索接口
	 *
	 * @param key {\"key\":\"\"}
	 */

	@POST(HTTP_COMMUNITY_SEARCH4USERORARTICLE)
	Observable<String> community_search4userorarticle(@Body String key);

	String HTTP_COMMUNITY_SEARCH4USERORARTICLE = HTTP_COMMUNITY + "search4userorarticle";
	int COMMUNITY_SEARCH4USERORARTICLE_VOCATIONAL_ID = 20006;


	/**
	 * 20007 搜索帖子接口
	 *
	 * @param arg {\"pageindex\":1, \"pagesize\":10, \"key\":\"\"}
	 *            pageindex进行分页
	 */

	@POST(HTTP_COMMUNITY_SEARCHTOPIC)
	Observable<String> community_searchtopic(@Body String arg);

	String HTTP_COMMUNITY_SEARCHTOPIC = HTTP_COMMUNITY + "searchtopic";
	int COMMUNITY_SEARCHTOPIC_VOCATIONAL_ID = 20007;

	@POST(HTTP_COMMUNITY_SEARCHTAG)
	Observable<String> community_searchtag(@Body String arg);

	String HTTP_COMMUNITY_SEARCHTAG = "community/searchtag";
	int COMMUNITY_SEARCHTAG_VOCATIONAL_ID = 20008;


	// 21xx 话题接口

	/**
	 * 21001 热门话题列表接口
	 */

	@GET(HTTP_TOPIC_HOTLIST)
	Observable<String> topic_hotlist();

	String HTTP_TOPIC_HOTLIST = HTTP_TOPIC + "hotlist";
	int TOPIC_HOTLIST_VOCATIONAL_ID = 21001;

	/**
	 * 21002 搜索话题列表接口
	 *
	 * @param arg {"key":""}
	 *            <p/>
	 *            pageindex进行分页
	 */

	@POST(HTTP_TOPIC_SEARCH)
	Observable<String> topic_search(@Body String arg);

	String HTTP_TOPIC_SEARCH = HTTP_TOPIC + "search";
	int TOPIC_SEARCH_VOCATIONAL_ID = 21002;

	/**
	 * 21003 话题For Banner推荐列表接口
	 */

	@GET(HTTP_TOPIC_LIST4BANNER)
	Observable<String> topic_list4banner();

	String HTTP_TOPIC_LIST4BANNER = HTTP_TOPIC + "list4banner";
	int TOPIC_LIST4BANNER_VOCATIONAL_ID = 21003;

	/**
	 * 21004 话题下的所有帖子接口
	 *
	 * @param arg {\"maxdate\":”0”, \"pagesize\":10, \"topicid\":10}
	 *            <p/>
	 *            pageindex进行分页
	 */

	@POST(HTTP_COMMUNITY_LIST4TOPIC)
	Observable<String> community_list4topic(@Body String arg);

	String HTTP_COMMUNITY_LIST4TOPIC = HTTP_COMMUNITY + "list4topic";
	int COMMUNITY_LIST4TOPIC_VOCATIONAL_ID = 21004;


	/**
	 * 21005 话题下的热门帖子接口
	 *
	 * @param arg {\"pageindex\":1, \"pagesize\":10, \"topicid\":10}
	 *            通过pageindex进行分页
	 */

	@POST(HTTP_COMMUNITY_LIST4TOPICBYUPCOUNT)
	Observable<String> community_list4topicbyupcount(@Body String arg);

	String HTTP_COMMUNITY_LIST4TOPICBYUPCOUNT = HTTP_COMMUNITY + "list4topicbyupcount";
	int COMMUNITY_LIST4TOPICBYUPCOUNT_VOCATIONAL_ID = 21005;

	/**
	 * 21006 关注话题接口
	 *
	 * @param arg {\"topicid\":10}
	 */

	@POST(HTTP_TOPICFOLLOW_ADD)
	Observable<String> topicfollow_add(@Body String arg);

	String HTTP_TOPICFOLLOW_ADD = HTTP_TOPICFOLLOW + "add";
	int TOPICFOLLOW_ADD_VOCATIONAL_ID = 21006;

	/**
	 * 21007 话题取消关注接口
	 *
	 * @param arg {\"topicid\":10}
	 */

	@POST(HTTP_TOPICFOLLOW_DELETE)
	Observable<String> topicfollow_delete(@Body String arg);

	String HTTP_TOPICFOLLOW_DELETE = HTTP_TOPICFOLLOW + "delete";
	int TOPICFOLLOW_DELETE_VOCATIONAL_ID = 21007;

	/**
	 * 21008 我的话题列表接口
	 *
	 * @param arg {\"pageindex\":1, \"pageindex\":10}
	 */

	@POST(HTTP_TOPICFOLLOW_LIST)
	Observable<String> topicfollow_list(@Body String arg);

	String HTTP_TOPICFOLLOW_LIST = HTTP_TOPICFOLLOW + "list";
	int TOPICFOLLOW_LIST_VOCATIONAL_ID = 21008;

	/**
	 * 21009 话题详情接口
	 *
	 * @param arg {"topicid":10}
	 */

	@POST(HTTP_TOPIC_DETAIL)
	Observable<String> topic_detail(@Body String arg);

	String HTTP_TOPIC_DETAIL = HTTP_TOPIC + "detail";
	int TOPIC_DETAIL_VOCATIONAL_ID = 21009;


	// 22 XX 个人中心接口

	/**
	 * 22001 我的标签接口
	 *
	 * @param arg {\"pageindex\": 1,\"pagesize\": 10,\"userid\": 10}
	 */

	@POST(HTTP_TAG_TAGLISTBYUSERID)
	Observable<String> tag_taglistbyuserid(@Body String arg);

	String HTTP_TAG_TAGLISTBYUSERID = HTTP_TAG + "taglistbyuserid";
	int TAG_TAGLISTBYUSERID_VOCATIONAL_ID = 22001;


	/**
	 * 22002 通过标签查询我的贴子列表接口
	 *
	 * @param arg{\"pageindex\":1, \"pagesize\":10, \"tagname\":\"美女\",\"userid\": 10}
	 *                             pageindex进行分页
	 */

	@POST(HTTP_COMMUNITY_LISTBYTAGBYUSERID)
	Observable<String> community_listbytagbyuserid(@Body String arg);

	String HTTP_COMMUNITY_LISTBYTAGBYUSERID = HTTP_COMMUNITY + "listbytagbyuserid";
	int COMMUNITY_LISTBYTAGBYUSERID_VOCATIONAL_ID = 22002;

	// 23XX 投票分享接口

	/**
	 * 23001 投票分享页面接口
	 *
	 * 多层投票：http://bz.metshow.com:8080/api/daysignvote/index-votetwo.html?daysignid=11
	 * 投票：http://bz.metshow.com:8080/api/daysignvote/index-vote.html?daysignid=10
	 * 问卷：http://bz.metshow.com:8080/api/daysignvote/index-question.html?daysignid=12
	 * 话题：http://bz.metshow.com:8080/api/topic/index.html?topic=1
	 *
	 */

	/**
	 * 24001 Android app升级接口
	 */

	@GET("appversion/new")
	Observable<String> appversion();

	int APPVERSION_NEW_VOCATIONAL_ID = 24001;

	@Streaming
	@GET
	Observable<ResponseBody> download(@Url String url);

	//--------------------2.0新版本接口

	/**
	 * @param arg {\"pageindex\":1,\"pagesize\":40}
	 *            通过pageindex分页
	 * @return
	 */
	@POST(HTTP_ARTICLE_RECLIST)
	Observable<String> article_reclist(@Body String arg);

	String HTTP_ARTICLE_RECLIST = HTTP_ARTICLE + "reclist";
	int ARTICLE_RECLIST_VOCATIONAL_ID = 0x025001;


	@POST(HTTP_TOPIC_LIST)
	Observable<String> topic_list(@Body String arg);

	String HTTP_TOPIC_LIST = "topic/list";
	int TOPIC_LIST_VOCATIONAL_ID = 0x025002;

	@POST(HTTP_TRIPRODUCT_LIST)
	Observable<String> trialproduct_list();

	String HTTP_TRIPRODUCT_LIST = "trialproduct/list";
	int TRIPRODUCT_LIST_VOCATIONAL_ID = 0x025003;

	@POST(HTTP_PRODUCTSPECIAL_LIST)
	Observable<String> productspecial_list(@Body String arg);

	String HTTP_PRODUCTSPECIAL_LIST = "productspecial/list";
	int PRODUCTSPECIAL_LIST_VOCATIONAL_ID = 0x025004;

	@POST(HTTP_PRODUCTSPECIAL_DETAIL)
	Observable<String> productspecial_detail(@Body String arg);

	String HTTP_PRODUCTSPECIAL_DETAIL = "productspecial/detail";
	int PRODUCTSPECIAL_DETAIL_VOCATIONAL_ID = 0x025005;


	//我 被喜欢的帖子
	@POST(HTTP_MYCOMMUNITY_FAVED)
	Observable<String> mycommunity_faved(@Body String arg);

	String HTTP_MYCOMMUNITY_FAVED = "action/mycommunityfaved";
	int MYCOMMUNITY_FAVED_VOCATIONAL_ID = 0x025006;


	@POST(HTTP_USERTYPE_LIST)
	Observable<String> usertype_list();

	String HTTP_USERTYPE_LIST = "usertype/list";
	int USERTYPE_LIST_VOCATIONAL_ID = 0x025007;

	@POST(HTTP_USERTYPE_USERLIST)
	Observable<String> usertype_userlist(@Body String arg);

	String HTTP_USERTYPE_USERLIST = "usertype/userlist";
	int USERTYPE_USERLIST_VOCATIONAL_ID = 0x025008;


	@POST(HTTP_TAG_HOTLIST4PHOTO)
	Observable<String> tag_hotlist4photo();

	String HTTP_TAG_HOTLIST4PHOTO = "tag/hotlist4photo";
	int TAG_HOTLIST4PHOTO_VOCATIONAL_ID = 0x025009;

	@POST(HTTP_STICKERTYPE_LIST)
	Observable<String> stickertype_list();

	String HTTP_STICKERTYPE_LIST = "stickertype/list";
	int STICKERTYPE_LIST_VOCATIONAL_ID = 0x025010;


	@POST(HTTP_STICKER_LIST)
	Observable<String> sticker_list(@Body String arg);

	String HTTP_STICKER_LIST = "sticker/list";
	int STICKER_LIST_VOCATIONAL_ID = 0x025011;

	@POST(HTTP_FILTER_LIST)
	Observable<String> filter_list();

	String HTTP_FILTER_LIST = "filter/list";
	int FILTER_LIST_VOCATIONAL_ID = 0x025012;

	@POST(HTTP_POINTSHOP_LIST)
	Observable<String> pointshop_list(@Body String arg);

	String HTTP_POINTSHOP_LIST = "pointshop/list";
	int POINTSHOP_LIST_VOCATIONAL_ID = 0x0260001;

	@POST(HTTP_POINTSHOP_DETAIL)
	Observable<String> pointshop_detail();

	String HTTP_POINTSHOP_DETAIL = "pointshop/detail";
	int POINTSHOP_DETAIL_VOCATIONAL_ID = 0x0260002;

	@POST(HTTP_POINTSHOP_ADD)
	Observable<String> pointshop_add(@Body String arg);

	String HTTP_POINTSHOP_ADD = "pointshop/add";
	int POINTSHOP_ADD_VOCATIONAL_ID = 0x0260003;

	@POST(HTTP_POINTSHOP_EXCHANGELIST)
	Observable<String> pointshop_exchangelist(@Body String arg);

	String HTTP_POINTSHOP_EXCHANGELIST = "pointshop/exchangelist";
	int POINTSHOP_EXCHANGELIST_VOCATIONAL_ID = 0x0260004;

	@POST(HTTP_USERPOINTSLOG_LIST)
	Observable<String> userpointslog_list(@Body String arg);

	String HTTP_USERPOINTSLOG_LIST = "userpointslog/list";
	int USERPOINTSLOG_LIST_VOCATIONAL_ID = 0x0260005;


}

