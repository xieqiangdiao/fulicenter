package cn.ucai.fulicenter.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.activity.LoginActivity;
import cn.ucai.fulicenter.activity.MainActivity;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;
import uai.cn.fullcenter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment {
    private static final String TAG = LoginActivity.class.getSimpleName();
    MainActivity mContext;
    @Bind(R.id.tv_layout_one)
    TextView tvLayoutOne;
    @Bind(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.center_user_info)
    LinearLayout centerUserInfo;
    @Bind(R.id.tv_user_fist)
    TextView tvUserFist;
    @Bind(R.id.relative_layout_text1)
    LinearLayout relativeLayoutText1;
    @Bind(R.id.iv_card_coupon1)
    ImageView ivCardCoupon1;
    @Bind(R.id.layout_image_one)
    LinearLayout layoutImageOne;
    @Bind(R.id.iv_card_coupon2)
    ImageView ivCardCoupon2;
    @Bind(R.id.layout_image_two)
    LinearLayout layoutImageTwo;
    @Bind(R.id.iv_card_coupon3)
    ImageView ivCardCoupon3;
    @Bind(R.id.tv_laout_one)
    TextView tvLaoutOne;
    @Bind(R.id.layout_image_three)
    LinearLayout layoutImageThree;
    UserAvatar user = null;


    public PersonalFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal, container, false);
        mContext = (MainActivity) getContext();

        ButterKnife.bind(this, layout);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {
//        String userName = FuLiCenterApplication.getUserName();
//        UserAvatar userAvatar = FuLiCenterApplication.getUserAvatar();
//        if ( userAvatar== null) {
//            MFGT.gotoLoginActiviy(getActivity());
//        }
//        else tvUserName.setText(userAvatar.getMuserNick());

    }

    @Override
    protected void initData() {
        UserAvatar user = FuLiCenterApplication.getUserAvatar();
        if (user != null) {
            tvUserName.setText(user.getMuserNick());
            if (user.getMavatarSuffix() != null) {
                ImageLoader.downloadAvatar(ImageLoader.getAvatarUrl(user), mContext, ivUserAvatar);
            }
        }
        else {
            MFGT.gotoLoginActiviy(getActivity());
        }
    }

//        UserAvatar userAvatar = FuLiCenterApplication.getUserAvatar();
//        if (userAvatar == null) {
//            MFGT.gotoLogin(mContext);
//        } else {
//            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(userAvatar), mContext, ivUserAvatar);
//            tvUserName.setText(userAvatar.getMuserNick());
//        }


    @Override
    protected void setListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_user_avatar, R.id.tv_user_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_avatar:
                break;
            case R.id.tv_user_name:
                break;
        }
    }

    @OnClick(R.id.tv_layout_one)
    public void onClick() {
        UserAvatar avatar = FuLiCenterApplication.getUserAvatar();
        MFGT.gotoSettings(mContext);
    }
}
