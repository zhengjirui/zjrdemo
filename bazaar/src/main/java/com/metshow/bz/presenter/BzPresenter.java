package com.metshow.bz.presenter;

import com.kwan.base.model.BaseModel;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.presenter.IBaseView;
import com.metshow.bz.datamodule.BZModule;

/**
 * Created by Mr.Kwan on 2017-4-11.
 */

public class BzPresenter extends BasePresenter {

	protected BZModule mModel;

	public BzPresenter(IBaseView iView) {
		super(iView);
		mModel = new BZModule(this);
	}

	@Override
	protected BaseModel getModel() {
		return mModel;
	}



}
