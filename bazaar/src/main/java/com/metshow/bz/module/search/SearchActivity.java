package com.metshow.bz.module.search;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kwan.base.adatper.CommonViewPageAdapter;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.SearchTagAdapter;
import com.metshow.bz.commons.adapter.SearchTopicAdapter;
import com.metshow.bz.commons.adapter.SearchUserAdapter;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.shai.TopicActivity;
import com.metshow.bz.module.commons.activity.FriendDetailActivity;
import com.metshow.bz.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends BaseSearchActivity implements ViewPager.OnPageChangeListener, BZContract.ISearchView {


	protected String[] tits = new String[]{"标签", "用户", "帖子"};
	private ObservableRecyclerView rl_tag, rl_user, rl_topic;
	SearchTagAdapter mTagAdapter;
	SearchUserAdapter mUserAdapter;
	SearchTopicAdapter mtTopicAdapter;

	ArrayList<Tag> mTags = new ArrayList<>();
	ArrayList<User> mUsers = new ArrayList<>();
	ArrayList<Topic> mTopics = new ArrayList<>();


	int current = 0;
	SearchPresenter msSearchPresenter;
	String mSearchTag = "";

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();

		msSearchPresenter = new SearchPresenter(this);
	}

	@Override
	protected void initViews() {
		super.initViews();
		setRecycle();
		mViewPager.addOnPageChangeListener(this);
		et_search.setHint("搜索标签、用户、帖子");
	}

	@Override
	public void onSearch(String txt) {
		et_search.setHint(txt);
		showProgress("");
		mSearchTag = txt;
		switch (current) {
			case 0:
				msSearchPresenter.searchTag(1, 20, txt);
				break;
			case 1:
				msSearchPresenter.searchUser(1, 20, txt);
				break;
			case 2:
				msSearchPresenter.searchTopic(1, 20, txt);
				break;
		}
	}


	private void setRecycle() {

		View view_tag = getLayoutInflater().inflate(R.layout.layout_base_recycle, null);
		View view_user = getLayoutInflater().inflate(R.layout.layout_base_recycle, null);
		View view_topic = getLayoutInflater().inflate(R.layout.layout_base_recycle, null);

		rl_tag = (ObservableRecyclerView) view_tag.findViewById(R.id.swipe_target);
		rl_user = (ObservableRecyclerView) view_user.findViewById(R.id.swipe_target);
		rl_topic = (ObservableRecyclerView) view_topic.findViewById(R.id.swipe_target);

		initRecycler(rl_tag);
		initRecycler(rl_user);
		initRecycler(rl_topic);
		mTagAdapter = new SearchTagAdapter(this, mTags);
		mUserAdapter = new SearchUserAdapter(this, mUsers);
		mtTopicAdapter = new SearchTopicAdapter(this, mTopics);


		mtTopicAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				Bundle bundle = new Bundle();
				bundle.putLong("topicid",mTopics.get(position).getArticleId());
				go2ActivityWithLeft(TopicActivity.class,bundle,false);
			}
		});

		mUserAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				Bundle bundle = new Bundle();
				bundle.putLong("userid",mUsers.get(position).getUserId());
				bundle.putString("name",mUsers.get(position).getNickName());
				bundle.putInt("isFollow",mUsers.get(position).getIsFollow());

				go2ActivityWithLeft(FriendDetailActivity.class,bundle,false);
			}
		});

		rl_tag.setAdapter(mTagAdapter);
		rl_user.setAdapter(mUserAdapter);
		rl_topic.setAdapter(mtTopicAdapter);
		ArrayList<View> vs = new ArrayList<>();
		vs.add(view_tag);
		vs.add(view_user);
		vs.add(view_topic);

		List<String> titles = Arrays.asList(tits);

		setUpViewPage(new CommonViewPageAdapter<>(vs, titles));

	}

	private void initRecycler(RecyclerView recyclerView) {

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		recyclerView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		recyclerView.setHasFixedSize(true);
		//mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		//解决更新 闪烁
		recyclerView.setItemAnimator(null);
		recyclerView.setNestedScrollingEnabled(false);


	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		current = position;
		switch (current) {
			case 0:
				if (mTags.size() == 0) {
					//showProgress("");
					//msSearchPresenter.searchTag(1, 20, mSearchTag);
				}
				break;
			case 1:
				if (mUsers.size() == 0) {
					//showProgress("");
					//msSearchPresenter.searchUser(1, 20, mSearchTag);
				}
				break;
			case 2:
				if (mTopics.size() == 0) {
					//showProgress("");
					//msSearchPresenter.searchTopic(1, 20, mSearchTag);
				}
				break;

		}

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void getTagSuccess(List<Tag> tags) {
		mTags.addAll(tags);
		mTagAdapter.setNewData(tags);
	}

	@Override
	public void getUserSuccess(List<User> users) {
		mUsers.addAll(users);
		mUserAdapter.setNewData(users);
	}

	@Override
	public void getTopicSuccsess(List<Topic> topics) {
		mTopics.addAll(topics);
		mtTopicAdapter.setNewData(topics);
	}
}
