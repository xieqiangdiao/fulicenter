package cn.ucai.fulicenter.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.ResultUtils;
import uai.cn.fullcenter.R;

public class ModifiedNickNameActivity extends BaseActivity {
    private static final String TAG = ModifiedNickNameActivity.class.getSimpleName();

    ModifiedNickNameActivity mContext;
    UserAvatar user = null;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_new_nickname)
    EditText tvNewNickname;
    @Bind(R.id.center_user_info)
    LinearLayout centerUserInfo;
    @Bind(R.id.but_true)
    Button butTrue;
    @Bind(R.id.layout_upload_avatar)
    LinearLayout layoutUploadAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_modified_nick_name);
        ButterKnife.bind(this);
        mContext = this;
        initData();
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initView() {
        //  DisplayUtils.initBackWithTitle(mContext,getResources().getString(R.string.user_name));
    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUserAvatar();
        L.e(FuLiCenterApplication.getUserAvatar() + "999999999999999999999999999999999");
        if (user != null) {
            tvNewNickname.setText(user.getMuserNick());
            tvNewNickname.setSelectAllOnFocus(true);
        } else {
            finish();
        }
    }

    @Override
    protected void setListener() {

    }


    public void updateNick(String nick) {
        L.e("更新昵称");
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.update_user_nick));
        pd.show();
        NetDao.updateNick(mContext, user.getMuserName(), nick, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, UserAvatar.class);
                L.e(TAG, "result=" + result);
                if (result == null) {
                    CommonUtils.showShortToast(R.string.update_fail);
                } else {
                    if (result.isRetMsg()) {
                        UserAvatar u = (UserAvatar) result.getRetData();
                        L.e(TAG, "user=" + u);
                        UserDao dao = new UserDao(mContext);
                        boolean isSuccess = dao.saveUser(user);
                        if (isSuccess) {
                            FuLiCenterApplication.setUserAvatar(user);
                            setResult(RESULT_OK);
                            MFGT.finish(mContext);
                        } else {
                            CommonUtils.showLongToast(R.string.user_database_error);
                        }
                    } else {
                        if (result.getRetCode() == I.MSG_USER_SAME_NICK) {
                            CommonUtils.showShortToast(R.string.update_nick_fail_unnodify);
                        } else if (result.getRetCode() == I.MSG_USER_UPDATE_NICK_FAIL) {
                            CommonUtils.showLongToast(R.string.update_fail);
                        } else {
                            CommonUtils.showLongToast(R.string.update_fail);
                        }
                    }
                }
                pd.dismiss();

            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(error);
                L.e(TAG, "error=" + error);
            }
        });

    }

    @OnClick(R.id.but_true)
    public void onClick() {
        L.e("点击事件");
        L.e("user1: " + FuLiCenterApplication.getUserAvatar());
        L.e("user: " + user);
        if (user != null) {
            String nick = tvNewNickname.getText().toString().trim();
            if (nick.equals(user.getMuserNick())) {
                CommonUtils.showLongToast(R.string.update_nick_fail_unnodify);

            } else if (TextUtils.isEmpty(nick)) {
                CommonUtils.showLongToast(R.string.nick_name_connot_be_empty);
            }else{
                updateNick(nick);
            }
        }
    }



//    @OnClick(R.id.tv_NewNickname)
//    public void onClick() {
//            if (user != null) {
//                String nick = tvOldNickname.getText().toString().trim();
//                if (nick.equals(user.getMuserNick())) {
//                    CommonUtils.showLongToast(R.string.update_nick_fail_unnodify);
//
//                } else if (TextUtils.isEmpty(nick)) {
//                    CommonUtils.showLongToast(R.string.nick_name_connot_be_empty);
//                }
//            }
//    }


}
