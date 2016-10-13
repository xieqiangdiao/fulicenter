package cn.ucai.fulicenter;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/10/13.
 */
public class FuLiCenterApplication extends Application{
    static Context context;
    public static Context getInstance(){
        return context;
    }
}
