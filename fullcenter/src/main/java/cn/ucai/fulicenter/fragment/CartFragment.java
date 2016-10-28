package cn.ucai.fulicenter.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.activity.MainActivity;
import cn.ucai.fulicenter.adapter.CartAdapter;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.views.SpaceItemDecoration;
import uai.cn.fullcenter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private static final String TAG = CartFragment.class.getSimpleName();
    MainActivity mContext;
    CartAdapter mAdapter;
    ArrayList<CartBean> mList;

    GridLayoutManager glm;

    int pageId = 1;
    @Bind(R.id.relative)
    TextView relative;
    @Bind(R.id.rv)
    RecyclerView rv;

    UserAvatar user = FuLiCenterApplication.getUserAvatar();
    @Bind(R.id.tv_cart_sum_price)
    TextView tvCartSumPrice;
    @Bind(R.id.tv_cart_save_price)
    TextView tvCartSavePrice;
    @Bind(R.id.tv_cart_buy)
    TextView tvCartBuy;
    @Bind(R.id.layout_cart)
    RelativeLayout layoutCart;
    @Bind(R.id.tv_nothing)
    TextView tvNothing;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    updateCartReceiver mReceiver;
    int orderprice = 0;
    String cartIds = null;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        L.e("BoutiqueFragment.onCreateView");
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, layout);
        initView();
        initData();
        setListener();
        return layout;
    }

    //    @Override
    protected void setListener() {
        setPullDownListener();

        IntentFilter filter = new IntentFilter();
        filter.addAction(I.BROADCAST_UPDATA_CART);
        mReceiver = new updateCartReceiver();
        mContext.registerReceiver(mReceiver, filter);
    }

    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                rv.setVisibility(View.VISIBLE);
                downloadCart();
                setCartLsout(true);

            }
        });
    }

    //    @Override
    protected void initData() {
        downloadCart();
    }

    private void downloadCart() {
        Log.i("a", "downloadCart: ");
        UserAvatar user = FuLiCenterApplication.getUserAvatar();
        if (user != null) {
            NetDao.downloadCart(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    L.e(TAG, "result=" + result);
                    srl.setRefreshing(false);
                    relative.setVisibility(View.GONE);
                    srl.setEnabled(true);
                    Log.i("main", result.toString());
                    if (result != null && result.length > 0) {
                        ArrayList<CartBean> list = ConvertUtils.array2List(result);
                        mList = list;
                        mAdapter.initData(list);
                    }
                }

                @Override
                public void onError(String error) {
                    srl.setRefreshing(false);
                    relative.setVisibility(View.GONE);
                    CommonUtils.showLongToast(error);
                    L.e("error:" + error);
                }
            });
        }
    }

    //    @Override
    protected void initView() {
        mList = new ArrayList<>();
        mContext = (MainActivity) getContext();
        mAdapter = new CartAdapter(mContext, mList);
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
        setCartLsout(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setCartLsout(boolean hasCart) {

        //结算
        layoutCart.setVisibility(hasCart ? View.VISIBLE : View.GONE);
        //空空如野
        tvNothing.setVisibility(hasCart ? View.GONE : View.VISIBLE);
        rv.setVisibility(hasCart ? View.VISIBLE : View.GONE);

    }
    @OnClick(R.id.tv_cart_buy)
    public void onClick() {
        if (cartIds != null&&cartIds.equals("")&&cartIds.length()>0) {

            MFGT.gotoBuy(mContext, cartIds);
        } else {
            CommonUtils.showLongToast(R.string.order_nothing);
        }
    }

    private void sumPrice() {
        cartIds = null;
        int sumPrice = 0;
        int rankPrice = 0;
        Log.i(TAG, "sumPrice: " + mList.size());
        if (mList != null && mList.size() > 0) {
            for (CartBean c : mList) {
                Log.i(TAG, "sumPrice: " + c.isChecked());
                if (c.isChecked()) {
                    cartIds += c.getId() + ",";
                    sumPrice += getPrice(c.getGoods().getCurrencyPrice()) * c.getCount();
                    rankPrice += getPrice(c.getGoods().getRankPrice()) * c.getCount();
                }
            }
            tvCartSumPrice.setText("合计：¥" + Double.valueOf(sumPrice));
            tvCartSavePrice.setText("节省：¥" + Double.valueOf(sumPrice - rankPrice));
        } else {
            setCartLsout(true);
            tvCartSumPrice.setText("合计:¥0");
            tvCartSavePrice.setText("节省：¥0");
        }

    }

    private int getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            mContext.unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        L.e(TAG, "onResume......");
    }

    //广播
    class updateCartReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            L.e(TAG, "updateCartReceiver");
            sumPrice();
            setCartLsout(mList != null && mList.size() > 0);
        }
    }
}
