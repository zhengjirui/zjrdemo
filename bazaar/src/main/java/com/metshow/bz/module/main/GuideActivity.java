package com.metshow.bz.module.main;

import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.kwan.base.activity.BaseActivity;
import com.kwan.base.config.APPCacheConfig;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;

import java.util.ArrayList;



public class GuideActivity extends BaseActivity {

    private int[] imgIds = {R.drawable.guide1, R.drawable.guide2, R.drawable.guide3,
            R.drawable.guide4};

    private ViewPager mViewPager;
    private MyPagerAdapter myPagerAdapter;
    private ArrayList<View> mViews;

    @Override
    protected void beForeSetContentView() {
		super.beForeSetContentView();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected int getTitleBarViewId() {
        return 0;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_guide;
    }

    @Override
    protected int getBottomViewId() {
        return 0;
    }

    @Override
    protected void initViews() {

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        myPagerAdapter = new MyPagerAdapter();
    }

    @Override
    protected void initViewSetting() {

        View v1 = getLayoutInflater().inflate(R.layout.welcome_guide_layout_1,
                null);
        View v2 = getLayoutInflater().inflate(R.layout.welcome_guide_layout_2,
                null);
        View v3 = getLayoutInflater().inflate(R.layout.welcome_guide_layout_3,
                null);
        View v4 = getLayoutInflater().inflate(R.layout.welcome_guide_layout_4,
                null);

        ImageView img1 = (ImageView) v1.findViewById(R.id.img1);
        ImageView img2 = (ImageView) v2.findViewById(R.id.img2);
        ImageView img3 = (ImageView) v3.findViewById(R.id.img3);
        ImageView img4 = (ImageView) v4.findViewById(R.id.img4);

//        img1.setImageBitmap(mImageUtil.compressBitmapFromResPathBySize
//                (getResources(),imgIds[0],App.SCREEN_WIDTH,App.SCREEN_HEIGHT));
//        img2.setImageBitmap(mImageUtil.compressBitmapFromResPathBySize
//                (getResources(),imgIds[1],App.SCREEN_WIDTH,App.SCREEN_HEIGHT));
//        img3.setImageBitmap(mImageUtil.compressBitmapFromResPathBySize
//                (getResources(),imgIds[2],App.SCREEN_WIDTH,App.SCREEN_HEIGHT));
//        img4.setImageBitmap(mImageUtil.compressBitmapFromResPathBySize
//                (getResources(),imgIds[3],App.SCREEN_WIDTH,App.SCREEN_HEIGHT));


		if(mImageUtil==null)
			Log.e(getTag(),"u is null");

        mImageUtil.loadFromRes(imgIds[0], img1);
        mImageUtil.loadFromRes(imgIds[1], img2);
        mImageUtil.loadFromRes(imgIds[2], img3);
        mImageUtil.loadFromRes(imgIds[3], img4);

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getInstance().getConfiguration().cacheBasicData(APPCacheConfig.KEY_IS_FIRST_CACHE, false);
                go2Activity(MainActivity.class, null, true);

            }
        });

        mViews = new ArrayList<>();
        mViews.add(v1);
        mViews.add(v2);
        mViews.add(v3);
        mViews.add(v4);

        // 设置适配器
        myPagerAdapter = new MyPagerAdapter();
        //转换动画
        // mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setAdapter(myPagerAdapter);
        // 设置监听事件
        mViewPager.addOnPageChangeListener(new MyPagerChangeListener());


    }

    @Override
    protected void initData() {

    }


    @Override
    protected BasePresenter getPresent() {
        return null;
    }

	@Override
	protected Bitmap getBackGroundBitmap() {
		return null;
	}

	private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {
            arg0.removeView(mViews.get(arg1));
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup arg0, int arg1) {
             arg0.addView(mViews.get(arg1));
            return mViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }

    private class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {

        }
    }
}
