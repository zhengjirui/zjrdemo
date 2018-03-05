package com.kwan.base.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 *
 * Created by Mr.Kwan on 2016-7-19.
 */
public class GlideCircleTransform extends BitmapTransformation {
    public GlideCircleTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);

        float r = size / 2f;



//
//        Paint paint2 = new Paint();
//        paint2.setAntiAlias(true);
//        paint2.setColor(Color.RED);  //设置画笔颜色
//        paint2.setStyle(Paint.Style.STROKE);//设置填充样式
//        paint2.setStrokeWidth(5);//设置画笔宽度
//
//
//        canvas.drawCircle(r, r, r, paint2);

        canvas.drawCircle(r, r, r, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}

