package cn.ucai.fulicenter.activity;

import android.app.ProgressDialog;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.views.DisplayUtils;
import uai.cn.fullcenter.R;

public class RegisterActivity extends BaseActivity {


    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.rv)
    RelativeLayout rv;
    @Bind(R.id.et_loginName)
    EditText etLoginName;
    @Bind(R.id.layout_Name)
    LinearLayout layoutName;
    @Bind(R.id.etNiChen)
    EditText etNiChen;
    @Bind(R.id.layout_NiChen)
    LinearLayout layoutNiChen;
    @Bind(R.id.etPasWord)
    EditText etPasWord;
    @Bind(R.id.layout_PassWord)
    LinearLayout layoutPassWord;
    @Bind(R.id.etCpassWord)
    EditText etCpassWord;
    @Bind(R.id.layout_CpassWord)
    LinearLayout layoutCpassWord;
    @Bind(R.id.btn_register)
    Button btnRegister;

    RegisterActivity mContext;
    @Bind(R.id.iv_title)
    ImageView ivTitle;
//    String mName = etLoginName.getText().toString().trim();
//    String nick = etNiChen.getText().toString().trim();
//    String password = etPasWord.getText().toString().trim();
//    String cpassword = etCpassWord.getText().toString().trim();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext = this;
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(this, "账户注册");
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.et_loginName, R.id.etNiChen, R.id.etPasWord, R.id.etCpassWord, R.id.btn_register,R.id.iv_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title:
                MFGT.finish(this);
                break;
            case R.id.et_loginName:
                break;
            case R.id.etNiChen:
                break;
            case R.id.etPasWord:
                break;
            case R.id.etCpassWord:
                break;
            case R.id.btn_register:
                if (TextUtils.isEmpty(etLoginName.getText().toString().trim())) {
                    CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
                    return;
                } else if (!etLoginName.getText().toString().trim().matches("[a-zA-Z]\\w{5,15}")) {
                    CommonUtils.showShortToast(R.string.illegal_user_name);
                    etLoginName.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(etNiChen.getText().toString().trim())) {
                    CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
                    etLoginName.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(etPasWord.getText().toString().trim())) {
                    CommonUtils.showShortToast(R.string.password_connot_be_empty);
                    etLoginName.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(etCpassWord.getText().toString())) {
                    CommonUtils.showShortToast(R.string.confirm_password_connot_be_empty);
                    etLoginName.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(etCpassWord.getText().toString())) {
                    CommonUtils.showShortToast(R.string.two_input_password);
                    etLoginName.requestFocus();
                    return;
                }
                register();
        }
    }
    public void register() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        String mName = etLoginName.getText().toString().trim();
        String nick = etNiChen.getText().toString().trim();
        String password = etPasWord.getText().toString().trim();
        pd.setMessage(getResources().getString(R.string.register));
        pd.show();
        NetDao.register(mContext, mName, nick, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
                if (result == null) {
                    CommonUtils.showShortToast(R.string.register_fail);
                } else {
                    if (result.isRetMsg()) {
                        setResult(RESULT_OK, new Intent().putExtra(I.User.USER_NAME, etLoginName.getText().toString().trim()));
                        CommonUtils.showShortToast(R.string.register_success);
                        MFGT.finish(mContext);
                    } else {
                        CommonUtils.showShortToast(R.string.register_fail_exists);
                        etLoginName.requestFocus();
                    }
                }
            }
            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(error);
                Log.i("main", "onError: " + error);
            }
        });

    }


}
