package cn.ucai.fulicenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.AlbumsBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.views.FlowIndicator;
import cn.ucai.fulicenter.views.SlideAutoLoopView;
import uai.cn.fullcenter.R;

public class GoodsDetailsActivity extends BaseActivity {
    int goodsID;
    @Bind(R.id.backClickArea)
    LinearLayout backClickArea;
    @Bind(R.id.tv_Common_title)
    TextView tvCommonTitle;
    @Bind(R.id.iv_good_share)
    ImageView ivGoodShare;
  /*  @Bind(R.id.iv_good_collect)
    ImageView ivGoodCollect;*/
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

    int goodsId;
    GoodsDetailsActivity mContext;
    boolean isCollect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_details);
        L.e("GoodsDetailsActivity.onCreateView");
        ButterKnife.bind(this);
        Intent intent = getIntent();
        goodsID = intent.getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("detatils", "goodsid=" + goodsID);
        super.onCreate(savedInstanceState);
        mContext = this;
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
    protected void setListener() {

    }

    @Override
    protected void initData() {
        NetDao.downloadGoodsDetail(mContext, goodsID, pageId, pageSize, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                Log.i("main", "onGoodsItemClick: " + goodsID);
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

    //    @Override
//    protected void onResume() {
//        super.onResume();
//        isCollect();
//    }
    @OnClick(R.id.iv_good_share)
    public void onShareclick() {
        showShare();
    }

    @OnClick(R.id.backClickArea)
    public void onBackClick() {
        MFGT.finish(this);
    }

    public void onback() {
        MFGT.finish(this);
    }

    @OnClick(R.id.iv_good_like)
    public void onClick() {
        UserAvatar user = FuLiCenterApplication.getUserAvatar();
        if (user == null) {
            MFGT.gotoLogin(mContext);
        } else {
            if (isCollect) {
                NetDao.deleteCollect(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            isCollect = !isCollect;
                            updateGoodsCoolectStatus();
                            CommonUtils.showLongToast(result.getMsg());
//                            sendStickyBroadcast(new Intent("update_collect").putExtra(I.Collect));
                        }
                    }

                    @Override
                    public void onError(String error) {
                    }
                });
            } else {
                NetDao.addCollect(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            isCollect = !isCollect;
                            updateGoodsCoolectStatus();
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        }

    }

    private void isCollected() {
        UserAvatar user = FuLiCenterApplication.getUserAvatar();
        if (user != null) {
            NetDao.isCollect(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        isCollect = true;
                    } else {
                        isCollect = false;
                    }
                    updateGoodsCoolectStatus();
                }

                @Override
                public void onError(String error) {

                }
            });
        }
        updateGoodsCoolectStatus();
    }

    private void updateGoodsCoolectStatus() {
        if (isCollect) {
            ivGoodLike.setImageResource(R.mipmap.bg_collect_out);
        } else {
            ivGoodLike.setImageResource(R.mipmap.bg_collect_in);

        }
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

}
