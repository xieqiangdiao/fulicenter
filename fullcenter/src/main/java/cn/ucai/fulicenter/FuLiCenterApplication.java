package cn.ucai.fulicenter;

import android.app.Application;
import android.content.Context;
import android.os.Looper;

/**
 * Created by Administrator on 2016/10/13.
 */
public class FuLiCenterApplication extends Application {
    private static FuLiCenterApplication applicationContext;

    @Override
    public Looper getMainLooper() {
        return super.getMainLooper();
    }

    public FuLiCenterApplication() {
        applicationContext = this;
    }

    public static FuLiCenterApplication getInstance() {
        if (applicationContext == null) {
            applicationContext=new FuLiCenterApplication();
        }
        return applicationContext;
    }


}
