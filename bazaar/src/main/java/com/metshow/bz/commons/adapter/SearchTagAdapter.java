package com.metshow.bz.commons.adapter;

import android.content.Context;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.Tag;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-7-14.
 */

public class SearchTagAdapter extends BaseQuickAdapter<Tag> {

	public SearchTagAdapter(Context context, List<Tag> data) {
		super(context, R.layout.list_item_search_tag, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, Tag item, int position) {

		helper.setText(R.id.tv_name,item.getName());
		helper.setText(R.id.tv_num,item.getCountNum()+"个帖子");
	}
}
