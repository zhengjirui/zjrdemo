package com.kwan.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.kwan.base.BaseApplication;

import com.zhy.autolayout.AutoFrameLayout;
import com.kwan.base.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by Mr.Kwan on 2016-9-30.
 */

public class ImageUtil {

    private Context mContext;

    public ImageUtil() {
        mContext = BaseApplication.getInstance();
    }

	private RequestListener<String, GlideDrawable> requestListener = new RequestListener<String, GlideDrawable>() {

		@Override
		public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
			// todo log exception
			// important to return false so the error placeholder can be placed
			if (e != null) {
				e.printStackTrace();
				Log.d("ImageUtil onException", "Exception: " + e.getMessage() + " model: " + model);
			}
			return false;
		}

		@Override
		public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
			return false;
		}
	};

	public void loadFromRes(int resid, final ImageView imageView) {
		Glide.with(mContext)
				.load(resid)
				.into(new SimpleTarget<GlideDrawable>() {
					@Override
					public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
						imageView.setImageDrawable(resource);
					}
				});
	}
	public void loadFromUrl(String url, final ImageView imageView) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			if (mContext instanceof Activity && ((Activity) mContext).isDestroyed())
				return;
		}
		Glide.with(mContext)
				.load(url)
				.listener(requestListener)
				.centerCrop()
				.placeholder(R.mipmap.item_default)
				.into(new ViewTarget<ImageView, GlideDrawable>(imageView) {
					@Override
					public void onLoadStarted(Drawable placeholder) {
						imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						imageView.setImageDrawable(placeholder);
					}

					@Override
					public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
						imageView.setImageDrawable(null);
						imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
						imageView.setImageDrawable(resource.getCurrent());
					}
				});
	}
	public void loadFromUrlCenterInside(String url, final ImageView imageView) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			if (mContext instanceof Activity && ((Activity) mContext).isDestroyed())
				return;
		}
		Glide.with(mContext)
				.load(url)
				.listener(requestListener)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.fitCenter()
				.placeholder(R.mipmap.item_default)
				.into(new ViewTarget<ImageView, GlideDrawable>(imageView) {
					@Override
					public void onLoadStarted(Drawable placeholder) {
						imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						imageView.setImageDrawable(placeholder);
					}

					@Override
					public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
						imageView.setImageDrawable(null);
						imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						imageView.setImageDrawable(resource.getCurrent());
					}
				});
	}

	public void loadFromUrlCenterInside(String url, final ImageView imageView, final int width) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			if (mContext instanceof Activity && ((Activity) mContext).isDestroyed())
				return;
		}
		Glide.with(mContext)
				.load(url)
				.asBitmap()
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.fitCenter()
				.placeholder(R.mipmap.item_default)
				.into(new ViewTarget<ImageView, Bitmap>(imageView) {
					@Override
					public void onLoadStarted(Drawable placeholder) {
						imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						imageView.setImageDrawable(placeholder);
					}

					@Override
					public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

						float scale = resource.getHeight() / (resource.getWidth() * 1.0f);
						int viewHeight = (int) (width * scale);
						imageView.setLayoutParams(new AutoFrameLayout.LayoutParams(width, viewHeight));
						imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
						imageView.setImageBitmap(resource);
					}
				});
	}

	public void loadGifFromUrl(String url, final ImageView imageView,final int width) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			if (mContext instanceof Activity && ((Activity) mContext).isDestroyed())
				return;
		}
		Glide.with(mContext)
				.load(url)
				.asGif()
				.toBytes()
				.error(R.mipmap.item_default)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.into(new SimpleTarget<byte[]>() {

					@Override
					public void onLoadStarted(Drawable placeholder) {
						super.onLoadStarted(placeholder);
						imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						imageView.setImageDrawable(placeholder);
					}

					@Override
					public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {

						try {
							GifDrawable gifFromBytes = new GifDrawable(resource);
							float scale = gifFromBytes.getMinimumHeight() / (gifFromBytes.getMinimumWidth() * 1.0f);
							int viewHeight = (int) (width * scale);
							imageView.setLayoutParams(new AutoFrameLayout.LayoutParams(width, viewHeight));
							imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
							imageView.setImageDrawable(gifFromBytes);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
	}

	public void loadFromUrlCenterInsidePading(String url, final ImageView imageView) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			if (mContext instanceof Activity && ((Activity) mContext).isDestroyed())
				return;
		}
		Glide.with(mContext)
				.load(url)
				.listener(requestListener)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.fitCenter()
				.placeholder(R.mipmap.item_default)
				.into(new ViewTarget<ImageView, GlideDrawable>(imageView) {
					@Override
					public void onLoadStarted(Drawable placeholder) {
						imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						imageView.setCropToPadding(true);
						imageView.setPadding(100, 100, 100, 100);
						imageView.setImageDrawable(placeholder);
					}

					@Override
					public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
						imageView.setImageDrawable(null);
						imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						imageView.setCropToPadding(true);
						imageView.setPadding(0, 0, 0, 0);
						imageView.setImageDrawable(resource.getCurrent());
					}
				});
	}

	public void loadFromUrlNoCrop(String url, final ImageView imageView) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			if (mContext instanceof Activity && ((Activity) mContext).isDestroyed())
				return;
		}
		Glide.with(mContext)
				.load(url)
				.listener(requestListener)
				.placeholder(R.mipmap.item_default)
				.into(new ViewTarget<ImageView, GlideDrawable>(imageView) {
					@Override
					public void onLoadStarted(Drawable placeholder) {
						imageView.setImageDrawable(placeholder);
					}

					@Override
					public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
						imageView.setImageDrawable(null);
						imageView.setImageDrawable(resource.getCurrent());
					}
				});
	}

	public void loadGifFromUrl(String url, final ImageView imageView) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			if (mContext instanceof Activity && ((Activity) mContext).isDestroyed())
				return;
		}
		Glide.with(mContext)
				.load(url)
				.asGif()
				.toBytes()
				.error(R.mipmap.item_default)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.into(new SimpleTarget<byte[]>() {
					@Override
					public void onLoadStarted(Drawable placeholder) {
						imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
						imageView.setImageDrawable(placeholder);
					}
					@Override
					public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
						try {
							GifDrawable gifFromBytes = new GifDrawable(resource);
							imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
							imageView.setImageDrawable(gifFromBytes);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
	}

	public void loadFromFile(String path, ImageView imageView) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			if (mContext instanceof Activity && ((Activity) mContext).isDestroyed())
				return;
		}
		Glide.with(mContext)
				.load(new File(path))
				.asBitmap()
				.error(R.mipmap.item_default)
				.into(imageView);
	}

	public void loadFromFileWithCircle(String path, ImageView imageView) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			if (mContext instanceof Activity && ((Activity) mContext).isDestroyed())
				return;
		}
		Glide.with(mContext)
				.load(new File(path))
				.asBitmap()
				.error(R.mipmap.item_default)
				.transform(new GlideCircleTransform(mContext))
				.into(imageView);
	}

