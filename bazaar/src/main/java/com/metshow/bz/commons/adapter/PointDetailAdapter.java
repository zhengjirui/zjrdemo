package com.metshow.bz.commons.adapter;

import android.content.Context;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.PointDetail;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-7-12.
 */

public class PointDetailAdapter extends BaseQuickAdapter<PointDetail> {

	boolean isShowDelete = false;

	public PointDetailAdapter(Context context, List<PointDetail> data) {
		super(context, R.layout.list_item_point_detail, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, PointDetail item, int position) {

		helper.setText(R.id.tv_name, item.getName());
		helper.setText(R.id.tv_time,BasePresenter.getfullData(item.getCreateDate()));
		helper.setText(R.id.tv_point, "+"+item.getPoints());


//		helper.setOnClickListener(R.id.tv_name, new OnItemChildClickListener());
//		helper.setOnClickListener(R.id.iv_delete, new OnItemChildClickListener());
//		helper.setVisible(R.id.iv_delete, isShowDelete);
	}

	public void setShowDelete(boolean showDelete) {
		isShowDelete = showDelete;
	}
}
