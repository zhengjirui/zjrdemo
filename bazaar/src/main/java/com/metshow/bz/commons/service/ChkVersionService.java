package com.metshow.bz.commons.service;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kwan.base.AppManager;
import com.kwan.base.api.ServerSubscriber;
import com.kwan.base.api.ServerSubscriberListener;
import com.kwan.base.api.download.Download;
import com.kwan.base.api.download.DownloadProgressInterceptor;
import com.kwan.base.bean.ServerMsg;
import com.kwan.base.util.PathUtil;
import com.kwan.base.util.SysUtil;
import com.kwan.base.util.ViewUtil;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.VersionMsg;
import com.metshow.bz.net.api.BazzarAPIUtil;
import com.metshow.bz.net.api.ServerAPI;
import com.metshow.bz.util.DialogFactory;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Timer;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 检查APP版本更新的后台服务
 *
 * @author zhaoyun
 */
public class ChkVersionService extends Service implements ServerSubscriberListener, DownloadProgressInterceptor.DownloadProgressListener {

    private static final String TAG = ChkVersionService.class.getSimpleName();

    /**
     * 接口实例
     **/
    private VersionUpdateCallBack mChkVersionCallBack;

    public void setVersionUpdateCallBack(VersionUpdateCallBack callBack) {
        this.mChkVersionCallBack = callBack;
    }

    private BazzarAPIUtil mApiUtil;

    private Timer mChkVersionUpdateTimer;
    private boolean isVersionChking = false;
    private boolean isVersionDownloading = false;

    private static final String LOCAL_APK_PATH = PathUtil.getApplicationDownloadPath() + "Bazaar.apk";
    private static String apkUrl;
    private Dialog mAgreeDialog;

    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private RemoteViews mContentView = null;
    private static final int download_notify_id = 10101;//通知栏唯一识别id

    private boolean isFouce;

    private String msg = null;

    @Override
    public void onCreate() {

        if (notificationManager == null) {

            notificationManager = (NotificationManager) App.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.icon)
                    .setContentTitle("版本更新")
                    .setContentText("Downloading File")
                    .setAutoCancel(true);

            mContentView = new RemoteViews(SysUtil.getAppPackageName(),
                    R.layout.notify_download);

            mContentView.setViewVisibility(R.id.notify_pd, View.GONE);
            mContentView.setTextViewText(R.id.notify_txt, "开始下载");
            mContentView.setTextColor(R.id.notify_txt, Color.BLACK);
            notificationBuilder.setContent(mContentView);
        }

