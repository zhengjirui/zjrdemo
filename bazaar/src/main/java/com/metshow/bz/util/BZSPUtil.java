package com.metshow.bz.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.kwan.base.BaseApplication;
import com.kwan.base.config.APPCacheConfig;
import com.metshow.bz.commons.bean.User;

/**
 * Created by Mr.Kwan on 2017-4-10.
 */

public class BZSPUtil {

	static{

		BZ_SP_APPINFO = BaseApplication.getInstance().getSharedPreferences("SP_APPINFO_FILE", Context.MODE_PRIVATE);
		BZ_SP_USERINFO = BaseApplication.getInstance().getSharedPreferences("SP_USERINFO_FILE", Context.MODE_PRIVATE);
	}

	/** APP信息：包括是否是第一次启动APP等 **/
	private static SharedPreferences BZ_SP_APPINFO;
	private static SharedPreferences.Editor sp_appinfo_editor;


	/** 用户信息：包括是否已登录等 **/
	private static SharedPreferences BZ_SP_USERINFO;
	private static SharedPreferences.Editor sp_userinfo_editor;

	/**
	 * 设置本应用是否第一次启动
	 * @param isFirstIn 是否第一次启动
	 * @return 是否提交成功
	 */
	public static boolean setIsFirstIn(boolean isFirstIn){
		sp_appinfo_editor = BZ_SP_APPINFO.edit();
		sp_appinfo_editor.putBoolean(APPCacheConfig.KEY_IS_FIRST_CACHE, isFirstIn);
		return sp_appinfo_editor.commit();
	}

	/**
	 * 获取本应用是否第一次启动(默认是true，是第一次启动，直接启动Splash效果)
	 * @return 是否第一次启动 默认是true
	 */
	public static boolean getIsFirstIn(){
		return BZ_SP_APPINFO.getBoolean(APPCacheConfig.KEY_IS_FIRST_CACHE, true);
	}


	//--------------------USERINFO--------------------------------------


	/** 1474358881557 1474359702385
	 * 保存用户的登录状态
	 * @param isLogin true：登录，false：未登录
	 */
	public static void setIsLogin(boolean isLogin) {
		sp_userinfo_editor = BZ_SP_USERINFO.edit();
		sp_userinfo_editor.putBoolean(APPCacheConfig.KEY_IS_LOGIN, isLogin);
		sp_userinfo_editor.apply();

	}

	/**
	 * 获取用户的登录状态( 默认为 false，未登录)
	 * @return
	 */
	public static boolean getIsLogin() {
		return BZ_SP_USERINFO.getBoolean(APPCacheConfig.KEY_IS_LOGIN, false);
	}

	public static boolean setFollowedDate(String data){
		sp_appinfo_editor = BZ_SP_APPINFO.edit();
		sp_appinfo_editor.putString(APPCacheConfig.KEY_FOLLOWED_DATA, data);
		return sp_appinfo_editor.commit();
	}

	public static String getFollowedDate(){
		return BZ_SP_APPINFO.getString(APPCacheConfig.KEY_FOLLOWED_DATA, "0");
	}



	public static void setUser(User user){

		sp_userinfo_editor = BZ_SP_USERINFO.edit();

		sp_userinfo_editor.putString("user_address", user.getAddress());
		sp_userinfo_editor.putLong("user_adminid", user.getAdminId());
		sp_userinfo_editor.putString("user_avatar", user.getAvatar());
		sp_userinfo_editor.putString("user_birthday", user.getBirthday());
		sp_userinfo_editor.putString("user_contact", user.getContact());
		sp_userinfo_editor.putString("user_description", user.getDescription());
		sp_userinfo_editor.putString("user_email", user.getEmail());
		sp_userinfo_editor.putString("user_infoextent", user.getInfoExtent());
		sp_userinfo_editor.putInt("user_isrecvip", user.getIsRecVip());
		sp_userinfo_editor.putInt("user_isrefusemessage", user.getIsRefuseMessage());
		sp_userinfo_editor.putInt("user_isvip", user.getIsVip());
		sp_userinfo_editor.putString("user_lastopenmsgdata", user.getLastOpenMsgDate());
		sp_userinfo_editor.putString("user_nickname", user.getNickName());
		sp_userinfo_editor.putString("user_password", user.getPassword());
		sp_userinfo_editor.putString("user_phonenum", user.getPhoneNum());
		sp_userinfo_editor.putInt("user_point", user.getPoints());
		sp_userinfo_editor.putString("user_rank", user.getRank());
		sp_userinfo_editor.putString("user_realname", user.getRealName());
		sp_userinfo_editor.putInt("user_role", user.getRole());
		sp_userinfo_editor.putLong("user_roleid", user.getRoleId());
		sp_userinfo_editor.putInt("user_sex", user.getSex());
		sp_userinfo_editor.putInt("user_state", user.getState());
		sp_userinfo_editor.putString("user_tags", user.getTags());
		sp_userinfo_editor.putString("user_tel", user.getTel());
		sp_userinfo_editor.putString("user_truename", user.getTrueName());
		sp_userinfo_editor.putLong("user_userid", user.getUserId());
		sp_userinfo_editor.putString("user_username", user.getUserName());
		sp_userinfo_editor.putInt("user_usertype", user.getUserType());
		sp_userinfo_editor.putString("user_vipenddate", user.getVipEndDate());
		sp_userinfo_editor.putInt("user_vipordernum", user.getVipOrderNum());
		sp_userinfo_editor.putInt("user_articlecount", user.getArticleCount());
		sp_userinfo_editor.putInt("user_befollowedcount", user.getBeFollowedCount());
		sp_userinfo_editor.putInt("user_followcount", user.getFollowCount());
		sp_userinfo_editor.putString("user_grade", user.getGrade());
		sp_userinfo_editor.putString("user_toke", user.getToken());

		sp_userinfo_editor.putInt("user_isslogin", user.getIsSlogin());
		sp_userinfo_editor.putInt("user_platform", user.getPlatform());
		sp_userinfo_editor.putInt("user_favcount", user.getFavCount());


		sp_userinfo_editor.apply();

	}

