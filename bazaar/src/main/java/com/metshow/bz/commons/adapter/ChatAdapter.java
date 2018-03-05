package com.metshow.bz.commons.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kwan.base.activity.BaseActivity;
import com.kwan.base.adatper.base.BaseMultiItemQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.util.ImageStringParser;
import com.kwan.base.util.ImageUtil;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.message.MsgChatDetail;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




/**
 *
 * Created by Mr.Kwan on 2016-7-27.
 */
public class ChatAdapter extends BaseMultiItemQuickAdapter<MsgChatDetail> {

    protected BaseActivity mContext;
    protected ImageUtil imageUtil;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param context The context.
     * @param data    A new list is created out of this one to avoid mutable list
     */
    public ChatAdapter(BaseActivity context, List<MsgChatDetail> data) {
        super(context, data);
        mContext = context;
        addItmeType(0, R.layout.chatting_item_msg_text_right);
        addItmeType(1, R.layout.chatting_item_msg_text_left);

        imageUtil = new ImageUtil();
    }


    @Override
    protected void convert(BaseViewHolder helper, MsgChatDetail item, int position) {

        int srcType = item.getType();

        if (srcType == 0) {

            helper.getView(R.id.iv_content).setVisibility(View.GONE);
            helper.getView(R.id.tv_content).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_content, getTxtMsg(item.getContent()));

        } else if (srcType == 1) {

            helper.getView(R.id.tv_content).setVisibility(View.GONE);
            helper.getView(R.id.iv_content).setVisibility(View.VISIBLE);

            final ImageView view = helper.getView(R.id.iv_content);
            Glide.with(mContext)
                    .load(item.getUrl())
                    .asBitmap()
                    .placeholder(R.mipmap.item_default)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                            float scale = resource.getHeight() / (resource.getWidth() * 1.0f);
                            int viewHeight = (int) (380 * scale);
                            view.setLayoutParams(new FrameLayout.LayoutParams(380, viewHeight));
                            view.setImageBitmap(resource);
                        }
                    });

        }
        imageUtil.loadFromUrlWithCircle(item.getUserAvatar(), (ImageView) helper.getView(R.id.iv_avatar));

        helper.setText(R.id.tv_date, BasePresenter.getStrData(item.getCreateDate()));

    }

    public static SpannableStringBuilder getTxtMsg(String content) {

        if (content == null)
            content = "";

        Pattern pattern = ImageStringParser.buildPattern(App.getInstance().getFaceString());
        Matcher matcher = pattern.matcher(content);
        HashMap<String, Bitmap> bitmapHashMap = new HashMap<String, Bitmap>();

        while (matcher.find()) {
            String str = matcher.group();
            Integer in = App.getInstance().getFaceMap().get(str);
            if (in != null) {
                Bitmap bitmap = BitmapFactory.decodeResource(
                        App.getInstance().getResources(), in);
                bitmapHashMap.put(str, ImageUtil.scaleBitmap(bitmap, 60, 60));
            }
        }
        ImageStringParser isp = new ImageStringParser(App.getInstance(), bitmapHashMap);
        return isp.replace(content);

    }
}
