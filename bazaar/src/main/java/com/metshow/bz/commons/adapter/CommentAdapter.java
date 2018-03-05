package com.metshow.bz.commons.adapter;

import android.graphics.Typeface;
import android.widget.ImageView;

import com.kwan.base.activity.BaseActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.topic.Comment;

import java.util.List;

/**
 *
 * Created by Mr.Kwan on 2017-7-10.
 */

public class CommentAdapter extends BaseQuickAdapter<Comment> {
	private BaseActivity mBaseActivity;

	public CommentAdapter(BaseActivity baseActivity, List<Comment> data) {
		super(baseActivity, R.layout.list_item_comment, data);
		mBaseActivity = baseActivity;
	}

	@Override
	protected void convert(BaseViewHolder helper, Comment item, int position) {

		mBaseActivity.mImageUtil.loadFromUrlWithCircle(item.getAvatar(), (ImageView) helper.getView(R.id.iv_avatar));
		helper.setText(R.id.tv_name, item.getNickName());
		helper.setTextTypeFace(R.id.tv_name, Typeface.DEFAULT_BOLD);
		helper.setText(R.id.tv_time, BasePresenter.getStrData(item.getCreateDate()));

		if (item.getParentId() > 0) {
			helper.setText(R.id.tv_content, ChatAdapter.getTxtMsg("@" + item.getToNickName() + " " + item.getContent()));
		} else {
			helper.setText(R.id.tv_content, ChatAdapter.getTxtMsg(item.getContent()));
		}

		if (item.getImage() == null || item.getImage().isEmpty()) {
			helper.setVisible(R.id.iv_content, false);
		} else {
			helper.setVisible(R.id.iv_content, true);
			helper.setImageUrl(R.id.iv_content, item.getImage());
		}

		helper.setOnClickListener(R.id.ll_more,new OnItemChildClickListener());
	}
}
