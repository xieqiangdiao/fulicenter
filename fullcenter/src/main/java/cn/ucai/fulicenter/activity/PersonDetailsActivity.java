package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.dao.ShareprefrenceUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.views.DisplayUtils;
import uai.cn.fullcenter.R;

public class PersonDetailsActivity extends AppCompatActivity {


    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_profile)
    TextView tvProfile;
    @Bind(R.id.layout_one)
    LinearLayout layoutOne;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.layout_two)
    LinearLayout layoutTwo;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.layout_three)
    LinearLayout layoutThree;
    @Bind(R.id.btn_logout)
    Button btnLogout;
    PersonDetailsActivity mCotext;
    UserAvatar user;
    @Bind(R.id.iv_useravar)
    ImageView ivUseravar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_person_details);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mCotext = this;
        initView();
        initData();
    }

   // @Override
    protected void initView() {
       // DisplayUtils.initBackWithTitle(mCotext, getResources().getString(R.string.user_name));
    }

  //  @Override
    protected void initData() {
        user = FuLiCenterApplication.getUserAvatar();
        if (user != null) {
            ImageLoader.downloadAvatar(ImageLoader.getAvatarUrl(user), mCotext, ivUseravar);
            tvUsername.setText(user.getMuserName());
            tvNickname.setText(user.getMuserNick());
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    // @Override
    protected void setListener() {
    }


    @OnClick({R.id.tv_profile, R.id.tv_username, R.id.tv_nickname, R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_profile:
                break;
            case R.id.tv_username:
                break;
            case R.id.tv_nickname:
                break;
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void logout() {
        if (user != null) {
            L.e("tuichu");
            ShareprefrenceUtils.getInstance(mCotext).removeUser();
            FuLiCenterApplication.setUserAvatar(null);
            MFGT.finish(this);
        }
    }
}
