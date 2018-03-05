package com.kwan.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.kwan.base.AppManager;
import com.kwan.base.LoadingDialog;
import com.kwan.base.R;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.presenter.IBaseView;
import com.kwan.base.util.ImageUtil;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * Activity 基类
 * Created by Mr.Kwan on 2016-9-30.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView, View.OnClickListener {

	public ImageUtil mImageUtil;
	public InputMethodManager mInputMethodManager;
	public BasePresenter mBasePresenter;
	protected AutoLinearLayout ll_base_main;
	private LoadingDialog loadingDialog;
	protected boolean isActive;
	protected View mBottomView;

	/**
	 * 生命周期 第一个方法 初始化布局，初始化Activity所需数据
	 *
	 * @param savedInstanceState
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.e("kwan",getTag()+" onCreate");

		beForeSetContentView();
		setContentView(R.layout.activity_base);
		addToActivityStack();
		initBaseViews();
		initViews();
		initViewSetting();
		initData();
		addOnSoftKeyBoardVisibleListener();
		into(savedInstanceState);
	}
	public void into(Bundle savedInstanceState) {
	}

	private void initBaseViews() {

		loadingDialog = new LoadingDialog(this);
		ll_base_main = (AutoLinearLayout) findViewById(R.id.ll_base_main);

		ViewStub stubTitleContent = (ViewStub) findViewById(R.id.view_stub_title);
		ViewStub stubMainContent = (ViewStub) findViewById(R.id.view_stub_content);
		ViewStub stubMainBottom = (ViewStub) findViewById(R.id.view_stub_bottom);

		if (stubMainContent != null) {
			stubMainContent.setLayoutResource(getContentViewId());
			stubMainContent.inflate();
		}
		//有自定义 title bar
		if (stubTitleContent != null && getTitleBarViewId() != 0) {
			stubTitleContent.setLayoutResource(getTitleBarViewId());
			stubTitleContent.inflate();
		}

		if (stubMainBottom != null && getBottomViewId() != 0) {
			stubMainBottom.setLayoutResource(getBottomViewId());
			mBottomView = stubMainBottom.inflate();
		}

		if (getBackGroundBitmap() != null)
			ll_base_main.setBackgroundDrawable(new BitmapDrawable(getResources(), getBackGroundBitmap()));
		else
			ll_base_main.setBackgroundColor(getBackGroundColor());

		StatusBarCompat.setStatusBarColor(this, Color.parseColor("#000000"));
	}

	protected int getBackGroundColor() {
		return getResources().getColor(R.color.colorPrimary);
	}

	private void removeFromActivitySetWithoutFinish() {
		AppManager.getAppManager().removeActivity(this);
	}

	protected void beForeSetContentView() {

		if (isFullScreen()) {
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		}

		init();
	}

	/**
	 * 相关初始化
	 */

	private void init() {

		//初始化图片工具
		mImageUtil = new ImageUtil();
		//软键盘工具
		mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		mBasePresenter = getPresent();
		if (mBasePresenter != null)
			mBasePresenter.onActivityCreate();
	}

	protected BasePresenter getPresent() {
		return null;
	}

	/**
	 * 设置 Activity 是否为全屏
	 *
	 * @return 是否为全屏
	 */

	public boolean isFullScreen() {
		return false;
	}

	/**
	 * 将Activity实例加入APP基类的堆栈中进行集中管理
	 */
	private void addToActivityStack() {
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public void toastMsg(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void showProgress(String txt) {
		if (loadingDialog != null && !loadingDialog.isShowing()) {
			loadingDialog.show();
			loadingDialog.setText(txt);
		}
	}

	protected String getTag() {
		return this.getClass().getSimpleName();
	}

	public Object getIntentData(String key) {

		if (getIntent().getExtras() != null)
			return getIntent().getExtras().get(key);
		else
			return null;
	}

	@Override
	public void dismissProgress() {
		if (loadingDialog != null)
			loadingDialog.closeDialog();
	}


	@Override
	public void onClick(View v) {

	}

	protected abstract Bitmap getBackGroundBitmap();

	protected abstract int getBottomViewId();

	protected abstract int getTitleBarViewId();

	protected abstract int getContentViewId();

	protected abstract void initViews();

	protected abstract void initViewSetting();

	protected abstract void initData();

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	/**
	 * Activity 正在启动，即将开始（在后台），可见但是没有出现在前台，无法交互。
	 * 已经显示但无法看到
	 */

	@Override
	protected void onStart() {
		super.onStart();
		isActive = true;
	}

	/**
	 * Activity 出现在前台显示
	 */
	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * Activty暂停,可以进行 数据存储，动画停止，
	 * 但不能太耗时，会影响新的Activity的onResume
	 */
	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * Activity停止，回收工作不能太耗时
	 */

	@Override
	protected void onStop() {
		super.onStop();
		isActive = false;
	}

	/**
	 * 生命周期最后一个函数，资源释放
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mBasePresenter != null)
			mBasePresenter.onActivityDestroy();
		removeFromActivitySetWithoutFinish();
	}

	/**
	 * Activity发生意外情况（屏幕方向改变等）下调用此方法（在onStop之前）
	 * 之后调用 onCreate --> onRestoreInstanceState
	 * <p>
	 * 此处保存数据，onRestoreInstanceState恢复数据
	 *
	 * @param outState           会传递给 onRestoreInstanceState onCreat
	 * @param outPersistentState
	 */

	@Override
	public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
		super.onSaveInstanceState(outState, outPersistentState);
	}

	/**
	 * onStart之后调用
	 *
	 * @param savedInstanceState
	 * @param persistentState
	 */

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
		super.onRestoreInstanceState(savedInstanceState, persistentState);
	}

	/**
	 * 当系统配置发生变化是 会使activity重新创建，可以在mainfast文件中配置
	 * android:configChanges = locale|orientation|keyboardHidden
	 * 等属性防止重新创建
	 *
	 * @param newConfig
	 */

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * 都是在当前 任务栈中。。。
	 * android:taskAffinity="zzz.zzz.zz"指定任务栈名称
	 * <p>
	 * singleTop（栈顶复用模式）<p/>
	 * <p>
	 * 启动模式下 如果新的Activty在当前任务栈的栈顶，不会创建新的Activity实例
	 * 会调用 onNewIntent方法获取intent
	 * 此时 onCreat,onStart不会调用，
	 * 如果 没有位于栈顶不会重复创建
	 * <p>
	 * <p/>
	 * singleTask（栈内复用模式）<p/>
	 * <p>
	 * 启动模式下 如果新的Activity在当前任务栈内，不会重复创建，将activity切到栈顶，并将
	 * Activity以上的所有Activity清空（默认带有clearTop属性）并且调用onNewIntent
	 * <p>
	 * <p/>
	 * singleInstance(单例模式)<p/>
	 * 具有singleTask所有特性 且会单出纯在一个任务栈中，后续打开 回调onNewIntent
	 * <p>
	 * <p/>
	 * IntentFilter
	 * <p>
	 * 隐式启动 需要同时匹配 action category data
	 * 一个Activity可以有多个 intent-filter,匹配任何一组 即可启动
	 * <p>
	 * action ： 匹配区分大小写，多个action ，匹配一个即可
	 *
	 * @param intent
	 */

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	public void go2ActivityWithLeft(Class<? extends Activity> activity, Bundle bundle, boolean isFinish) {

		Intent intent = new Intent(this, activity);
		if (bundle != null)
			intent.putExtras(bundle);
		startActivity(intent);

		// if (enterAnim != 0 && exitAnim != 0)
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		if (isFinish)
			onBackPressed();

	}

	public void go2Activity(Class<? extends Activity> activity, Bundle bundle, boolean isFinish, int enterAnim, int exitAnim) {

		Intent intent = new Intent(this, activity);
		if (bundle != null)
			intent.putExtras(bundle);
		startActivity(intent);
		// if (enterAnim != 0 && exitAnim != 0)
		overridePendingTransition(enterAnim, exitAnim);
		if (isFinish)
			onBackPressed();

	}

	public void go2Activity(Class<? extends Activity> activity, Bundle bundle, boolean isFinish) {

		Intent intent = new Intent(this, activity);
		if (bundle != null)
			intent.putExtras(bundle);
		startActivity(intent);
		if (isFinish)
			onBackPressed();
	}

	@Override
	public void onNoNetWork() {

		//Looper.loop();

	}

	@Override
	public void onServerError(int vocational_id, Throwable e) {
		dismissProgress();
	}

	@Override
	public void onServerFailed(String s) {
		dismissProgress();
	}


	boolean sLastVisiable = false;
	/**
	 * 监听软键盘状态
	 */
	private void addOnSoftKeyBoardVisibleListener() {

		final View decorView = getWindow().getDecorView();

		decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {

				Rect rect = new Rect();
				decorView.getWindowVisibleDisplayFrame(rect);
				int displayHight = rect.bottom - rect.top;
				int hight = decorView.getHeight();
				boolean visible = (double) displayHight / hight < 0.8;

//                Log.d(getTag(), "DecorView display hight = " + displayHight);
//                Log.d(getTag(), "DecorView hight = " + hight);
//                Log.d(getTag(), "softkeyboard visible = " + visible);

				if (visible != sLastVisiable) {
					onSoftKeyBoardVisible(visible);
				}
				sLastVisiable = visible;
			}
		});
	}

	protected void onSoftKeyBoardVisible(boolean visible) {
		Log.e(getTag(), "onSoftKeyBoardVisible::" + visible);
	}

	public void go2ActivityForResult(Class<? extends Activity> activity, int requestcode, Bundle bundle, int enterAnim, int exitAnim) {

		Intent intent = new Intent(this, activity);
		if (bundle != null)
			intent.putExtras(bundle);
		startActivityForResult(intent, requestcode);

		if(enterAnim!=0&&exitAnim!=0)

		overridePendingTransition(enterAnim, exitAnim);
	}

}
