package cn.ucai.fulicenter.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.activity.MainActivity;
import cn.ucai.fulicenter.adapter.GoodAdapter;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.views.SpaceItemDecoration;
import uai.cn.fullcenter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends BaseFragment {

    MainActivity mContext;
    GoodAdapter mAdapter;
    ArrayList<NewGoodsBean> mlist;

    GridLayoutManager glm;

    int pageId = 1;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    @Bind(R.id.relative)
    TextView relative;
    @Bind(R.id.rv)
    RecyclerView rv;

    public NewGoodsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        L.e("NewGoodsFragment.onCreateView");
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        mlist = new ArrayList<>();
        mAdapter = new GoodAdapter(mContext, mlist);
        super.onCreateView(inflater, container, savedInstanceState);
      /*  initView();
        initData();
        setListener();*/
        return layout;
    }

    @Override
    protected void setListener() {
        setPullUpListener();
        setPullDownListener();
    }

    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageId = 1;
                srl.setRefreshing(true);
                srl.setEnabled(true);
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
                srl.setEnabled(firstPostion == 0);
            }
        });
    }

    private void downloadNewGoods(final int action) {
        NetDao.downloadNewGoods(mContext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                srl.setRefreshing(false);
                relative.setVisibility(View.GONE);
                mAdapter.setMore(true);
                L.i("result=" + result);
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    mAdapter.initData(list);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(list);
                    } else {
                        mAdapter.addData(list);
                    }
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    } else {
                        mAdapter.setMore(true);
                    }
                }
            }

            @Override
            public void onError(String error) {
                srl.setRefreshing(false);
                relative.setVisibility(View.GONE);
                CommonUtils.showShortToast(error);
                L.e("error:" + error);
            }
        });
    }

    //下载数据
    @Override
    protected  void initData() {
        downloadNewGoods(I.ACTION_DOWNLOAD);
    }

    @Override
    protected  void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(mContext, I.COLUM_NUM);
        rv.setLayoutManager(glm);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new SpaceItemDecoration(12));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
