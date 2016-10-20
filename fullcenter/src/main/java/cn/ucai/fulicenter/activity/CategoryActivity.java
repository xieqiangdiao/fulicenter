package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
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
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.views.I;
import uai.cn.fullcenter.R;

public class CategoryActivity extends AppCompatActivity {

    CategoryActivity mContext;
    GoodAdapter mAdapter;
    ArrayList<NewGoodsBean> mlist;

    GridLayoutManager glm;

    int pageId = 1;
    int catId;

    boolean addTimeAsc = false;
    boolean priceAsc = false;
    int sortBy = I.SORT_BY_ADDTIME_DESC;

    @Bind(R.id.backClickArea)
    LinearLayout backClickArea;
    @Bind(R.id.tv_Common_title)
    TextView tvCommonTitle;
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

//    @Override
//    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        setContentView(R.layout.activity_category);
//        ButterKnife.bind(this);
//        mContext = this;
//        mlist = new ArrayList<>();
//        mAdapter = new GoodAdapter(mContext, mlist);
//        catId = getIntent().getIntExtra(I.CategoryChild.CAT_ID, 0);
//        if (catId == 0) {
//            finish();
//        }
//        super.onCreate(savedInstanceState, persistentState);
//    }
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
    }
    protected void initData() {
        downloadCategoryGoods(I.ACTION_DOWNLOAD);
    }


    private void downloadCategoryGoods(final int action) {
        NetDao.downloadCategoryGoods(this, catId,pageId,  new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                Log.i("main", "oncreate: "+catId);
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
                Log.i("main", "onError: "+error);
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
        switch (view.getId()) {
            case R.id.btn_sort_price:
                if(priceAsc) {
                    sortBy = I.SORT_BY_ADDTIME_ASC;
                }else{
                    sortBy=I.SORT_BY_ADDTIME_DESC;
                }
                priceAsc=!priceAsc;
                break;
            case R.id.btn_sort_addtime:
                if(addTimeAsc){
                   sortBy=I.SORT_BY_ADDTIME_ASC;
                }else {
                    sortBy=I.SORT_BY_ADDTIME_DESC;
                }
                addTimeAsc=!addTimeAsc;
                break;
        }
    }
}
