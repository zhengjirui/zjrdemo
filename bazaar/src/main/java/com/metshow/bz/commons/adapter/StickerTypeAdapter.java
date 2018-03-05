package com.metshow.bz.commons.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.StickerType;

import java.util.List;

/**
 * Created by Mr.Kwan on 2017-11-2.
 */

public class StickerTypeAdapter extends BaseQuickAdapter<StickerType> {

	public StickerTypeAdapter(Context context, List<StickerType> data) {
		super(context, R.layout.list_item_sticker_type, data);
	}

	TextView lastTextView;

	@Override
	protected void convert(BaseViewHolder helper, final StickerType item, final int position) {
		helper.setText(R.id.tv_name, item.getName());

		if (lastTextView == null) {
			helper.setTextColor(R.id.tv_name, Color.RED);
			lastTextView = helper.getView(R.id.tv_name);
		}

		helper.setOnClickListener(R.id.tv_name, new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				lastTextView.setTextColor(Color.BLACK);
				((TextView) v).setTextColor(Color.RED);
				lastTextView = ((TextView) v);

				itemClickListener.onItemClick(v, item, position);



			}
		});

	}

	private ItemClickListener itemClickListener;

	public void setItemClickListener(ItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}

	public interface ItemClickListener {
		void onItemClick(View v, StickerType item, int position);
	}

}
