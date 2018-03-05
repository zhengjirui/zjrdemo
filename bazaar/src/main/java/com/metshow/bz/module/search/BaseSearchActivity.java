package com.metshow.bz.module.search;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwan.base.activity.CommonPageActivity;
import com.metshow.bz.R;
import com.zhy.autolayout.AutoLinearLayout;

public abstract class BaseSearchActivity extends CommonPageActivity {

	protected EditText et_search;
	protected TextView tv_cancel;
	protected ImageView iv_search;
	protected AutoLinearLayout ll_search;


	@Override
	protected String getTitleTxt() {
		return null;
	}

	@Override
	protected int getTitleBarViewId() {
		return R.layout.title_bar_search;
	}

	@Override
	protected void initViews() {
		super.initViews();

		iv_search = (ImageView) findViewById(R.id.iv_title_search);
		et_search = (EditText) findViewById(R.id.et_search);
		tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		ll_search = (AutoLinearLayout) findViewById(R.id.ll_search);
		et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

		et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					//do something;
					onSearch(et_search.getText().toString().trim());
					return true;
				}
				return false;
			}
		});


		tv_cancel.setOnClickListener(this);
		iv_search.setOnClickListener(this);



	}



	public abstract void onSearch(String txt);



	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
			case R.id.tv_cancel:
				onBackPressed();
		}
	}
}
