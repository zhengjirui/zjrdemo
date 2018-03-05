package com.metshow.bz.commons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.kwan.base.BaseApplication;
import com.metshow.bz.IUmengEvent;
import com.metshow.bz.module.in.activity.ArticleActivity;
import com.metshow.bz.module.main.MainActivity;
import com.metshow.bz.module.shai.ProductActivity;
import com.metshow.bz.module.shai.TopicActivity;
import com.metshow.bz.module.commons.activity.FriendDetailActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.Kwan on 2017-2-21.
 */

public class App extends BaseApplication {

	public static UMShareAPI ShareAPI;
	PushAgent mPushAgent;

	public static final String APP_ID_WX = "wx43ee9c646a4b93ff";
	public static final String APP_ID_WB = "4185097647";
	public static final String APP_ID_QQ = "1105681777";

	{
		PlatformConfig.setSinaWeibo("4185097647", "e6be4e567646fa0ebc33a972661f9492");
		Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
		PlatformConfig.setWeixin("wx43ee9c646a4b93ff", "c6cb1377959e2f43413414cf1aa8c62e");
		PlatformConfig.setQQZone("1105681777", "91RgO1MFs2o5tHMN");
	}

	@Override
	public void onCreate() {
		super.onCreate();

		initUmeng();
		initShare();
	}

	private void initUmeng() {

		MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

		mPushAgent = PushAgent.getInstance(this);
		//注册推送服务，每次调用register方法都会回调该接口
		mPushAgent.register(new IUmengRegisterCallback() {
			@Override
			public void onSuccess(String deviceToken) {
				//注册成功会返回device token
				Log.d("deviceToken", deviceToken);
			}

			@Override
			public void onFailure(String s, String s1) {
			}
		});

		UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
			@Override
			public void dealWithCustomAction(Context context, UMessage msg) {
				// Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();

				Log.d("push kwan", msg.text);

				for (Map.Entry<String, String> entry : msg.extra.entrySet()) {

					String key = entry.getKey();
					String value = entry.getValue();

					Log.d("push::", "key--" + key);
					Log.d("push::", "value--" + value);

				}

				String key_type = msg.extra.get("key_type");
				String key_refid = msg.extra.get("key_refid");
				String key_url = msg.extra.get("key_url");
				String key_refType = msg.extra.get("key_refType");
				String key_messageid = msg.extra.get("key_messageid");

				String arg = "";

				if (key_type.equals("0")) {//普通
					arg = "普通";
				} else if (key_type.equals("1")) {//关联文章
					arg = "关联文章";
					Bundle bundle = new Bundle();
					bundle.putLong("id", Long.parseLong(key_refid));
					bundle.putString("icon", "");
					bundle.putBoolean("isFav", false);

					Log.d("push", "read go");
					go2Activity(context, ArticleActivity.class, bundle);

				} else if (key_type.equals("2")) {//跳转到晒
					arg = "跳转到晒";
					go2Activity(context, MainActivity.class, null);
				} else if (key_type.equals("3")) {//转到个人主页
					arg = "转到个人主页";
					Bundle bundle = new Bundle();
					bundle.putLong("userid", Long.parseLong(key_refid));
					bundle.putInt("isFollow", 0);
					go2Activity(context, FriendDetailActivity.class, bundle);

				} else if (key_type.equals("4")) {//跳转到单品
					arg = "跳转到单品";
					Bundle bundle = new Bundle();
					bundle.putLong("id", Long.parseLong(key_refid));
					bundle.putString("name", msg.title);
					go2Activity(context, ProductActivity.class, bundle);


				} else if (key_type.equals("5")) {//跳转一篇帖子
					arg = "跳转一篇帖子";
					Bundle bundle = new Bundle();
					bundle.putLong("topicid", Long.parseLong(key_refid));
					go2Activity(context, TopicActivity.class, bundle);

				} else if (key_type.equals("6")) {//升级消息
					arg = "升级消息";
				}
				App.umengEvent(App.this, IUmengEvent.RemoteNotificationClick, arg + ":" + key_type);

			}
		};
		mPushAgent.setNotificationClickHandler(notificationClickHandler);

		UmengMessageHandler messageHandler = new UmengMessageHandler() {
			@Override
			public void dealWithCustomMessage(final Context context, final UMessage msg) {

				new Handler(getMainLooper()).post(new Runnable() {

					@Override
					public void run() {
						// 对于自定义消息，PushSDK默认只统计送达。若开发者需要统计点击和忽略，则需手动调用统计方法。
						boolean isClickOrDismissed = true;
						if (isClickOrDismissed) {
							//自定义消息的点击统计
							UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
						} else {
							//自定义消息的忽略统计
							UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
						}

						Log.d("push zdy", msg.custom);
						Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
					}
				});
			}
		};
		mPushAgent.setMessageHandler(messageHandler);

	}

	private void initShare() {
		ShareAPI = UMShareAPI.get(this);
	}

	public static void umengEvent(Context context, String id, String arg) {
		Map<String, String> m = new HashMap<>();
		m.put(arg, arg);
		MobclickAgent.onEventValue(context, id, m, 1);

	}
	public void go2Activity(Context context, Class<? extends Activity> activity, Bundle bundle) {

		Intent intent = new Intent(this, activity);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (bundle != null)
			intent.putExtras(bundle);
		context.startActivity(intent);

	}

}
