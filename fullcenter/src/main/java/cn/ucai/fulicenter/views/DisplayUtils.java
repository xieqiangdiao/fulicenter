package cn.ucai.fulicenter.views;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import cn.ucai.fulicenter.utils.MFGT;
import uai.cn.fullcenter.R;


/**
 * Created by clawpo on 16/8/3.
 */
public class DisplayUtils {
    public static void initBack(final Activity activity) {
        activity.findViewById(R.id.backClickArea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   activity.finish();
                MFGT.finish(activity);
            }
        });
    }

    public static void initBackWithTitle(final Activity activity, final String title) {
        initBack(activity);
        ((TextView) activity.findViewById(R.id.tv_Common_title)).setText(title);
    }
}
