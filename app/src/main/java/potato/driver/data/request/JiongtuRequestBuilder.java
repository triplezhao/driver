package potato.driver.data.request;

import com.potato.library.net.DefaultRequest;
import com.potato.library.net.RequestWraper;

import java.util.HashMap;

import potato.driver.chips.base.BaseRequestBuilder;

public class JiongtuRequestBuilder extends BaseRequestBuilder {

    /**
     * 秀场-手机首页列表
     */
    // 手机首页列表 http://show.17173.com/m/index.action?uct=2&hct=1
    public static RequestWraper getShowIndexRequest(String uct, String hct) {
        RequestWraper request = new DefaultRequest();
        request.reqMethod = RequestWraper.REQ_METHOD_GET;
        request.url = JiongtuRequestUrls.URL_TEST;

        request.params = new HashMap<String, Object>();
        addParam(request.params, "uct", uct);
        addParam(request.params, "hct", hct);
        return request;
    }


    /**
     * 囧图图册列表请求
     *
     * @param sectionId     囧图栏目ID
     * @param minPublicDate 本地已有数据的最早更新时间
     */
    public static RequestWraper getAlbumListRequest(long sectionId, long minPublicDate) {
        RequestWraper req = new DefaultRequest();
        req.url = JiongtuRequestUrls.URL_JIONG_ALBUM_LIST;
        req.reqMethod = RequestWraper.REQ_METHOD_GET;
        req.params = new HashMap<String, Object>();

        addParam(req.params, "platCode", "ANDROID");
        if (minPublicDate > 0) {
            addParam(req.params, "maxPublicDate", minPublicDate);
        }
        addParam(req.params, "sections", sectionId);
        return req;
    }

    /**
     * 囧图栏目列表请求
     */
    public static RequestWraper getSectionListRequest() {
        RequestWraper req = new DefaultRequest();
        req.url = JiongtuRequestUrls.URL_JIONG_SECTION_LIST;
        req.reqMethod = RequestWraper.REQ_METHOD_GET;
        req.params = new HashMap<String, Object>();

        addParam(req.params, "platCode", "ANDROID");
        req.params.put("pageSize", -1);
        return req;
    }

    /**
     * 囧图图册下图片列表请求
     *
     * @param albumId 图册ID
     */
    public static RequestWraper getPhotoListRequest(String albumId) {
        RequestWraper req = new DefaultRequest();
        req.url = JiongtuRequestUrls.URL_JIONG_PHOTO_LIST;
        req.reqMethod = RequestWraper.REQ_METHOD_GET;
        req.params = new HashMap<String, Object>();

        addParam(req.params, "platCode", "ANDROID");
        addParam(req.params, "photosID", albumId);
        return req;
    }
}
