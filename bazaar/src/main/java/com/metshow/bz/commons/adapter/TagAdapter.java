package com.metshow.bz.commons.adapter;

import android.content.Context;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.metshow.bz.R;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-7-12.
 */

public class TagAdapter extends BaseQuickAdapter<String> {

	boolean isShowDelete = false;

	public TagAdapter(Context context, List<String> data) {
		super(context, R.layout.list_item_tag, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, String item, int position) {

		helper.setText(R.id.tv_name, item);
		helper.setOnClickListener(R.id.tv_name, new OnItemChildClickListener());
		helper.setOnClickListener(R.id.iv_delete, new OnItemChildClickListener());
		helper.setVisible(R.id.iv_delete, isShowDelete);
	}

	public void setShowDelete(boolean showDelete) {
		isShowDelete = showDelete;
	}
}
