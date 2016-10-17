package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.utils.L;
import uai.cn.fullcenter.R;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.jinxuan)
    RadioButton jinxuan;
    @Bind(R.id.rb_id_cars)
    RadioButton rbIdCars;
    @Bind(R.id.tv_id_car_hint)
    TextView tvIdCarHint;
    @Bind(R.id.shopping_cart)
    RelativeLayout shoppingCart;
    @Bind(R.id.rg_bottom_layout)
    RadioGroup rgBottomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity onCreate");
    }

    public void onCheckedChange() {

    }

    @OnClick({R.id.iv, R.id.jinxuan, R.id.rb_id_cars, R.id.tv_id_car_hint, R.id.shopping_cart, R.id.rg_bottom_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv:
                break;
            case R.id.jinxuan:
                break;
            case R.id.rb_id_cars:
                break;
            case R.id.tv_id_car_hint:
                break;
            case R.id.shopping_cart:
                break;
            case R.id.rg_bottom_layout:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
