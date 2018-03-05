package com.metshow.bz.module.commons.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
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
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.ChatAdapter;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.commons.bean.topic.Comment;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.CommentPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.metshow.bz.util.DialogFactory;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends BaseChatActivity implements BZContract.ICommentView, OnRefreshListener, BaseQuickAdapter.OnRecyclerViewItemClickListener, OnLoadMoreListener, BaseQuickAdapter.OnRecyclerViewItemChildClickListener {

    // Type:1为文章 2为商品 3为社区 4为专题

    public static final int TYPE_ARTICAL = 1;
    public static final int TYPE_PRODUCT = 2;
    public static final int TYPE_TOPIC = 3;
    public static final int TYPE_SUBJECT = 4;
    private List<Comment> mComments = new ArrayList<>();
    private BaseQuickAdapter<Comment> adapter;
    private long ParentId = 0;
    private int type;
    private long refid;
    private CommentPresenter mPresenter;
    private String imgContent;
    private boolean isShowKeyBoard = false;
    private boolean isLoadMore = false;
    private Comment sendComment;

	private ArrayList<Comment> sendedComment = new ArrayList<>();

    private User user;
    private String toNikename;
    private long toUserId;

    @Override
    protected void beForeSetContentView() {
        super.beForeSetContentView();

        type = (int) getIntentData("type");
        refid = (long) getIntentData("refid");
        mPresenter = new CommentPresenter(this);

    }

    @Override
    protected void initViewSetting() {
        super.initViewSetting();

        iv_delete.setOnClickListener(this);

        swipeToLoadLayout.setLoadMoreEnabled(true);
        swipeToLoadLayout.setRefreshEnabled(true);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        initAdapter();

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
    protected void onSend(String content) {

        if (user == null)
            user = BZSPUtil.getUser();

        sendComment = new Comment();
        sendComment.setNickName(user.getNickName());
        sendComment.setAvatar(user.getAvatar());
        sendComment.setParentId(ParentId);
        sendComment.setContent(content);
        sendComment.setRefId(refid);
        sendComment.setType(type);
        sendComment.setUserId(user.getUserId());
        sendComment.setToNickName(toNikename);
        sendComment.setToUserId(toUserId);
        sendComment.setCreateDate("/Date(" + (System.currentTimeMillis() + (8 * 60 * 60 * 1000)) + ")/");

        if (imgContent != null && !imgContent.isEmpty()) {
            //先上传图片 获取图片的url
            showProgress("发送中..");

            mPresenter.upLoadImag(imgContent, content, refid, type, ParentId);
            fl_pop.setVisibility(View.GONE);
            sendComment.setImage(imgContent);
            imgContent = null;

        } else {
            //直接发送文字评论
            showProgress("发送中..");
            mPresenter.addComment(content, null, refid, type, ParentId);
        }
    }

    @Override
    protected void onSelectImageBack(String s) {
        imgContent = s;
        showImgPopup(imgContent);
    }

    @Override
    protected BasePresenter getPresent() {
        return mPresenter;
    }

    @Override
    protected String getTitleTxt() {
        return "评论";
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        mPresenter.getCommentList(type, refid, "0");
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        mPresenter.getCommentList(type, refid, BasePresenter.getData(mComments.get(mComments.size() - 1).getCreateDate()));

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


        if (isLoadMore)
            adapter.addData(comments);
        else
            adapter.setNewData(comments);


        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
        mRecyclerView.setVisibility(View.VISIBLE);
    }



    @Override
    public void addCommnetSuccess(int result) {


        sendComment.setCommentId(result);

        if (mComments.size() == 0) {
            mComments.add(sendComment);
            adapter.setNewData(mComments);

        } else {
            mComments.add(sendComment);
            adapter.add(0, sendComment);
        }

        mRecyclerView.scrollVerticallyTo(0);

		sendedComment.add(sendComment);
        dismissProgress();
    }




    @Override
    public void reportSuccess(Integer integer) {

        toastMsg("举报成功");
		dismissProgress();
    }


    private void initAdapter() {

        adapter = new BaseQuickAdapter<Comment>(this, R.layout.list_item_comment, mComments) {

            @Override
            protected void convert(BaseViewHolder helper, Comment item, int position) {

                mImageUtil.loadFromUrlWithCircle(item.getAvatar(), (ImageView) helper.getView(R.id.iv_avatar));

                helper.setText(R.id.tv_name, item.getNickName());
                helper.setTextTypeFace(R.id.tv_name, Typeface.DEFAULT_BOLD);
                helper.setText(R.id.tv_time, BasePresenter.getStrData(item.getCreateDate()));

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

                helper.setOnClickListener(R.id.ll_more, new OnItemChildClickListener());
            }
        };

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 20, 20, 20);

        TextView textView = new TextView(this);

        textView.setLayoutParams(lp);
        textView.setTextSize(15);
        textView.setText("还没有评论，去发布");
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.color.blueGray);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(20, 20, 20, 20);

        textView.setOnClickListener(this);

        adapter.setEmptyView(textView);
        adapter.setOnRecyclerViewItemClickListener(this);
        adapter.setOnRecyclerViewItemChildClickListener(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setVisibility(View.GONE);

    }

    @Override
    public void onItemClick(View view, int position) {

        if (!isShowKeyBoard) {

            Comment comment = mComments.get(position);
            et_content.requestFocus();
            mInputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            et_content.setHint("回复：" + comment.getNickName());

            ParentId = mComments.get(position).getCommentId();
            toNikename = comment.getNickName();
            toUserId = comment.getUserId();
        }
    }

    @Override
    public void onServerError(int vocational_id, Throwable e) {
        super.onServerError(vocational_id, e);
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onServerFailed(String s) {
        super.onServerFailed(s);
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v == iv_delete) {
            fl_pop.setVisibility(View.GONE);
            imgContent = null;
        } else if (v == adapter.getEmptyView()) {

            et_content.requestFocus();
            mInputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            et_content.setHint("我有话说..");
            ParentId = 0;
        }
    }

    private void showImgPopup(String imgPath) {

        fl_pop.setVisibility(View.VISIBLE);
        BubbleImageView imageView = (BubbleImageView) fl_pop.findViewById(R.id.iv_content);
        mImageUtil.loadFromFile(imgPath, imageView);
    }

    @Override
    protected void onSoftKeyBoardVisible(boolean visible) {
        super.onSoftKeyBoardVisible(visible);
        isShowKeyBoard = visible;

        if (!isShowKeyBoard) {
            ParentId = 0;
            et_content.setHint("我有话要说..");
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("data", sendComment);
		intent.putExtra("array",sendedComment);
        setResult(RESULT_OK, intent);
        super.onBackPressed();

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        if (view.getId() == R.id.ll_more) {

            final Comment comment = mComments.get(position);

            View parent = getLayoutInflater().inflate(R.layout.menu_comment, null, false);
            final Dialog mDialog = DialogFactory.showMenuDialog(this, parent);
            mDialog.setCanceledOnTouchOutside(true);

            TextView tv_cancel = (TextView) parent.findViewById(R.id.tv_cancel);
            TextView tv_report = (TextView) parent.findViewById(R.id.tv_report);

            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();
                }
            });

            tv_report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();
                    mPresenter.reportComment(comment.getCommentId());
                    showProgress("");
                }
            });

        }
    }

}
