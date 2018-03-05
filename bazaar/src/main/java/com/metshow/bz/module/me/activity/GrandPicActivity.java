package com.metshow.bz.module.me.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kwan.base.activity.CommonRecycleActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.metshow.bz.commons.adapter.GrandPicAdapter;

import java.util.ArrayList;

public class GrandPicActivity extends CommonRecycleActivity {

	ArrayList<String> data = new ArrayList<>();

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		data = (ArrayList<String>) getIntentData("data");

	}

	@Override
	protected void initData() {

	}

	@Override
	protected BaseQuickAdapter getAdapter() {
		return new GrandPicAdapter(this,data);
	}

	@Override
	protected RecyclerView.LayoutManager getLayoutManager() {
		return new GridLayoutManager(this,4);
	}

	@Override
	protected String getTitleTxt() {
		return "活动图片";
	}
}
