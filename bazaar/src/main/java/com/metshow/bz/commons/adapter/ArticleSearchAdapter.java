package com.metshow.bz.commons.adapter;

import android.content.Context;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.article.Article;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-7-11.
 */

public class ArticleSearchAdapter<T extends Article> extends BaseQuickAdapter<T> {


	public ArticleSearchAdapter(Context context, List<T> data) {
		super(context, R.layout.list_item_article_search, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, Article item, int position) {

//		if (item instanceof FavArticle) {

			helper.setImageUrl(R.id.iv_icon, item.getIco());
			helper.setText(R.id.tv_content, item.getSummary());

			helper.setCircleUrl(R.id.iv_avatar, item.getAuthorIco());
			helper.setText(R.id.tv_name, item.getAuthor());
			helper.setText(R.id.tv_comment, item.getCommentCount() + "");

			if (item.getFavCount() == 0)
				helper.setVisible(R.id.ll_fav, false);
			else
				helper.setText(R.id.tv_fav, item.getFavCount() + "");
		//}
	}

}
