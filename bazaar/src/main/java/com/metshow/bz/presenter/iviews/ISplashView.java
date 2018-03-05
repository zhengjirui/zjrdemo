package com.metshow.bz.presenter.iviews;

import com.kwan.base.presenter.IBaseView;
import com.metshow.bz.commons.bean.Splash;

/**
 * Created by Mr.Kwan on 2017-4-11.
 */

public interface ISplashView extends IBaseView{

	void getConfigSuccess();

	void showImage(String url,final boolean isFrontPage,String url2,Splash s);

	void showGif(String url,String url2,Splash s);

	void showVideo(String url,String imgUrl,Splash s);

	void showHtml(String url,Splash s);

	void showPerson(long refId);

}
