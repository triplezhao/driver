package potato.driver.data.request;

import android.text.TextUtils;

import com.potato.library.net.DefaultRequest;
import com.potato.library.net.RequestWraper;

import java.util.HashMap;

import potato.driver.chips.base.BaseRequestBuilder;

public class DriverRequestBuilder extends BaseRequestBuilder {

    /**
     * http://api.test.ifsee.cn/?g=v1&c=user&a=login&username=test&passport=test
     * <p/>
     * http://api.test.ifsee.cn/?g=v1&c=user&a=location&login_id=111&latitude=39.908484&longitude=116.205238
     */
    public static RequestWraper login(String username, String passport) {
        RequestWraper req = new DefaultRequest();
        req.url = DriverRequestUrls.login;
        req.reqMethod = RequestWraper.REQ_METHOD_GET;
        req.params = new HashMap<String, Object>();
        addParam(req.params, "g", "v1");
        addParam(req.params, "c", "user");
        addParam(req.params, "a", "login");

        if (!TextUtils.isEmpty(username)) {
            addParam(req.params, "username", username);
        }
        if (!TextUtils.isEmpty(passport)) {
            addParam(req.params, "passport", passport);
        }

        return req;
    }

    public static RequestWraper location(String login_id, String latitude, String longitude) {
        RequestWraper req = new DefaultRequest();
        req.url = DriverRequestUrls.login;
        req.reqMethod = RequestWraper.REQ_METHOD_GET;
        req.params = new HashMap<String, Object>();
        addParam(req.params, "g", "v1");
        addParam(req.params, "c", "user");
        addParam(req.params, "a", "location");

        if (!TextUtils.isEmpty(latitude)) {
            addParam(req.params, "latitude", latitude);
        }
        if (!TextUtils.isEmpty(longitude)) {
            addParam(req.params, "longitude", longitude);
        }
        if (!TextUtils.isEmpty(login_id)) {
            addParam(req.params, "login_id", login_id);
        }

        return req;
    }

}
