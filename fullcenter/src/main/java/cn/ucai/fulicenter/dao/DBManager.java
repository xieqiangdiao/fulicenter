package cn.ucai.fulicenter.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.ucai.fulicenter.bean.UserAvatar;

/**
 * Created by Administrator on 2016/10/24.
 */

//逻辑
public class DBManager {
    private static DBManager dbMgr = new DBManager();
    private DBOpenHelper dbHelper;

    public static synchronized DBManager getInstance() {
        return dbMgr;
    }

    void onInit(Context context) {
        dbHelper = new DBOpenHelper(context);
    }

    public synchronized void closeDB() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public synchronized boolean saveUser(UserAvatar userAvatar) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserDao.TABLE_COLUMN_NAME,userAvatar.getMuserName() );
        values.put(UserDao.TABLE_COLUMN_NICK, userAvatar.getMuserNick());
        values.put(UserDao.TABLE_COLUMN_AVATAR_ID,userAvatar.getMavatarId());
        values.put(UserDao.TABLE_COLUMN_AVATAR_TYPE,userAvatar.getMavatarType() );
        values.put(UserDao.TABLE_COLUMN_AVATAR_PATH,userAvatar.getMavatarPath() );
        values.put(UserDao.TABLE_COLUMN_SUFFIX, userAvatar.getMavatarSuffix());
        values.put(UserDao.TABLE_COLUMN_LASTUPDATE_TIME,userAvatar.getMavatarLastUpdateTime() );
        if (db.isOpen()) {
            return db.replace(UserDao.TABLE_USER_NAME, null, values) != -1;
        }
        return false;
    }

    public synchronized UserAvatar getUser(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from " + UserDao.TABLE_USER_NAME + " where "
                + UserDao.TABLE_COLUMN_NAME + " =?";
        UserAvatar user = null;
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        if (cursor.moveToNext()) {
            user = new UserAvatar();
            user.setMuserName(username);
            user.setMuserNick(cursor.getString(cursor.getColumnIndex(UserDao.TABLE_COLUMN_NICK)));
            user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.TABLE_COLUMN_AVATAR_ID)));
            user.setMavatarType(cursor.getInt(cursor.getColumnIndex(UserDao.TABLE_COLUMN_AVATAR_TYPE)));
            user.setMavatarPath(cursor.getString(cursor.getColumnIndex(UserDao.TABLE_COLUMN_AVATAR_PATH)));
            user.setMavatarSuffix(cursor.getString(cursor.getColumnIndex(UserDao.TABLE_COLUMN_SUFFIX)));
            user.setMavatarLastUpdateTime(cursor.getString(cursor.getColumnIndex(UserDao.TABLE_COLUMN_LASTUPDATE_TIME)));
        }
        return user;
    }

    public synchronized boolean updateUser(UserAvatar user) {
        int result=-1;
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String sql=UserDao.TABLE_USER_NAME+"=?";
        ContentValues values=new ContentValues();
        values.put(UserDao.TABLE_COLUMN_NICK, user.getMuserNick() );
        if(db.isOpen()){
           result=db.update(UserDao.TABLE_USER_NAME,values,sql,new String[]{user.getMuserName()});

        }
        return result>0;
    }
}
