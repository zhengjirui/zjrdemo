package com.metshow.bz.commons.adapter;

import android.content.Context;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.topic.Topic;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-7-11.
 */

public class SearchTopicAdapter extends BaseQuickAdapter<Topic> {


	public SearchTopicAdapter(Context context, List<Topic> data) {
		super(context, R.layout.list_item_article_search, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, Topic item, int position) {


		helper.setImageUrl(R.id.iv_icon, item.getIco());
		helper.setText(R.id.tv_content, item.getContent());

		helper.setCircleUrl(R.id.iv_avatar, item.getAuthorIco());
		helper.setText(R.id.tv_name, item.getAuthor());
		helper.setText(R.id.tv_comment, item.getCommentCount() + "");
		helper.setText(R.id.tv_fav, item.getFavCount() + "");


	}

}
