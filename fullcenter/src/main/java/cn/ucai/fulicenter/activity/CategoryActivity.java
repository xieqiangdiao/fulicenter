package cn.ucai.fulicenter.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.adapter.GoodAdapter;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.views.CatChildFilterButton;
import cn.ucai.fulicenter.I;
import uai.cn.fullcenter.R;

public class CategoryActivity extends AppCompatActivity {

    CategoryActivity mContext;
    GoodAdapter mAdapter;
    ArrayList<NewGoodsBean> mlist;

    GridLayoutManager glm;

    int pageId = 1;
    int catId;


    @Bind(R.id.backClickArea)
    LinearLayout backClickArea;
    @Bind(R.id.relative)
    TextView relative;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    @Bind(R.id.activity_category_child)
    LinearLayout activityCategoryChild;

    @Bind(R.id.btn_sort_price)
    Button btnSortPrice;
    @Bind(R.id.btn_sort_addtime)
    Button btnSortAddtime;

    boolean addTimeAsc = false;
    boolean PriceAsc = false;
    int sortBy = I.SORT_BY_ADDTIME_DESC;
    @Bind(R.id.btnCatChildFilter)
    CatChildFilterButton btnCatChildFilter;
    String groupName;
    ArrayList<CategoryChildBean> mChildList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        mContext = this;
        mlist = new ArrayList<>();
        mAdapter = new GoodAdapter(mContext, mlist);
        catId = getIntent().getIntExtra(I.CategoryChild.CAT_ID, 0);

        if (catId == 0) {
            finish();
        }
        groupName=getIntent().getStringExtra(I.CategoryGroup.NAME);
        mChildList= (ArrayList<CategoryChildBean>) getIntent().getSerializableExtra(I.CategoryChild.ID);

        initView();
        initData();
        setListener();
    }

    protected void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(this, I.COLUM_NUM);
        rv.setLayoutManager(glm);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        btnCatChildFilter.setText(groupName);

    }

    protected void initData() {
        downloadCategoryGoods(I.ACTION_DOWNLOAD);
        btnCatChildFilter.setOnCatFilterClickListener(groupName,mChildList);
    }


    private void downloadCategoryGoods(final int action) {
        NetDao.downloadCategoryGoods(this, catId, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                Log.i("main", "oncreate: " + catId);
                Log.i("main", result[0].toString());
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(list);
                    } else {
                        mAdapter.addData(list);
                    }
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    }
                } else {
                    mAdapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                Log.i("main", "onError: " + error);
                srl.setRefreshing(false);
                relative.setVisibility(View.GONE);
                mAdapter.setMore(false);
            }
        });
    }

    protected void setListener() {
    }

    @OnClick({R.id.btn_sort_price, R.id.btn_sort_addtime})
    public void onClick(View view) {
        Drawable right;
        switch (view.getId()) {
            case R.id.btn_sort_price:
                if (PriceAsc) {
                    sortBy = I.SORT_BY_ADDTIME_ASC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                } else {
                    sortBy = I.SORT_BY_ADDTIME_DESC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
                btnSortPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                PriceAsc = !PriceAsc;
                break;
            case R.id.btn_sort_addtime:
                if (addTimeAsc) {
                    sortBy = I.SORT_BY_ADDTIME_ASC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                } else {
                    sortBy = I.SORT_BY_ADDTIME_DESC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
                btnSortAddtime.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                addTimeAsc = !addTimeAsc;
                break;
        }
    }

    @OnClick(R.id.btnCatChildFilter)
    public void onClick() {
    }
}
