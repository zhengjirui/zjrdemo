package com.metshow.bz.commons.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.util.ImageUtil;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.Product;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-7-7.
 */

public class ProductAdapter extends BaseQuickAdapter<Product> {


	private final ImageUtil mImageUtil;

	public ProductAdapter(Context context, List<Product> data) {
		super(context, R.layout.list_item_product, data);
		mImageUtil = new ImageUtil();
	}

	@Override
	protected void convert(BaseViewHolder helper, Product item, int position) {

		mImageUtil.loadFromUrlCenterInsidePading(item.getPicture(), (ImageView) helper.getView(R.id.iv_icon));

		helper.setText(R.id.tv_title, item.getBrandName());
		helper.setText(R.id.tv_name, item.getProductName());
		helper.setText(R.id.tv_price, "￥" + item.getUnitPrice());
		helper.setTextColor(R.id.tv_price, Color.BLACK);
		//helper.setText(R.id.tv_fav, item.getFavCount() + "");

		if (item.getIsFav() > 0)
			helper.setImageResource(R.id.iv_fav, R.mipmap.article_fav_icon);
		else
			helper.setImageResource(R.id.iv_fav, R.mipmap.article_fav_normal_icon);

		helper.setOnClickListener(R.id.iv_fav, new OnItemChildClickListener());

	}
}
