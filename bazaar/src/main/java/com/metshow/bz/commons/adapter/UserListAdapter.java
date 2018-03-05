package com.metshow.bz.commons.adapter;


import com.kwan.base.activity.BaseActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-2-24.
 */

public class UserListAdapter extends BaseQuickAdapter<String> {

	public UserListAdapter(int layoutResId, List<String> data, BaseActivity baseActivity) {
		super(baseActivity,layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, String item,int position) {

	}
}
