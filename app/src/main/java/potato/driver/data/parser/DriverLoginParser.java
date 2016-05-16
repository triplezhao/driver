package potato.driver.data.parser;

import org.json.JSONObject;

import potato.driver.data.bean.DriverBean;

public class DriverLoginParser extends DriverBaseParser {

    public DriverBean bean = null;

    public DriverLoginParser(String jsonStr) {
        super(jsonStr);
        try {
            JSONObject data = root.optJSONObject("data");
            bean = DriverBean.json2Bean(data);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
