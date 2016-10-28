package cn.ucai.fulicenter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.ResultUtils;
import cn.ucai.fulicenter.views.DisplayUtils;
import uai.cn.fullcenter.R;

public class OrderActivity extends BaseActivity {
    private static final String TAG = OrderActivity.class.getSimpleName();
    @Bind(R.id.layout_name)
    LinearLayout layoutName;
    @Bind(R.id.tv_order_name)
    TextView tvOrderName;
    @Bind(R.id.ed_order_name)
    EditText edOrderName;
    @Bind(R.id.layout_order_name)
    RelativeLayout layoutOrderName;
    @Bind(R.id.tv_order_phone)
    TextView tvOrderPhone;
    @Bind(R.id.ed_order_phone)
    EditText edOrderPhone;
    @Bind(R.id.layout_order_phone)
    RelativeLayout layoutOrderPhone;
    @Bind(R.id.tv_order_privince)
    TextView tvOrderPrivince;
    @Bind(R.id.spin_order_province)
    Spinner spinOrderProvince;
    @Bind(R.id.tv_order_province)
    RelativeLayout tvOrderProvince;
    @Bind(R.id.tv_order_stree)
    TextView tvOrderStree;
    @Bind(R.id.ed_order_price)
    EditText edOrderPrice;
    @Bind(R.id.layout_order_stree)
    RelativeLayout layoutOrderStree;
    @Bind(R.id.tv_order_price)
    TextView tvOrderPrice;
    @Bind(R.id.tv_order_buy)
    TextView tvOrderBuy;
    @Bind(R.id.layout_order)
    RelativeLayout layoutOrder;

    OrderActivity mContext;
    UserAvatar user = null;
    String carIds = "";
    ArrayList<CartBean> mList = null;
    String[] ids = new String[]{};
    int rankPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        mContext = this;
        mList = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext, getString(R.string.confiem_order));//确认订单
    }

    @Override
    protected void initData() {
        String carIds = getIntent().getStringExtra(I.Cart.ID);
        user = FuLiCenterApplication.getUserAvatar();
        L.e("main", "ssss");
        if (carIds == null || carIds.equals("")
                || user == null) {
            finish();
        }
        String[] ids = carIds.split(",");
        goOrderList();
    }

    private void goOrderList() {
        NetDao.downCart(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                ArrayList<CartBean> list = ResultUtils.getCartFromJson(s);
                if (list == null || list.size() == 0) {
                    finish();
                } else {

                }
            }
            @Override
            public void onError(String error) {

            }
        });
    }

    private void sumPrice() {
        int rankPrice = 0;
        Log.i("main", "sumPrice: " + mList.size());
        if (mList != null && mList.size() > 0) {
            for (CartBean c : mList) {
                Log.i(TAG, "sumPrice: " + c.isChecked());
                for (String id : ids) {
                    if (id.equals(c.getId())) {
                        rankPrice += getPrice(c.getGoods().getRankPrice()) * c.getCount();
                    }
                }
            }
            tvOrderPrice.setText("合计:¥"+Double.valueOf(rankPrice));
        }

    }

    private int getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.tv_order_buy)
    public void checkOrder() {
        String receiveName = edOrderName.getText().toString();
        if (TextUtils.isEmpty(receiveName)) {
            edOrderName.setError("收货人不能为空");
            edOrderName.requestFocus();
            return;
        }
        String moblie = edOrderPhone.getText().toString();
        if (TextUtils.isEmpty(moblie)) {
            edOrderPhone.setError("手机号码不能为空");
            edOrderPhone.requestFocus();
            return;
        }
        if (!moblie.matches("[\\d]{11}")) {
            edOrderPhone.setError("手机号码格式不能为空");
            edOrderPhone.requestFocus();
            return;
        }
        String area = spinOrderProvince.getSelectedItem().toString();
        if (TextUtils.isEmpty(area)) {
            Toast.makeText(OrderActivity.this, "收货地区不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String address = edOrderPrice.getText().toString();
        if (TextUtils.isEmpty(address)) {
            edOrderPrice.setError("街道地址不能为空");
            edOrderPrice.requestFocus();
            return;
        }
        gotoStatements();
    }

    private void gotoStatements() {
        Log.e(TAG, "rankPrice" + rankPrice);
    }


}
