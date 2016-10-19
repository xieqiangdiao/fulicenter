package cn.ucai.fulicenter.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.activity.MainActivity;
import cn.ucai.fulicenter.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.views.I;
import cn.ucai.fulicenter.views.SpaceItemDecoration;
import uai.cn.fullcenter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends BaseFragment {
    MainActivity mContext;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> mList;

    GridLayoutManager glm;

    int pageId = 1;
    @Bind(R.id.relative)
    TextView relative;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.boutiquesrl)
    SwipeRefreshLayout srl;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        L.e("BoutiqueFragment.onCreateView");
        View layout = inflater.inflate(R.layout.fragment_boutique, container, false);
        ButterKnife.bind(this, layout);
        mList = new ArrayList<>();
        mContext = (MainActivity) getContext();
        mAdapter = new BoutiqueAdapter(mContext, mList);
        /*initView();
        initData();
        setListener();*/
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }
    @Override
    protected  void setListener() {
        setPullUpListener();
        setPullDownListener();
    }

    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageId=1;
                srl.setRefreshing(true);
                srl.setEnabled(true);
                rv.setVisibility(View.VISIBLE);
                mAdapter.setMore(false);
                downloadBoutique(I.ACTION_PULL_DOWN);
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
                        && lastPostion ==mAdapter.getItemCount() - 1
                        && mAdapter.isMore()) {
                    pageId++;
                    downloadBoutique(I.ACTION_PULL_UP);

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPostion=glm.findFirstCompletelyVisibleItemPosition();
                srl.setEnabled(firstPostion==0);
            }
        });
    }

    @Override
    protected void initData() {
        downloadBoutique(I.ACTION_DOWNLOAD);
    }

    private void downloadBoutique(final int action) {
        NetDao.downloadBuotique(mContext, new OkHttpUtils.OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                Log.i("main",result.toString());
                if (result != null && result.length > 0) {
                    ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(list);
                    }else{
                        mAdapter.addData(list);
                    }
                    if (list.size()<I.PAGE_SIZE_DEFAULT) {
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
    protected  void initView() {
      srl.setColorSchemeColors(
              getResources().getColor(R.color.google_blue),
              getResources().getColor(R.color.google_green),
              getResources().getColor(R.color.google_red),
              getResources().getColor(R.color.google_yellow)
      );
        glm = new GridLayoutManager(mContext, 1);
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
