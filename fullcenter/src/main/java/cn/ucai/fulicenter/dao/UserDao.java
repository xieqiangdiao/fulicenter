package cn.ucai.fulicenter.dao;

import android.content.Context;

import cn.ucai.fulicenter.bean.UserAvatar;


/**
 * Created by Administrator on 2016/10/24.
 */
public class UserDao {
    public static final String TABLE_USER_NAME = "t_superwechat_user";
    public static final String TABLE_COLUMN_NAME = "m_user_name";
    public static final String TABLE_COLUMN_NICK = "m_user_nick";
    public static final String TABLE_COLUMN_AVATAR_ID = "m_avatar_user_id";
    public static final String TABLE_COLUMN_AVATAR_TYPE = "m_avatar_type";
    public static final String TABLE_COLUMN_AVATAR_PATH = "m_avatar_path";
    public static final String TABLE_COLUMN_SUFFIX = "m_user_avatar_suffix";
    public static final String TABLE_COLUMN_LASTUPDATE_TIME = "m_user_avatar_lastupdate_time";


    public UserDao(Context context) {
        DBManager.getInstance().onInit(context);
    }

    public boolean saveUser(UserAvatar user) {
        return DBManager.getInstance().saveUser(user);
    }

    public UserAvatar getUser(String username) {

        return DBManager.getInstance().getUser(username);
    }

    public boolean updateUser(UserAvatar user) {

        return DBManager.getInstance().updateUser(user);
    }
}