	public static User getUser(){

		User user = new User();

		user.setAddress(BZ_SP_USERINFO.getString("user_address",""));
		user.setAdminId(BZ_SP_USERINFO.getLong("user_adminid", user.getAdminId()));
		user.setAvatar(BZ_SP_USERINFO.getString("user_avatar", user.getAvatar()));
		user.setBirthday(BZ_SP_USERINFO.getString("user_birthday", user.getBirthday()));
		user.setContact(BZ_SP_USERINFO.getString("user_contact", user.getContact()));;
		user.setDescription(BZ_SP_USERINFO.getString("user_description", user.getDescription())); ;
		user.setEmail(BZ_SP_USERINFO.getString("user_email", user.getEmail()));
		user.setInfoExtent(BZ_SP_USERINFO.getString("user_infoextent", user.getInfoExtent()));
		user.setIsRecVip(BZ_SP_USERINFO.getInt("user_isrecvip", user.getIsRecVip()));
		user.setIsRefuseMessage(BZ_SP_USERINFO.getInt("user_isrefusemessage", user.getIsRefuseMessage()));
		user.setIsVip(BZ_SP_USERINFO.getInt("user_isvip", user.getIsVip()));
		user.setLastOpenMsgDate(BZ_SP_USERINFO.getString("user_lastopenmsgdata", user.getLastOpenMsgDate()));
		user.setNickName(BZ_SP_USERINFO.getString("user_nickname", user.getNickName()));
		user.setPassword(BZ_SP_USERINFO.getString("user_password", user.getPassword()));
		user.setPhoneNum(BZ_SP_USERINFO.getString("user_phonenum", user.getPhoneNum()));
		user.setPoints(BZ_SP_USERINFO.getInt("user_point", user.getPoints()));
		user.setRank(BZ_SP_USERINFO.getString("user_rank", user.getRank()));
		user.setRealName(BZ_SP_USERINFO.getString("user_realname", user.getRealName()));
		user.setRole(BZ_SP_USERINFO.getInt("user_role", user.getRole()));
		user.setRoleId(BZ_SP_USERINFO.getLong("user_roleid", user.getRoleId()));
		user.setSex(BZ_SP_USERINFO.getInt("user_sex", user.getSex()));
		user.setState(BZ_SP_USERINFO.getInt("user_state", user.getState()));
		user.setTag(BZ_SP_USERINFO.getString("user_tags", user.getTags()));
		user.setTel(BZ_SP_USERINFO.getString("user_tel", user.getTel()));
		user.setTrueName(BZ_SP_USERINFO.getString("user_truename", user.getTrueName()));
		user.setUserId(BZ_SP_USERINFO.getLong("user_userid", user.getUserId()));
		user.setUserName(BZ_SP_USERINFO.getString("user_username", user.getUserName()));
		user.setUserType(BZ_SP_USERINFO.getInt("user_usertype", user.getUserType()));
		user.setVipEndDate(BZ_SP_USERINFO.getString("user_vipenddate", user.getVipEndDate()));
		user.setVipOrderNum(BZ_SP_USERINFO.getInt("user_vipordernum", user.getVipOrderNum()));
		user.setArticleCount(BZ_SP_USERINFO.getInt("user_articlecount", user.getArticleCount()));
		user.setBeFollowedCount(BZ_SP_USERINFO.getInt("user_befollowedcount", user.getBeFollowedCount()));
		user.setFollowCount(BZ_SP_USERINFO.getInt("user_followcount", user.getFollowCount()));
		user.setGrade(BZ_SP_USERINFO.getString("user_grade", user.getGrade()));
		user.setToken(BZ_SP_USERINFO.getString("user_toke", user.getToken()));
		user.setPlatform(BZ_SP_USERINFO.getInt("user_platform", user.getPlatform()));
		user.setIsSlogin(BZ_SP_USERINFO.getInt("user_isslogin", user.getIsSlogin()));
		user.setFavCount(BZ_SP_USERINFO.getInt("user_favcount", user.getFavCount()));

		return user;
	}

}
