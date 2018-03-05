package com.metshow.bz.util;

import android.Manifest;
import android.app.Dialog;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwan.base.activity.BaseActivity;
import com.kwan.base.util.ImageUtil;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.adapter.BZViewPageAdapter;
import com.metshow.bz.commons.adapter.FaceAdapter;
import com.metshow.bz.commons.widget.CirclePageIndicator;
import com.yongchun.library.view.ImageSelectorActivity;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Mr.Kwan on 2017-7-10.
 */

public abstract class EditDialog implements View.OnClickListener {

	public static final int NUM_PAGE = 6;
	public static final int NUM = 20;
	private EditText et_content;
	private ViewPager mFaceViewPager;
	int mCurrentPage = 0;
	BaseActivity mBaseActivity;
	Dialog dialog;
	View view;
	protected ArrayList<String> mFaceMapKeys;
	ImageView iv_emo;
	ImageView iv_camera;
	AutoLinearLayout mFaceRoot;
	private TextView tv_icon_send;
	protected boolean mIsFaceShow = false;
	protected final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;

	public void cancel(){
		dialog.cancel();
	}

	public EditDialog(BaseActivity activity) {

		mBaseActivity = activity;

		// 将表情map的key保存在数组中
		Set<String> keySet = App.getInstance().getFaceMap().keySet();
		mFaceMapKeys = new ArrayList<>();
		mFaceMapKeys.addAll(keySet);

		dialog = new Dialog(mBaseActivity, R.style.EditDialogStyle);
		view = mBaseActivity.getLayoutInflater().inflate(R.layout.layout_dialog_edit, null);
		dialog.setContentView(view);

		iv_emo = (ImageView) view.findViewById(R.id.iv_emo);
		iv_camera = (ImageView) view.findViewById(R.id.iv_camera);
		mFaceRoot = (AutoLinearLayout) view.findViewById(R.id.ll_face);
		mFaceViewPager = (ViewPager) view.findViewById(R.id.face_pager);
		et_content = (EditText) view.findViewById(R.id.et_content);

		tv_icon_send = (TextView) view.findViewById(R.id.tv_icon_send);

		tv_icon_send.setOnClickListener(this);
		iv_emo.setOnClickListener(this);
		iv_camera.setOnClickListener(this);
		et_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
										  KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND) {

					Log.e("kwan","send");
					onSend(et_content.getText().toString());
					et_content.setText("");
					dialog.cancel();
					//recycleMoveEnd();
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

		initFacePage();
	}

	public Dialog show() {

		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = App.SCREEN_WIDTH;
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		window.setWindowAnimations(R.style.MenuDialogAnimation); // 添加动画
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		et_content.setFocusable(true);
		et_content.setFocusableInTouchMode(true);
		et_content.requestFocus();
		((InputMethodManager) mBaseActivity.getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(et_content, 0);
		mFaceRoot.setVisibility(View.GONE);
		return dialog;

	}

	private void initFacePage() {

		List<View> lv = new ArrayList<>();
		for (int i = 0; i < NUM_PAGE; ++i)
			lv.add(getGridView(i));

		BZViewPageAdapter<View> adapter = new BZViewPageAdapter<>(lv);

		mFaceViewPager.setAdapter(adapter);
		mFaceViewPager.setCurrentItem(mCurrentPage);

		CirclePageIndicator indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
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
		GridView gv = new GridView(mBaseActivity);
		gv.setNumColumns(7);
		gv.setSelector(new ColorDrawable(Color.TRANSPARENT));// 屏蔽GridView默认点击效果
		gv.setBackgroundColor(Color.TRANSPARENT);
		gv.setCacheColorHint(Color.TRANSPARENT);
		gv.setHorizontalSpacing(1);
		gv.setVerticalSpacing(1);
		gv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		gv.setGravity(Gravity.CENTER);
		gv.setAdapter(new FaceAdapter(mBaseActivity, i));

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
							mBaseActivity.getResources(), (Integer) App.getInstance()
									.getFaceMap().values().toArray()[count]);

					if (bitmap != null) {

						ImageSpan imageSpan = new ImageSpan(mBaseActivity, ImageUtil.scaleBitmap(bitmap, 60, 60));
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

	@Override
	public void onClick(View v) {
		if (v == iv_emo) {
			if (!mIsFaceShow) {
				mBaseActivity.mInputMethodManager.hideSoftInputFromWindow(et_content.getWindowToken(), 0);
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
				mBaseActivity.mInputMethodManager.showSoftInput(et_content, 0);
			}
		} else if (v == iv_camera) {
			if (ContextCompat.checkSelfPermission(mBaseActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
					!= PackageManager.PERMISSION_GRANTED) {
				//申请WRITE_EXTERNAL_STORAGE权限
				ActivityCompat.requestPermissions(mBaseActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
						WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
			} else {
				ImageSelectorActivity.start(mBaseActivity, 1, ImageSelectorActivity.MODE_MULTIPLE, true, false, false);
			}
		}else if(v==tv_icon_send){
			onSend(et_content.getText().toString());
			et_content.setText("");
			dialog.cancel();
			//recycleMoveEnd();
		}

	}

	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

		if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				ImageSelectorActivity.start(mBaseActivity, 1, ImageSelectorActivity.MODE_MULTIPLE, true, false, false);
			} else {
				mBaseActivity.toastMsg("请允许应用访问图片");
			}
		}
	}


	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
			ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
			onSelectImageBack(images.get(0));
		}
	}


	protected abstract void onSend(String s);
	protected abstract void onSelectImageBack(String s);

}
