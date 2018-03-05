package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.DaySign;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2017-7-11.
 */

public class MainPresenter extends BzPresenter {

	BZContract.IMainView iMainView;

	public MainPresenter(BZContract.IMainView iView) {
		super(iView);
		iMainView = iView;
	}

	public void getDaySign(){
		mModel.getDaySign();
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {
			case ServerAPI.DAYSIGN_LIST_VOCATIONAL_ID:
				iMainView.getDaySignSuccess((DaySign) s.getResult());
				break;
		}
	}
}
