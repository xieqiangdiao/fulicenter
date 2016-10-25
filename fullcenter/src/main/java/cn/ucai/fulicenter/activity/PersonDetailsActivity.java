package cn.ucai.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.dao.ShareprefrenceUtils;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.utils.ResultUtils;
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
    OnSetAvatarListener mOnSetAvatarListener;

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
        if (user == null) {
            finish();
        }
        showInfo();
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
                mOnSetAvatarListener = new OnSetAvatarListener(mCotext, R.id.layout_upload_avatar, user.getMuserName(), I.AVATAR_TYPE_USER_PATH);
                break;
            case R.id.tv_username:
                break;
            case R.id.tv_nickname:
                MFGT.gotoUpdateNick(mCotext);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e("onAcivityResult,requestCode=" + requestCode, "resultCode=" + resultCode);
        if (resultCode != RESULT_OK) {
            return;
        }
        mOnSetAvatarListener.setAvatar(requestCode, data, ivUseravar);
        if (requestCode == I.REQUEST_CODE_NICK) {
            CommonUtils.showLongToast(R.string.update_user_nick_success);
        }
        if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            updateAvatar();
        }
    }
    private void updateAvatar() {
        File file = new File(OnSetAvatarListener.getAvatarPath(mCotext, user.getMavatarPath() +
                "/" + user.getMuserName() + I.AVATAR_SUFFIX_JPG));
        L.e("file=" + file.exists());
        L.e("file=" + file.getAbsolutePath());
        final ProgressDialog pd = new ProgressDialog(mCotext);
        pd.setMessage(getResources().getString(R.string.update_user_avatar));
        pd.show();
        NetDao.updateAvatar(mCotext, user.getMuserName(), file, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                L.e("s=" + s);
                Result result = ResultUtils.getResultFromJson(s, UserAvatar.class);
                L.e("result=" + result);
                if (result == null) {
                    CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                } else {
                     UserAvatar u = (UserAvatar) result.getRetData();
                    if (result.isRetMsg()) {
                        ImageLoader.downloadAvatar(ImageLoader.getAvatarUrl(u), mCotext, ivUseravar);
                        CommonUtils.showLongToast(R.string.update_user_avatar_success);
                    } else {
                        CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                    }
                }
                pd.dismiss();
            }
            @Override
            public void onError(String error) {
                L.e("result=" + error);
            }
        });
    }
    private void showInfo() {
        user = FuLiCenterApplication.getUserAvatar();
        if (user != null) {
            ImageLoader.downloadAvatar(ImageLoader.getAvatarUrl(user), mCotext, ivUseravar);
            tvUsername.setText(user.getMuserName());
            tvNickname.setText(user.getMuserNick());
        }
    }
}
