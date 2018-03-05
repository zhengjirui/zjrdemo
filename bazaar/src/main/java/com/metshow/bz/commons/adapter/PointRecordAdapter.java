package com.metshow.bz.commons.adapter;

import android.content.Context;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.PointRecord;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-7-12.
 */

public class PointRecordAdapter extends BaseQuickAdapter<PointRecord> {

	public PointRecordAdapter(Context context, List<PointRecord> data) {
		super(context, R.layout.list_item_point_record, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, PointRecord item, int position) {
		helper.setText(R.id.tv_name, item.getPointShopName());
		helper.setImageUrl(R.id.iv_icon,item.getPointShopIco());

		switch (item.getType()){

			case 1://实物
				//State：0为待发货，1为已发货  -1为拒绝

				String state;

				switch (item.getState()){
					case 0:
						state = "待发货";
				        break;
					case 1:
						state = "已发货";
						break;
					case -1:
						state = "拒绝";
						break;
					default:
						state="";

				}
				helper.setText(R.id.tv_state, state);
				break;
			case 2://电子券
				helper.setText(R.id.tv_state, BasePresenter.getfullData(item.getCouponEndDate()));
				break;
		}

//		helper.setOnClickListener(R.id.tv_name, new OnItemChildClickListener());
//		helper.setOnClickListener(R.id.iv_delete, new OnItemChildClickListener());
//		helper.setVisible(R.id.iv_delete, isShowDelete);
	}


}
