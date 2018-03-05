package com.metshow.bz.module.commons.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.kwan.base.activity.BaseCommonActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.TagPresenter;

import java.util.ArrayList;
import java.util.List;

public class TagActivity extends BaseCommonActivity implements BZContract.ITagView {

	/*
	 *                    .::::.
	 *                  .::::::::.
	 *                 :::::::::::
	 *             ..:::::::::::'
	 *           '::::::::::::'            /\_/\
	 *             .::::::::::           =( °w°)=
	 *        '::::::::::::::..           )   ( //
	 *             ..::::::::::::.       (____)//
	 *           ``::::::::::::::::                    What a stupid cat ,
	 *            ::::``:::::::::'        .:::.        What a fucking code 。
	 *           ::::'   ':::::'       .::::::::.
	 *         .::::'      ::::     .:::::::'::::.
	 *        .:::'       :::::  .:::::::::' ':::::.
	 *       .::'        :::::.:::::::::'      ':::::.
	 *      .::'         ::::::::::::::'         ``::::.
	 *  ...:::           ::::::::::::'              ``::.
	 * ```` ':.          ':::::::::'                  ::::..
	 *                    '.:::::'                    ':'````..
	 */

	private TextView titleRight;
	private TagPresenter mPresenter;
	private EditText et_content;
	private RecyclerView rv_hot, rv_often;
	private BaseQuickAdapter<Tag> mHotAdapter, mOftenAdapter;
	private ArrayList<Tag> mHotTags = new ArrayList<>();
	private ArrayList<Tag> mOftenTags = new ArrayList<>();
	private Boolean isOne = false;

	@Override
	protected void beForeSetContentView() {

		mPresenter = new TagPresenter(this);
		if (getIntentData("data") != null)
			isOne = (Boolean) getIntentData("data");

	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_tag;
	}

