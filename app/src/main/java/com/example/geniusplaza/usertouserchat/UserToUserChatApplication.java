package com.example.geniusplaza.usertouserchat;

/**
 * Created by geniusplaza on 7/19/17.
 */

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class UserToUserChatApplication extends Application {

    public static RefWatcher getRefWatcher(Context context) {
        UserToUserChatApplication application = (UserToUserChatApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
    }
}