package com.kwan.base.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/** 此Viewpage修复 在使用 setPageTransformer 后 viewpage
 *  中的fragment无法响应点击事件的问题
 *
 *
 *  通过阅读源码发现，ViewPager.setPageTransformer()方法可以设置切换动画，
 *  但是如果ViewPager的子页中要处理触摸事件，如浏览图片时对图片放大缩小，
 *  FragmentStateAdpter中要处理item的点击事件，ViewPager切换子页后，
 *  不能处理触摸事件，或者触摸事件只有在切换后才响应，
 *  似乎子页里面的变的不可点击。尝试了很多中Google上的方法，
 *  包括修改setPageTransformer仍不能解决问题。
 *  其实这个是android4.1+版本上的bug，在调用了setPageTransformer()方法后，
 *  切换子页后，当前最上面的View并不是眼睛所看的，而是另一个隐藏的子页,该隐藏的子页消费了触摸事件
 *  尝试了把当前子页“放到最上面”，view.bringToFornt()，甚至把其他看不见的子页都设置为隐藏，
 *  otherView.setVisibility(View.GONE)，当前子页仍然不能处理触摸事件。
 *
 *  原理很简单，既然是因为设置了PageTransformer才导致子页的触摸事件异常，
 *  那么就不设置该属性，通过间接的方式执行切换动画。上面的类继承了ViewPager，
 *  覆盖了setPageTransformer()和onPageScrolled()，
 *  保存传进来的PageTransformer对象，父类ViewPager的mPageTransformer实际上为空
 *  ，在onPageScrolled()方法中“手动执行”切换动画。
 *
 * Created by Mr.Kwan on 2017-6-30.
 */

public class FixViewPage extends ViewPager{

	private PageTransformer mPageTransformer;

	public FixViewPage(Context context) {
		this(context, null);
	}

	public FixViewPage(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * android4.1+设置PageTransformer会使ViewPager的子页里面的触摸事件异常
	 * （当前看到的子页并非在最上面，所以触摸事件被隐藏在其上面的View给消费了）
	 * 所以结合setPageTransformer()，在onPageScrolled()里“手动”调用切换页面的动画
	 *
	 * @param position
	 * @param offset
	 * @param offsetPixels
	 */
	@Override
	protected void onPageScrolled(int position, float offset, int offsetPixels) {
		super.onPageScrolled(position, offset, offsetPixels);
		// 下面的源码来自super.onPageScrolled()
		int scrollX;
		int childCount;
		int i;
		if (this.mPageTransformer != null) {
			scrollX = this.getScrollX();
			childCount = this.getChildCount();

			for (i = 0; i < childCount; ++i) {
				View var15 = this.getChildAt(i);
				ViewPager.LayoutParams var16 = (ViewPager.LayoutParams) var15.getLayoutParams();
				if (!var16.isDecor) {
					float var17 = (float) (var15.getLeft() - scrollX) / (float) this.getClientWidth();
					this.mPageTransformer.transformPage(var15, var17);
				}
			}
		}
	}

	private int getClientWidth() {
		return this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight();
	}

	/**
	 * 覆盖该方法，不设置PageTransformer，以成员变量的形式保存PageTransformer
	 *
	 * @param reverseDrawingOrder
	 * @param transformer
	 */
	@Override
	public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
		super.setPageTransformer(reverseDrawingOrder, null);
		mPageTransformer = transformer;
	}

	/**
	 * 然而，到这一步，并还没有完全搞定，你会发现滑动事件冲突了，
	 * 点击事件也是有bug,特别是fragment里面有listview或者scrooview的，
	 * 要把viewpaer的滑动事件处理交给下一层，代码如下：
	 */

//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//
//		try {
//			return super.onInterceptTouchEvent(ev);
//		} catch (Exception e) {
//			// e.printStackTrace();
//		}
//		return false;
//	}
//
//	@Override
//	public boolean onTouchEvent(MotionEvent arg0) {
//		try {
//			return super.onTouchEvent(arg0);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

	@Override
	protected boolean canScroll(View view, boolean arg1, int arg2, int arg3, int arg4) {

		if (view != this && view instanceof ViewPager) {
			return true;
		}

		return super.canScroll(view, arg1, arg2, arg3, arg4);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		this.requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		this.requestDisallowInterceptTouchEvent(true);
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.requestDisallowInterceptTouchEvent(true);
		return super.onTouchEvent(event);
	}
}

