package cn.ucai.fulicenter.activity;

import android.content.Intent;
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
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.views.DisplayUtils;
import uai.cn.fullcenter.R;

public class LoginActivity extends BaseActivity {

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
//                Intent intent=new Intent(this,RegisterActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                break;
        }
    }
}
