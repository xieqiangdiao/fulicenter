package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.CollectBean;


import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import uai.cn.fullcenter.R;

/**
 * Created by Administrator on 2016/10/17.
 */
public class CollectionAdapter extends RecyclerView.Adapter {
    static Context mContext;
    List<CollectBean> mgoodlist;
    boolean isMore;

    public CollectionAdapter(Context mContext, List<CollectBean> mList) {
        this.mContext = mContext;
        this.mgoodlist = mList;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case I.TYPE_FOOTER:
                holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
                break;
            case I.TYPE_ITEM:
                holder = new CollectionViewHolder(View.inflate(mContext, R.layout.item_collection, null));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.tv.setText(getFootString());
            return;
        } else {
            CollectionViewHolder colllectionViewHolder = (CollectionViewHolder) holder;
            final CollectBean collectioinBean = mgoodlist.get(position);
            colllectionViewHolder.goodsId.setText(collectioinBean.getGoodsName());
            // goodsViewHolder.ivcolectdel.setImageResource(ivcolectdel.ge);
            ImageLoader.downloadImg(mContext, colllectionViewHolder.iv, collectioinBean.getGoodsThumb());
            /*colllectionViewHolder.goods_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MFGT.gotoGoodsDetailActivity(mContext, collectioinBean.getGoodsId());
                    // collectioinBean.getGoodsId()
                }
            });*/
        }
    }

    @Override
    public int getItemCount() {
        return mgoodlist != null ? mgoodlist.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if ((position == getItemCount() - 1)) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }


    public void initData(ArrayList<CollectBean> list) {
        if (mgoodlist != null) {
            mgoodlist.clear();
        }
        mgoodlist.addAll(list);
        notifyDataSetChanged();
    }

    public int getFootString() {

        return isMore ? R.string.load_more : R.string.no_more;
    }

    public void addData(ArrayList<CollectBean> list) {
        mgoodlist.addAll(list);
        notifyDataSetChanged();
    }


    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv)
        TextView tv;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

     class CollectionViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv)
        ImageView iv;
        @Bind(R.id.goods_id)
        TextView goodsId;
        @Bind(R.id.iv_colect_del)
        ImageView ivcolectdel;
        @Bind(R.id.goods_item)
        RelativeLayout goods_item;

        CollectionViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.goods_item)
        public void onGoosItemClick() {
            CollectBean goods=(CollectBean) goods_item.getTag();
            MFGT.gotoGoodsDetailActivity(mContext,goods.getGoodsId());
        }
        @OnClick(R.id.iv_colect_del)
        public void deleteCollect() {
            final CollectBean goods = (CollectBean) goods_item.getTag();
            String username = FuLiCenterApplication.getUserAvatar().getMuserName();
            NetDao.deleteCollect(mContext, username, goods.getGoodsId(),new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        mgoodlist.remove(goods);
                        notifyDataSetChanged();
                    }else{
                        CommonUtils.showLongToast(result!=null?result.getMsg():
                                mContext.getResources().getString(R.string.delete_collect_fail));
                    }
                }
                @Override
                public void onError(String error) {
                    L.e("error="+error);
                    CommonUtils.showLongToast(mContext.getResources().getString(R.string.delete_collect_fail));
                }
            });

        }

    }
}
