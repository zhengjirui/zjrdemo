package com.kwan.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Toast;

import com.kwan.base.R;
import com.kwan.base.activity.BaseActivity;
import com.kwan.base.presenter.IBaseView;
import com.kwan.base.util.ImageUtil;


/**
 * fragment基类
 * Created by Mr.Kwan on 2016-5-4.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener, IBaseView {

    public static boolean LOG = false;
    protected LayoutInflater mInflater;
    protected BaseActivity mBaseActivity;
	protected Context mContext;
	/**
	 * fragment最外层包装view
	 */
	protected View mBaseContentView;
	protected View mBottomView;
	protected View mTitleView;
	protected View mContentView;
    protected ImageUtil mImageUtil;

	private boolean isActive;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
		mContext = context;
		mBaseActivity = (BaseActivity) context;
        mInflater = mBaseActivity.getLayoutInflater();
		mImageUtil = mBaseActivity.mImageUtil;
      //  mImageUtil = new ImageUtil(mBaseActivity);
        if (LOG)
            Log.d(getTag(), "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		initBaseContentView();
        if (LOG)
            Log.d(getTag(), "onCreate");
    }

	@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (LOG)
            Log.d(getTag(), "onCreateView");
        return mBaseContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (LOG)
            Log.d(getTag(), "onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (LOG)
            Log.d(getTag(), "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
		isActive = true;
        if (LOG)
            Log.d(getTag(), "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
      //  MobclickAgent.onResume(getActivity());
        if (LOG)
            Log.d(getTag(), "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
		isActive = false;
      //  MobclickAgent.onPause(getActivity());
        if (LOG)
            Log.d(getTag(), "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (LOG)
            Log.d(getTag(), "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (LOG)
            Log.d(getTag(), "onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (LOG)
            Log.d(getTag(), "onDetach");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onNoNetWork() {

    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(mBaseActivity, msg, Toast.LENGTH_SHORT).show();
    }

	/**
	 * frament 是否暂停
	 * @return
	 */
	@Override
	public boolean isActive() {
		return isActive;
	}

	private void initBaseContentView() {

		mBaseContentView = mInflater.inflate(R.layout.fragment_base, null);
		ViewStub stubTitleContent = (ViewStub) mBaseContentView.findViewById(R.id.view_stub_main_title);
		ViewStub stubMainContent = (ViewStub) mBaseContentView.findViewById(R.id.view_stub_main_content);
		ViewStub stubMainBottom = (ViewStub) mBaseContentView.findViewById(R.id.view_stub_main_bottom);

		if (stubMainContent != null) {
			stubMainContent.setLayoutResource(getContentViewId());
			mContentView = stubMainContent.inflate();
		}
		//有自定义 title bar
		if (stubTitleContent != null && getTitleBarViewId() != 0) {
			stubTitleContent.setLayoutResource(getTitleBarViewId());
			mTitleView = stubTitleContent.inflate();
		}

		if (stubMainBottom != null && getBottomViewId() != 0) {
			stubMainBottom.setLayoutResource(getBottomViewId());
			mBottomView = stubMainBottom.inflate();
		}


	}

    public void go2ActivityWithLeft(Class<? extends Activity> activity, Bundle bundle, boolean isFinish) {

        Intent intent = new Intent(mBaseActivity, activity);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);

        // if (enterAnim != 0 && exitAnim != 0)
        mBaseActivity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        if (isFinish)
            mBaseActivity.onBackPressed();

    }

    public void go2Activity(Class<? extends Activity> activity, Bundle bundle, boolean isFinish, int enterAnim, int exitAnim) {

        Intent intent = new Intent(mBaseActivity, activity);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
        // if (enterAnim != 0 && exitAnim != 0)
        mBaseActivity.overridePendingTransition(enterAnim, exitAnim);
        if (isFinish)
            mBaseActivity.onBackPressed();

    }

    public void go2Activity(Class<? extends Activity> activity, Bundle bundle, boolean isFinish) {

        Intent intent = new Intent(mBaseActivity, activity);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
        if (isFinish)
            mBaseActivity.onBackPressed();
    }





	protected int getTitleBarViewId() {
		return 0;
	}

	protected int getBottomViewId() {
		return 0;
	}

	protected abstract int getContentViewId();

	@Override
	public void showProgress(String txt) {
		mBaseActivity.showProgress(txt);
	}

	@Override
	public void dismissProgress() {
		mBaseActivity.dismissProgress();
	}

	@Override
	public void onServerError(int vocational_id, Throwable e) {
		mBaseActivity.dismissProgress();
	}

	@Override
	public void onServerFailed(String s) {

	}

}
