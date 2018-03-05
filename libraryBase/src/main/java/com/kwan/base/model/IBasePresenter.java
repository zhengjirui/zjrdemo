package com.kwan.base.model;

import com.kwan.base.bean.ServerMsg;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2016-10-8.
 */

public interface IBasePresenter {

	void onServerUploadError(int vocational_id, HashMap exdata, Throwable e);

	void onServerUploadCompleted(int vocational_id, HashMap exdata);

	void onServerUploadNext(int vocational_id, HashMap exdata, Object s);

	void onNoNetWork();

    void onServerError(int vocational_id, Throwable throwable);

    void onServerFailed(String msg);

    void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s);

	void onServerSuccess(int vocational_id, HashMap<String, Object> exData, byte[] s);
}
