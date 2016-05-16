package potato.driver.data.parser;

import potato.driver.data.bean.YKVideo;

import org.json.JSONArray;

public class YKVideosByUserParser extends YKBaseParser {

    public String last_item;

    public YKVideosByUserParser(String jsonStr) {
        super(jsonStr);
        try {
            total = root.optString("total");
            page = root.optString("page");
            count = root.optString("count");
            last_item = root.optString("last_item");
            JSONArray array = root.getJSONArray("videos");
            list = YKVideo.createFromJSONArray(array);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
