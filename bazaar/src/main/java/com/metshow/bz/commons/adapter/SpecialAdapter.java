package com.metshow.bz.commons.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.util.ImageUtil;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.Special;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-7-7.
 */

public class SpecialAdapter extends BaseQuickAdapter<Special> {

	private final ImageUtil mImageUtil;

	public SpecialAdapter(Context context, List<Special> data) {
		super(context, R.layout.list_item_special, data);
		mImageUtil = new ImageUtil();
	}

	@Override
	protected void convert(BaseViewHolder helper, Special item, int position) {

		String icon = item.getIco();
		if (icon.endsWith(".gif"))
			mImageUtil.loadGifFromUrl(icon, (ImageView) helper.getView(R.id.iv_icon), App.SCREEN_WIDTH);
		else
			helper.setImageUrl(R.id.iv_icon, item.getIco());
		helper.setText(R.id.tv_name, item.getTitle());

	}
}