	@Override
	protected void initViews() {
		super.initViews();

		rv_hot = (RecyclerView) findViewById(R.id.rv_hot);
		rv_often = (RecyclerView) findViewById(R.id.rv_often);

		rv_hot.setLayoutManager(new LinearLayoutManager(this));
		rv_hot.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		rv_hot.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		rv_hot.setHasFixedSize(true);
		//mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		//解决更新 闪烁
		rv_hot.setItemAnimator(null);
		rv_hot.setNestedScrollingEnabled(false);
		et_content = (EditText) findViewById(R.id.et_content);
		rv_often.setLayoutManager(new LinearLayoutManager(this));
		rv_often.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		rv_often.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		rv_often.setHasFixedSize(true);
		//mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		//解决更新 闪烁
		rv_often.setItemAnimator(null);
		rv_often.setNestedScrollingEnabled(false);

		mHotAdapter = new BaseQuickAdapter<Tag>(this, R.layout.list_txt, mHotTags) {
			@Override
			protected void convert(BaseViewHolder helper, Tag item, int position) {
				helper.setText(R.id.txt, item.getName());
			}
		};

		mOftenAdapter = new BaseQuickAdapter<Tag>(this, R.layout.list_txt, mOftenTags) {
			@Override
			protected void convert(BaseViewHolder helper, Tag item, int position) {
				helper.setText(R.id.txt, item.getName());

			}
		};

		rv_often.setAdapter(mOftenAdapter);
		rv_hot.setAdapter(mHotAdapter);

		mOftenAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {


				if(isOne){
				    Log.e("kwan","back 。。"+mOftenTags.get(position).getName());
					backWithResult(mOftenTags.get(position).getName());
				}else {

					String edit = et_content.getText().toString();
					if (edit.contains(mOftenTags.get(position).getName())) {
						toastMsg("已添加该标签");
					}
					if (edit.isEmpty()) {
						edit = mOftenTags.get(position).getName();
					} else {
						edit = edit + "," + mOftenTags.get(position).getName();
					}
					et_content.setText(edit);
				}

			}
		});

		mHotAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {


				if(isOne){
                    Log.e("kwan","back 。。"+mHotTags.get(position).getName());
					backWithResult(mHotTags.get(position).getName());
				}else {

					String edit = et_content.getText().toString();
					if (edit.contains(mHotTags.get(position).getName())) {
						toastMsg("已添加该标签");
					}
					if (edit.isEmpty()) {
						edit = mHotTags.get(position).getName();
					} else {
						edit = edit + "," + mHotTags.get(position).getName();
					}
					et_content.setText(edit);
				}
			}
		});


		//  fl_tag = (FlowLayout) findViewById(R.id.fl_tag);


		et_content.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				et_content.setSelection(s.length());//将光标移至文字末尾
			}
		});

		et_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
										  KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					String tag = et_content.getText().toString();
					if (!tag.isEmpty())
						backWithResult(tag);
				}
				return false;
			}
		});
	}

	@Override
	protected void initData() {
		showProgress("");
		mPresenter.getTags();
		mPresenter.getHotTags();
	}


	@Override
	protected BasePresenter getPresent() {
		return mPresenter;
	}

	@Override
	protected int getTitleBarRightLayoutId() {
		return R.layout.title_right_txt;
	}

	@Override
	protected void setUpTitleRightView(View v) {
		super.setUpTitleRightView(v);

		titleRight = (TextView) v;
		titleRight.setText("完成");
		titleRight.setTextColor(Color.RED);
		titleRight.setOnClickListener(this);

	}

	@Override
	protected String getTitleTxt() {
		return "添加标签";
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v == titleRight) {

			String tag = et_content.getText().toString();
			if (!tag.isEmpty())
				backWithResult(tag);
			else
				toastMsg("请填写标签");

		}

	}

	@Override
	public void getTagsSuccess(List<Tag> result) {


		Log.d("kwan", "tags::" + result.size());

//        mOftenTags.addAll(result);
//	    mOftenAdapter.notifyDataSetChanged();
		mOftenTags.addAll(result);
		mOftenAdapter.addData(result);

//        ViewGroup.MarginLayoutParams lp_tag = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
//        lp_tag.setMargins(0, 0, 20, 20);
//
//        for (final Tag tag : result) {
//
//            final TextView textView = new TextView(this);
//            textView.setText(tag.getName());
//            textView.setLayoutParams(lp_tag);
//            textView.setTextColor(getResources().getColor(R.color.redWin));
//            textView.setBackgroundResource(R.drawable.shape_red_bg);
//            textView.setPadding(20, 20, 20, 20);
//            textView.setTextSize(14);
//
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    String strTag = tag.getName();
//                    boolean isSelect;
//
//                    String str = et_content.getText().toString();
//                    if (str.contains(strTag + ",")) {
//                        str = str.replace(strTag + ",", "");
//                        isSelect = false;
//                    } else if (str.contains("," + strTag)) {
//                        str = str.replace("," + strTag, "");
//                        isSelect = false;
//                    } else if (str.contains(strTag)) {
//                        str = str.replace(strTag, "");
//                        isSelect = false;
//                    } else if (str.isEmpty()) {
//                        str = strTag;
//                        isSelect = true;
//                    } else {
//                        str = str + "," + strTag;
//                        isSelect = true;
//                    }
//
//                    if (isSelect) {
//                        textView.setBackgroundResource((R.drawable.shape_red_bg_full));
//                        textView.setTextColor(Color.WHITE);
//                    } else {
//                        textView.setBackgroundResource((R.drawable.shape_red_bg));
//                        textView.setTextColor(getResources().getColor(R.color.redWin));
//                    }
//
//                    et_content.setText(str);
//
//
//                    //backWithResult(tag.getName());
//                }
//            });
//            fl_tag.addView(textView);
//        }

		dismissProgress();
	}

	@Override
	public void getHotTagsSuccess(List<Tag> result) {

		Log.d("kwan", "hot tags::" + result.size());

//        mHotTags.addAll(result);
//        mHotAdapter.notifyDataSetChanged();
		mHotTags.addAll(result);
		mHotAdapter.addData(result);

	}

	void backWithResult(String tag) {
		Intent intent = new Intent();
		intent.putExtra("tag", tag);
		setResult(RESULT_OK, intent);
		onBackPressed();
	}


	class TextAdapter extends BaseQuickAdapter {


		public TextAdapter(Context context, int layoutResId, List data) {
			super(context, R.layout.list_item_tag, data);
		}

		@Override
		protected void convert(BaseViewHolder helper, Object item, int position) {

		}
	}

}
