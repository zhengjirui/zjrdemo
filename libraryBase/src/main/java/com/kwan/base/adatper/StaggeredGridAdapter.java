package com.kwan.base.adatper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ViewGroup;

import com.kwan.base.adatper.base.BaseMultiItemQuickAdapter;
import com.kwan.base.bean.POJO;

import java.util.List;

/**
 * Created by Mr.Kwan on 2016-7-21.
 */

public abstract class StaggeredGridAdapter<T extends POJO> extends BaseMultiItemQuickAdapter<T> {

	protected int[] mFullSpanIndex;

	public StaggeredGridAdapter(Context context, List<T> data) {
		super(context, data);
	}

	@Override
	public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
		super.onViewAttachedToWindow(holder);
		Log.d("StaggeredGridAdapter","StaggeredGridAdapter:");
		if (mFullSpanIndex != null)

			for (int index : mFullSpanIndex) {
				Log.d("StaggeredGridAdapter","index:"+index);
				ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
				if (lp != null
						&& lp instanceof StaggeredGridLayoutManager.LayoutParams
						&& holder.getLayoutPosition() == index) {
					Log.d("StaggeredGridAdapter","set:"+index);
					StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
					p.setFullSpan(true);
				}
			}
	}

	public int[] getFullSpanIndex() {
		return mFullSpanIndex;
	}

	public void setFullSpanIndex(int[] mFullSpanIndex) {
		this.mFullSpanIndex = mFullSpanIndex;
	}

}
