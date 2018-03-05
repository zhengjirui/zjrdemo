package com.kwan.base.api;


import android.util.Log;

import com.kwan.base.api.download.DownloadProgressInterceptor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 接口工具类
 * Created by Mr.Kwan on 2016-3-30.
 */
public abstract class BaseAPIUtil {

    //private final String BASE_URL = BaseServerAPI.HTTP_TEST_SERVER;
    //private final String BASE_URL = ServerAPI.HTTP_SERVER;

    protected abstract String getBaseUrl();

    protected abstract String getBaseTokenUrl();

    protected abstract String getBaseUpLoadUrl();

    protected abstract String getToken();

    public static int TIME_OUT = 20000;


    private class Builder {

        OkHttpClient.Builder builder;
        String baseUrl;

		private Builder(String baseUrl) {
            this.baseUrl = baseUrl;
            builder = new OkHttpClient.Builder()
                    .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                    .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
        }

		private Builder addInterceptor(Interceptor tokenInterceptor) {
            builder.addInterceptor(tokenInterceptor);
            return this;
        }

		private Retrofit create() {

            OkHttpClient okHttpClient = builder.build();

            return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

		private Retrofit createByet() {

			OkHttpClient okHttpClient = builder.build();

			return new Retrofit.Builder()
					.baseUrl(baseUrl)
					.addConverterFactory(new ToByteConvertFactory())
					.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
					.client(okHttpClient)
					.build();
		}

    }

	protected Retrofit serverByteAPI() {

		Interceptor interceptor = new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {

				Request request = chain.request();
				try {
					if (request != null && request.body() != null) {
						MediaType mediaType = request.body().contentType();
						if (mediaType != null) {
							Field field = mediaType.getClass().getDeclaredField("mediaType");
							field.setAccessible(true);
							field.set(mediaType, "application/json");
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}


				Log.v("zcb", "request:" + request.toString());
				Log.v("zcb", "request headers:" + request.headers().toString());

				return chain.proceed(request);
			}
		};

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
				.addInterceptor(interceptor)
				.build();

		return new Retrofit.Builder()
				.baseUrl(getBaseUrl())
				.addConverterFactory(new ToByteConvertFactory())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.client(okHttpClient)
				.build();

	}


    protected Retrofit serverAPI() {

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                try {
                    if (request != null && request.body() != null) {
                        MediaType mediaType = request.body().contentType();
                        if (mediaType != null) {
                            Field field = mediaType.getClass().getDeclaredField("mediaType");
                            field.setAccessible(true);
                            field.set(mediaType, "application/json");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


				Log.v("zcb", "request:" + request.toString());
				Log.v("zcb", "request headers:" + request.headers().toString());

                return chain.proceed(request);
            }
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

    }


    protected Retrofit withCache(){

//        File httpCacheDirectory = newFile(BaseApplication.getCacheDir(),"responses");
//        intcacheSize =10*1024*1024;// 10 MiB
//        Cache cache = newCache(httpCacheDirectory, cacheSize);
//
//        Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR  = new Interceptor(){
//
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                CacheControl.Builder cacheBuilder = newCacheControl.Builder();
//                cacheBuilder.maxAge(0, TimeUnit.SECONDS);
//                cacheBuilder.maxStale(365,TimeUnit.DAYS);
//                CacheControl cacheControl = cacheBuilder.build();
//
//                Request request = chain.request();
//                if(!StateUtils.isNetworkAvailable(MyApp.mContext)){
//                    request = request.newBuilder()
//                            .cacheControl(cacheControl)
//                            .build();
//                }
//                Response originalResponse = chain.proceed(request);
//                if(StateUtils.isNetworkAvailable(MyApp.mContext)) {
//                    intmaxAge =0;// read from cache
//                    return originalResponse.newBuilder()
//                            .removeHeader("Pragma")
//                            .header("Cache-Control","public ,max-age="+ maxAge)
//                            .build();
//                } else{
//                    intmaxStale =60*60*24*28;// tolerate 4-weeks stale
//                    return originalResponse.newBuilder()
//                            .removeHeader("Pragma")
//                            .header("Cache-Control","public, only-if-cached, max-stale="+ maxStale)
//                            .build();
//                }
//            }
//        };
//
//
//
//
//
//        OkHttpClient client = newOkHttpClient.Builder()
//                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
//                .cache(cache).build();
//
//        Retrofit retrofit = newRetrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();

        return null;
    }

    protected Retrofit upLoadAPI() {

        Interceptor tokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();

                Request authorised = originalRequest.newBuilder()
                        .header("token", getToken())
                        .build();





                return chain.proceed(authorised);
            }
        };

        return new Builder(getBaseUpLoadUrl()).addInterceptor(tokenInterceptor).create();

//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
//                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
//                .addInterceptor(tokenInterceptor)
//                .build();
//
//        return new Retrofit.Builder()
//                .baseUrl(getBaseUpLoadUrl())
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .client(okHttpClient)
//                .build();
    }

    protected Retrofit downloadAPI(String url, DownloadProgressInterceptor.DownloadProgressListener listener) {

        DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(listener);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    protected Retrofit withToken() {

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                try {
                    if (request != null && request.body() != null) {
                        MediaType mediaType = request.body().contentType();
                        if (mediaType != null) {
                            Field field = mediaType.getClass().getDeclaredField("mediaType");
                            field.setAccessible(true);
                            field.set(mediaType, "application/json");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return chain.proceed(request);
            }
        };

		//Log.e("Token",getToken());

        Interceptor tokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request originalRequest = chain.request();

                Request authorised = originalRequest.newBuilder()
                        .header("token", getToken())
                        .build();
                return chain.proceed(authorised);
            }
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(tokenInterceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(getBaseTokenUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

    }
}