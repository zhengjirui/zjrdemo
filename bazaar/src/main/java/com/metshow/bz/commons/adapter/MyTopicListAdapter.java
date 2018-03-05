package com.metshow.bz.commons.adapter;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.kwan.base.activity.BaseActivity;
import com.kwan.base.adatper.base.BaseMultiItemQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.bean.POJO;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-2-24.
 */

public class MyTopicListAdapter extends BaseMultiItemQuickAdapter<POJO> {


	/**
	 * Same as QuickAdapter#QuickAdapter(Context,int) but with
	 * some initialization data.
	 *
	 * @param data A new list is created out of this one to avoid mutable list
	 */
	public MyTopicListAdapter(List<POJO> data, BaseActivity baseActivity) {
		super(baseActivity,data);
	}


	public void onViewAttachedToWindow(BaseViewHolder holder) {
		super.onViewAttachedToWindow(holder);

		ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
		if (lp != null
				&& lp instanceof StaggeredGridLayoutManager.LayoutParams
				&& holder.getLayoutPosition() == 0) {
			StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
			p.setFullSpan(true);
		}
	}

	@Override
	protected void convert(BaseViewHolder helper, POJO item,int positon) {

		//helper.getLayoutPosition()


	}

}
