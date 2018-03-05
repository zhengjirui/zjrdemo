package com.metshow.bz.module.me.activity;

import android.graphics.Bitmap;

import com.kwan.base.activity.BaseCommonActivity;
import com.metshow.bz.R;

public class PointsNoteActivity extends BaseCommonActivity {

	@Override
	protected Bitmap getBackGroundBitmap() {
		return null;
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_points_note;
	}

	@Override
	protected void initData() {

	}

	@Override
	protected String getTitleTxt() {
		return "积分说明";
	}
}
