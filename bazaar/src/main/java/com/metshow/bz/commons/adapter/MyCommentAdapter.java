package com.metshow.bz.commons.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.util.ImageUtil;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.topic.Comment;
import com.metshow.bz.presenter.BzPresenter;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-7-12.
 */

public class MyCommentAdapter extends BaseQuickAdapter<Comment> {

	ImageUtil imageUtil;

	public MyCommentAdapter(Context context, List<Comment> data) {
		super(context, R.layout.list_item_my_comment, data);
		imageUtil = App.getInstance().getImageUtil();
	}

	@Override
	protected void convert(BaseViewHolder helper, Comment item, int position) {

		imageUtil.loadFromUrlWithCircle(item.getAvatar(), (ImageView) helper.getView(R.id.iv_icon));

		helper.setText(R.id.tv_comment,ChatAdapter.getTxtMsg("我 评论说："+item.getContent()));
		helper.setText(R.id.tv_time, BzPresenter.getStrData(item.getCreateDate()));

		if(item.getSourceImage().endsWith("gif"))
		imageUtil.loadGifFromUrl(item.getSourceImage(), (ImageView) helper.getView(R.id.iv_icon2));
		else
			imageUtil.loadFromUrl(item.getSourceImage(), (ImageView) helper.getView(R.id.iv_icon2));

	}


}
