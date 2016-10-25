package cn.ucai.fulicenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.activity.BoutiqueChildActivity;
import cn.ucai.fulicenter.activity.CategoryActivity;
import cn.ucai.fulicenter.activity.GoodsDetailsActivity;
import cn.ucai.fulicenter.activity.LoginActivity;
import cn.ucai.fulicenter.activity.MainActivity;
import cn.ucai.fulicenter.activity.ModifiedNickNameActivity;
import cn.ucai.fulicenter.activity.PersonDetailsActivity;
import cn.ucai.fulicenter.activity.RegisterActivity;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import uai.cn.fullcenter.R;


public class MFGT {
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void gotoMainActivity(Activity context) {
        startActivity(context, MainActivity.class);
    }

    public static void startActivity(Activity context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context,cls);
        startActivity(context, intent);
    }

    public static void gotoGoodsDetailActivity(Context context, int catid) {
        Intent intent = new Intent();
        intent.setClass(context, GoodsDetailsActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, catid);
        startActivity(context, intent);
    }

    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoBoutiqueActivity(Context context, int catId) {
        Intent intent = new Intent();
        intent.setClass(context, BoutiqueChildActivity.class);
        intent.putExtra(I.Boutique.CAT_ID, catId);
        startActivity(context, intent);
    }

    public static void gotoCategoryChildActivity(Context context, int catId, String groupName, ArrayList<CategoryChildBean> list) {
        Intent intent = new Intent();
        intent.setClass(context, CategoryActivity.class);
        intent.putExtra(I.CategoryChild.CAT_ID, catId);
        intent.putExtra(I.CategoryChild.NAME, groupName);
        intent.putExtra(I.CategoryChild.ID, list);
        startActivity(context, intent);
    }

    public static void gotoLogin(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        startActivity(context, LoginActivity.class);
    }

    public static void gotoRegister(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, RegisterActivity.class);
        context.startActivityForResult(intent,I.REQUEST_COOE_REQUEST);
        startActivity(context, RegisterActivity.class);
    }
    public static void startActivityForResult(Activity context,Intent intent,int requestCode){
        context.startActivityForResult(intent,requestCode);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    public static  void gotoLoginActiviy(Activity context){
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivityForResult(intent,I.REQUEST_COOE_LOGIN);

    }
    public static  void loginGotoActiviy(Activity context){
        Intent intent=new Intent();
        intent.setClass(context,MainActivity.class);
        context.setResult(Activity.RESULT_OK,intent);
    }
public static void gotoSettings(Activity context){
    startActivity(context, PersonDetailsActivity.class);
}
    public static void gotoUpdateNick(Activity context){
        startActivityForResult(context, new Intent(context, ModifiedNickNameActivity.class),I.REQUEST_CODE_NICK);
    }
}
