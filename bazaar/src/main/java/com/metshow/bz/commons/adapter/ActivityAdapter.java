package com.metshow.bz.commons.adapter;

import android.util.Log;
import android.widget.ImageView;

import com.kwan.base.activity.BaseActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.util.ImageUtil;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.BzActivity;

import java.util.List;


/**
 * Created by Mr.Kwan on 2017-7-5.
 */

public class ActivityAdapter extends BaseQuickAdapter<BzActivity> {

	BaseActivity mBaseActivity;
	private final ImageUtil imageUtil;
	private boolean isShowTag2 = false;
	private boolean isShowTag1 = false;
	private int tag2Position = -1;
	private boolean isShowGo = true;

	public  void setIsShowGo(boolean isShow) {
		isShowGo = isShow;
	}

	public ActivityAdapter(BaseActivity context, List<BzActivity> data) {
		super(context, R.layout.list_item_activity, data);
		mBaseActivity = context;
		imageUtil = new ImageUtil();

	}

	@Override
	protected void convert(BaseViewHolder helper, BzActivity item, int position) {
		setUpActivity(helper, item, position);
	}

	private void setUpActivity(BaseViewHolder helper, BzActivity item, int position) {

		helper.setVisible(R.id.ll_tag, false);

//		if (position == 1) {
//			helper.setVisible(R.id.ll_tag, true);
//			isShowTag1 = true;
//		}
//
//		if ((item.getState() == -1 && !isShowTag2) || tag2Position == position) {
//			helper.setText(R.id.tv_tag, "往期活动");
//			helper.setVisible(R.id.ll_tag, true);
//			isShowTag2 = true;
//			tag2Position = position;
//		}
		ImageView imageView = helper.getView(R.id.iv_icon);

		Log.e("kwan","item.getPic():"+item.getPic());
		if (item.getPic().endsWith(".gif")) {
			mBaseActivity.mImageUtil.loadGifFromUrl(item.getPic(), imageView);
		} else {
			mBaseActivity.mImageUtil.loadFromUrl(item.getPic(), imageView);
		}


		helper.setText(R.id.tv_title, item.getName());
		helper.setText(R.id.tv_num, item.getJoinUserCount() + "人参与");
		helper.setVisible(R.id.tv_go,isShowGo);
		if (item.getState() == 1) {
			helper.setText(R.id.tv_time, "距离结束" + BasePresenter.getStrEndData(item.getEndDate()));
			helper.setText(R.id.tv_go, "参加");
		} else {
			helper.setText(R.id.tv_time, "已经结束");
			helper.setText(R.id.tv_go, "查看");
		}
		helper.setOnClickListener(R.id.tv_go, new OnItemChildClickListener());

	}

}
