package com.kwan.base.util;

/*
 *ViewUtil.java
 *@author 谢明峰
 * 控件工具类
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

public class ViewUtil {
	

	public static int getScreenWidth(Activity activity){

        DisplayMetrics dm = new DisplayMetrics();
        activity. getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小
		return dm.widthPixels;
	}

	public static int getScreenHeight(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity. getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}
	
	/**
	 * 状态条的像素值
	 * @return > 0 success; <= 0 fail
	 */
	public static int getStatusHeight(Activity activity) {

        int statusHeight = 0;
		Rect localRect = new Rect();
		activity.getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(localRect);
		statusHeight = localRect.top;
		if (0 == statusHeight) {
			Class<?> localClass;
			try {
				localClass = Class.forName("com.android.internal.R$dimen");
				Object localObject = localClass.newInstance();
				int i5 = Integer.parseInt(localClass
                        .getField("status_bar_height").get(localObject)
                        .toString());
				statusHeight = activity.getResources()
						.getDimensionPixelSize(i5);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statusHeight;
	}

	public static void measureView(View view) {
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
		}
		view.measure(childWidthSpec, childHeightSpec);
	}
	
	/**
	 *
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
	
	/**
	 *
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	 /** 
     * 将px值转换为sp值，保证文字大小不变 
     * @param pxValue 标准像素值
     * @return 
     */  
    public static int pxToSp(Context context , float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        int fontSp = (int) (pxValue / fontScale + 0.5f);
        return fontSp;
    }  
  
    /** 
     * 将sp值转换为px值，保证文字大小不变 
     * @param spValue 比例像素值
     * @return 
     */  
    public static int spToPx(Context context , float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        int fontPx = (int) (spValue * fontScale + 0.5f);
        return fontPx;
    }


	
}