        mApiUtil = new BazzarAPIUtil();

        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ChkVersionServiceBindler();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        clearDialog();
        return super.onUnbind(intent);
    }

    /**
     * 开始检查更新
     */
    public void startCheck() {
		Log.e("lee","startCheck");
        ServerSubscriber<VersionMsg> subscriber = new ServerSubscriber<>(this);
        subscriber.vocational_id = ServerAPI.APPVERSION_NEW_VOCATIONAL_ID;
        Observable<String> observable = mApiUtil.checkVersion();

        java.lang.reflect.Type type = new TypeToken<ServerMsg<VersionMsg>>() {
        }.getType();

        observable
                .subscribeOn(Schedulers.io())
                .flatMap(new TransFormJson<ServerMsg<VersionMsg>>().getTransFormer(type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(subscriber);

    }

    @Override
    public void onServerCompleted(int vocational_id, HashMap<String, Object> exdata) {

    }

    @Override
    public void onServerError(int vocational_id, HashMap<String, Object> exdata, Throwable e) {

    }

    @Override
    public void onServerNext(int vocational_id, HashMap<String, Object> exdata, ServerMsg s) {

        if (s.isSuc()) {

			Log.e("lee","onServerNext");

            VersionMsg versionMsg = (VersionMsg) s.getResult();
            apkUrl = versionMsg.getUrl();
            msg = versionMsg.getMessage();
            isFouce = versionMsg.getForce() == 1;
            boolean hasNew = isNewVersion(getVersionNameNum(versionMsg.getVersion()), getVersionCode());

//            apkUrl = "http://bznewitem2.metshow.com:8080/apk/20161118092128_1150.apk";
//            hasNew = true;

            if (hasNew) {
                showDialog();
            }
        }

    }


    void showDialog() {

        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view = inflater.inflate(R.layout.system_dialog_layout, null);

//        ImageView icon_img = (ImageView) view
//                .findViewById(R.id.system_dialog_title_img);
        TextView title_txt = (TextView) view
                .findViewById(R.id.system_dialog_title_txt);
        title_txt.setText("版本更新");
        TextView msg_txt = (TextView) view.findViewById(R.id.system_dialog_msg);

        StringBuilder txt = new StringBuilder("有新版本了，你要更新么？");

        if (msg != null && !msg.trim().isEmpty())
            txt.append("\n" + msg);

        if (isFouce)
            txt.append("\n当前为强制升级，取消将退出");
        msg_txt.setText(txt.toString());


        TextView cancel_btn = (TextView) view
                .findViewById(R.id.system_dialog_cancel_btn);
        cancel_btn.setText("以后再说");
        TextView sure_btn = (TextView) view
                .findViewById(R.id.system_dialog_sure_btn);
        sure_btn.setText("确定");
        ViewUtil.measureView(title_txt);
//        int width = title_txt.getMeasuredHeight();
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
//                width);
//        icon_img.setLayoutParams(params);

        cancel_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                clearDialog();
                ///不更新，销毁该后台服务
                closeChkVersionService();
                if (isFouce)
                    AppManager.getAppManager().AppExit();
            }
        });
        sure_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                clearDialog();
                // 开始下载，最开始用temp文件保存
                downloadApkFile(apkUrl, LOCAL_APK_PATH);
            }
        });
        mAgreeDialog = DialogFactory.showCustomDialog(mActivity, view);
    }


    public class TransFormJson<T> {

        public Func1<String, Observable<T>> getTransFormer(final java.lang.reflect.Type type) {

            return new Func1<String, Observable<T>>() {
                @Override
                public Observable<T> call(String s) {
					Log.e("CheckVersion:",s);
                    Gson gson = new Gson();
                    final T msg = gson.fromJson(s, type);
                    Logger.t("ServerBack::").d("fromJson ok");
                    return Observable.create(new Observable.OnSubscribe<T>() {
                        @Override
                        public void call(Subscriber<? super T> subscriber) {
                            subscriber.onNext(msg);
                            subscriber.onCompleted();
                        }
                    });
                }
            };

        }
    }


    /**
     * ChkVersionService调用bind后外部调用接口
     */

    Activity mActivity;

    public class ChkVersionServiceBindler extends Binder {

        public ChkVersionService getChkVersionServiceInstance(Activity activity) {
            mActivity = activity;
            return ChkVersionService.this;
        }

    }


    /**
     * 将VersionName转换为数字
     */
    private int getVersionNameNum(String versionCode) {
        int versionNum;
        try {
           //String str = versionName.replace(".", "");
            versionNum = Integer.parseInt(versionCode);
        } catch (Exception e) {
            versionNum = 0;
        }
        Log.d("kwan","versionNum::"+versionNum);
        return versionNum;
    }

    /**
     * 判断是否是新版本
     */
    private boolean isNewVersion(int newVer,int oldVer) {
        return newVer > oldVer;
    }

    /**
     * 下载APK文件
     *
     * @param apkUrl         提供的下载路径
     * @param targetFilePath 制定的存储路径
     */
    private void downloadApkFile(String apkUrl, String targetFilePath) {
        boolean hasDir;

        final File file = new File(targetFilePath);
        hasDir = file.exists() || file.mkdirs();
        ;
        if (hasDir) {

            notificationManager.notify(download_notify_id, notificationBuilder.build());

            mApiUtil.downLoadFile(getHostName(apkUrl), apkUrl, this)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .map(new Func1<ResponseBody, InputStream>() {
                        @Override
                        public InputStream call(ResponseBody responseBody) {
                            return responseBody.byteStream();
                        }
                    })
                    .observeOn(Schedulers.computation())
                    .doOnNext(new Action1<InputStream>() {
                        @Override
                        public void call(InputStream inputStream) {
                            try {
                                writeFile(inputStream, file);
                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<InputStream>() {
                        @Override
                        public void onCompleted() {
                            Log.d("ChkVersionService", "down onCompleted");
                            downloadCompleted();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("ChkVersionService", "down onError::" + e.getMessage());
                            downloadCompleted();
                        }

                        @Override
                        public void onNext(InputStream inputStream) {

                        }
                    });

        } else {
            Logger.d("创建存储文件夹失败！");
            closeChkVersionService();
        }
    }

    @Override
    public void update(long bytesRead, long contentLength, boolean done) {
        Download download = new Download();
        download.setTotalFileSize(contentLength);
        download.setCurrentFileSize(bytesRead);
        int progress = (int) ((bytesRead * 100) / contentLength);
        download.setProgress(progress);

        sendNotification(download);
    }

    private void downloadCompleted() {

//        Download download = new Download();
//        download.setProgress(100);
//        // sendIntent(download);
//
//        mContentView.setViewVisibility(R.id.notify_pd, View.GONE);
//        mContentView.setProgressBar(R.id.notify_pd, 100, 100, false);
//        mContentView.setTextViewText(R.id.notify_txt, "下载完成，点击安装");
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, installApk(LOCAL_APK_PATH, false), 0);
//        mContentView.setOnClickPendingIntent(R.id.content, pendingIntent);
//
//        notificationBuilder.setContentIntent(pendingIntent);
//        notificationManager.notify(download_notify_id, notificationBuilder.build());

        notificationManager.cancel(download_notify_id);

        installApk(LOCAL_APK_PATH, true);

    }

    private void sendNotification(Download download) {

        //  sendIntent(download);
//        notificationBuilder.setProgress(100, download.getProgress(), false);
//        notificationBuilder.setContentText(
//                StringUtils.getDataSize(download.getCurrentFileSize()) + "/" +
//                        StringUtils.getDataSize(download.getTotalFileSize()));


        mContentView.setViewVisibility(R.id.notify_pd, View.VISIBLE);
        mContentView.setProgressBar(R.id.notify_pd, 100, download.getProgress(), false);
        mContentView.setTextViewText(R.id.notify_txt, "已下载"
                + download.getProgress() + "%");
        notificationBuilder.setContent(mContentView);

        notificationManager.notify(download_notify_id, notificationBuilder.build());
    }

//    private void sendIntent(Download download) {
//
//        Intent intent = new Intent(MainActivity.MESSAGE_PROGRESS);
//        intent.putExtra("download", download);
//        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
//    }


    private String getHostName(String urlString) {


        String head = "";
        int index = urlString.indexOf("://");
        if (index != -1) {
            head = urlString.substring(0, index + 3);
            urlString = urlString.substring(index + 3);
        }
        index = urlString.indexOf("/");
        if (index != -1) {
            urlString = urlString.substring(0, index + 1);
        }
        return head + urlString;

    }

    public void writeFile(InputStream in, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (file != null && file.exists())
            file.delete();

        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024 * 128];
        int len;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
        out.close();
        in.close();

    }


    /**
     * 安装APK
     *
     * @param updateFilePath APK文件本地路径
     */
    public Intent installApk(String updateFilePath, boolean auto) {
        try {
            File updateFile = new File(updateFilePath);
            Intent intent = new Intent();
            // 执行动作
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(updateFile),
                    "application/vnd.android.package-archive");
            if (auto)
                startActivity(intent);
            return intent;
            //	BaseActivity.exit();
        } catch (Exception e) {
            return null;
            //DialogFactory.displayLongToast("Apk文件安装失败！");
        }
    }

    /**
     * 通过接口使bind了Service的Activity调用unBind
     */
    public void closeChkVersionService() {

        isVersionChking = false;
        isVersionDownloading = false;
        if (mChkVersionCallBack != null) {
            mChkVersionCallBack.chkUpdateEnded();
        }
    }

    /**
     * 销毁所有Dialog
     */
    private void clearDialog() {
        DialogFactory.dismissDialog(mAgreeDialog);
    }


    /**
     * 检查版本过程的回调接口
     */
    public interface VersionUpdateCallBack {
        //检查版本开始
        void chkUpdateStarted();

        //检查版本结束
        void chkUpdateEnded();
    }

    /**
     * 获取当前APP的版本号（写在AndroidManifest的android:versionName中）
     *
     * @return versionName 如果发生异常则返回R.string.version_unknown所指向的字符串值
     */
    public static String getVersionName() {
        String versionName;
        try {
            PackageManager mPackManager = App.getInstance().getPackageManager();
            PackageInfo mPackInfo = mPackManager.getPackageInfo(App.getInstance().getPackageName(), 0);
            versionName = mPackInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionName = "1.0.0";
            return versionName;
        }
    }

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);


	}

	/**
     * 获取当前APP的版本Code（写在AndroidManifest的android:versionCode中）
     *
     * @return versionCode 如果发生异常则返回R.string.versioncode_unknown所指向的int值
     */
    public static int getVersionCode() {
        int versionCode;
        try {
            PackageManager mPackManager = App.getInstance().getPackageManager();
            PackageInfo mPackInfo = mPackManager.getPackageInfo(App.getInstance().getPackageName(), 0);
            versionCode = mPackInfo.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionCode = 1;
            return versionCode;
        }
    }

//    public class NotificationBroadcastReceiver extends BroadcastReceiver {
//
//        public static final String TYPE = "type"; //这个type是为了Notification更新信息的，这个不明白的朋友可以去搜搜，很多
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            int type = intent.getIntExtra(TYPE, -1);
//
//            if (type != -1) {
//                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.cancel(type);
//            }
//
//            if (action.equals("notification_clicked")) {
//                //处理点击事件
//            }
//
//            if (action.equals("notification_cancelled")) {
//                //处理滑动清除和点击删除事件
//            }
//        }
//    }

}

