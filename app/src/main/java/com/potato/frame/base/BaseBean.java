package com.potato.frame.base;

import android.database.Cursor;

import com.potato.demo.youku.data.bean.YKVideo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ztw on 2015/8/5.
 */
public class BaseBean {
    public static BaseBean creatFromJson(String json) {
        return null;
    }

    public static YKVideo createFromCursor(Cursor cursor) {
        return null;
    }

    public static YKVideo createFromJSON(JSONObject json) throws JSONException {
        return null;
    }
}
