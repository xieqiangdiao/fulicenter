package cn.ucai.fulicenter.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.UserAvatar;

import cn.ucai.fulicenter.dao.ShareprefrenceUtils;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import uai.cn.fullcenter.R;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    static final long sleepTime = 2000;
    SplashActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext=this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
               UserAvatar user= FuLiCenterApplication.getUserAvatar();
                String username= ShareprefrenceUtils.getInstance(mContext).getUser();
                if(user==null) {
                    UserDao dao = new UserDao(mContext);
                     user = dao.getUser("username");
                    L.e(TAG,"user="+user);
                    if(user!=null){
                        FuLiCenterApplication.setUserAvatar(user);
                    }
                }
                MFGT.gotoMainActivity(SplashActivity.this);
                finish();
            }
        }, sleepTime);

    }
}

