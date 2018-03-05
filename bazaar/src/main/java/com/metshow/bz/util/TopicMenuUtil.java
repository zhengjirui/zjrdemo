package com.metshow.bz.util;

import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import com.kwan.base.activity.BaseActivity;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.TopicMenuPresenter;


/**
 * Created by Mr.Kwan on 2016-8-10.
 */
public class TopicMenuUtil implements View.OnClickListener {

    private Dialog mDialog;
    private long mUserId;
    private int isFollow;
    private BaseActivity mActivity;
    private View parent;

    private TextView tv_cancel, tv_delete, tv_delete_follow, tv_disview, tv_report;
    private TopicMenuPresenter mPresenter;
    private Topic mTopic;
    private int position;

    public TopicMenuUtil(BaseActivity activity, Topic topic, BZContract.ITopicMenuView iTopicMenuView, int position) {

        mActivity = activity;
        mTopic = topic;
        mUserId = topic.getUserId();
        isFollow = topic.getIsFollow();
        this.position = position;

        mPresenter = new TopicMenuPresenter(iTopicMenuView);

        parent = mActivity.getLayoutInflater().inflate(R.layout.menu_topic, null);

        tv_cancel = (TextView) parent.findViewById(R.id.tv_cancel);
        tv_delete = (TextView) parent.findViewById(R.id.tv_delete);
        tv_delete_follow = (TextView) parent.findViewById(R.id.tv_delete_follow);
        tv_disview = (TextView) parent.findViewById(R.id.tv_disview);
        tv_report = (TextView) parent.findViewById(R.id.tv_report);

        if (mUserId == BZSPUtil.getUser().getUserId()) {

            tv_delete_follow.setVisibility(View.GONE);
            tv_disview.setVisibility(View.GONE);
            tv_report.setVisibility(View.GONE);

            tv_delete.setOnClickListener(this);

        } else {

            tv_delete.setVisibility(View.GONE);

            if (isFollow > 0)
                tv_delete_follow.setOnClickListener(this);
            else
                tv_delete_follow.setVisibility(View.GONE);

            tv_report.setOnClickListener(this);
            tv_disview.setOnClickListener(this);

        }
        tv_cancel.setOnClickListener(this);
    }

    public Dialog showShareDialog() {
        mDialog = DialogFactory.showMenuDialog(mActivity, parent);
        return mDialog;
    }


    @Override
    public void onClick(View view) {

        if (view == tv_delete) {
            mPresenter.deleteTopic(mTopic.getArticleId(), position);
            mActivity.showProgress("");
        } else if (view == tv_delete_follow) {
            mPresenter.deleteFollow(mUserId, position);
            mActivity.showProgress("");
        } else if (view == tv_disview) {
            mPresenter.disview(mTopic.getArticleId(), position);
            mActivity.showProgress("");
        } else if (view == tv_report) {
            mPresenter.report(mTopic.getArticleId(), position);
            mActivity.showProgress("");
        }

        mDialog.dismiss();

    }
}
