package com.metshow.bz.module.commons.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwan.base.activity.BaseCommonActivity;
import com.metshow.bz.R;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.UserFeedBackPresenter;
import com.yongchun.library.view.ImageSelectorActivity;

import java.util.ArrayList;

public class UserFeedBackActivity extends BaseCommonActivity implements BZContract.IUserFeedBackView {

    private EditText et_feed_back;
    private ImageView iv_add, iv_content;
    private TextView tv_send;
    private String imagePath, imageUrl;
	private String content;
    private UserFeedBackPresenter mPresenter;
    protected final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;

    @Override
    protected void beForeSetContentView() {
		super.beForeSetContentView();
        mPresenter = new UserFeedBackPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_feed_back;
    }

    @Override
    protected void initViews() {
        super.initViews();

        et_feed_back = (EditText) findViewById(R.id.et_feed_back);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        tv_send = (TextView) findViewById(R.id.tv_send);

        iv_content = (ImageView) findViewById(R.id.iv_content);
    }


    @Override
    protected void initViewSetting() {
        super.initViewSetting();
        iv_add.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        iv_content.setOnClickListener(this);
    }

	@Override
	protected void initData() {

	}

	@Override
    protected String getTitleTxt() {
        return "用户反馈";
    }

    @Override
    protected int getTitleBarRightLayoutId() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v == iv_add || v == iv_content) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            } else {
                ImageSelectorActivity.start(this, 1, ImageSelectorActivity.MODE_MULTIPLE, true, false, false);
            }
        } else if (v == tv_send) {

            String content = et_feed_back.getText().toString();

            if (content.isEmpty() && imagePath.isEmpty()) {
                toastMsg("请输入内容或选择图片！");
            } else {

                if (imagePath != null && !imagePath.isEmpty()) {
                    showProgress("");
                    mPresenter.upLoadImage(imagePath);
                } else {
                    showProgress("");
                    mPresenter.addFeedBack("", content);
                }


            }


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
            imagePath = images.get(0);
            iv_add.setVisibility(View.GONE);
            iv_content.setVisibility(View.VISIBLE);
            mImageUtil.loadFromFile(imagePath, iv_content);

        }
    }

    @Override
    public void uploadImgSuccess(String url) {
        mPresenter.addFeedBack(url, content);
    }

    @Override
    public void uploadImgFailed() {
       dismissProgress();
        toastMsg("上传图片失败");
    }

    @Override
    public void addFeedBackSuccess(String result) {
        dismissProgress();
        toastMsg("反馈成功");
        onBackPressed();
    }
}
