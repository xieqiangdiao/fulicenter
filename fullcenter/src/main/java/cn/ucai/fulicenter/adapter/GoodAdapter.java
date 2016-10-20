package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.activity.GoodsDetailsActivity;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.views.I;
import uai.cn.fullcenter.R;

/**
 * Created by Administrator on 2016/10/17.
 */
public class GoodAdapter extends RecyclerView.Adapter {
    static Context mContext;
    List<NewGoodsBean> mgoodlist;
    boolean isMore;

    int sortBy = I.SORT_BY_ADDTIME_DESC;

    public GoodAdapter(Context mContext, List<NewGoodsBean> mList) {
        this.mContext = mContext;
        this.mgoodlist = mList;
    }

    //排序
    public void setSortBy(int soryBy) {
        this.sortBy = soryBy;
        sortBy();
        notifyDataSetChanged();
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
                holder = new GoodsViewHolder(View.inflate(mContext, R.layout.item_new_goods, null));
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
            GoodsViewHolder goodsViewHolder = (GoodsViewHolder) holder;
            final NewGoodsBean goodsBean = mgoodlist.get(position);
            goodsViewHolder.goodsId.setText(goodsBean.getGoodsName());
            goodsViewHolder.goodsPrice.setText(goodsBean.getCurrencyPrice());
            ImageLoader.downloadImg(mContext, goodsViewHolder.iv, goodsBean.getGoodsThumb());
            goodsViewHolder.goods_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MFGT.gotoGoodsDetailActivity(mContext, goodsBean.getGoodsId());
                }
            });
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


    public void initData(ArrayList<NewGoodsBean> list) {
        if (mgoodlist != null) {
            mgoodlist.clear();
        }
        mgoodlist.addAll(list);
        notifyDataSetChanged();
    }

    public int getFootString() {

        return isMore ? R.string.load_more : R.string.no_more;
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mgoodlist.addAll(list);
        notifyDataSetChanged();
    }

    private void sortBy() {
        Collections.sort(mgoodlist, new Comparator<NewGoodsBean>() {
            @Override
            public int compare(NewGoodsBean left, NewGoodsBean right) {
                int result=0;
                switch (sortBy) {
                    case I.SORT_BY_ADDTIME_ASC:
                        result = (int) (Long.valueOf(left.getAddTime()) - Long.valueOf(right.getAddTime()));
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result = (int) (Long.valueOf(right.getAddTime()) - Long.valueOf(left.getAddTime()));
                        break;
                    case I.SORT_BY_PRICE_ASC:
                        result =getPrice(left.getCurrencyPrice())-getPrice(right.getCurrencyPrice());
                        break;
                    case I.SORT_BY_PRICE_DESC:
                        result =getPrice(right.getCurrencyPrice())-getPrice(left.getCurrencyPrice());
                        break;

                }
                return result;
            }

            private int getPrice(String price) {
                price = price.substring(price.indexOf("¥") + 1);
                return Integer.valueOf(price);
            }
        });
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv)
        TextView tv;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class GoodsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv)
        ImageView iv;
        @Bind(R.id.goods_id)
        TextView goodsId;
        @Bind(R.id.goods_price)
        TextView goodsPrice;
        @Bind(R.id.goods_item)
        LinearLayout goods_item;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
