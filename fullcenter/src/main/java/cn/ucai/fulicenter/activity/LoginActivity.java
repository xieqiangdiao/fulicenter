package cn.ucai.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.dao.ShareprefrenceUtils;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.ResultUtils;
import uai.cn.fullcenter.R;

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
//    @Bind(R.id.ivtitle)
//    ImageView ivtitle;
//    @Bind(R.id.tvlogin)
//    TextView tvlogin;
//    @Bind(R.id.rv)
//    RelativeLayout rv;
//    @Bind(R.id.et_name)
//    EditText etName;
//    @Bind(R.id.etpassword)
//    EditText etpassword;
//    @Bind(R.id.layout_password)
//    LinearLayout layoutPassword;
//    @Bind(R.id.btn_login)
//    Button btnLogin;
//    @Bind(R.id.but_register)
//    Button butRegister;

    String name;
    String password;
    LoginActivity mContext;
    @Bind(R.id.ivtitle)
    ImageView ivtitle;
    @Bind(R.id.tvlogin)
    TextView tvlogin;
    @Bind(R.id.rv)
    RelativeLayout rv;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.etpassword)
    EditText etpassword;
    @Bind(R.id.layout_password)
    LinearLayout layoutPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.but_register)
    Button butRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setListener() {
    }

    @OnClick({R.id.et_name, R.id.etpassword, R.id.btn_login, R.id.but_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_name:
                break;
            case R.id.etpassword:
                break;
            case R.id.btn_login:
                break;
            case R.id.but_register:
                MFGT.gotoRegister(this);
                break;
        }

    }

    private void checkedInput() {
        if (TextUtils.isEmpty(this.name)) {
            CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
            etName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(this.password)) {
            CommonUtils.showShortToast(R.string.password_connot_be_empty);
            etpassword.requestFocus();
            return;
        }
    }

    private void login() {
        NetDao.login(mContext, name, password, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getListResultFromJson(s, UserAvatar.class);
                L.e(TAG, "result=" + result);
                if (result == null) {
                    CommonUtils.showShortToast(R.string.login_fail);
                } else {
                    if (result.isRetMsg()) {
                        Gson gson = new Gson();
                        UserAvatar user = gson.fromJson(result.getRetData().toString(), UserAvatar.class);
                        L.e(TAG, "user=" + user);
//                        UserDao dao = new UserDao(mContext);
//                        boolean isSuccess = dao.saveUser(user);
//                        if (isSuccess) {
//                            ShareprefrenceUtils.getInstance(mContext).saveUser(user.getMuserName());
//                            FuLiCenterApplication.setUserAvatar(user);
//                            MFGT.loginGotoActiviy(mContext);
//                            MFGT.finish(mContext);
//                        } else {
//                            CommonUtils.showLongToast("用户数据异常");
//                        }
//                        MFGT.finish(mContext);
                    } else {
                        if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                            CommonUtils.showShortToast(R.string.login_fail);
                        } else if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                            CommonUtils.showLongToast(R.string.login_fail);
                        } else {
                            CommonUtils.showLongToast(R.string.login_fail);
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(error);
                L.e(TAG, "error=" + error);
            }
        });
        {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && requestCode == I.REQUEST_COOE_REQUEST) {
            String name = data.getStringExtra(I.User.USER_NAME);
            etName.setText(name);
        }
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        name = etName.getText().toString().trim();
        password = etpassword.getText().toString().trim();
        L.e(name + password);
        checkedInput();
        NetDao.login(mContext, name, password, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result) {
                L.e("123" + result);
                if (result != null) {
                    Result re = ResultUtils.getResultFromJson(result, UserAvatar.class);
                    if (re.getRetCode() == 0 && re.isRetMsg()) {
                        UserAvatar user = (UserAvatar) re.getRetData();
                        FuLiCenterApplication.getInstance().setUserAvatar(user);
                        //ShareprefrenceUtils.
                        UserDao dao = new UserDao(mContext);
                        boolean isSuccess = dao.saveUser(user);
                        if (isSuccess) {
                            ShareprefrenceUtils.getInstance(mContext).saveUser(user.getMuserName());
                            FuLiCenterApplication.setUserAvatar(user);
                            MFGT.loginGotoActiviy(mContext);
                            FuLiCenterApplication.getInstance().setUserName(user.getMuserName());
                            Log.e("main", "dengluchenggong" + user);
                            MFGT.finish(mContext);
                        } else {
                            CommonUtils.showLongToast("用户数据异常");
                        }
                        MFGT.finish(mContext);
                    }
                }
            }

            @Override
            public void onError(String error) {
            }
        });

    }
}
