package cn.ucai.fulicenter;

import android.app.Application;


/**
 * Created by Administrator on 2016/10/13.
 */
public class FuLiCenterApplication extends Application {
    public static FuLiCenterApplication applicationContext;
    private static FuLiCenterApplication instance;
 public FuLiCenterApplication(){
     instance=this;
  }
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext=this;
        instance=this;
    }
    public static FuLiCenterApplication getInstance() {
        if (instance == null) {
            instance=new FuLiCenterApplication();
        }
        return instance;
    }
}
