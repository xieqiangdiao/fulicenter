package cn.ucai.fulicenter;

import android.app.Application;

import cn.ucai.fulicenter.bean.UserAvatar;


/**
 * Created by Administrator on 2016/10/13.
 */
public class FuLiCenterApplication extends Application {
    public static FuLiCenterApplication applicationContext;
    private static FuLiCenterApplication instance;
    private static String userName;
    private static UserAvatar userAvatar;

    public static UserAvatar getUserAvatar() {
        return userAvatar;
    }

    public static void setUserAvatar(UserAvatar userAvatar) {
        FuLiCenterApplication.userAvatar = userAvatar;
    }

    public FuLiCenterApplication(){
     instance=this;
  }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        FuLiCenterApplication.userName = userName;
    }

    public static FuLiCenterApplication getInstance() {
        if (instance == null) {
            instance=new FuLiCenterApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext=this;
        instance=this;
    }
}
