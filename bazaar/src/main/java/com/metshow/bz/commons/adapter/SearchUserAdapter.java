package com.metshow.bz.commons.adapter;

import android.content.Context;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.User;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-7-14.
 */

public class SearchUserAdapter extends BaseQuickAdapter<User> {

	public SearchUserAdapter(Context context, List<User> data) {
		super(context, R.layout.list_item_search_user, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, User item, int position) {

		helper.setImageUrl(R.id.iv_avatar,item.getAvatar());
		helper.setText(R.id.tv_name,item.getNickName());
		helper.setText(R.id.tv_num,"关注"+item.getFollowCount()+"  粉丝"+item.getBeFollowedCount());
		helper.setOnClickListener(R.id.tv_follow,new OnItemChildClickListener());
	}
}
