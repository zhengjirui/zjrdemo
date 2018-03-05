package com.kwan.base.util;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Mr.Kwan on 2017-8-25.
 */

public class GlideConfiguration implements GlideModule {
	@Override
	public void applyOptions(Context context, GlideBuilder builder) {
		builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
	}
	@Override
	public void registerComponents(Context context, Glide glide) {

	}
}
