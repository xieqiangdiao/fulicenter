package cn.ucai.fulicenter.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.views.I;
import uai.cn.fullcenter.R;

/**
 * Created by Administrator on 2016/10/19.
 */
public class BoutiqueAdapter extends Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    boolean isMore;


    public BoutiqueAdapter(Context mContext, ArrayList<BoutiqueBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void initData(ArrayList<BoutiqueBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<BoutiqueBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

     class BoutiqueViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivBoutiqueImg)
        ImageView ivBoutiqueImg;
        @Bind(R.id.tvBoutiquttitle)
        TextView tvBoutiquttitle;
        @Bind(R.id.tvBoutiqueName)
        TextView tvBoutiqueName;
        @Bind(R.id.tvBoutiqueDescription)
        TextView tvBoutiqueDescription;
        @Bind(R.id.layout_boutique_item)
        RelativeLayout layoutBoutiqueItem;


        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layout_boutique_item)
        public void onGoodsItemClick() {
            int catId = (int)layoutBoutiqueItem.getTag();
            MFGT.gotoBoutiqueActivity(mContext, catId);
        }
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
        if (viewType == I.TYPE_FOOTER) {
            holder = new GoodAdapter.FooterViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_footer, parent, false));
        } else {
            holder = new BoutiqueViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_boutique, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GoodAdapter.FooterViewHolder) {
            ((GoodAdapter.FooterViewHolder) holder).tv.setText(getFootext());
        }
        if (holder instanceof BoutiqueViewHolder) {
            BoutiqueViewHolder boutiqueViewHolder = (BoutiqueViewHolder) holder;
            BoutiqueBean boutiqueBean = mList.get(position);
            ImageLoader.downloadImg(mContext, ((BoutiqueViewHolder) holder).ivBoutiqueImg, boutiqueBean.getImageurl());
            ((BoutiqueViewHolder) holder).tvBoutiquttitle.setText(boutiqueBean.getTitle());
            ((BoutiqueViewHolder) holder).tvBoutiqueName.setText(boutiqueBean.getName());
            ((BoutiqueViewHolder) holder).tvBoutiqueDescription.setText(boutiqueBean.getDescription());
            boutiqueViewHolder.layoutBoutiqueItem.setTag(boutiqueBean.getId());
        }
    }

    public String getFootext() {
        if (isMore()) {
            Footext = "加载更多数据";
        } else {
            Footext = "没有更多数据加载";
        }
        return Footext;
    }

    String Footext;


    @Override
    public int getItemCount() {
        return mList == null ? 1 : mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

}



