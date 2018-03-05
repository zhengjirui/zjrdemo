package com.kwan.base.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Mr.Kwan on 2016-7-20.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = space;
//        if (parent.getChildAdapterPosition(view) == 0) {
//
//        }
    }
}
