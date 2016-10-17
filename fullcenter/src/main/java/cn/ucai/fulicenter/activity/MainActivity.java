package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.utils.L;
import uai.cn.fullcenter.R;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.Boutique)
    RadioButton Boutique;
    @Bind(R.id.Category)
    RadioButton Category;
    @Bind(R.id.new_Goods)
    RadioButton newGoods;
    @Bind(R.id.Personal)
    RadioButton Personal;
    @Bind(R.id.Cars)
    RadioButton Cars;
    @Bind(R.id.rg_bottom_layout)
    RadioGroup rgBottomLayout;
    @Bind(R.id.tv_id_car_hint)
    TextView tvIdCarHint;
    @Bind(R.id.view)
    View view;

    NewGoodsFragment newGoodsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity onCreate");
    }


    @OnClick({R.id.view, R.id.Boutique, R.id.Category, R.id.new_Goods, R.id.Personal, R.id.Cars})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.Boutique:
                break;
            case R.id.Category:
                break;
            case R.id.new_Goods:
                if(newGoodsFragment==null){
                    newGoodsFragment=new NewGoodsFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment,newGoodsFragment).show(newGoodsFragment).commit();
                }
                break;
            case R.id.Personal:
                break;
            case R.id.Cars:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
