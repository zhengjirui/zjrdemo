package com.metshow.bz.module.shai;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kwan.base.activity.CommonRecycleActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.TopicListAdapter;
import com.metshow.bz.commons.bean.article.ArticlePicture;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.activity.CommentActivity;
import com.metshow.bz.module.commons.activity.ImageActivity;
import com.metshow.bz.module.commons.activity.RecorderActivity;
import com.metshow.bz.module.commons.activity.SendTopicActivity;
import com.metshow.bz.module.commons.activity.FriendDetailActivity;
import com.metshow.bz.presenter.TopicListPresenter;
import com.metshow.bz.util.DialogFactory;
import com.metshow.bz.util.TopicMenuUtil;
import com.tencent.smtt.sdk.VideoActivity;
import com.yongchun.library.utils.FileUtils;
import com.yongchun.library.view.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TopicListActivity extends CommonRecycleActivity implements BZContract.ITopicListView, BaseQuickAdapter.OnRecyclerViewItemChildClickListener, TopicListAdapter.TagClickListener, BZContract.ITopicMenuView, BaseQuickAdapter.OnRecyclerViewItemClickListener {

    private long userId;
    private TopicListPresenter mPresenter;
    private boolean isLoadMore;
    private final int CAMERA_REQUEST_CODE = 1;
    private final int PageSize = 10;
    //private ArrayList<Topic> mTopics = new ArrayList<>();
    private TopicListAdapter mAdapter;
    private String name;
    public static final int MODE_USER = 1;
    public static final int MODE_TAG = 2;
    private String cameraPath;
    private int pageindex = 1;
    private String mTag;
    private View titleRight;
    private int mType;
    private Dialog mSendTopicDialog;

    @Override
    protected void beForeSetContentView() {

        mType = (int) getIntentData("type");

        if (mType == MODE_USER) {
            name = (String) getIntentData("name");
            userId = (long) getIntentData("userid");
        } else {
            mTag = (String) getIntentData("tag");
        }

        mPresenter = new TopicListPresenter(this);

    }

    @Override
    protected void initViewSetting() {


        super.initViewSetting();

        mRecyclerView.setAdapter(mAdapter);

    }

	@Override
	protected void initData() {
		super.initData();
		postSwipeLayout();
	}

	@Override
	protected BaseQuickAdapter getAdapter() {
		mAdapter = new TopicListAdapter(this, new ArrayList<Topic>());
		mAdapter.setOnRecyclerViewItemChildClickListener(this);
		mAdapter.setOnRecyclerViewItemClickListener(this);
		mAdapter.setTagClickListener(this);
		return mAdapter;
	}

	@Override
	protected RecyclerView.LayoutManager getLayoutManager() {
		return new LinearLayoutManager(this);
	}

	@Override
    protected String getTitleTxt() {

        if (mType == MODE_USER)
            return name;
        return mTag;
    }

    protected int getTitleBarRightLayoutId() {
        return R.layout.title_right_setting;
    }

    @Override
    protected void setUpTitleRightView(View v) {
        super.setUpTitleRightView(v);
        ((ImageView) v).setImageResource(R.mipmap.add_topic_icon);
        titleRight = v;
        titleRight.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if(v==titleRight)
            showTopicDialog();
        else {
            switch (v.getId()) {
                case R.id.tv_video:
                    go2Activity(RecorderActivity.class, null, false);
                    mSendTopicDialog.cancel();
                    break;

                case R.id.tv_camera:

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        //申请WRITE_EXTERNAL_STORAGE权限
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                                CAMERA_REQUEST_CODE);

                    } else {
                        starCam();
                    }
                    mSendTopicDialog.cancel();
                    break;

                case R.id.tv_photo:

                    ImageSelectorActivity.setOnResultListener(new ImageSelectorActivity.OnResultListener() {
                        @Override
                        public void onResult(ArrayList<String> images) {
                            Bundle bundle = new Bundle();
                            bundle.putString("output", images.get(0));
                            bundle.putInt("mode", 2);
                            go2Activity(SendTopicActivity.class, bundle, false);
                        }
                    });

                    Bundle bundle = new Bundle();
                    bundle.putInt(ImageSelectorActivity.EXTRA_SELECT_MODE, ImageSelectorActivity.MODE_SINGLE);
                    bundle.putBoolean(ImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
                    bundle.putBoolean(ImageSelectorActivity.EXTRA_IS_FINISHRESULT, false);
                    go2Activity(ImageSelectorActivity.class, bundle, false);

                    mSendTopicDialog.cancel();
                    break;

                case R.id.tv_cancel:
                    mSendTopicDialog.cancel();
                    break;

            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                starCam();
            } else {
                Toast.makeText(this, "没有相机权限", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void starCam() {

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File cameraFile = FileUtils.createCameraFile(this);
        cameraPath = cameraFile.getAbsolutePath();

        i.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
        startActivityForResult(i, CAMERA_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE) {

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(cameraPath))));
            Bundle bundle = new Bundle();
            bundle.putInt("mode", 2);
            bundle.putString("output", cameraPath);
            go2Activity(SendTopicActivity.class, bundle, false);

        }
    }


    private void showTopicDialog() {

        View parent = getLayoutInflater().inflate(R.layout.send_topic_dialog, null);
        TextView tv_video = (TextView) parent.findViewById(R.id.tv_video);
        TextView tv_cancel = (TextView) parent.findViewById(R.id.tv_cancel);
        TextView tv_photo = (TextView) parent.findViewById(R.id.tv_photo);
        TextView tv_camera = (TextView) parent.findViewById(R.id.tv_camera);

        tv_video.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_photo.setOnClickListener(this);
        tv_camera.setOnClickListener(this);


        mSendTopicDialog = DialogFactory.showMenuDialog(this, parent);
    }

    @Override
    public void onLoadMore() {

        isLoadMore = true;

        if (mType == MODE_USER)
            mPresenter.getUserTopics(PageSize, BasePresenter.getData(mAdapter.getData().get(mAdapter.getData().size() - 1).getPublishDate()), userId);
        else if (mType == MODE_TAG) {
            pageindex = pageindex + 1;
            mPresenter.getTagTopics(PageSize, pageindex, mTag);
        }
    }

    @Override
    public void onRefresh() {

        isLoadMore = false;

        if (mType == MODE_USER)
            mPresenter.getUserTopics(PageSize, "0", userId);
        else if (mType == MODE_TAG)
            mPresenter.getTagTopics(PageSize, 1, mTag);
    }

    @Override
    public void getUserTopicsSuccess(List<Topic> items) {

        if (!isLoadMore) {
            // mTopics.clear();
            mAdapter.setNewData(items);
        } else {
            mAdapter.addData(items);
        }

        //mTopics.addAll(items);
		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
    }

    public void postSwipeLayout() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
				swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Topic topic = mAdapter.getData().get(position);
        switch (view.getId()) {

            case R.id.iv_avatar:
                go2UserDetail(position);
                break;
            case R.id.tv_name:
                go2UserDetail(position);
                break;
            case R.id.tv_follow:
                mPresenter.addFollow(userId, position);
                showProgress("");
                break;
            case R.id.iv_icon:
                toTopicDetail(topic);
                break;
            case R.id.iv_fav:
                if (topic.getIsFav() == 1)
                    mPresenter.deleteFav(topic.getArticleId(), position);
                else
                    mPresenter.addAcition(1, topic.getArticleId(), position);
				showProgress("");
                break;
            case R.id.iv_pl:
                Bundle bundle = new Bundle();
                bundle.putInt("type", CommentActivity.TYPE_TOPIC);
                bundle.putLong("refid", topic.getArticleId());
                go2ActivityWithLeft(CommentActivity.class, bundle, false);
                break;

            case R.id.ll_comment:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", CommentActivity.TYPE_TOPIC);
                bundle1.putLong("refid", topic.getArticleId());
                go2ActivityWithLeft(CommentActivity.class, bundle1, false);
                break;

            case R.id.iv_more:
                new TopicMenuUtil(this, topic, this, position).showShareDialog();
                break;
        }
    }

    @Override
    public void favSuccess(String result, int position) {

        mAdapter.getItem(position).setIsFav(1);
        mAdapter.getItem(position).setFavCount(Integer.parseInt(result));
        mAdapter.notifyItemChanged(position);
        dismissProgress();
    }

    @Override
    public void deleteFavSuccess(String result, int position) {
        mAdapter.getItem(position).setIsFav(0);
        mAdapter.getItem(position).setFavCount(Integer.parseInt(result));
        mAdapter.notifyItemChanged(position);
        dismissProgress();
    }

    @Override
    public void addFollowSuccess(String result, int position) {

        for (Topic topic : mAdapter.getData()) {
            topic.setIsFollow(1);
        }

        mAdapter.notifyDataSetChanged();
        toastMsg("已关注");
        dismissProgress();
    }

    private void go2UserDetail(int position) {

        Bundle bundle = new Bundle();
        bundle.putLong("userid", userId);
        bundle.putInt("isFollow", mAdapter.getData().get(position).getIsFollow());
        go2ActivityWithLeft(FriendDetailActivity.class, bundle, false);

    }

    private void toTopicDetail(Topic topic) {

        int type = topic.getArticleType();
        Bundle bundle = new Bundle();

        switch (type) {
            //视频
            case 6:
                bundle.putString("url", topic.getVideoUrl());
                go2Activity(VideoActivity.class, bundle, false);
                break;
            //图组
            case 7:
                List<ArticlePicture> pictures = topic.getArticlePictures();
                ArrayList<String> urls = new ArrayList<>();
                ArrayList<String> titles = new ArrayList<>();
                for (ArticlePicture picture : pictures) {
                    urls.add(picture.getPicPath());
                    titles.add(picture.getRemark());
                }
                bundle.putStringArrayList("urls", urls);
                bundle.putStringArrayList("titles", titles);
                go2Activity(ImageActivity.class, bundle, false);
                break;
        }
    }

    @Override
    public void onTagClick(String tag, int indextag, int position) {

        Bundle bundle = new Bundle();
        bundle.putInt("type", TopicListActivity.MODE_TAG);
        bundle.putString("tag", tag);
        go2ActivityWithLeft(TopicListActivity.class, bundle, false);

    }

    //取消关注
    @Override
    public void deleteFollowSuccess(Boolean result, int position) {

        if (result) {
            for (Topic topic : mAdapter.getData())
                topic.setIsFollow(0);

            mAdapter.notifyDataSetChanged();
            toastMsg("已取消关注");
        } else {
            toastMsg("取消失败");
        }
        dismissProgress();
    }

    //屏蔽
    @Override
    public void disViewSuccess(int result, int position) {

        if (result == 1) {
            mAdapter.remove(position);
            mAdapter.remove(position);
            mAdapter.notifyItemRemoved(position);
            toastMsg("已屏蔽");
        } else {
            toastMsg("屏蔽失败");
        }
        dismissProgress();
    }

    //举报
    @Override
    public void reportSuccess(Boolean result, int position) {
        if (result) {
            toastMsg("举报成功");
        } else {
            toastMsg("举报失败");
        }
        dismissProgress();

    }

    //删除
    @Override
    public void deleteTopicSuccess(Boolean result, int position) {
        if (result) {
            mAdapter.remove(position);
            mAdapter.notifyItemRemoved(position);
            toastMsg("已删除");
        } else {
            toastMsg("删除失败");
        }
       dismissProgress();
    }

    @Override
    public void onItemClick(View view, int position) {
        Topic topic = mAdapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putLong("topicid",topic.getArticleId());
        go2Activity(TopicActivity.class,bundle,false);
    }
}
