package com.metshow.bz.commons.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.util.ImageUtil;
import com.metshow.bz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Kwan on 2017-11-14.
 */

public class ImageSelectAdapter extends BaseQuickAdapter<String>  {


	ArrayList<String> mSelectData = new ArrayList<>();
	ImageUtil imageUtil;
	List<String> mData;


	public ImageSelectAdapter(Context context, List<String> data) {
		super(context, R.layout.layout_image_select, data);
		imageUtil = new ImageUtil();
		mData = data;
	}

	@Override
	protected void convert(final BaseViewHolder helper, final String item, int position) {
		imageUtil.loadFromFile(item, (ImageView) helper.getView(R.id.iv_icon));

		helper.getView(R.id.iv_icon).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (mSelectData.contains(item)) {
					mSelectData.remove(item);
					helper.getView(R.id.iv_tag).setVisibility(View.GONE);
				} else {

					if(mSelectData.size()==9){
						Toast.makeText(mContext,"最多选择9张",Toast.LENGTH_SHORT).show();
					}else {
						mSelectData.add(item);
						helper.getView(R.id.iv_tag).setVisibility(View.VISIBLE);
					}
				}
			}
		});
	}

	public ArrayList<String> getSelectData() {
		return mSelectData;
	}
}
