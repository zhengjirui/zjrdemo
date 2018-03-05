package com.kwan.base.adatper.base;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.kwan.base.bean.POJO;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public abstract class BaseMultiItemQuickAdapter<T extends POJO> extends BaseQuickAdapter<T> {

    /**
     * layouts indexed with their types
     */
    private SparseArray<Integer> layouts;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param context The context.
     * @param data    A new list is created out of this one to avoid mutable list
     */
    public BaseMultiItemQuickAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    protected int getDefItemViewType(int position) {
        return  mData.get(position).getItemType();
    }


    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
		if (layouts==null)
		Log.d("kwan","layouts:null");
		return layouts.get(viewType);
    }

    public void addItmeType(int type, int layoutResId) {
        if (layouts == null) {
            layouts = new SparseArray<>();
        }
        layouts.put(type, layoutResId);
    }

}


