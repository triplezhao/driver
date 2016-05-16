package potato.driver.chips.base;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ztw on 2015/8/5.
 */
public class BaseBean {

    public static BaseBean cursor2Bean(Cursor cursor) {
        return null;
    }

    public static BaseBean json2bean(JSONObject json) throws JSONException {
        return null;
    }
    public static ContentValues bean2CV(BaseBean bean) throws JSONException {
        return null;
    }
}
