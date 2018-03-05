package com.metshow.bz.module.commons.activity;

import android.view.View;
import android.widget.ImageView;

import com.kwan.base.activity.BaseWebActivity;
import com.metshow.bz.R;
import com.metshow.bz.util.ShareUtil;

public class WebShareActivity extends BaseWebActivity {

	@Override
	protected int getTitleBarRightLayoutId() {
		return R.layout.title_right_follow;
	}

	@Override
	protected void setUpTitleRightView(View v) {
		super.setUpTitleRightView(v);
		((ImageView)v).setImageResource(R.mipmap.icon_me_share);
		v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareUtil util = new ShareUtil(WebShareActivity.this);

				util.setStr_url(url);
				util.showShareDialog();
			}
		});

	}
}
