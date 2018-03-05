package com.metshow.bz.commons.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.util.ImageUtil;
import com.kwan.base.widget.CircularImage;
import com.kwan.base.widget.FlowLayout;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.commons.bean.topic.Comment;
import com.metshow.bz.commons.bean.topic.Topic;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

/**
 * Created by Mr.Kwan on 2016-8-9.
 */
public class TopicListAdapter extends BaseQuickAdapter<Topic> {

    private ImageUtil imageUtil;
    private Context mContext;
    private TagClickListener tagClickListener;

    private boolean isShowRank = true;

    public TopicListAdapter(Context context, List<Topic> data) {

        super(context, R.layout.list_ltem_topic, data);
        mContext = context;
        imageUtil = new ImageUtil();
    }

    @Override
    protected void convert(BaseViewHolder helper, Topic item, final int position) {

        OnItemChildClickListener childClickListener = new OnItemChildClickListener();
        childClickListener.position = position;

        helper.setOnClickListener(R.id.iv_avatar, childClickListener);
        helper.setOnClickListener(R.id.tv_name, childClickListener);
      //  helper.setOnClickListener(R.id.tv_follow, childClickListener);

        helper.setOnClickListener(R.id.iv_icon, childClickListener);
        helper.setOnClickListener(R.id.iv_fav, childClickListener);
        helper.setOnClickListener(R.id.iv_pl, childClickListener);
        helper.setOnClickListener(R.id.iv_more, childClickListener);
        helper.setOnClickListener(R.id.ll_comment,childClickListener);

        //  helper.setImageUrl(, );
        imageUtil.loadFromUrlWithCircle(item.getAuthorIco(), (ImageView) helper.getView(R.id.iv_avatar));

        helper.setText(R.id.tv_name, item.getAuthor());
        helper.setText(R.id.tv_time, BasePresenter.getStrData(item.getPublishDate()));

        helper.setVisible(R.id.iv_isVip, item.getIsVip() > 0);
      //  helper.setVisible(R.id.iv_avatar_bg, item.getIsVip() > 0);
      //  helper.setVisible(R.id.tv_follow, !(item.getIsFollow() > 0));

        if (item.getRank() != null && !item.getRank().isEmpty()) {

            helper.setVisible(R.id.tv_rank, isShowRank);
            helper.setText(R.id.tv_rank, item.getRank());
        }

        helper.setTextTypeFace(R.id.tv_name, Typeface.DEFAULT_BOLD);

        if (item.getIsFav() == 1)
            helper.setImageResource(R.id.iv_fav, R.mipmap.article_fav_icon);
        else
            helper.setImageResource(R.id.iv_fav, R.mipmap.article_fav_normal_icon);

        final int width = App.SCREEN_WIDTH;
        final ImageView iv_icon = helper.getView(R.id.iv_icon);
        String imgpath = item.getIco();

        if (!imgpath.equals(iv_icon.getTag())) {

            iv_icon.setTag(imgpath);
            Glide.with(mContext)
                    .load(imgpath)
                    .asBitmap()
                    .placeholder(R.mipmap.item_default)
                    .override(width, BitmapImageViewTarget.SIZE_ORIGINAL)
                    .into(new SimpleTarget<Bitmap>() {

                        public void onLoadStarted(Drawable placeholder) {
                            iv_icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            iv_icon.setImageDrawable(placeholder);
                        }

                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            float scale = resource.getHeight() / (resource.getWidth() * 1.0f);
                            int viewHeight = (int) (width * scale);
                            iv_icon.setLayoutParams(new FrameLayout.LayoutParams(width, viewHeight));
                            iv_icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            iv_icon.setImageBitmap(resource);
                        }
                    });


            AutoLinearLayout ll_fav = helper.getView(R.id.ll_fav);
            List<User> users = item.getUpUserList();

            if (users.size() > 0) {

                helper.setText(R.id.tv_fav, item.getFavCount() + "个喜欢");
             //   FlowLayout fl_fav = helper.getView(R.id.fl_fav);
//                ViewGroup.MarginLayoutParams lp_fav = new ViewGroup.MarginLayoutParams(80, 80);
//                lp_fav.setMargins(0, 0, 20, 0);
//                for (User user : users) {
//
//                    CircularImage image = new CircularImage(mContext);
//                    image.setLayoutParams(lp_fav);
//                    imageUtil.loadFromUrl(user.getAvatar(), image);
//                    fl_fav.addView(image);
//
//                }

             //   ll_fav.setVisibility(View.VISIBLE);
            } else
                ll_fav.setVisibility(View.GONE);

            if (item.getContent() != null && !item.getContent().isEmpty()) {

                helper.setVisible(R.id.tv_content, true);
              //  helper.setTextTypeFace(R.id.tv_content, Typeface.DEFAULT_BOLD);
                helper.setText(R.id.tv_content, item.getContent());

            } else {
                helper.setVisible(R.id.tv_content, false);
            }

            FlowLayout fl_tag = helper.getView(R.id.fl_tag);

            if (item.getTags() != null && !item.getTags().isEmpty()) {

                String[] tags = item.getTags().split(",");
                ViewGroup.MarginLayoutParams lp_tag = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
                lp_tag.setMargins(0, 0, 20, 20);

                for (int i = 0; i < tags.length; i++) {

                    final int index = i;
                    final String tag = tags[i];
                    TextView textView = new TextView(mContext);
                    ViewGroup.MarginLayoutParams lp_tv = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
                    lp_tv.setMargins(0, 0, 10, 0);
                    textView.setLayoutParams(lp_tv);

                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 20);
                    textView.setLayoutParams(lp_tag);
                    textView.setTextColor(mContext.getResources().getColor(R.color.redWin));
                    textView.setBackgroundResource(R.drawable.shape_red_bg);
                    textView.setPadding(10, 5, 10, 5);
                    textView.setText(tag);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            tagClickListener.onTagClick(tag, index, position);
                        }
                    });

                    fl_tag.addView(textView);
                }
                fl_tag.setVisibility(View.VISIBLE);
            } else {
                fl_tag.setVisibility(View.GONE);
            }

            AutoLinearLayout ll_comment = helper.getView(R.id.ll_comment);
            List<Comment> comments = item.getCommentList();

            if (comments != null && comments.size() > 0) {

                helper.setText(R.id.tv_comment, item.getCommentCount() + "条评论");

				int i = 0;

                for (Comment comment : comments) {

					if(i==4)
						break;

                    AutoLinearLayout layout = new AutoLinearLayout(mContext);
                    layout.setOrientation(AutoLinearLayout.HORIZONTAL);

                    TextView tv_name = new TextView(mContext);
                    tv_name.setTypeface(Typeface.DEFAULT_BOLD);
					tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);

                    TextView tv_comment = new TextView(mContext);
                    tv_name.setText(comment.getNickName() + ": ");
                    tv_comment.setText(comment.getContent());
					tv_comment.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);

                    tv_comment.setPadding(0, 0, 0, 20);
                    tv_name.setPadding(0, 0, 0, 20);

                    layout.addView(tv_name);
                    layout.addView(tv_comment);

                    ll_comment.addView(layout);
					i++;
                }
                ll_comment.setVisibility(View.VISIBLE);
            } else {
                ll_comment.setVisibility(View.GONE);
            }
        }
    }

    public void setTagClickListener(TagClickListener tagClickListener) {
        this.tagClickListener = tagClickListener;
    }

    public interface TagClickListener {
        void onTagClick(String tag, int indextag, int position);
    }

    public void setShowRank(boolean showRank) {
        isShowRank = showRank;
    }
}
