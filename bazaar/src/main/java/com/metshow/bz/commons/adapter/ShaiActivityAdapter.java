package com.metshow.bz.commons.adapter;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.kwan.base.activity.BaseActivity;
import com.kwan.base.activity.BaseWebActivity;
import com.kwan.base.adatper.base.BaseMultiItemQuickAdapter;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.config.Config;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.util.ImageUtil;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.BzActivity;
import com.metshow.bz.commons.bean.TriProduct;
import com.metshow.bz.module.commons.activity.WebShareActivity;

import java.util.List;



/**
 * Created by Mr.Kwan on 2017-7-5.
 */

public class ShaiActivityAdapter extends BaseMultiItemQuickAdapter<BzActivity> {

	BaseActivity mBaseActivity;
	private final ImageUtil imageUtil;
	private boolean isShowTag2 = false;
	private boolean isShowTag1 = false;
	private int tag2Position = -1;

	public ShaiActivityAdapter(BaseActivity context, List data) {
		super(context, data);
		mBaseActivity = context;
		imageUtil = new ImageUtil();
		addItmeType(0, R.layout.list_item_activity);
		addItmeType(1, R.layout.layout_item_triproduct);
	}

	@Override
	protected void convert(BaseViewHolder helper, BzActivity item, int position) {

		int itemType = helper.getItemViewType();
		switch (itemType) {

			case 0:
				setUpActivity(helper, item, position);
				break;
			case 1:
				setUpTriproduct(helper, item, position);
				break;

		}

	}

	private void setUpActivity(BaseViewHolder helper, BzActivity item, int position) {

		helper.setVisible(R.id.ll_tag, false);
		if (position == 1) {
			helper.setVisible(R.id.ll_tag, true);
			isShowTag1 = true;
		}

		if ((item.getState() == -1 && !isShowTag2) || tag2Position == position) {
			helper.setText(R.id.tv_tag, "往期活动");
			helper.setVisible(R.id.ll_tag, true);
			isShowTag2 = true;
			tag2Position = position;
		}

		ImageView imageView = helper.getView(R.id.iv_icon);
		if (item.getPic().endsWith(".gif")) {
			mBaseActivity.mImageUtil.loadGifFromUrl(item.getPic(), imageView);
		} else {
			mBaseActivity.mImageUtil.loadFromUrl(item.getPic(), imageView);
		}
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);


		helper.setText(R.id.tv_title, item.getName());
		helper.setText(R.id.tv_num, item.getJoinUserCount() + "人参与");
		if (item.getState() == 1){
			helper.setText(R.id.tv_time, "距离结束" + BasePresenter.getStrEndData(item.getEndDate()));
			helper.setText(R.id.tv_go,"参加");
		}
		else {
			helper.setText(R.id.tv_time, "已经结束");
			helper.setText(R.id.tv_go,"查看");
		}
		helper.setOnClickListener(R.id.tv_go, new OnItemChildClickListener());
		helper.setOnClickListener(R.id.iv_icon, new OnItemChildClickListener());
		helper.setOnClickListener(R.id.tv_title,  new OnItemChildClickListener());
	}

	private void setUpTriproduct(BaseViewHolder helper, BzActivity item, int position) {
		final List<TriProduct> triProducts = item.getTriProducts();

		RecyclerView recyclerView = helper.getView(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setHasFixedSize(true);

		BaseQuickAdapter<TriProduct> adapter = new BaseQuickAdapter<TriProduct>(mContext, R.layout.list_item_triproduct, triProducts) {
			@Override
			protected void convert(BaseViewHolder helper, TriProduct item, int position) {
				imageUtil.loadFromUrl(item.getPic(), (ImageView) helper.getView(R.id.iv_icon));
				helper.setText(R.id.tv_name, item.getName());
			}
		};
		adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {

				Bundle bundle = new Bundle();
				bundle.putString(Config.INTENT_KEY_TITLE, triProducts.get(position).getName());
				bundle.putInt(Config.INTENT_KEY_MODE, BaseWebActivity.MODE_WEB);
				bundle.putString(Config.INTENT_KEY_DATA, triProducts.get(position).getUrl());

				mBaseActivity.go2ActivityWithLeft(WebShareActivity.class, bundle, false);
			}
		});
		recyclerView.setAdapter(adapter);
	}

}
