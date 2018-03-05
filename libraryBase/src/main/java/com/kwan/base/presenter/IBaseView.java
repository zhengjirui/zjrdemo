package com.kwan.base.presenter;

/**
 * Created by Mr.Kwan on 2016-10-8.
 */

public interface IBaseView {

    void toastMsg(String msg);

    void showProgress(String txt);

    void dismissProgress();

    boolean isActive();

    void onNoNetWork();

    void onServerError(int vocational_id, Throwable e);

    void onServerFailed(String s);



}
