package com.metshow.bz.commons.adapter;


import com.kwan.base.activity.BaseActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.Product;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-2-24.
 */

public class ProductSearchAdapter extends BaseQuickAdapter<Product> {

	public ProductSearchAdapter(BaseActivity baseActivity, List<Product> data) {
		super(baseActivity, R.layout.list_item_product_search, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, Product item, int position) {

		helper.setImageUrl(R.id.iv_icon,item.getPicture());
		helper.setText(R.id.tv_comment,item.getCommentCount()+"");
		helper.setText(R.id.tv_fav,item.getFavCount()+"");
		helper.setText(R.id.tv_name,item.getProductName());

		if(item.getIsComment()==1)
			helper.setImageResource(R.id.iv_comment,R.mipmap.article_comment_icon);
		else
			helper.setImageResource(R.id.iv_comment,R.mipmap.article_comment_normal_icon);

		if(item.getIsFav()==1)
			helper.setImageResource(R.id.iv_fav,R.mipmap.article_fav_icon);
		else
			helper.setImageResource(R.id.iv_fav,R.mipmap.article_fav_normal_icon);
	}
}
