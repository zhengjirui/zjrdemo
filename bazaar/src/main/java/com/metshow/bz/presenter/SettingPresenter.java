package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2016-8-28.
 */
public class SettingPresenter extends BzPresenter  {
    private BZContract.ISettingView mISettingView;


    public SettingPresenter(BZContract.ISettingView iSettingView) {
        super(iSettingView);
        mISettingView = iSettingView;
    }

    public void setState(int state){
        mModel.setState(state);
    }

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);
		switch (vocational_id) {
			case ServerAPI.USER_SETMESSAGESTATE_VOCATIONAL_ID:
				mISettingView.setStateSuccess((String)s.getResult());
				break;

		}
	}
}
