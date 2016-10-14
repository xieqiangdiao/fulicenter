package cn.ucai.fulicenter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uai.cn.fullcenter.R;

public class SplashActivity extends AppCompatActivity {
    static final long SLEEPTIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long  start=System.currentTimeMillis();
                long courTime= System.currentTimeMillis()-start;
                if(SLEEPTIME-courTime>0){
                    try {
                        Thread.sleep(SLEEPTIME-courTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        }).start();
    }
}
