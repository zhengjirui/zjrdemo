package com.metshow.bz.module.commons.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.kwan.base.activity.BaseCommonActivity;
import com.kwan.base.util.ImageUtil;
import com.kwan.base.util.ViewUtil;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.adapter.BZViewPageAdapter;
import com.metshow.bz.commons.adapter.FaceAdapter;
import com.metshow.bz.commons.widget.CirclePageIndicator;
import com.yongchun.library.view.ImageSelectorActivity;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class BaseChatActivity extends BaseCommonActivity {

	protected SwipeToLoadLayout swipeToLoadLayout;
	protected AutoLinearLayout mFaceRoot;
	protected ViewPager mFaceViewPager;
	protected EditText et_content;
	protected int mCurrentPage = 0;
	protected ArrayList<String> mFaceMapKeys;
	protected boolean mIsFaceShow = false;
	protected ImageView iv_emo, iv_camera;
	protected ObservableRecyclerView mRecyclerView;
	protected final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
	protected AutoLinearLayout ll_edit;
	protected AutoFrameLayout fl_pop;
	protected ImageView iv_delete;

	protected TextView tv_icon_send;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		// 将表情map的key保存在数组中
		Set<String> keySet = App.getInstance().getFaceMap().keySet();
		mFaceMapKeys = new ArrayList<>();
		mFaceMapKeys.addAll(keySet);
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_chat;
	}

	@Override
	protected void initViews() {
		super.initViews();

		swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
		mRecyclerView = (ObservableRecyclerView) findViewById(R.id.swipe_target);

		fl_pop = (AutoFrameLayout) findViewById(R.id.fl_pop);
		ll_edit = (AutoLinearLayout) findViewById(R.id.ll_edit);
		et_content = (EditText) findViewById(R.id.et_content);
		iv_emo = (ImageView) findViewById(R.id.iv_emo);
		iv_camera = (ImageView) findViewById(R.id.iv_camera);
		mFaceRoot = (AutoLinearLayout) findViewById(R.id.ll_face);
		mFaceViewPager = (ViewPager) findViewById(R.id.face_pager);
		iv_delete = (ImageView) findViewById(R.id.iv_delete);

		tv_icon_send = (TextView) findViewById(R.id.tv_icon_send);

		initFacePage();
	}

	@Override
	protected void initViewSetting() {
		super.initViewSetting();
		tv_icon_send.setOnClickListener(this);
		iv_emo.setOnClickListener(this);
		iv_camera.setOnClickListener(this);
		et_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
										  KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					onSend(et_content.getText().toString());
					et_content.setText("");
					recycleMoveEnd();
				}
				return false;
			}
		});

		et_content.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				iv_emo.setImageResource(R.mipmap.zan_normal_icon);
				mIsFaceShow = false;
				mFaceRoot.setVisibility(View.GONE);
				return false;
			}
		});

		setSwipeMargins(true);
	}

	public void setSwipeMargins(boolean isMargin) {

		FrameLayout.LayoutParams lpar = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

		if (isMargin) {
			ViewUtil.measureView(ll_edit);

			Log.e("kwan","ll_edit.getMeasuredHeight()"+ll_edit.getMeasuredHeight());

			lpar.setMargins(0, 0, 0, ll_edit.getMeasuredHeight());
		} else {
			lpar.setMargins(0, 0, 0, 0);
		}

		swipeToLoadLayout.setLayoutParams(lpar);

	}

	protected abstract void onSend(String s);

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v == iv_emo) {

			if (!mIsFaceShow) {

				mInputMethodManager.hideSoftInputFromWindow(et_content.getWindowToken(), 0);
				try {
					Thread.sleep(80);// 解决此时会黑一下屏幕的问题
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				iv_emo.setImageResource(R.mipmap.chat_type_icon);
				mIsFaceShow = true;
				mFaceRoot.setVisibility(View.VISIBLE);

			} else {

				iv_emo.setImageResource(R.mipmap.zan_normal_icon);
				mIsFaceShow = false;
				mFaceRoot.setVisibility(View.GONE);
				mInputMethodManager.showSoftInput(et_content, 0);

			}
		} else if (v == iv_camera) {
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
					!= PackageManager.PERMISSION_GRANTED) {
				//申请WRITE_EXTERNAL_STORAGE权限
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
						WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
			} else {
				ImageSelectorActivity.start(this, 1, ImageSelectorActivity.MODE_MULTIPLE, true, false, false);
			}
		} else if (v == tv_icon_send) {
			onSend(et_content.getText().toString());
			et_content.setText("");
			recycleMoveEnd();
		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				ImageSelectorActivity.start(this, 1, ImageSelectorActivity.MODE_MULTIPLE, true, false, false);
			} else {
				toastMsg("请允许应用访问图片");
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
			ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
			onSelectImageBack(images.get(0));
		}
	}

	protected abstract void onSelectImageBack(String s);

	public static final int NUM_PAGE = 6;
	public static final int NUM = 20;

	private void initFacePage() {

		List<View> lv = new ArrayList<>();
		for (int i = 0; i < NUM_PAGE; ++i)
			lv.add(getGridView(i));

		BZViewPageAdapter<View> adapter = new BZViewPageAdapter<>(lv);

		mFaceViewPager.setAdapter(adapter);
		mFaceViewPager.setCurrentItem(mCurrentPage);

		CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(mFaceViewPager);
		adapter.notifyDataSetChanged();
		mFaceRoot.setVisibility(View.GONE);

		indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mCurrentPage = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// do nothing
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// do nothing
			}
		});
	}

	private GridView getGridView(int i) {

		// TODO Auto-generated method stub
		GridView gv = new GridView(this);
		gv.setNumColumns(7);
		gv.setSelector(new ColorDrawable(Color.TRANSPARENT));// 屏蔽GridView默认点击效果
		gv.setBackgroundColor(Color.TRANSPARENT);
		gv.setCacheColorHint(Color.TRANSPARENT);
		gv.setHorizontalSpacing(1);
		gv.setVerticalSpacing(1);
		gv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		gv.setGravity(Gravity.CENTER);
		gv.setAdapter(new FaceAdapter(this, i));

		//防止乱滚动
		gv.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					return true;
				}
				return false;
			}
		});
		gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == NUM) {// 删除键的位置
					int selection = et_content.getSelectionStart();
					String text = et_content.getText().toString();
					if (selection > 0) {
						String text2 = text.substring(selection - 1);
						if ("]".equals(text2)) {
							int start = text.lastIndexOf("[");
							int end = selection;
							et_content.getText().delete(start, end);
							return;
						}
						et_content.getText()
								.delete(selection - 1, selection);
					}
				} else {
					int count = mCurrentPage * NUM + arg2;
					// 注释的部分，在EditText中显示字符串

					// 下面这部分，在EditText中显示表情
					Bitmap bitmap = BitmapFactory.decodeResource(
							getResources(), (Integer) App.getInstance()
									.getFaceMap().values().toArray()[count]);

					if (bitmap != null) {

						ImageSpan imageSpan = new ImageSpan(BaseChatActivity.this, ImageUtil.scaleBitmap(bitmap, 60, 60));
						String emojiStr = mFaceMapKeys.get(count);
						SpannableString spannableString = new SpannableString(emojiStr);
						spannableString.setSpan(imageSpan,
								emojiStr.indexOf('['),
								emojiStr.indexOf(']') + 1,
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						et_content.append(spannableString);

					} else {

						String ori = et_content.getText().toString();
						int index = et_content.getSelectionStart();
						StringBuilder stringBuilder = new StringBuilder(ori);
						stringBuilder.insert(index, mFaceMapKeys.get(count));
						et_content.setText(stringBuilder.toString());
						et_content.setSelection(index
								+ mFaceMapKeys.get(count).length());
					}
				}
			}
		});
		return gv;
	}

	public void recycleMoveEnd() {
		mRecyclerView.postDelayed(new Runnable() {
			@Override
			public void run() {
				mRecyclerView.scrollVerticallyTo(0);
			}
		}, 100);
	}
}
