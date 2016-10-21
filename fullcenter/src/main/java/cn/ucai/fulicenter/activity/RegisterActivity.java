package cn.ucai.fulicenter.activity;

import android.os.Bundle;
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
import cn.ucai.fulicenter.views.DisplayUtils;
import uai.cn.fullcenter.R;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.iv_title)
    ImageView ivTitle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.et_loginName, R.id.etNiChen, R.id.etPasWord, R.id.etCpassWord,R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_loginName:
                break;
            case R.id.etNiChen:
                break;
            case R.id.etPasWord:
                break;
            case R.id.etCpassWord:
                break;
            case R.id.btn_register:
                break;
        }
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

    @OnClick(R.id.btn_register)
    public void onClick() {
    }
}
