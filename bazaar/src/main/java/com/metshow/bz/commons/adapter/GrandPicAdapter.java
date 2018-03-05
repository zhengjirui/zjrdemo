package com.metshow.bz.commons.adapter;

import android.widget.ImageView;

import com.kwan.base.activity.BaseActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.util.ImageUtil;
import com.metshow.bz.R;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-7-17.
 */

public class GrandPicAdapter extends BaseQuickAdapter<String> {

	ImageUtil mImageUtil;

	public GrandPicAdapter(BaseActivity context, List<String> data) {
		super(context, R.layout.list_item_pic, data);
		mImageUtil = new ImageUtil();


	}

	@Override
	protected void convert(BaseViewHolder helper, String item, int position) {
		if (item.endsWith(".gif")) {
			mImageUtil.loadGifFromUrl(item, (ImageView) helper.getView(R.id.iv_icon));
		} else {
			mImageUtil.loadFromUrl(item, (ImageView) helper.getView(R.id.iv_icon));
		}
	}
}
