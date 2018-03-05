package com.metshow.bz.commons.adapter;

import android.content.Context;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.util.ImageUtil;
import com.metshow.bz.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2017-11-2.
 */

public class PhotoEditAdapter extends BaseQuickAdapter<HashMap<String, Object>> {

    private final ImageUtil mImageUtil;

    public PhotoEditAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
        super(context, R.layout.list_item_photo_edit, data);
        mImageUtil = new ImageUtil();
    }


    @Override
    protected void convert(BaseViewHolder helper, HashMap<String, Object> item, int position) {


        if (item.get("icon") instanceof Integer)
            // mImageUtil.loadFromRes((Integer) item.get("icon"),(ImageView)helper.getView(R.id.iv_icon));
            helper.setImageResource(R.id.iv_icon, (Integer) item.get("icon"));
        else
            helper.setImageUrl(R.id.iv_icon, (String) item.get("icon"));
        helper.setText(R.id.tv_name, (String) item.get("name"));

    }
}
