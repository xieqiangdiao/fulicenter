package cn.ucai.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ucai.fulicenter.utils.L;

/**
 * Created by Administrator on 2016/10/19.
 */
public abstract  class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.e("BaseFragment.onCreateView");
        initView();
        initData();
        setListener();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    protected abstract  void initView();
    protected abstract  void initData();
    protected abstract  void setListener();

}
