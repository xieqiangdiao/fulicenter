package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.adapter.GoodAdapter;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.views.SpaceItemDecoration;
import uai.cn.fullcenter.R;

public class BoutiqueChildActivity extends AppCompatActivity {
    GoodAdapter mAdapter;
    ArrayList<NewGoodsBean> mlist;

    GridLayoutManager glm;

    int pageId = 1;
    int catId;
    @Bind(R.id.relative)
    TextView relative;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.boutiqueChildsrl)
    SwipeRefreshLayout boutiqueChildsrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_boutique_child);
        ButterKnife.bind(this);
        catId = getIntent().getIntExtra(I.Boutique.CAT_ID, 0);
        if (catId == 0) {
            finish();
        }
        mlist = new ArrayList<>();
        mAdapter = new GoodAdapter(this, mlist);
        super.onCreate(savedInstanceState);
        Log.i("main", "onCreate: "+catId);
        initView();
        initData();
        setListener();
    }

    protected void setListener() {
        setPullUpListener();
        setPullDownListener();
    }

    private void setPullDownListener() {
        boutiqueChildsrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageId = 1;
                boutiqueChildsrl.setRefreshing(true);
                boutiqueChildsrl.setEnabled(true);
                rv.setVisibility(View.VISIBLE);
                mAdapter.setMore(false);
                downloadNewGoods(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void setPullUpListener() {
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPostion = glm.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastPostion == mAdapter.getItemCount() - 1
                        && mAdapter.isMore()) {
                    pageId++;
                    downloadNewGoods(I.ACTION_PULL_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPostion = glm.findFirstCompletelyVisibleItemPosition();
                boutiqueChildsrl.setEnabled(firstPostion == 0);
            }
        });
    }

    protected void initView() {
        boutiqueChildsrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(this, I.COLUM_NUM);
        rv.setLayoutManager(glm);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new SpaceItemDecoration(12));
    }

    protected void initData() {
        downloadNewGoods(I.ACTION_DOWNLOAD);
    }

    private void downloadNewGoods(final int action) {
        NetDao.downloadNewGoods(this, pageId,catId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {

            @Override
            public void onSuccess(NewGoodsBean[] result) {
                Log.i("main", result.toString());
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
                boutiqueChildsrl.setRefreshing(false);
                relative.setVisibility(View.GONE);
                mAdapter.setMore(false);
            }
        });
    }


}
