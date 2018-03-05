package com.metshow.bz.commons.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;

/**
 * Created by Mr.Kwan on 2016-8-29.
 */
public class NotificationService extends Service {


    private Timer mTimer;

    @Override
    public void onCreate() {
        super.onCreate();

        if (mTimer == null) {
            mTimer = new Timer();
        } else {
            mTimer.purge();
            mTimer.cancel();
            mTimer = null;
            mTimer = new Timer();
        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {




        return new NotificationServiceBinder();
    }





    Context mContext;

    public class NotificationServiceBinder extends Binder{

        public NotificationService getChkVersionServiceInstance(Context mContext) {
            mContext = mContext;
            return NotificationService.this;
        }

    }



}
