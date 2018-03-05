package com.kwan.base.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kwan.base.R;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * 带有标题栏的Activity
 */

public abstract class BaseCommonActivity extends BaseActivity {

    protected ImageView iv_title_back;
    protected TextView tv_title;
    protected ViewStub stubTitleRight;
    protected AutoLinearLayout ll_title_back;

    @Override
    protected void initViews() {

        if (getTitleTxt() != null) {
            ll_title_back = (AutoLinearLayout) findViewById(R.id.ll_title_back);
            iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
            tv_title = (TextView) findViewById(R.id.tv_title);
            stubTitleRight = (ViewStub) findViewById(R.id.view_stub_common_title_right);
        }

    }

    @Override
    protected void initViewSetting() {

        if (getTitleTxt() != null) {
            ll_title_back.setOnClickListener(this);
            //tv_title.setTypeface(Typeface.DEFAULT_BOLD);
            tv_title.setText(getTitleTxt());
        }

        if (getTitleBarRightLayoutId() != 0) {
            stubTitleRight.setLayoutResource(getTitleBarRightLayoutId());
            setUpTitleRightView(stubTitleRight.inflate());
        }

    }

    protected void setUpTitleRightView(View v) {

        if (v instanceof ImageView) {

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(80, 80);
            lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            //lp.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            v.setLayoutParams(lp);
        }

    }

	@Override
	protected Bitmap getBackGroundBitmap() {
		return null;
	}

    @Override
    protected int getTitleBarViewId() {

        if (getTitleTxt() != null)
            return R.layout.activity_base_common_title_bar;
        else
			return 0;
    }

    @Override
    protected int getBottomViewId() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        if (v == ll_title_back) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    protected abstract String getTitleTxt();

	public void setTitleTxt(String str) {
		tv_title.setText(str);
	}


	protected int getTitleBarRightLayoutId() {
        return 0;
    }

    @Override
    public void onNoNetWork() {
        toastMsg("当前无网络");
    }

    @Override
    public void onServerError(int vocational_id, Throwable e) {
        toastMsg("网络失败");
    }

    @Override
    public void onServerFailed(String s) {
        toastMsg("服务器错误："+s);
    }

}
