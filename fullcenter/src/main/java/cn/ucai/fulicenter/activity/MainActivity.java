package cn.ucai.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.fragment.CategoryFragment;
import cn.ucai.fulicenter.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
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
    BoutiqueFragment boutiqueFragment;
    CategoryFragment categoryFragment;

    int index;
    int currentIndex=8;
    Fragment[] mFragment = new Fragment[9];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity onCreate");
        mFragment[0] = new NewGoodsFragment();
        mFragment[1] = new BoutiqueFragment();
        mFragment[2] = new CategoryFragment();
        mFragment[3] = new CategoryFragment();
        mFragment[4] = new CategoryFragment();
    }

    @OnClick({R.id.view, R.id.Boutique, R.id.Category, R.id.new_Goods, R.id.Personal, R.id.Cars})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Boutique:
                index=1;
                break;
            case R.id.Category:
                index=2;
                break;
            case R.id.new_Goods:
                index=0;
                break;
            case R.id.Personal:
                index=4;
                if(FuLiCenterApplication.getUserName()==null){
                    MFGT.gotoLogin(this);
                }
                break;
            case R.id.Cars:
                break;
        }
        setFragment();
    }
//fragment切换优化
    private void setFragment() {
        if (index != currentIndex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (currentIndex<5){
                ft.hide(mFragment[currentIndex]);
            }
            if (!mFragment[index].isAdded()) {
                ft.add(R.id.fragment, mFragment[index]);
            }
            ft.show(mFragment[index]).commit();
        }
        currentIndex = index;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    public void onBackPressed(){
        finish();
    }

}
