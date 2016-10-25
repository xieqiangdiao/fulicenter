package cn.ucai.fulicenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.bean.AlbumsBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.views.FlowIndicator;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.views.SlideAutoLoopView;
import uai.cn.fullcenter.R;

public class GoodsDetailsActivity extends BaseActivity {


    Context context;
    int goodsID;
    @Bind(R.id.backClickArea)
    LinearLayout backClickArea;
    @Bind(R.id.tv_Common_title)
    TextView tvCommonTitle;
    @Bind(R.id.iv_good_share)
    ImageView ivGoodShare;
    @Bind(R.id.iv_good_collect)
    ImageView ivGoodCollect;
    @Bind(R.id.iv_good_like)
    ImageView ivGoodLike;
    @Bind(R.id.iv_good_car)
    ImageView ivGoodCar;
    @Bind(R.id.litile)
    RelativeLayout litile;
    @Bind(R.id.englishname)
    TextView englishname;
    @Bind(R.id.chinaname)
    TextView chinaname;
    @Bind(R.id.salv)
    SlideAutoLoopView salv;
    @Bind(R.id.indicator)
    FlowIndicator indicator;
    @Bind(R.id.layout_image)
    RelativeLayout layoutImage;
    @Bind(R.id.wv_good_brief)
    WebView wvGoodBrief;
    @Bind(R.id.goods_price)
    TextView goods_price;


    int pageId = 1;
    int pageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_goods_details);
        L.e("GoodsDetailsActivity.onCreateView");
        ButterKnife.bind(this);
        Intent intent = getIntent();
        goodsID = intent.getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("detatils", "goodsid=" + goodsID);
        super.onCreate(savedInstanceState);
        context = this;
        if (goodsID == 0) {
            finish();
        }
        initView();
        initData();
        setListener();
    }
    @Override
    protected void initView() {
    }

    @Override
    protected  void setListener() {

    }
    @Override
    protected  void initData() {
        NetDao.downloadGoodsDetail(context, goodsID, pageId, pageSize, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                Log.i("main", "onGoodsItemClick: "+goodsID);
                L.i("details=" + result);
                if (result != null) {
                    showGoodsDetails(result);
                } else {
                   /* finish();*/
                }
            }
            @Override
            public void onError(String error) {
                finish();
                L.i("details,error=" + error);
                CommonUtils.showShortToast(error);
            }
        });
    }

    private void showGoodsDetails(GoodsDetailsBean details) {
        chinaname.setText(details.getGoodsName());
        englishname.setText(details.getGoodsEnglishName());
        goods_price.setText(details.getCurrencyPrice());
        salv.startPlayLoop(indicator, getAlbumImgUrl(details), getAlbumImagCount(details));
        wvGoodBrief.loadDataWithBaseURL(null, details.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
    }

    private int getAlbumImagCount(GoodsDetailsBean details) {
        if (details.getProperties() != null && details.getProperties().length > 0) {
            return details.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumImgUrl(GoodsDetailsBean details) {
        String[] urls = new String[]{};
        if (details.getProperties() != null && details.getProperties().length > 0) {
            AlbumsBean[] albums = details.getProperties()[0].getAlbums();
            urls = new String[albums.length];
            for (int i = 0; i < albums.length; i++) {
                urls[i] = albums[i].getImgUrl();
            }
        }
        return urls;
    }

    @OnClick(R.id.backClickArea)
    public void onBackClick() {
        MFGT.finish(this);
    }

    public void onback() {
        MFGT.finish(this);
    }


    @OnClick({R.id.iv_good_share, R.id.iv_good_collect, R.id.iv_good_like, R.id.iv_good_car, R.id.englishname, R.id.chinaname, R.id.salv, R.id.wv_good_brief,R.id.goods_price})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_good_share:
                break;
            case R.id.iv_good_collect:
                break;
            case R.id.iv_good_like:
                break;
            case R.id.iv_good_car:
                break;
            case R.id.englishname:
                break;
            case R.id.chinaname:
                break;
            case R.id.salv:
                break;
            case R.id.goods_price:
                break;
        }
    }
}
