package com.metshow.bz.module.commons.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.metshow.bz.commons.bean.TopicSubject;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.TopicSubjectPresenter;

import java.util.ArrayList;
import java.util.List;

public class TopicSubjectActivity extends BaseCommonActivity implements BZContract.ITopicSubjectView, BaseQuickAdapter.OnRecyclerViewItemClickListener {


    private TopicSubjectPresenter mPresenter;
    private TextView tv_hot;
    private RecyclerView recyclerView;
    private EditText et_content;

    private BaseQuickAdapter<TopicSubject> mHotAdapter;
    private BaseQuickAdapter<TopicSubject> mSearchAdapter;

    private List<TopicSubject> mHotSubjects = new ArrayList<>();
    private List<TopicSubject> mSubjects = new ArrayList<>();

    @Override
    protected void beForeSetContentView() {
        mPresenter = new TopicSubjectPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_topic_subject;
    }


    @Override
    protected void initViews() {
        super.initViews();
        tv_hot = (TextView) findViewById(R.id.tv_hot);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        et_content = (EditText) findViewById(R.id.et_content);
    }

    @Override
    protected void initViewSetting() {
        super.initViewSetting();
        et_content.setHint("输入关键字搜索话题");
        et_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    showProgress("");
                    String key = et_content.getText().toString();
                    mPresenter.searchSubject(key);
                }
                return false;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        recyclerView.setHasFixedSize(true);

        mHotAdapter = new BaseQuickAdapter<TopicSubject>(this, R.layout.item, mHotSubjects) {
            @Override
            protected void convert(BaseViewHolder helper, TopicSubject item, int position) {
                helper.setText(R.id.tv, "#" + item.getName());
            }
        };

        mHotAdapter.setOnRecyclerViewItemClickListener(this);

        mSearchAdapter = new BaseQuickAdapter<TopicSubject>(this, R.layout.list_item_subject, mSubjects) {
            @Override
            protected void convert(BaseViewHolder helper, TopicSubject item, int position) {
                helper.setText(R.id.tv_name, "#" + item.getName());
                helper.setImageUrl(R.id.iv_icon, item.getPic());
            }
        };

        mSearchAdapter.setOnRecyclerViewItemClickListener(this);

        recyclerView.setAdapter(mHotAdapter);
    }

	@Override
	protected void initData() {
		showProgress("");
		mPresenter.getHotSubject();
	}

    @Override
    protected BasePresenter getPresent() {
        return mPresenter;
    }

    @Override
    protected String getTitleTxt() {
        return "话题";
    }

    @Override
    public void getHotSubjectSuccess(List<TopicSubject> items) {

        mHotSubjects.addAll(items);
        mHotAdapter.addData(items);

       dismissProgress();
    }

    @Override
    public void searchSubject(List<TopicSubject> items) {

        mSubjects.addAll(items);
        mSearchAdapter.addData(items);
        recyclerView.setAdapter(mSearchAdapter);
        tv_hot.setVisibility(View.GONE);

        dismissProgress();
    }

    @Override
    public void onItemClick(View view, int position) {

        if (view.getId() == R.id.tv) {
            backWithResult(mHotSubjects.get(position));
        } else {
            backWithResult(mSubjects.get(position));
        }

    }

    private void backWithResult(TopicSubject topicSubject) {

        Intent i = new Intent();
        i.putExtra("subject", topicSubject);
        setResult(RESULT_OK, i);
        onBackPressed();

    }
}
