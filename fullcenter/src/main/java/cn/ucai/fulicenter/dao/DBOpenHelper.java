package cn.ucai.fulicenter.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.ucai.fulicenter.I;

/**
 * Created by Administrator on 2016/10/24.
 */
//对具体数据操作
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_USER_TABLE = " CREATE TABLE "
            + UserDao.TABLE_USER_NAME + " ( "
            + UserDao.TABLE_COLUMN_NAME + " TEXT PRIMARY KEY, "
            + UserDao.TABLE_COLUMN_NICK + " TEXT, "
            + UserDao.TABLE_COLUMN_AVATAR_ID + " INTEGER, "
            + UserDao.TABLE_COLUMN_AVATAR_TYPE + " INTEGER, "
            + UserDao.TABLE_COLUMN_AVATAR_PATH + " INTEGER, "
            + UserDao.TABLE_COLUMN_SUFFIX + " TEXT, "
            + UserDao.TABLE_COLUMN_LASTUPDATE_TIME + " TEXT);";
    private static DBOpenHelper instance;

    public DBOpenHelper(Context context/*, String name, SQLiteDatabase.CursorFactory factory, int version*/) {
        super(context, getUserDatabaseName(), null, DATABASE_VERSION);
    }

    public static String getUserDatabaseName() {
        return I.User.TABLE_NAME + "_demo.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void closeDb() {
        if (instance != null) {
            instance.close();
            instance = null;
        }
    }
}