//	public void loadFromResWithCircle(int resid, ImageView imageView) {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//			if (mContext instanceof Activity && ((Activity) mContext).isDestroyed())
//				return;
//		}
//		Glide
//				.with(mContext)
//				.load(resid)
//				.transform(new GlideCircleTransform(mContext))
//				.into(imageView);
//	}

	public void loadFromUrlWithCircle(String url, final ImageView imageView) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			if (mContext instanceof Activity && ((Activity) mContext).isDestroyed())
				return;
		}

		Glide.with(mContext)
				.load(url)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.placeholder(R.mipmap.title_user_icon)
				.transform(new GlideCircleTransform(mContext))
				.into(imageView);
	}

	public void loadFromResWithCircle(int res, final ImageView imageView) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			if (mContext instanceof Activity && ((Activity) mContext).isDestroyed())
				return;
		}
		Glide.with(mContext)
				.load(res)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.placeholder(R.mipmap.title_user_icon)
				.transform(new GlideCircleTransform(mContext))
				.into(imageView);
	}

	public void loadFromUrlWithRound(String url, final ImageView imageView) {

		Glide.with(mContext)
				.load(url)
				.transform(new GlideRoundTransform(mContext))
				.into(new SimpleTarget<GlideDrawable>() {
					@Override
					public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

						imageView.setBackground(resource);
					}
				});
	}

	public static String compressImage(String filePath, String targetPath, int quality) {
		//获取一定尺寸的图片
		Bitmap bm = getSmallBitmap(filePath);
		//获取相片拍摄角度
		int degree = readPictureDegree(filePath);
		//旋转照片角度，防止头像横着显示
		if (degree != 0)
			bm = rotateBitmap(bm, degree);

		File outputFile = new File(targetPath);
		try {
			if (!outputFile.exists()) {
				outputFile.getParentFile().mkdirs();
				//outputFile.createNewFile();
			} else {
				outputFile.delete();
			}
			FileOutputStream out = new FileOutputStream(outputFile);

			Log.e("kwan",""+(bm==null));

			bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFile.getPath();
	}

	/**
	 * 根据路径获得图片信息并按比例压缩，返回bitmap
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
		BitmapFactory.decodeFile(filePath, options);
		// 计算缩放比
		options.inSampleSize = calculateInSampleSize(options, 1080, 1920);
		// 完整解析图片返回bitmap
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}


	/**
	 * 获取照片角度
	 *
	 * @param path
	 * @return
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 旋转照片
	 *
	 * @param bitmap
	 * @param degress
	 * @return
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
		if (bitmap != null) {
			Matrix m = new Matrix();
			m.postRotate(degress);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), m, true);
			return bitmap;
		}
		return bitmap;
	}

	//计算图片的缩放值
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	/**
	 * 按照给定尺寸的最小比例(意思是按等比缩放，
	 * 但是宽高绝对不超过指定宽高)和文件路径，生成Bitmap，压缩图片尺寸
	 *
	 * @param targetWidth
	 * @param targetHeight
	 * @return
	 */
	public static Bitmap compressBitmapFromResPathBySize(Resources roc, int res, int targetWidth,
												  int targetHeight) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
		BitmapFactory.decodeResource(roc, res, opts);

		// 得到图片的宽度、高度；
		float imgWidth = opts.outWidth;
		float imgHeight = opts.outHeight;

		// 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
		int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
		int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
		opts.inSampleSize = 1;

		if (widthRatio > 1 || heightRatio > 1) {
			if (widthRatio > heightRatio) {
				opts.inSampleSize = widthRatio;
			} else {
				opts.inSampleSize = heightRatio;
			}
		}
		// 设置好缩放比例后，加载图片进内容；
		opts.inJustDecodeBounds = false;

		return BitmapFactory.decodeResource(roc, res, opts);
	}


	public static Bitmap compressBitmapFromPathBySize(String path, int targetWidth,
														 int targetHeight) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
		BitmapFactory.decodeFile(path, opts);

		// 得到图片的宽度、高度；
		float imgWidth = opts.outWidth;
		float imgHeight = opts.outHeight;

		// 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
		int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
		int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
		opts.inSampleSize = 1;

		if (widthRatio > 1 || heightRatio > 1) {
			if (widthRatio > heightRatio) {
				opts.inSampleSize = widthRatio;
			} else {
				opts.inSampleSize = heightRatio;
			}
		}
		// 设置好缩放比例后，加载图片进内容；
		opts.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(path, opts);
	}



	public static Bitmap scaleBitmap(Bitmap bitmap, int newHeight, int newWidth) {

		int rawHeigh = bitmap.getHeight();
		int rawWidth = bitmap.getHeight();

		//设置显示的大小
//        int newHeight = 60;
//        int newWidth = 60;
		// 计算缩放因子
		float heightScale = ((float) newHeight) / rawHeigh;
		float widthScale = ((float) newWidth) / rawWidth;
		// 新建立矩阵
		Matrix matrix = new Matrix();
		matrix.postScale(heightScale, widthScale);
		// 设置图片的旋转角度
		// matrix.postRotate(-30);
		// 设置图片的倾斜
		// matrix.postSkew(0.1f, 0.1f);
		// 将图片大小压缩
		// 压缩后图片的宽和高以及kB大小均会变化

		return Bitmap.createBitmap(bitmap, 0, 0,
				rawWidth, rawHeigh, matrix, true);
	}
}
