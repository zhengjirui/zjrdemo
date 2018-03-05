package com.metshow.bz.presenter;

import android.util.Log;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ServerConfigNew;
import com.metshow.bz.commons.bean.Splash;
import com.metshow.bz.net.api.BazzarAPIUtil;
import com.metshow.bz.net.api.ServerAPI;
import com.metshow.bz.presenter.iviews.ISplashView;

import java.util.HashMap;


/**
 * Created by Mr.Kwan on 2016-6-28.
 */
public class SplashPresenter extends BzPresenter {

	private ISplashView mISplashView;

	public SplashPresenter(ISplashView iSplashView) {
		super(iSplashView);
		mISplashView = iSplashView;
	}

	public void getConfig() {
		mModel.getServerConfigAddress();
	}

	public void getSplashList(String data) {
		mModel.getSplashList(data);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {
			//获取服务器地址信息
			case ServerAPI.SERVERCONFIG_NEW_VOCATIONAL_ID:
				getConfigSuccess((ServerConfigNew) s.getResult());
				break;
			case ServerAPI.SPLASH_LIST_VOCATIONAL_ID:
				onSuccess((Splash) s.getResult());
				break;
		}


	}

	public void onSuccess(Splash s) {

		if (s.getRefId() > 0) {
			Log.d("SplashPresenter", "个人");
			mISplashView.showPerson(s.getRefId());
		}

		switch (s.getType()) {

			case 1://图片
				Log.d("SplashPresenter", "图片");
				mISplashView.showImage(s.getImage4(), false, s.getClickUrl(), s);
				break;
			case 2://视频
				Log.d("SplashPresenter", "视频");
				mISplashView.showVideo(s.getUrl(), s.getImage4(), s);
				break;
			case 3://html
				Log.d("SplashPresenter", "html");
				mISplashView.showHtml(s.getUrl(), s);
				break;
			case 4://gif
				Log.d("SplashPresenter", "gif");
				mISplashView.showGif(s.getImage4(), s.getClickUrl(), s);
				break;

		}
	}


	public void getConfigSuccess(ServerConfigNew configNew) {

		Log.d("kwan", "getConfigSuccess");

		BazzarAPIUtil.HTTP_BASE = configNew.getApiServer();
		BazzarAPIUtil.HTTP_SERVER_UPLOAD = configNew.getImgServer();
		mISplashView.getConfigSuccess();
	}





	@Override
	public void onNoNetWork() {
		super.onNoNetWork();
	}


}
