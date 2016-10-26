package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.adapter.CollectionAdapter;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.views.DisplayUtils;
import cn.ucai.fulicenter.views.SpaceItemDecoration;
import uai.cn.fullcenter.R;

public class CollectionActivity extends BaseActivity {
    CollectionActivity mContext;
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
    @Bind(R.id.activity_collects)
    LinearLayout activityCollects;

    CollectionAdapter mAdapter;
    ArrayList<CollectBean> mList;

    GridLayoutManager glm;
    SwipeRefreshLayout mSrl;
    int pageId = 1;
    UserAvatar user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        mContext = this;
        mList = new ArrayList<>();
        mAdapter = new CollectionAdapter(mContext, mList);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext, getResources().getString(R.string.collte_title));
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
        rv.addItemDecoration(new SpaceItemDecoration(12));
    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUserAvatar();
        if(user==null){
            finish();
        }
        downloadCollects(I.ACTION_DOWNLOAD);
    }

    private void downloadCollects(final int action) {

        NetDao.downloadCollects(this, user.getMuserName(), pageId, new OkHttpUtils.OnCompleteListener<CollectBean[]>() {

            @Override
            public void onSuccess(CollectBean[] result) {
                Log.i("main", result.toString());
                mSrl.setRefreshing(false);
                relative.setVisibility(View.GONE);
                mAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    ArrayList<CollectBean> list = ConvertUtils.array2List(result);
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
                srl.setRefreshing(false);
                relative.setVisibility(View.GONE);
                mAdapter.setMore(false);
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.relative, R.id.rv, R.id.srl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative:
                break;
            case R.id.rv:
                break;
            case R.id.srl:
                break;
        }
    }
}
