package com.metshow.bz.module.shai;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.github.library.bubbleview.BubbleImageView;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.util.SPUtil;
import com.metshow.bz.IUmengEvent;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.adapter.BZViewPageAdapter;
import com.metshow.bz.commons.adapter.ChatAdapter;
import com.metshow.bz.commons.bean.Product;
import com.metshow.bz.commons.bean.ProductPicture;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.commons.bean.topic.Comment;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.activity.BaseChatActivity;
import com.metshow.bz.module.commons.activity.ImageActivity;
import com.metshow.bz.module.commons.activity.WebProductActivity;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.presenter.CommentPresenter;
import com.metshow.bz.presenter.ProductPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.metshow.bz.util.ShareUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends BaseChatActivity implements BZContract.IProductView, BZViewPageAdapter.ClickItemListener, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnRecyclerViewItemClickListener, BZContract.ICommentView {

    private String name;
    private boolean isLogin;
    private long id;
    private ViewPager viewPager;
    private ProductPresenter mPresenter;
    private CommentPresenter mCommentPresenter;
    private User user;
    private Comment sendComment;
    private AutoLinearLayout ll_banner_dot;
    private ArrayList<View> dots;
    private ArrayList<ImageView> imageViews;
    private ArrayList<Comment> mComments = new ArrayList<>();
    private boolean isShowKeyBoard = false;
    private boolean isFav;
    private Product mProduct;
    private BaseQuickAdapter<Comment> adapter;
    private boolean isFirstLoad = true;
    private boolean isLoadMore = false;
    private long ParentId = 0;
    private String toNikename;
    private long toUserId;
    private String imgContent;
    private List<ProductPicture> pictures;
    private TextView tv_name, tv_price, tv_buy, tv_note, tv_info, tv_tag_info, tv_tag_note;
    private ImageView iv_comment, iv_fav, iv_resend;
    private AutoLinearLayout ll_relative_list,ll_relative_tag;
    private ArrayList<String> urls = new ArrayList<>();
    private View headView;
    private BZViewPageAdapter<ImageView> mBannerAdapter;
    private List<Product> relatives;

    @Override
    protected void beForeSetContentView() {
        super.beForeSetContentView();

        isLogin = SPUtil.getIsLogin();
        name = (String) getIntentData("name");
        id = (long) getIntentData("id");
        mPresenter = new ProductPresenter(this);
        mCommentPresenter = new CommentPresenter(this);
        App.umengEvent(this, IUmengEvent.ProductView,name+":"+id);

    }


    @Override
    protected void initViewSetting() {
        super.initViewSetting();

        swipeToLoadLayout.setLoadMoreEnabled(true);
        swipeToLoadLayout.setRefreshEnabled(true);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        headView = getLayoutInflater().inflate(R.layout.activity_product, mRecyclerView, false);
        viewPager = (ViewPager) headView.findViewById(R.id.vp);
        ll_banner_dot = (AutoLinearLayout) headView.findViewById(R.id.ll_banner_dot);

       // ll_banner_dot.setVisibility(View.GONE);

        tv_name = (TextView) headView.findViewById(R.id.tv_name);
        tv_price = (TextView) headView.findViewById(R.id.tv_price);
        tv_buy = (TextView) headView.findViewById(R.id.tv_buy);
        tv_note = (TextView) headView.findViewById(R.id.tv_note);
        tv_info = (TextView) headView.findViewById(R.id.tv_info);
        tv_tag_info = (TextView) headView.findViewById(R.id.tv_tag_info);
        tv_tag_note = (TextView) headView.findViewById(R.id.tv_tag_note);
        iv_comment = (ImageView) headView.findViewById(R.id.iv_comment);
        iv_fav = (ImageView) headView.findViewById(R.id.iv_fav);
        iv_resend = (ImageView) headView.findViewById(R.id.iv_resend);
        ll_relative_list = (AutoLinearLayout) headView.findViewById(R.id.ll_relative_list);
		ll_relative_tag= (AutoLinearLayout) headView.findViewById(R.id.ll_relative_tag);
        tv_buy.setOnClickListener(this);
        iv_comment.setOnClickListener(this);
        iv_fav.setOnClickListener(this);
        iv_resend.setOnClickListener(this);

        setSwipeMargins(false);
        ll_edit.setVisibility(View.GONE);


    }

    @Override
    protected void onSend(String content) {

        if (user == null)
            user = BZSPUtil.getUser();

        sendComment = new Comment();
        sendComment.setNickName(user.getNickName());
        sendComment.setAvatar(user.getAvatar());
        sendComment.setParentId(ParentId);
        sendComment.setContent(content);
        sendComment.setRefId(id);
        sendComment.setType(2);
        sendComment.setUserId(user.getUserId());
        sendComment.setToNickName(toNikename);
        sendComment.setToUserId(toUserId);
        sendComment.setCreateDate("/Date(" + (System.currentTimeMillis()+(8*60*60*1000)) + ")/");

        if (imgContent != null && !imgContent.isEmpty()) {
            //先上传图片 获取图片的url
            showProgress("发送中..");

            mCommentPresenter.upLoadImag(imgContent, content, id, 2, ParentId);
            fl_pop.setVisibility(View.GONE);
            sendComment.setImage(imgContent);
            imgContent = null;
            isbackimag = false;
        } else {
            //直接发送文字评论
            showProgress("发送中..");
            mCommentPresenter.addComment(content, null, id, 2, ParentId);
        }
    }

    private boolean isbackimag;

    @Override
    protected void onSelectImageBack(String s) {
        imgContent = s;
        isbackimag = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isbackimag) {

            ll_edit.setVisibility(View.VISIBLE);

            if (mIsFaceShow)
                mInputMethodManager.hideSoftInputFromWindow(et_content.getWindowToken(), 0);
            else {
                et_content.requestFocus();
                mInputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
            showImgPopup(imgContent);
        }
    }

	@Override
	protected void initData() {
		swipeToLoadLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeToLoadLayout.setRefreshing(true);
			}
		});
	}

	@Override
    public void onRefresh() {

        isLoadMore = false;

        if (isFirstLoad) {
            isFirstLoad = false;
            mPresenter.getProduct(id, isLogin);
        } else {
            mCommentPresenter.getCommentList(2, id, "0");
        }
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        if (mComments.size() > 0)
            mCommentPresenter.getCommentList(2, id, BasePresenter.getData(mComments.get(mComments.size() - 1).getCreateDate()));
        else
            mCommentPresenter.getCommentList(2, id, "0");
    }

    @Override
    protected String getTitleTxt() {
        return "单品详情";
    }

    @Override
    public void getProductSuccess(Product result) {

        if (result == null) {
            toastMsg("无效商品ID");
            onBackPressed();
            return;
        }
        pictures = result.getProductPictures();
        mProduct = result;
        setUpBanner();

        if (name == null || name.isEmpty())
            tv_title.setText(result.getProductName());

        tv_name.setText(result.getProductName());
        tv_price.setText("价格 ￥" + result.getUnitPrice());

        if (result.getEditorNote() == null || result.getEditorNote().isEmpty()) {
            tv_tag_note.setVisibility(View.GONE);
            tv_note.setVisibility(View.GONE);
        } else
            tv_note.setText(result.getEditorNote());

        if (result.getContent() == null || result.getContent().isEmpty()) {
            tv_tag_info.setVisibility(View.GONE);
            tv_info.setVisibility(View.GONE);
        } else
            tv_info.setText(result.getContent());


        if (result.getIsFav() > 0) {
            isFav = true;
            iv_fav.setImageResource(R.mipmap.article_fav_icon);
        }

        if (result.getIsComment() > 0) {
            isFav = false;
            iv_comment.setImageResource(R.mipmap.article_comment_icon);
        }

        relatives = result.getRelativeList();

		ll_relative_tag.setVisibility(View.GONE);
        if (relatives != null && relatives.size() > 0)
			ll_relative_tag.setVisibility(View.VISIBLE);
            for (int i = 0; i < relatives.size(); i++) {

                if (i == 3)
                    break;

                final Product product = relatives.get(i);

                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 240);
                lp.weight = 1;
                lp.setMargins(5, 5, 5, 5);
                imageView.setLayoutParams(lp);
                imageView.setPadding(10, 10, 10, 10);

                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			//	imageView.setBackgroundColor(getResources().getColor(R.color.txt_white));


                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("name", product.getProductName());
                        bundle.putLong("id", product.getProductId());

                        go2Activity(ProductActivity.class, bundle, false);

                    }
                });
                mImageUtil.loadFromUrlNoCrop(relatives.get(i).getPicture(), imageView);
                ll_relative_list.addView(imageView);

            }
        mCommentPresenter.getCommentList(2, id, "0");
    }

    @Override
    public void favSuccess(String result, int position) {

        isFav = true;
        iv_fav.setImageResource(R.mipmap.article_fav_icon);
		dismissProgress();
    }

    @Override
    public void deleteFavSuccess(String result, int position) {
        isFav = false;
        iv_fav.setImageResource(R.mipmap.article_fav_normal_icon);
       dismissProgress();
    }

    @Override
    public void onServerError(int vocational_id, Throwable e) {
        super.onServerError(vocational_id, e);

        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
    }

    private void setUpBanner() {

        imageViews = new ArrayList<>();
        dots = new ArrayList<>();

        for (int i = 0; i < pictures.size(); i++) {

            View v = new View(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(16, 16);
            lp.setMargins(10, 0, 10, 0);
            v.setLayoutParams(lp);

            if (i == 0)
                v.setBackgroundResource(R.drawable.dot_focused);
            else
                v.setBackgroundResource(R.drawable.dot_normal);

            dots.add(v);
            ll_banner_dot.addView(v);

        }

        for (int i = 0; i < pictures.size(); i++) {

            ImageView imageView = new ImageView(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            mImageUtil.loadFromUrlNoCrop(pictures.get(i).getPicPath(), imageView);

            imageViews.add(imageView);
            dots.get(i).setVisibility(View.VISIBLE);
            urls.add(pictures.get(i).getPicPath());

        }

        mBannerAdapter = new BZViewPageAdapter<>(imageViews);
        mBannerAdapter.setOnClickItemListener(this);

        // 设置填充ViewPager页面的适配器
        viewPager.setAdapter(mBannerAdapter);
        // 设置一个监听器，当ViewPager中的页面改变时调用
        viewPager.addOnPageChangeListener(new MyPageChangeListener());
    }

    @Override
    public void onClickItem(ViewGroup container, int position, View view) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("urls", urls);
        go2Activity(ImageActivity.class, bundle, false);
    }


    @Override
    public void onItemClick(View view, int position) {

        if (isLogin) {

            if (!isShowKeyBoard) {

                ll_edit.setVisibility(View.VISIBLE);

                Comment comment = mComments.get(position);
                et_content.requestFocus();
                mInputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                et_content.setHint("回复：" + comment.getNickName());
                ParentId = comment.getCommentId();

                toNikename = comment.getNickName();
                toUserId = comment.getUserId();
            }
        } else {
            go2Activity(DologinActivity.class, null, false);
        }

    }

	@Override
	public void getMyCommentSuccess(List<Comment> comments) {

	}

	@Override
    public void getCommentListSuccess(List<Comment> comments) {

        if (isLoadMore)
            mComments.addAll(comments);
        else {
            mComments.clear();
            mComments.addAll(comments);
        }

        if (adapter == null) {
            initAdapter();
        } else {
            if (isLoadMore)
                adapter.addData(comments);
            else
                adapter.setNewData(comments);
        }

        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);

    }

    @Override
    public void addCommnetSuccess(int result) {

        sendComment.setCommentId(result);
        mComments.add(0, sendComment);
        adapter.add(0, sendComment);
        mRecyclerView.scrollToPosition(1);
        dismissProgress();

    }

    @Override
    public void reportSuccess(Integer integer) {

    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        private int oldPosition = 0;

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {

            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            oldPosition = position;
        }

    }


    private void initAdapter() {

        adapter = new BaseQuickAdapter<Comment>(this, R.layout.list_item_comment, mComments) {

            @Override
            protected void convert(BaseViewHolder helper, Comment item, int position) {

                // helper.setImageUrl(R.id.iv_avatar, );

                mImageUtil.loadFromUrlWithCircle(item.getAvatar(), (ImageView) helper.getView(R.id.iv_avatar));

                helper.setText(R.id.tv_name, item.getNickName());
                helper.setTextTypeFace(R.id.tv_name, Typeface.DEFAULT_BOLD);
                helper.setText(R.id.tv_time, BasePresenter.getStrData(item.getCreateDate()));
                helper.setVisible(R.id.line, false);

                if (position == mComments.size() - 1)
                    helper.setVisible(R.id.line, false);
                else
                    helper.setVisible(R.id.dashline, true);


                //  ((ImageView) helper.getView(R.id.line)).setImageResource(R.drawable.shape_dash);

                if (item.getParentId() > 0) {
                    helper.setText(R.id.tv_content, ChatAdapter.getTxtMsg("@" + item.getToNickName() + " " + item.getContent()));
                } else {
                    helper.setText(R.id.tv_content, ChatAdapter.getTxtMsg(item.getContent()));
                }

                if (item.getImage() == null || item.getImage().isEmpty()) {
                    helper.setVisible(R.id.iv_content, false);
                } else {
                    helper.setVisible(R.id.iv_content, true);
                    helper.setImageUrl(R.id.iv_content, item.getImage());
                }
            }
        };

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 20, 20, 40);

        TextView textView = new TextView(this);
        textView.setLayoutParams(lp);
        textView.setTextSize(20);
        textView.setText("发表评论");
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.color.blueGray);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(20, 20, 20, 20);

        textView.setOnClickListener(this);
        adapter.setEmptyView(textView);
        adapter.addHeaderView(headView);
        adapter.setOnRecyclerViewItemClickListener(this);
        mRecyclerView.setAdapter(adapter);


    }


    private void showImgPopup(String imgPath) {

        fl_pop.setVisibility(View.VISIBLE);
        BubbleImageView imageView = (BubbleImageView) fl_pop.findViewById(R.id.iv_content);
        mImageUtil.loadFromFile(imgPath, imageView);
    }

    protected void onSoftKeyBoardVisible(boolean visible) {
        super.onSoftKeyBoardVisible(visible);
        isShowKeyBoard = visible;


        if (!isShowKeyBoard && mFaceRoot.getVisibility() == View.GONE && !isbackimag && !mIsFaceShow) {

            fl_pop.setVisibility(View.GONE);
            ll_edit.setVisibility(View.GONE);
            setSwipeMargins(false);

        } else {

            ll_edit.setVisibility(View.VISIBLE);
            setSwipeMargins(true);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v == iv_comment || v == adapter.getEmptyView()) {
            if (SPUtil.getIsLogin()) {
                ll_edit.setVisibility(View.VISIBLE);
                et_content.requestFocus();
                mInputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                et_content.setHint("我有话说..");
                ParentId = 0;
            } else {
                go2Activity(DologinActivity.class, null, false);
            }

        } else if (v == iv_fav) {
            if (SPUtil.getIsLogin()) {
                if (isFav)
                    mPresenter.deteleFav(id, 0);
                else
                    mPresenter.addFav(id, 0);
                showProgress("");
            } else {
                go2Activity(DologinActivity.class, null, false);
            }

        } else if (v == iv_resend) {

            ShareUtil util = new ShareUtil(this);
            util.setShare_Type(IUmengEvent.ProductShare);
            util.setStr_url("http://bz.metshow.com/api/product.html?productid=" + mProduct.getProductId());
            util.setStr_img(mProduct.getPicture());
            util.setStr_title(mProduct.getProductName());
            util.setmText(mProduct.getProductName());

            util.showShareDialog();

        } else if (v == tv_buy) {
            Bundle bundle = new Bundle();
            bundle.putString("url", mProduct.getLink());
            bundle.putString("title", mProduct.getProductName());
            bundle.putString("txt", mProduct.getBrandName());
            bundle.putString("img_url", mProduct.getPicture());

            go2ActivityWithLeft(WebProductActivity.class, bundle, false);
        }
    }
}
