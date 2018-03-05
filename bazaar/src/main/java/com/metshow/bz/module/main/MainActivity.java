package com.metshow.bz.module.main;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.kwan.base.activity.BaseActivity;
import com.kwan.base.adatper.CommonFragmentPageAdapter;
import com.kwan.base.transformer.DepthPageTransformer;
import com.kwan.base.util.SPUtil;
import com.kwan.base.widget.SlidingTabLayout;
import com.metshow.bz.IUmengEvent;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.DaySign;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.commons.service.ChkVersionService;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.buy.BuyFragment;
import com.metshow.bz.module.in.fragment.InFragment;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.module.me.fragment.MeFragment;
import com.metshow.bz.module.message.MessageActivity;
import com.metshow.bz.module.search.ArticleSearchActivity;
import com.metshow.bz.module.search.BuySearchActivity;
import com.metshow.bz.module.search.SearchActivity;
import com.metshow.bz.module.shai.fragment.ShaiFragment;
import com.metshow.bz.presenter.FriendDetailPresenter;
import com.metshow.bz.presenter.MainPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.metshow.bz.util.ShareUtil;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, SlidingTabLayout.OnTabClickLister, BZContract.IMainView, ChkVersionService.VersionUpdateCallBack, BZContract.IFriendDetailView {

	private ImageView iv_title_search, iv_title_logo, iv_title_chat;
	private TextView tv_title_txt;
	private SlidingTabLayout stl_shai_tabs;
	private ImageView iv_tab_in, iv_tab_shai, iv_tab_buy, iv_tab_me;
	private AutoLinearLayout ll_bottom_tab;
	public ViewPager vp_content;

	private boolean hasNewMsg = false;
	private MainPresenter mPresenter;
	private FriendDetailPresenter mFriPresenter;

	private int current;
	private final int INDEX_IN = 0;
	private final int INDEX_SHAI = 1;
	private final int INDEX_BUY = 2;
	private final int INDEX_ME = 3;

	private int FollowedBadgeCount;
	private int ReplyBadgeCount;
	private int FavBadgeCount;
	private int MessageBadgeCount;
	private int SystemMsgBadgeCount;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mPresenter = new MainPresenter(this);
		mFriPresenter = new FriendDetailPresenter(this);
	}

	@Override
	protected Bitmap getBackGroundBitmap() {
		return null;
	}

	@Override
	protected int getBottomViewId() {
		return 0;
	}

	@Override
	protected int getTitleBarViewId() {
		return 0;
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_main;
	}

	@Override
	protected void initViews() {

		//顶部标题栏控件
		iv_title_search = (ImageView) findViewById(R.id.iv_title_search);
		iv_title_logo = (ImageView) findViewById(R.id.iv_title_logo);
		iv_title_chat = (ImageView) findViewById(R.id.iv_title_chat);
		tv_title_txt = (TextView) findViewById(R.id.tv_title_txt);
		stl_shai_tabs = (SlidingTabLayout) findViewById(R.id.stl_shai_tabs);
		vp_content = (ViewPager) findViewById(R.id.vp_content);
		ll_bottom_tab = (AutoLinearLayout) findViewById(R.id.ll_bottom_tab);
		ll_bottom_tab.bringToFront();
		//底部tab
		iv_tab_in = (ImageView) findViewById(R.id.iv_tab_in);
		iv_tab_shai = (ImageView) findViewById(R.id.iv_tab_shai);
		iv_tab_buy = (ImageView) findViewById(R.id.iv_tab_buy);
		iv_tab_me = (ImageView) findViewById(R.id.iv_tab_me);

		int red = getResources().getColor(R.color.bazzarRed);
		stl_shai_tabs.setTitleTextColor(red, getResources().getColor(R.color.txt_cobalt_blue));//标题字体颜色
		// mTabLayout.setTabStripWidth(110);//滑动条宽度
		stl_shai_tabs.setSelectedIndicatorColors(getResources().getColor(R.color.transparent));//滑动条颜色
		stl_shai_tabs.setTabTitleTextSize(14);//标题字体大小
		stl_shai_tabs.setDistributeEvenly(true); //均匀平铺选项卡
		stl_shai_tabs.setOnTabClickLister(this);
		stl_shai_tabs.setUp(new String[]{"发现", "圈子", "活动"});

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (current == INDEX_ME && !SPUtil.getIsLogin())
			vp_content.setCurrentItem(0);

	}

	@Override
	protected void initViewSetting() {

		iv_title_search.setOnClickListener(this);

		iv_tab_in.setSelected(true);
		iv_tab_in.setOnClickListener(this);
		iv_tab_shai.setOnClickListener(this);
		iv_tab_buy.setOnClickListener(this);
		iv_tab_me.setOnClickListener(this);
		ll_bottom_tab.setOnClickListener(this);
		iv_title_chat.setOnClickListener(this);

		CommonFragmentPageAdapter mPageAdapter = new CommonFragmentPageAdapter(getSupportFragmentManager());

		mPageAdapter.addFragment(InFragment.newInstance(), "in");
		mPageAdapter.addFragment(ShaiFragment.newInstance(), "shai");
		mPageAdapter.addFragment(BuyFragment.newInstance(), "buy");
		mPageAdapter.addFragment(MeFragment.newInstance(), "me");
		ViewPager.PageTransformer transformer = new DepthPageTransformer();
		vp_content.setPageTransformer(true, transformer);
		vp_content.addOnPageChangeListener(this);
		vp_content.setAdapter(mPageAdapter);

		onPageSelected(0);
	}

	@Override
	protected void initData() {

		if (isShowDaySign())
			mPresenter.getDaySign();
		doBindService();
	}

	private boolean isShowDaySign() {

		if (SPUtil.getNextDate() == 0) {

			Date date = new Date(System.currentTimeMillis());
			Log.e("kwan", "currentTimeMillis::" + System.currentTimeMillis());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {

				Date date2 = format.parse(format.format(date));
				long time = date2.getTime();
				Log.e("kwan", "time2::" + time);
				time = time + 86400000;
				SPUtil.setNextDate(time);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			return true;
		} else {

			if (System.currentTimeMillis() >= SPUtil.getNextDate()) {
				SPUtil.setNextDate(SPUtil.getNextDate() + 86400000);
				return true;
			} else
				return false;
		}


	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {

			case R.id.iv_title_search:
				goSearchActivity();
				break;
			case R.id.iv_tab_in:
				vp_content.setCurrentItem(0);
				break;
			case R.id.iv_tab_shai:
				vp_content.setCurrentItem(1);
				break;
			case R.id.iv_tab_buy:
				vp_content.setCurrentItem(2);
				break;
			case R.id.iv_tab_me:
				if (SPUtil.getIsLogin())
					vp_content.setCurrentItem(3);
				else
					go2ActivityWithLeft(DologinActivity.class, null, false);
				break;
			case R.id.iv_title_chat:
				if (SPUtil.getIsLogin())
					go2ActivityWithLeft(MessageActivity.class, null, false);
				else
					go2ActivityWithLeft(DologinActivity.class, null, false);
				break;
		}
	}


	@Override
	public void onNoNetWork() {

	}

	@Override
	public void onServerError(int vocational_id, Throwable e) {

	}

	@Override
	public void onServerFailed(String s) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}


	@Override
	public void onPageSelected(int position) {

		current = position;

		switch (current) {

			case INDEX_IN:
				App.umengEvent(this, IUmengEvent.ModuleBrowse, "in");
				iv_title_logo.setVisibility(View.VISIBLE);
				tv_title_txt.setVisibility(View.GONE);
				stl_shai_tabs.setVisibility(View.GONE);
				iv_title_chat.setVisibility(View.GONE);
				iv_tab_in.setSelected(true);
				iv_tab_shai.setSelected(false);
				iv_tab_buy.setSelected(false);
				iv_tab_me.setSelected(false);
				break;
			case INDEX_SHAI:

				mFriPresenter.getUserInfo(BZSPUtil.getUser().getUserId() + "");

				App.umengEvent(this, IUmengEvent.ModuleBrowse, "晒");
				stl_shai_tabs.setVisibility(View.VISIBLE);
				iv_title_logo.setVisibility(View.GONE);
				tv_title_txt.setVisibility(View.GONE);

				if (SPUtil.getIsLogin() && hasNewMsg)
					iv_title_chat.setImageResource(R.mipmap.article_comment_icon);
				else
					iv_title_chat.setImageResource(R.mipmap.article_comment_normal_icon);
				iv_title_chat.setVisibility(View.VISIBLE);

				iv_tab_shai.setSelected(true);
				iv_tab_in.setSelected(false);
				iv_tab_buy.setSelected(false);
				iv_tab_me.setSelected(false);
				break;
			case INDEX_BUY:
				App.umengEvent(this, IUmengEvent.ModuleBrowse, "买");
				tv_title_txt.setText("商城");
				tv_title_txt.setVisibility(View.VISIBLE);
				stl_shai_tabs.setVisibility(View.GONE);
				iv_title_logo.setVisibility(View.GONE);
				iv_title_chat.setVisibility(View.GONE);
				iv_tab_buy.setSelected(true);
				iv_tab_in.setSelected(false);
				iv_tab_shai.setSelected(false);
				iv_tab_me.setSelected(false);
				break;

			case INDEX_ME:

				mFriPresenter.getUserInfo(BZSPUtil.getUser().getUserId() + "");

				if (SPUtil.getIsLogin()) {

					App.umengEvent(this, IUmengEvent.ModuleBrowse, "我");
					tv_title_txt.setText("我");
					tv_title_txt.setVisibility(View.VISIBLE);
					stl_shai_tabs.setVisibility(View.GONE);
					iv_title_logo.setVisibility(View.GONE);
					if (SPUtil.getIsLogin() && hasNewMsg)
						iv_title_chat.setImageResource(R.mipmap.article_comment_icon);
					else
						iv_title_chat.setImageResource(R.mipmap.article_comment_normal_icon);
					iv_title_chat.setVisibility(View.VISIBLE);
					iv_tab_me.setSelected(true);
					iv_tab_buy.setSelected(false);
					iv_tab_in.setSelected(false);
					iv_tab_shai.setSelected(false);
				} else {
					vp_content.setCurrentItem(2);
					go2ActivityForResult(DologinActivity.class, 100, null, R.anim.push_left_in, R.anim.push_left_out);
				}

				break;
		}

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onTabClick(int position) {
		mShaiFragmentChangeListener.OnChange(position);
	}

	private void goSearchActivity() {
		switch (current) {
			case INDEX_IN:
				go2Activity(ArticleSearchActivity.class, null, false);
				break;
			case INDEX_SHAI:
				go2Activity(SearchActivity.class, null, false);
				break;
			case INDEX_BUY:
				go2Activity(BuySearchActivity.class, null, false);
				break;
			case INDEX_ME:
				go2Activity(SearchActivity.class, null, false);
				break;
		}
	}

	private OnMainActivtyShaiFragmentChangeListener mShaiFragmentChangeListener;

	public void setShaiFragmentChangeListener(OnMainActivtyShaiFragmentChangeListener shaiFragmentChangeListener) {
		this.mShaiFragmentChangeListener = shaiFragmentChangeListener;
	}

	protected Dialog popupWindow;

	@Override
	public void getDaySignSuccess(final DaySign result) {

		ViewGroup parent = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
		View dialogView = getLayoutInflater().inflate(R.layout.daysign_dialog, parent, false);

		ImageView iv_icon = (ImageView) dialogView.findViewById(R.id.iv_icon);
		ImageView iv_close = (ImageView) dialogView.findViewById(R.id.iv_close);
		TextView tv_share = (TextView) dialogView.findViewById(R.id.tv_share);

		popupWindow = new Dialog(this, R.style.MenuDialogStyle);
		popupWindow.setContentView(dialogView);

		Window window = popupWindow.getWindow();
		int dialog_width = App.SCREEN_WIDTH * 3 / 4;
		int dialog_height = App.SCREEN_HEIGHT * 4 / 5;
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = dialog_width;
		lp.height = dialog_height;

		int _50px = App.SCREEN_WIDTH * 50 / 750;
		int _25px = _50px / 2;
		int _40px = _50px / 5 * 4;

		AutoRelativeLayout.LayoutParams lp_close = new AutoRelativeLayout.LayoutParams(_50px, _50px);
		lp_close.addRule(AutoRelativeLayout.ALIGN_PARENT_RIGHT);
		iv_close.setLayoutParams(lp_close);

		AutoRelativeLayout.LayoutParams lp_icon = new AutoRelativeLayout.LayoutParams(AutoRelativeLayout.LayoutParams.MATCH_PARENT, AutoRelativeLayout.LayoutParams.MATCH_PARENT);
		lp_icon.setMargins(0, _25px, _25px, 0);
		iv_icon.setLayoutParams(lp_icon);

		tv_share.setTextSize(TypedValue.COMPLEX_UNIT_PX, _40px);

		AutoLinearLayout.LayoutParams lp_share = new AutoLinearLayout.LayoutParams(AutoLinearLayout.LayoutParams.MATCH_PARENT, AutoLinearLayout.LayoutParams.WRAP_CONTENT);
		lp_share.setMargins(0, _25px, _25px, 0);
//		lp_share.addRule(AutoRelativeLayout.ALIGN_PARENT_BOTTOM);
		tv_share.setLayoutParams(lp_share);

		mImageUtil.loadFromUrlWithRound(result.getPic(), iv_icon);

		tv_share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				ShareUtil util = new ShareUtil(MainActivity.this);
				util.setShare_Type(IUmengEvent.DayShare);
				util.setStr_img(result.getPic());
				util.setStr_title("芭莎in每日美图");
				util.setmText(result.getRemark());
				util.showShareDialog();
			}
		});

		iv_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				closePopupWindow();
			}
		});
		popupWindow.show();
	}

	private void closePopupWindow() {

		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			popupWindow = null;
		}
	}

	@Override
	public void chkUpdateStarted() {

	}

	@Override
	public void chkUpdateEnded() {
		doUnbindService();
	}

	private void doUnbindService() {
		if (mIsBind) {
			unbindService(mSc);
			mIsBind = false;
		}
	}

	@Override
	public void getUserInfoSuccess(User user) {


		Log.e("kwan","main act: user info "+user.toString());

		FollowedBadgeCount = user.getFollowedBadgeCount();
		ReplyBadgeCount = user.getMessageBadgeCount();
		FavBadgeCount = user.getFavBadgeCount();
		MessageBadgeCount = user.getMessageBadgeCount();
		SystemMsgBadgeCount = user.getSystemMsgBadgeCount();

		if (FollowedBadgeCount + ReplyBadgeCount + FavBadgeCount + MessageBadgeCount + SystemMsgBadgeCount > 0) {
			iv_title_chat.setImageResource(R.mipmap.article_comment_icon);
		} else
			iv_title_chat.setImageResource(R.mipmap.article_comment_normal_icon);

	}

	@Override
	public void getUserTopicsSuccess(List<Topic> topics) {

	}

	@Override
	public void getUserTagsSuccess(List<Tag> tags) {

	}

	@Override
	public void addFollowSuccess() {

	}

	@Override
	public void deleteFollowSuccess() {

	}

	@Override
	public void favSuccess(String result, int position) {

	}

	@Override
	public void deleteFavSuccess(String result, int type, int position, int fragmentId) {

	}

	@Override
	public void reportSuccess(Boolean result, int position) {

	}

	public interface OnMainActivtyShaiFragmentChangeListener {
		void OnChange(int position);
	}

	public void dogo(View view, Intent intent) {
		ActivityTransitionLauncher.with(this).from(view).launch(intent);
	}


	private ChkVersionService mChkVersionServiceInstance;
	private Intent chkVersion;
	private boolean mIsBind;

	private void doBindService() {

		chkVersion = new Intent(this, ChkVersionService.class);
		bindService(chkVersion, mSc, Context.BIND_AUTO_CREATE);
		mIsBind = true;
	}

	private ServiceConnection mSc = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			if (mChkVersionServiceInstance != null) {
				mChkVersionServiceInstance = null;
			}
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.e("lee", "onServiceConnected");

			mChkVersionServiceInstance = ((ChkVersionService.ChkVersionServiceBindler) service)
					.getChkVersionServiceInstance(MainActivity.this);
			if (mChkVersionServiceInstance != null) {
				mChkVersionServiceInstance.setVersionUpdateCallBack(MainActivity.this);
				mChkVersionServiceInstance.startCheck();
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK)
			vp_content.setCurrentItem(INDEX_ME);
		else
			vp_content.setCurrentItem(INDEX_BUY);
	}


}
