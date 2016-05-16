package potato.driver.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.potato.library.util.L;

import java.util.ArrayList;
import java.util.List;

import potato.driver.chips.common.DatabaseHelper;
import potato.driver.data.bean.DriverBean;

/**
 * create by freemaker
 */
public class DriverBeanDao {
    public static final String TAG = "DriverBeanDao";
    public static final String TABLE_NAME = "DriverBeanTB";
    public SQLiteOpenHelper mOpenHelper;

    public static class Columns implements BaseColumns {
        public static final String login_id = "login_id";
        public static final String islogin = "islogin";
        public static final String username = "username";
    }

    public String[] allProjection = new String[]{
            Columns._ID,
            Columns.login_id,
            Columns.islogin,
            Columns.username

    };


    public DriverBeanDao(Context context) {
        mOpenHelper = DatabaseHelper.getInstance(context);
        L.d(TAG, "In onCreate method, create the provider: " + this
                + ", and DatabaseHelper: " + mOpenHelper);
    }

    /**
     * Use this static method to create the table
     * It will be called by {@link DatabaseHelper} during first launch time to create DB.
     *
     * @param db
     */
    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + Columns._ID + " integer primary key autoincrement, "
                + Columns.login_id + " text, "
                + Columns.islogin + " text, "
                + Columns.username + " text "
                + ");");
    }

    public long insert(DriverBean bean) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        ContentValues values = DriverBean.bean2CV(bean);
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public void delete(String columnsName, String value) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        String selection = columnsName + " = ?";
        String[] selectionArgs = {value};
        db.delete(TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    public void update(String columnsName, DriverBean bean) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        ContentValues values = DriverBean.bean2CV(bean);
        String selection = columnsName + " = ?";
        String[] selectionArgs = {"id"};

        db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }

    public DriverBean getDriverBeanByKey(String columnsName, String key) {
        DriverBean bean = null;
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        String selection = columnsName + " = ?";
        String[] selectionArgs = {key};
        Cursor c = db.query(
                TABLE_NAME, allProjection, selection, selectionArgs, null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                bean = DriverBean.cursor2Bean(c);
                break;
            }
        }
        if (c != null) {
            c.close();
        }
        db.close();
        return bean;
    }

    public List<DriverBean> getDriverBeanList() {
        List<DriverBean> list = new ArrayList<DriverBean>();
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = db.query(
                TABLE_NAME, allProjection, null, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            DriverBean bean = null;
            while (c.moveToNext()) {
                bean = DriverBean.cursor2Bean(c);
                list.add(bean);
            }
        }
        if (c != null) {
            c.close();
        }
        db.close();
        return list;
    }


    public DriverBean getLoginedDiver() {
        DriverBean bean = null;
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        String selection = Columns.islogin + " = ?";
        String[] selectionArgs = {"1"};
        Cursor c = db.query(
                TABLE_NAME, allProjection, selection, selectionArgs, null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                bean = DriverBean.cursor2Bean(c);
                break;
            }
        }
        if (c != null) {
            c.close();
        }
        db.close();
        return bean;
    }

    public boolean isLogin() {
        return (getLoginedDiver() != null);
    }

    public void logoutDiver() {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        String selection = Columns.islogin + " = ?";
        String[] selectionArgs = {"1"};

        ContentValues values = new ContentValues();
        values.put(Columns.islogin, "0");

        db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }

}

